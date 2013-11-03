package syg_package01;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import syg_Obliczenia.LiczbaZespolona;
import syg_Obliczenia.Punkt;
import syg_Szablony.PanelObslugi.zwracana_wartosc;
import syg_package01.Filtr.filtr_okno;
import syg_package01.Filtr.filtr_przepustowosc;
import org.apache.commons.math.complex.Complex;
import org.apache.commons.math.stat.regression.OLSMultipleLinearRegression;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;

//jfreechart
public class Sygnal {
	public enum RodzajSygnalu {
		CIAGLY, DYSKRETNY, SPROBKOWANY, SKWANTOWANY, ZINTERPOLOWANY, ZREKONSTRUOWANY, ZESPOLONY
	}

	/**
	 * <b>Typ sygnału.</b><br>
	 * <i>(S1-S11)</i>
	 */
	int typ;

	RodzajSygnalu rodzaj = RodzajSygnalu.CIAGLY;
	/**
	 * <b>Amplituda sygnału.</b><br>
	 * Wymagane parametry sygnału: <li>ciągły
	 */
	double A;
	/**
	 * <b>Czas początkowy sygnału.</b><br>
	 */
	double t1;
	/**
	 * <b>Długość sygnału.</b><br>
	 * Wymagane parametry sygnału: <li>ciągły
	 */
	double d;
	/**
	 * <b>Okres podstawowy sygnału.</b><br>
	 * Wymagane parametry sygnału: <li>ciągły<li>okresowy
	 */
	double T;
	/**
	 * <b>Współczynnik wypełanienia sygnału.</b><br>
	 * Wymagane parametry sygnału: <li>ciągły<li>typ: prostokątny, trójkątny
	 */
	double kw;
	double ts;
	/**
	 * <b>Krok próbkowania</b> - dla zapisu i przekształceń.
	 */
	double krok;
	/**
	 * <b>Odległość pomiędzy próbkami dla wyświetlania sygnału ciągłego.</b><br>
	 */
	double kroczek;
	/**
	 * <b>Skok sygnału.</b><br>
	 * Wymagane parametry sygnału: <li>ciągły<li>typ: impulsowy, skokowy
	 */
	double skok;
	Random gaus;

	/**
	 * Lista aplitud po spróbkowaniu (dla wykresu i zapisu);
	 */
	private List<Double> punktyY_wykres = new ArrayList<Double>();
	private List<Double> punktyY_probkowanie = new ArrayList<Double>();
	private List<Double> punktyY_kwantyzacja = new ArrayList<Double>();
	private List<LiczbaZespolona> punktyY_zespolone = new ArrayList<LiczbaZespolona>();
	public XYSeries punktyNaWYkresie = new XYSeries("Wykres");
	public XYSeries punktyZrekonstruowane = new XYSeries("Rekonstrukcja");

	public List<Double> getPunktyY_kwantyzacja() {
		return punktyY_kwantyzacja;
	}

	public double getPunktyY_kwantyzacja(int _id) {
		return punktyY_kwantyzacja.get(_id);
	}

	public void setPunktyY_kwantyzacja(double punktyY_kwantyzacja) {
		this.punktyY_kwantyzacja.add(punktyY_kwantyzacja);
	}

	/**
	 * Poziom kwantyzacji - krok przemieszczenia pomiędzy nimi
	 */
	double poziom_kwantyzacji_krok;
	int poziomy_kwantyzacji;

	private static final Random random = new Random();

	public Sygnal() {
		this.typ = 4;
		this.A = 10;
		this.t1 = 0;
		this.ts = 0;
		this.d = 20;
		this.T = 1;
		this.kw = 0.5;
		this.krok = 0.5;
		// this.kroczek = (this.getd() - this.gett1()) / 1000.0;
		this.skok = 10;
	}

	/**
	 * Konwersja A/C - próbkowanie (S1) - próbkowanie równomierne
	 * 
	 * @param _punktX
	 *            : double
	 * @return
	 */
	public double konwersja_probkowanie(double _t) {
		return 0.0D;
	}
	
	public void kopiuj (Sygnal _sygnalKopiowany)
	{
		this.A = _sygnalKopiowany.getA();
		this.d = _sygnalKopiowany.getd();
		this.kroczek = _sygnalKopiowany.getkroczek();
		this.krok = _sygnalKopiowany.getkrok();
		this.punktyNaWYkresie = new XYSeries(_sygnalKopiowany.punktyNaWYkresie.getKey());
		for (int i = 0; i < _sygnalKopiowany.punktyNaWYkresie.getItemCount(); ++i)
		{
			this.punktyNaWYkresie.add(new XYDataItem(_sygnalKopiowany.punktyNaWYkresie.getX(i).doubleValue(), _sygnalKopiowany.punktyNaWYkresie.getY(i).doubleValue()));
		}
		this.rodzaj = _sygnalKopiowany.rodzaj;
		this.T = _sygnalKopiowany.getT();
		this.t1 = _sygnalKopiowany.gett1();
		this.typ = _sygnalKopiowany.gettyp();
	}

	/**
	 * Szukanie wartości dla danego czasu. Jeśli nie ma wartości w danym punkcie
	 * to brana wartość po prostej z nalbliższych punktów.
	 * 
	 * @param _x
	 * @param _punkty
	 * @return
	 */
	public double znajdzWartoscNaWykresie(double _x, XYSeries _punkty) {
		if (_punkty != null && _punkty.getItemCount() > 0) {
			int iMniejszy = 0; // indeks dla najbliższego punktu i mniejszego od
								// _x
			int iWiekszy = 2; // indeks dla najbliższego punktu i większego od
								// _x
			for (int i = 0; i < _punkty.getItemCount(); ++i) {
				if (_punkty.getX(i).doubleValue() == _x)
					return _punkty.getY(i).doubleValue();
				if (i > 0) {
					if (_punkty.getX(i - 1).doubleValue() < _x
							&& _punkty.getX(i).doubleValue() > _x) {
						iMniejszy = i - 1;
						iWiekszy = i;
						break;
					}
				}
			}
			if (_punkty.getItemCount() < 2)
				return _punkty.getY(0).doubleValue();
			// jeśli nie znaleziono wartości dla podanego argumentu to obliczany
			// z
			// prostej pomiędzy najbliższymi punktami
			if (_punkty.getY(iMniejszy).doubleValue() > _punkty.getY(iWiekszy).doubleValue()) // jeśli
																								// wartość
																								// przy
																								// większym
																								// czasie
																								// jest
																								// mniejsza
			{
				return (_punkty.getY(iWiekszy).doubleValue() + Math.abs((_punkty.getY(iMniejszy)
						.doubleValue() - _punkty.getY(iWiekszy).doubleValue())
						* (_punkty.getX(iWiekszy).doubleValue() - _x)
						/ (_punkty.getX(iWiekszy).doubleValue() - _punkty.getX(iMniejszy)
								.doubleValue())));
			} else {
				return (_punkty.getY(iMniejszy).doubleValue() + Math.abs((_punkty.getY(iWiekszy)
						.doubleValue() - _punkty.getY(iMniejszy).doubleValue())
						* (_x - _punkty.getX(iMniejszy).doubleValue())
						/ (_punkty.getX(iWiekszy).doubleValue() - _punkty.getX(iMniejszy)
								.doubleValue())));
			}
		}else
		{
			System.out.println("Brak danych o wartościach próbek w: Sygnal:znajdzWartoscNaWykresie");
			return 0.0D;
		}
	}

	/**
	 * Błąd średniokwadratowy (MSE, ang. <i>Mean Squared Error</i>)<br>
	 * obliczany na podstawie wartości w punktyNaWYkresie i
	 * punktyZrekonstruowane (jeśli nie można znaleźć wartości dla argumentu -
	 * szuka po prostej)
	 * 
	 * @return
	 */
	public double obl_MSE() {
		double wynik = 0;
		try {
			if (!punktyNaWYkresie.isEmpty() && !punktyZrekonstruowane.isEmpty()) {

				double i = this.t1;
				int iloscPunktow = 0;
				while (i < this.t1 + this.d - this.kroczek) {
					wynik += Math.pow(
							(this.znajdzWartoscNaWykresie(i, this.punktyNaWYkresie) - this
									.znajdzWartoscNaWykresie(i, this.punktyZrekonstruowane)), 2);
					i += this.kroczek;
					++iloscPunktow;
				}

				wynik = (1.0D / iloscPunktow) * wynik;

			} else {
				if (this.punktyNaWYkresie.isEmpty())
					JOptionPane.showMessageDialog(null, "Brak sygnału.", "Błąd",
							JOptionPane.ERROR_MESSAGE);
				else if (punktyZrekonstruowane.isEmpty())
					JOptionPane.showMessageDialog(null, "Brak rekonstrukcji sygnału.", "Błąd",
							JOptionPane.ERROR_MESSAGE);

			}
		} catch (Exception exc_MSE) {
			JOptionPane.showMessageDialog(null, "Nie można obliczyć:\n" + exc_MSE.getMessage(),
					"Błąd", JOptionPane.ERROR_MESSAGE);
		}
		return wynik;
	}

	/**
	 * Stosunek sygnał - szum (SNR, ang. <i>Signal to Noise Ratio</i>)
	 */
	public double obl_SNR() {
		double wynik = 0;
		try {
			if (!this.punktyNaWYkresie.isEmpty() && !this.punktyZrekonstruowane.isEmpty()) {

				double licznik = 0, mianownik = 0;
				double i = this.t1;
				int iloscPunktow = 0;

				while (i < this.t1 + this.d - this.kroczek) {
					licznik += Math.pow((this.znajdzWartoscNaWykresie(i, punktyNaWYkresie)), 2);
					i += this.kroczek;
					++iloscPunktow;
				}

				i = this.t1;
				while (i < this.t1 + this.d - this.kroczek) {
					mianownik += Math.pow(
							((this.znajdzWartoscNaWykresie(i, punktyNaWYkresie)) - (this
									.znajdzWartoscNaWykresie(i, punktyZrekonstruowane))), 2);
					i += this.kroczek;
				}

				if (mianownik != 0) {
					wynik = licznik / mianownik;
					wynik = 10.0D * Math.log10(wynik);
				} else {
					wynik = 0;
				}

			} else {
				if (this.punktyNaWYkresie.isEmpty())
					JOptionPane.showMessageDialog(null, "Brak sygnału.", "Błąd",
							JOptionPane.ERROR_MESSAGE);
				else if (punktyZrekonstruowane.isEmpty())
					JOptionPane.showMessageDialog(null, "Brak rekonstrukcji sygnału.", "Błąd",
							JOptionPane.ERROR_MESSAGE);

			}
		} catch (Exception exc_MSE) {
			JOptionPane.showMessageDialog(null, "Nie można obliczyć:\n" + exc_MSE.getMessage(),
					"Błąd", JOptionPane.ERROR_MESSAGE);
			wynik = -1;
		}
		return wynik;
	}

	/**
	 * Szczytowy stosunek sygnał - szum (PSNR, ang. <i>Peak Signal to Noise
	 * Ratio</i>)
	 */
	public double obl_PSNR() {
		double wynik = 0;
		try {
			if (!this.punktyNaWYkresie.isEmpty() && !punktyZrekonstruowane.isEmpty()) {

				double licznik = this.punktyNaWYkresie.getMaxY(), mianownik = 0;

				mianownik = this.obl_MSE();
				if (mianownik != 0) {
					wynik = licznik / mianownik;
					wynik = 10.0D * Math.log10(wynik);
				} else
					wynik = 0;

			} else {
				if (this.punktyNaWYkresie.isEmpty())
					JOptionPane.showMessageDialog(null, "Brak sygnału.", "Błąd",
							JOptionPane.ERROR_MESSAGE);
				else if (punktyZrekonstruowane.isEmpty())
					JOptionPane.showMessageDialog(null, "Brak zapisanej konwersji sygnału.",
							"Błąd", JOptionPane.ERROR_MESSAGE);

			}
		} catch (Exception exc_MSE) {
			JOptionPane.showMessageDialog(null, "Nie można obliczyć:\n" + exc_MSE.getMessage(),
					"Błąd", JOptionPane.ERROR_MESSAGE);
		}
		return wynik;
	}

	/**
	 * Maksymalna różnica (MD, ang. <i>Maximum Difference</i>)
	 * 
	 * @return
	 */
	public double obl_MD() {
		double wynik = 0;
		try {
			if (!this.punktyNaWYkresie.isEmpty() && !punktyZrekonstruowane.isEmpty()) {

				double tmp;
				double t = this.t1, tMax = t;
				int maxPunkt = 0, i = 0;

				wynik = Math.abs(this.znajdzWartoscNaWykresie(t, punktyNaWYkresie)
						- this.znajdzWartoscNaWykresie(t, punktyZrekonstruowane));

				while (t < this.t1 + this.d - this.kroczek) {
					t += this.kroczek;
					tmp = Math.abs(this.znajdzWartoscNaWykresie(t, punktyNaWYkresie)
							- this.znajdzWartoscNaWykresie(t, punktyZrekonstruowane));

					if (wynik < tmp) {
						wynik = tmp;
						maxPunkt = i;
						tMax = t;
					}
					++i;
				}

				// System.out.println("lp     x      y");
				// System.out.println("1     " + tMax + "      "
				// + this.znajdzWartoscNaWykresie(tMax, punktyNaWYkresie) + " ["
				// + maxPunkt + "]");
				// System.out.println("2     " + tMax + "      "
				// + this.znajdzWartoscNaWykresie(tMax, punktyZrekonstruowane));
				// System.out.println(obl_zaokr(wynik));

			} else {
				if (this.punktyNaWYkresie.isEmpty())
					JOptionPane.showMessageDialog(null, "Brak sygnału.", "Błąd",
							JOptionPane.ERROR_MESSAGE);
				else if (punktyZrekonstruowane.isEmpty())
					JOptionPane.showMessageDialog(null, "Brak rekonstrukcji sygnału.", "Błąd",
							JOptionPane.ERROR_MESSAGE);

			}
		} catch (Exception exc_MSE) {
			JOptionPane.showMessageDialog(null, "Nie można obliczyć:\n" + exc_MSE.getMessage(),
					"Błąd", JOptionPane.ERROR_MESSAGE);
		}
		return wynik;
	}

	private enum OBL_TYPCALKI {
		/**
		 * Całka z x(t)
		 */
		OBL_CALKA_ZWYKLA,
		/**
		 * Całka z x^2(t)
		 */
		OBL_CALKA_KWADRAT,
		/**
		 * Całka z |x(t)|
		 */
		OBL_CALKA_ABS,
		/**
		 * Całka z (x(t) + wartosc)^2
		 */
		OBL_CALKA_KWADRAT_POWIEKSZENIE
	}

	private double obl_calka(double _xp, double _xk, double _n, OBL_TYPCALKI _typ) {
		return obl_calka(_xp, _xk, _n, _typ, 0);
	}

	/**
	 * Obliczenie całki<br>
	 * <a href="http://edu.i-lo.tarnow.pl/inf/alg/004_int/0003.php">Źródło</a>
	 * 
	 * @param _xp
	 *            - początek przedziału całkowania
	 * @param _xk
	 *            - koniec przedziału całkowania
	 * @param _n
	 *            - liczba punktów podziałowych
	 * @param _typ
	 *            - typ obliczenia całki: OBL_TYPCALKI
	 * @param _wartoscDodawana
	 *            - wartość dodawana przy obliczaniu całki typu:
	 *            OBL_CALKA_KWADRAT_POWIEKSZENIE
	 * @return
	 */
	private double obl_calka(double _xp, double _xk, double _n, OBL_TYPCALKI _typ,
			double _wartoscDodawana) {
		double dx = 0; // odległość między dwoma sąsiednimi punktami
						// podziałowymi
		double s = 0; // wynik
		int i = 0; // licznik

		if (_n <= 0)
			_n = 10;

		dx = (_xk - _xp) / _n;

		while (++i < _n) {
			switch (_typ) {
			case OBL_CALKA_ZWYKLA: {
				s += wykres_punkty(s, _xp + (i * dx));
				break;
			}
			case OBL_CALKA_ABS: {
				s += Math.abs(wykres_punkty(s, _xp + (i * dx)));
				break;
			}
			case OBL_CALKA_KWADRAT: {
				s += Math.pow((wykres_punkty(s, _xp + (i * dx))), 2);
				break;
			}
			case OBL_CALKA_KWADRAT_POWIEKSZENIE: {
				s += Math.pow((wykres_punkty(s, _xp + (i * dx)) + _wartoscDodawana), 2);
				break;
			}
			}
		}
		s = (s + (wykres_punkty(s, _xp) + wykres_punkty(s, _xk)) * 0.5) * dx;

		return s;
	}

	/**
	 * Obliczenia dla sygnału dyskretnego lub ciągłego: wartość średnia
	 * 
	 * @param _rodzaj
	 *            : rodzaj_sygnalu
	 * @return: double
	 */
	public double obl_sredniawartosc(RodzajSygnalu _rodzaj) {
		int i;
		double srednia = 0;
		int size = 0;
		if (_rodzaj == RodzajSygnalu.DYSKRETNY) {
			for (i = 0; i < this.getPunktyY_wykres().size(); i++) {
				srednia = srednia + this.getPunktzindexu(i);
			}
			size = this.getPunktyY_wykres().size();
			srednia /= size;
		} else {
			if (this.d >= this.T) {
				double t2 = this.t1 + (Math.floor(this.d / this.T)) * this.T;
				srednia = (this.obl_calka(this.t1, t2, this.d / this.kroczek,
						OBL_TYPCALKI.OBL_CALKA_ZWYKLA)) / (t2 - this.t1);
			}
		}

		return obl_zaokr(srednia);
	}

	/**
	 * miejsca po przecinku
	 * 
	 * @param _liczba
	 * @param _miejscPoPrzec
	 * @return
	 */
	private double obl_zaokr(double _liczba, int _miejscPoPrzec) {
		_liczba *= (10 * _miejscPoPrzec);
		_liczba = Math.round(_liczba) * 1.0;
		_liczba /= (10 * _miejscPoPrzec);
		return _liczba;
	}

	/**
	 * Domyślne zaokrąglenie: 3 miejsca po przecinku
	 * 
	 * @param _liczba
	 * @return
	 */
	private double obl_zaokr(double _liczba) {
		return this.obl_zaokr(_liczba, 3);
	}

	public double obl_sredniawartoscbezwzgledna(RodzajSygnalu _rodzaj) {
		int i;
		double srednia = 0;
		double liczba = 0;
		if (_rodzaj == RodzajSygnalu.DYSKRETNY) {
			for (i = 0; i < this.getPunktyY_wykres().size(); i++) {
				liczba = Math.abs(this.getPunktzindexu(i));
				srednia = srednia + liczba;

			}
			srednia /= this.getPunktyY_wykres().size();
		} else {
			if (this.d >= this.T) {
				double t2 = this.t1 + (Math.floor(this.d / this.T)) * this.T;
				srednia = 1.0
						/ (t2 - this.t1)
						* (this.obl_calka(this.t1, t2, this.d / this.kroczek,
								OBL_TYPCALKI.OBL_CALKA_ABS));
			}
		}

		return obl_zaokr(srednia);
	}

	public double obl_mocsrednia(RodzajSygnalu _rodzaj) {
		int i;
		double moc = 0;
		if (_rodzaj == RodzajSygnalu.DYSKRETNY) {
			for (i = 0; i < this.getPunktyY_wykres().size(); i++) {
				moc += (this.getPunktzindexu(i) * this.getPunktzindexu(i));

			}
			moc /= this.getPunktyY_wykres().size();
		} else {
			if (this.d >= this.T) {
				double t2 = this.t1 + (Math.floor(this.d / this.T)) * this.T;
				moc = 1.0
						/ (t2 - this.t1)
						* (this.obl_calka(this.t1, t2, this.d / this.kroczek,
								OBL_TYPCALKI.OBL_CALKA_KWADRAT));
			}
		}
		return obl_zaokr(moc);
	}

	/**
	 * RMS
	 * 
	 * @param _rodzaj
	 * @return
	 */
	public double obl_wartoscskuteczna(RodzajSygnalu _rodzaj) {
		int i;
		double wynik = 0;
		if (_rodzaj == RodzajSygnalu.DYSKRETNY) {
			for (i = 0; i < this.getPunktyY_wykres().size(); i++) {
				wynik = wynik + (this.getPunktzindexu(i) * this.getPunktzindexu(i));
			}
			if (wynik >= 0) {
				wynik = Math.sqrt(wynik / this.getPunktyY_wykres().size());
			}
		} else {
			wynik = this.obl_mocsrednia(_rodzaj);
			if (wynik >= 0) {
				wynik = Math.sqrt(wynik);
			}
		}
		return obl_zaokr(wynik);
	}

	/**
	 * wariancja sygnału w przedziale wokół wartości średniej
	 * 
	 * @param _rodzaj
	 * @return
	 */
	public double obl_wariancja(RodzajSygnalu _rodzaj) {
		int i;
		double wariancja = 0;
		if (_rodzaj == RodzajSygnalu.DYSKRETNY) {
			for (i = 0; i < this.getPunktyY_wykres().size(); i++) {
				double _wartosc = this.getPunktzindexu(i)
						- this.obl_sredniawartosc(RodzajSygnalu.DYSKRETNY);
				wariancja = wariancja + (_wartosc * _wartosc);
			}
			wariancja /= this.getPunktyY_wykres().size();
		} else {
			if (this.d >= this.T) {
				double t2 = this.t1 + (Math.floor(this.d / this.T)) * this.T;
				wariancja = 1.0
						/ (t2 - this.t1)
						* (this.obl_calka(this.t1, t2, this.d / this.kroczek,
								OBL_TYPCALKI.OBL_CALKA_KWADRAT_POWIEKSZENIE,
								-this.obl_sredniawartosc(_rodzaj)));
			}
		}
		return obl_zaokr(wariancja);
	}

	/**
	 * Sprawdzenie czy sygnał jest typem <3;8> i ma okres dodatni.
	 * 
	 * @return
	 */
	public boolean czyOkresowy() {
		return (this.gettyp() > 2 && this.gettyp() < 9 && this.getT() > 0);
	}

	/**
	 * Dla sygnału dyskretnego: sprawdzenie czasów początkowych i kroków. Dla
	 * innych rodzajów sygnałów: sprawdzenie czy ten sam rodzaj.
	 * 
	 * @param _sygnalPorownywany
	 * @param _sprawdzanyRodzaj
	 *            /rodzaj_sygnalu/
	 * @return
	 */
	public boolean porownajSygnal(Sygnal _sygnalPorownywany, RodzajSygnalu _sprawdzanyRodzaj) {
		if (_sprawdzanyRodzaj == RodzajSygnalu.DYSKRETNY) {
			return (_sygnalPorownywany.getRodzaj() == _sprawdzanyRodzaj
					&& this.rodzaj == _sprawdzanyRodzaj && (this.t1 == _sygnalPorownywany.gett1() && this
					.getkrok() == _sygnalPorownywany.getkrok()));
		} else {
			return (_sygnalPorownywany.getRodzaj() == _sprawdzanyRodzaj && this.rodzaj == _sprawdzanyRodzaj);
		}
	}

	public void pobierzParametryUzytkownika(int _typ, double _A, double _t1, double _ts, double _d,
			double _T, double _kw, double _skok) {

		this.typ = _typ;
		this.A = _A;
		this.t1 = _t1;
		this.ts = _ts;
		this.d = _d;
		this.T = _T;
		this.kw = _kw;
		this.skok = _skok;
		// this.kroczek = (this.t1() + this.d()) / 1000;
	}

	public double sin2piTtminT1(double _t) {
		return Math.sin(((2.0 * Math.PI) / (double) this.getT()) * (double) (_t - this.gett1()));
	}

	public double getkrok() {
		return this.krok;
	}

	public void setkrok(double _krok) {
		this.krok = _krok;
	}

	public void setrodzajciagly() {
		this.rodzaj = RodzajSygnalu.CIAGLY;
	}

	public void setrodzajdyskretny() {
		this.rodzaj = RodzajSygnalu.DYSKRETNY;
	}

	public RodzajSygnalu getRodzaj() {
		return this.rodzaj;
	}

	public RodzajSygnalu getRodzajDlaObl() {
		if (this.getRodzaj() == RodzajSygnalu.CIAGLY && this.gettyp() > 9) {
			return RodzajSygnalu.DYSKRETNY;
		} else {
			return this.rodzaj;
		}
	}

	public void setRodzaj(RodzajSygnalu rodzaj) {
		this.rodzaj = rodzaj;
	}

	public void ustawPunkty() {
		double ta = this.gett1();
		double punkt = this.gett1();

		while (ta <= this.gett1() + this.getd()) {
			punkt = this.wykres_punkty(punkt, ta);
			this.setPunktyY_wykres(punkt);
			ta = ta + this.getkroczek();
		}
	}

	public double getPunktzindexu(int index) {
		return this.getPunktyY_wykres().get(index);
	}

	public void setkroczek(double _kroczek) {
		this.kroczek = _kroczek;
	}

	/**
	 * Zwraca różnice między argumentami (dla sygnałów ciągłych: 1000 próbek;
	 * dla dyskretnych: ok. 100)
	 * 
	 * @return
	 */
	public double getkroczek() {
		if (this.getRodzaj() == RodzajSygnalu.DYSKRETNY) {
			this.kroczek = this.krok;
		} else if (this.gettyp() == 10) {
			this.kroczek = (this.getskok() - this.gett1())
					/ (Math.floor(100.0 * (this.getskok() - this.gett1()) / this.getd()) * 1.0);
		} else if (this.gettyp() == 11) {
			this.kroczek = (this.getd() - this.gett1()) / 100.0;
		} else if (this.getRodzaj() == RodzajSygnalu.CIAGLY)
			this.kroczek = (this.getd() - this.gett1()) / 1000.0;
		return this.kroczek;
	}

	public double getskok() {
		return this.skok;
	}

	public void setskok(double _skok) {
		this.skok = _skok;
	}

	public int gettyp() {
		return typ;
	}

	public void settyp(int _typ) {
		this.typ = _typ;
	}

	public double getA() {
		return A;
	}

	public void setA(double _A) {
		this.A = _A;
	}

	public double gett1() {
		return t1;
	}

	public void sett1(double _t1) {
		this.t1 = _t1;
	}

	public double getts() {
		return ts;
	}

	public void setts(double _ts) {
		this.ts = _ts;
	}

	public double getd() {
		return d;
	}

	public void setd(double _d) {
		this.d = _d;
	}

	public double getT() {
		return T;
	}

	public void setT(double _T) {
		this.T = _T;
	}

	public double getKw() {
		return kw;
	}

	public void setKw(double _kw) {
		this.kw = _kw;
	}

	/**
	 * Generowanie sygnałów w zależności od typu.
	 * 
	 * @param _wartosc
	 *            : double - wartość
	 * @param _argument
	 *            : double - argument
	 * @return : double
	 */
	public double wykres_punkty(double _wartosc, double _argument) {
		int biezacyTyp = this.gettyp();
		switch (biezacyTyp) {
		case 1:
			_wartosc = this.sygnalS1();
			break;

		case 2:
			_wartosc = this.sygnalS2();
			break;

		case 3:
			_wartosc = this.sygnalS3(_argument);
			break;

		case 4:
			_wartosc = this.sygnalS4(_argument);
			break;

		case 5:
			_wartosc = this.sygnalS5(_argument);
			break;

		case 6:
			_wartosc = this.sygnalS6(_argument);
			break;

		case 7:
			_wartosc = this.sygnalS7(_argument);
			break;

		case 8:
			_wartosc = this.sygnalS8(_argument);
			break;

		case 9:
			_wartosc = this.sygnalS9(_argument);
			break;

		case 10:
			_wartosc = this.sygnalS10(_argument);
			break;

		case 11:
			_wartosc = this.sygnalS11(_argument);
			break;
		case 12:
			_wartosc = this.sygnalS12(_argument);
			break;
		}
		return _wartosc;

	}

	/**
	 * Losowe wartości z przedziału <-A;A>
	 * 
	 * @return : double
	 */
	public double sygnalS1() {
		return (Math.random() * this.getA() * 2.0D) - this.getA();
	}

	public void sygnalS1punkty(Double punktyY) {

		punktyY = (this.sygnalS1());

	}

	/**
	 * Szum gaussowski
	 * 
	 * @return : double
	 */
	public double sygnalS2() {
		return random.nextGaussian() * this.getA();
	}

	/**
	 * Sygnał sinusoidalny.
	 * 
	 * @param t
	 *            : double
	 * @return : double
	 */
	public double sygnalS3(double t) {

		return this.getA() * this.sin2piTtminT1(t);

	}

	/**
	 * Sygnał sinusoidalny wyprostowany jednopołówkowo
	 * 
	 * @param t
	 * @return
	 */
	public double sygnalS4(double t) {
		return 0.5 * this.getA() * (sin2piTtminT1(t) + Math.abs(sin2piTtminT1(t)));
	}

	/**
	 * Sygnał sinusoidalny wyprostowany dwupołówkowo
	 * 
	 * @param t
	 * @return
	 */
	public double sygnalS5(double t) {
		return this.getA() * Math.abs(sin2piTtminT1(t));
	}

	/**
	 * Sygnał prostokątny
	 * 
	 * @param t
	 *            : double
	 * @return : double
	 */
	public double sygnalS6(double t) { // sygnał prostokątny

		double k = Math.floor((t - this.gett1()) / this.getT());

		if (t >= k * this.getT() + this.gett1()
				&& t < this.getKw() * this.getT() + k * this.getT() + this.gett1())
			return this.getA();
		else
			return 0;
	}

	/**
	 * Sygnał prostokątny symetryczny
	 * 
	 * @param t
	 * @return
	 */
	public double sygnalS7(double t) {
		double k = Math.floor((t - this.gett1()) / this.getT());

		if (t >= k * this.getT() + this.gett1()
				&& t < this.getKw() * this.getT() + k * this.getT() + this.gett1())
			return this.getA();
		else
			return -this.getA();
	}

	/**
	 * Sygnał trójkątny
	 * 
	 * @param t
	 * @return
	 */
	public double sygnalS8(double t) {
		double k = Math.floor((t - this.gett1()) / this.getT());

		if (t >= k * this.getT() + this.gett1()
				&& t < this.getKw() * this.getT() + k * this.getT() - this.gett1()) {
			return (this.getA() / (this.getKw() * this.getT()))
					* (t - k * this.getT() - this.gett1());

		} else {
			return (-this.getA()) / (this.getT() * (1 - this.getKw()))
					* (t - k * this.getT() - this.gett1()) + this.getA() / (1 - this.getKw());

		}
	}

	/**
	 * Skok jednostkowy - ciągły
	 * 
	 * @param t
	 *            : double
	 * @return : double
	 */
	public double sygnalS9(double t) {

		if (t > this.skok)
			return this.getA();
		else if (t == this.skok)
			return 0.5 * this.getA();
		else
			return 0;
	}

	/**
	 * Impuls jednostkowy
	 * 
	 * @param t
	 * @return
	 */
	public double sygnalS10(double t) {

		// przybliżenie związane z nietokładnym trafieniem próbki w skok
		if (t >= this.skok - this.skok * 0.001 && t < this.skok + this.skok * 0.001) {
			return this.getA();
		} else {
			return 0;
		}

	}

	/**
	 * Szum impulsowy
	 * 
	 * @param t
	 * @return
	 */
	public double sygnalS11(double t) {

		if (this.skok == 0) {
			return 0;
		} else if (random.nextInt(100) <= this.skok) {
			return this.getA();
		} else {
			return 0;
		}

	}

	/**
	 * Sygnał dla zadania 4: S2 S(t)=2*sin(2PI/2*t)+sin(2PI/1*t)+5sin(2PI/0,5*t)
	 * f_pr=16
	 * 
	 * @param t
	 * @return
	 */
	public double sygnalS12(double t) {

		double PI2t = 2 * Math.PI * t;
		return 2 * Math.sin(PI2t * 0.5 * t) + Math.sin(PI2t * t) + 5 * Math.sin(PI2t * 2 * t);

	}

	/**
	 * Wyczyszczenie tablicy wartości i punktów.
	 * 
	 * @param _wykresu
	 *            - czy usunąć punkty dla rysowania wykresu
	 */
	public void wyczyscPunkty(boolean _wykresu) {
		if (_wykresu) {
			this.getPunktyY_wykres().clear();
			this.getPunktyY_probkowanie().clear();
			this.getPunktyY_kwantyzacja().clear();
			this.punktyNaWYkresie.clear();
			this.punktyZrekonstruowane.clear();
		} else {
			this.getPunktyY_probkowanie().clear();
			this.getPunktyY_kwantyzacja().clear();
			this.punktyZrekonstruowane.clear();
		}
	}

	public void setPunktyY_wykres(double _punktyY_wykres) {
		this.punktyY_wykres.add(_punktyY_wykres);
	}

	public List<Double> getPunktyY_wykres() {
		return punktyY_wykres;
	}

	public void setPunktyY_probkowanie(double _punktyY_wykres) {
		this.punktyY_probkowanie.add(_punktyY_wykres);
	}

	public List<Double> getPunktyY_probkowanie() {
		return punktyY_probkowanie;
	}

	public double getPunktyY_probkowanie(int _id) {
		return punktyY_probkowanie.get(_id);
	}

	public void set_poziom_kwantyzacji(int _liczba) {
		this.poziomy_kwantyzacji = _liczba;

		if (this.poziomy_kwantyzacji == 2) {
			this.poziom_kwantyzacji_krok = (this.getA() + this.getA());
		} else {
			this.poziom_kwantyzacji_krok = ((this.getA() + this.getA()) / (this.poziomy_kwantyzacji - 1));
		}

	}

	public int get_poziom_kwantyzacji() {
		return this.poziomy_kwantyzacji;
	}

	public double kwantyzacja(double _punktX_konwersji) {
		int poziom = 1;
		double poziom_kwa = this.getA() * (-1);
		double return_this = 0; // nasz obliczony poziom kwantyzacji czyli nasz
								// Y

		for (int i = 0; i <= poziomy_kwantyzacji; i++) {
			if (poziom == 1) { // jeśli jest sam dół
				if (_punktX_konwersji <= poziom_kwa + (this.poziom_kwantyzacji_krok / 2))
					return_this = -this.A; // poziom najmniejszy
				else {
					poziom_kwa = poziom_kwa + this.poziom_kwantyzacji_krok;
					poziom = poziom + 1;
				}
			}

			else if (poziom == this.poziomy_kwantyzacji + 1) // jeśli sama góra
			{
				if (_punktX_konwersji > poziom_kwa - (this.poziom_kwantyzacji_krok / 2))
					return_this = this.A; // poziom największy
				else {
					poziom_kwa = poziom_kwa + this.poziom_kwantyzacji_krok;
					poziom = poziom + 1;
				}
			}

			else {
				if (_punktX_konwersji <= poziom_kwa + (this.poziom_kwantyzacji_krok / 2)) {
					if (_punktX_konwersji > poziom_kwa - (this.poziom_kwantyzacji_krok / 2))
						return_this = poziom_kwa;
					else {
						poziom_kwa = poziom_kwa + this.poziom_kwantyzacji_krok;
						poziom = poziom + 1;
					}
				} else {
					poziom_kwa = poziom_kwa + this.poziom_kwantyzacji_krok;
					poziom = poziom + 1;
				}

			}
		}
		return return_this;
	}

	public double sinc(double t) {
		if (t == 0) {
			return 1;
		} else {
			return Math.sin(Math.PI * t) / (Math.PI * t);
		}
	}

	/**
	 * Odpowiedź impulsowa - h(n)
	 * 
	 * @param _n
	 *            = 0, 1, 2, ..., M-1
	 * @param _filtr
	 *            - filtr sygnału
	 * @return
	 */
	public double odpowiedzImpulsowa(int _n, Filtr _filtr, boolean _czy2sygnal, Sygnal _sygnal) {
		double f_p = 1.0D / ((_sygnal.getRodzaj() == RodzajSygnalu.CIAGLY) ? (_sygnal.getkroczek())
				: (_sygnal.getkrok()));
		double f_0 = _filtr.getCzestotliwoscObciecia();
		int M = (!_czy2sygnal ? _filtr.getLiczbaWspolczynnikow() : _sygnal.getPunktyY_wykres()
				.size()); // ilość punktów h(_n)
		double K = f_p
				/ ((_filtr.getPrzepustowosc() == filtr_przepustowosc.DOLNOPRZEPUSTOWY) ? f_0
						: (f_p / 4.0D + f_0));
		double w_n = 1.0D; // okno
		double s_n = 1.0D; // przepustowość
		double h_n = 0.0D; // wynik
		int pom1 = (M - 1) / 2; // zmienna pomocnicza

		// okno
		if (_filtr.getOkno() == filtr_okno.PROSTOKATNE) {
			w_n = 1.0D;
		} else if (_filtr.getOkno() == filtr_okno.HANNINGA) {
			w_n = 0.5D - (0.5D * (Math.cos((2.0D * Math.PI * (double) _n) / (double) M)));
		}

		// przepustowość
		if (_filtr.getPrzepustowosc() == filtr_przepustowosc.DOLNOPRZEPUSTOWY) {
			s_n = 1.0D;
		} else if (_filtr.getPrzepustowosc() == filtr_przepustowosc.GORNOPRZEPUSTOWY) {
			s_n = Math.pow(-1.0D, _n);
		}

		// odpowiedź
		if (!_czy2sygnal) {
			if (_n == pom1)
				h_n = 2.0D / K;
			else
				h_n = (Math.sin(2.0D * Math.PI * (_n - pom1) / K))
						/ (double) (Math.PI * (double) (_n - pom1));
		} else {
			h_n = _sygnal.getPunktzindexu(_n);
		}

		h_n = h_n * w_n * s_n;

		return h_n;
	}

	/**
	 * Funkcja splotu
	 * 
	 * @param _t
	 *            - nr kolejnej próbki
	 * @param _filtr
	 *            - filtr sygnału
	 * @return
	 */
	public double splot(int _t, Filtr _filtr) {
		double y_t = 0.0D;
		int M = _filtr.getLiczbaWspolczynnikow();// _t;//this.getPunktyY_wykres().size();
		int pom = 0;

		for (int k = 0; k < M; ++k) {
			pom = _t - k;
			if (pom >= 0 && pom < this.punktyY_wykres.size())
				y_t = y_t
						+ (odpowiedzImpulsowa(k, _filtr, false, this) * this.getPunktzindexu(pom));
		}

		return y_t;
	}

	/**
	 * Korelacja dwóch sygnałów
	 * 
	 * @param _n
	 *            - nr kolejnej próbki sygnału R (począwszy od ujemnych)
	 * @param _sygnal2
	 *            - drugi sygnał
	 * @param _ktoraOpcja
	 *            : <li>0 - implementacja bezpośrednia <li>1 - implementacja z
	 *            uśyciem splotu
	 * @return
	 */
	public double korelacja(int _n, Filtr _filtr, Sygnal _sygnal2, int _ktoraOpcja) {
		int M = _sygnal2.getPunktyY_wykres().size();
		double R_n = 0.0D;
		int pom = 0;
		double h_k = 0.0;

		for (int k = 0; k < M; ++k) {
			pom = _n - k;
			if (_ktoraOpcja == 0) {
				h_k = _sygnal2.getPunktzindexu(k);
			} else {
				h_k = this.odpowiedzImpulsowa(k, _filtr, true, _sygnal2);
			}
			if (pom >= 0 && pom < this.punktyY_wykres.size())
				R_n = R_n + (h_k * this.getPunktzindexu(pom));
		}

		return R_n;
	}

	private static Complex W(int m, int N) {
		return new Complex(Math.cos(2 * Math.PI * m / N), -Math.sin(2 * Math.PI * m / N));
	}

	public List<Complex> FFT(List<Double> punktyY) {
		Complex gora, dol;
		int motylkiWbloku; // liczba motylkow w bloku;
		int liczbaBlokow;
		int indexA, indexB;

		List<Complex> listaTemp = new ArrayList<Complex>();
		for (int i = 0; i < punktyY.size(); i++) {
			listaTemp.add(new Complex(punktyY.get(i), 0));
		}

		int liczbaBitow = 0;

		// uzupelnienie zerami do potegi 2:
		for (int i = listaTemp.size(); i < liczbaBitow; i++) {
			listaTemp.add(new Complex(0, 0));
		}

		for (int N = punktyY.size(); N > 1; N /= 2) { // etapy
			liczbaBitow++;
			motylkiWbloku = N / 2;
			liczbaBlokow = punktyY.size() / N;
			for (int i = 0; i < liczbaBlokow; i++) {
				for (int j = 0; j < motylkiWbloku; j++) {
					indexA = i * N + j;
					indexB = indexA + motylkiWbloku;
					gora = listaTemp.get(indexA).add(listaTemp.get(indexB));
					dol = listaTemp.get(indexA).subtract(listaTemp.get(indexB)).multiply(W(j, N));
					listaTemp.set(indexA, gora);
					listaTemp.set(indexB, dol);
				}
			}
		}

		// zamiana kolejnosci:
		List<Complex> transformata = new ArrayList<Complex>();
		for (int i = 0; i < listaTemp.size(); i++) {
			transformata.add(listaTemp.get(odwrocBity(i, liczbaBitow)));
		}

		return transformata;
	}

	public static List<Complex> DFT(List<Double> punktyY) {
		List<Complex> wynik = new ArrayList<Complex>();
		for (int m = 0; m < punktyY.size(); m++) {
			Complex suma = new Complex(0, 0);
			for (int n = 0; n < punktyY.size(); n++) {
				suma = suma.add(W(m * n, punktyY.size()).multiply(punktyY.get(n)));
			}
			wynik.add(suma);
		}
		return wynik;
	}

	public static int odwrocBity(int liczba, int liczbaBitow) {
		int wynik = 0;
		int bit;
		for (int i = 0; i < liczbaBitow; i++) {
			bit = liczba % 2;
			liczba = liczba >> 1;
			wynik = wynik << 1;
			wynik += bit;
		}
		return wynik;
	}
}