package syg_package01;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import syg_Szablony.PanelObslugi.zwracana_wartosc;
import syg_package01.Filtr.filtr_okno;
import syg_package01.Filtr.filtr_przepustowosc;
import org.apache.commons.math.complex.Complex;

//jfreechart
public class Sygnal {
	public enum rodzaj_sygnalu {
		CIAGLY, DYSKRETNY, SPROBKOWANY, SKWANTOWANY, ZINTERPOLOWANY, ZREKONSTRUOWANY, ZESPOLONY
	}

	/**
	 * <b>Typ sygnału.</b><br>
	 * <i>(S1-S11)</i>
	 */
	int typ;

	rodzaj_sygnalu rodzaj = rodzaj_sygnalu.CIAGLY;
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

	/**
	 * Błąd średniokwadratowy (MSE, ang. <i>Mean Squared Error</i>)
	 * 
	 * @param _doPorownania
	 * 
	 * @return
	 */
	public double obl_MSE(List<Double> _doPorownania) {
		double wynik = 0;
		try {
			if (!this.getPunktyY_wykres().isEmpty() && !_doPorownania.isEmpty()) {

				for (int i = 0; i < _doPorownania.size(); i++) {
					wynik = wynik + (this.getPunktyY_wykres().get(i) - _doPorownania.get(i))
							* (this.getPunktyY_wykres().get(i) - _doPorownania.get(i));
				}

				wynik = (1.0D / _doPorownania.size()) * wynik;

			} else {
				if (this.getPunktyY_wykres().isEmpty())
					JOptionPane.showMessageDialog(null, "Brak sygnału.", "Błąd",
							JOptionPane.ERROR_MESSAGE);
				else if (_doPorownania.isEmpty())
					JOptionPane.showMessageDialog(null, "Brak konwersji sygnału.", "Błąd",
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
	 * 
	 * @return
	 */
	public double obl_SNR(List<Double> _doPorownania) {
		double wynik = 0;
		try {
			if (!this.getPunktyY_wykres().isEmpty() && !_doPorownania.isEmpty()) {

				double licznik = 0, mianownik = 0;

				for (int i = 0; i < _doPorownania.size(); i++) {
					licznik = licznik
							+ (this.getPunktyY_wykres().get(i) * this.getPunktyY_wykres().get(i));
				}

				for (int i = 0; i < _doPorownania.size(); i++) {
					mianownik = mianownik
							+ (this.getPunktyY_wykres().get(i) - _doPorownania.get(i))
							* (this.getPunktyY_wykres().get(i) - _doPorownania.get(i));
				}

				wynik = licznik / mianownik;
				wynik = 10.0D * Math.log10(wynik);

			} else {
				if (this.getPunktyY_wykres().isEmpty())
					JOptionPane.showMessageDialog(null, "Brak sygnału.", "Błąd",
							JOptionPane.ERROR_MESSAGE);
				else if (_doPorownania.isEmpty())
					JOptionPane.showMessageDialog(null, "Brak konwersji sygnału.", "Błąd",
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
	 * 
	 * @return
	 */
	public double obl_PSNR(List<Double> _doPorownania) {
		double wynik = 0;
		try {
			if (!this.getPunktyY_wykres().isEmpty() && !_doPorownania.isEmpty()) {

				double licznik = this.getPunktyY_wykres().get(0), mianownik = 0;

				for (int i = 1; i < _doPorownania.size(); i++) {
					if (licznik < this.getPunktyY_wykres().get(i))
						licznik = this.getPunktyY_wykres().get(i);
				}

				mianownik = this.obl_MSE(_doPorownania);
				wynik = licznik / mianownik;
				wynik = 10.0D * Math.log10(wynik);

			} else {
				if (this.getPunktyY_wykres().isEmpty())
					JOptionPane.showMessageDialog(null, "Brak sygnału.", "Błąd",
							JOptionPane.ERROR_MESSAGE);
				else if (_doPorownania.isEmpty())
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
	public double obl_MD(List<Double> _doPorownania) {
		double wynik = 0;
		try {
			if (!this.getPunktyY_wykres().isEmpty() && !_doPorownania.isEmpty()) {

				double tmp;
				wynik = Math.abs(this.getPunktyY_wykres().get(0) - _doPorownania.get(0));

				for (int i = 1; i < _doPorownania.size(); i++) {
					tmp = Math.abs(this.getPunktyY_wykres().get(i) - _doPorownania.get(i));
					if (wynik < tmp)
						wynik = tmp;
				}

			} else {
				if (this.getPunktyY_wykres().isEmpty())
					JOptionPane.showMessageDialog(null, "Brak sygnału.", "Błąd",
							JOptionPane.ERROR_MESSAGE);
				else if (_doPorownania.isEmpty())
					JOptionPane.showMessageDialog(null, "Brak konwersji sygnału.", "Błąd",
							JOptionPane.ERROR_MESSAGE);

			}
		} catch (Exception exc_MSE) {
			JOptionPane.showMessageDialog(null, "Nie można obliczyć:\n" + exc_MSE.getMessage(),
					"Błąd", JOptionPane.ERROR_MESSAGE);
		}
		return wynik;
	}

	/**
	 * Obliczenia dla sygnału dyskretnego: wartość średnia
	 * 
	 * @param _rodzaj
	 *            : rodzaj_sygnalu
	 * @return: double
	 */
	public double obl_sredniawartosc(rodzaj_sygnalu _rodzaj) {
		int i;
		double srednia = 0;
		int size = 0;
		if (_rodzaj == rodzaj_sygnalu.DYSKRETNY) {

			if (this.gettyp() == 3 || this.gettyp() == 4 || this.gettyp() == 5
					|| this.gettyp() == 6 || this.gettyp() == 7 || this.gettyp() == 8) {
				// double now= this.getT();
				double _krok = this.gett1();
				int liczba = 0;

				while (_krok < this.getT()) {
					srednia = srednia + this.getPunktzindexu(liczba);
					liczba = liczba + 1;
					_krok = _krok + this.getkrok();
				}
				size = liczba;
			} else {
				for (i = 0; i < this.getPunktyY_wykres().size(); i++) {
					srednia = srednia + this.getPunktzindexu(i);

				}

				size = this.getPunktyY_wykres().size();
			}

		}

		return srednia / size;
	}

	public double obl_sredniawartoscbezwzgledna(rodzaj_sygnalu _rodzaj) {
		int i;
		double srednia = 0;
		double liczba = 0;
		if (_rodzaj == rodzaj_sygnalu.DYSKRETNY) {
			for (i = 0; i < this.getPunktyY_wykres().size(); i++) {
				liczba = Math.abs(this.getPunktzindexu(i));
				srednia = srednia + liczba;

			}
		}

		return srednia / this.getPunktyY_wykres().size();
	}

	public double obl_mocsrednia(rodzaj_sygnalu _rodzaj) {
		int i;
		double moc = 0;
		if (_rodzaj == rodzaj_sygnalu.DYSKRETNY) {
			for (i = 0; i < this.getPunktyY_wykres().size(); i++) {
				moc = moc + (this.getPunktzindexu(i) * this.getPunktzindexu(i));

			}

		}
		return moc / this.getPunktyY_wykres().size();
	}

	public double obl_wartoscskuteczna(rodzaj_sygnalu _rodzaj) {
		int i;
		double moc = 0;
		if (_rodzaj == rodzaj_sygnalu.DYSKRETNY) {
			for (i = 0; i < this.getPunktyY_wykres().size(); i++) {
				moc = moc + (this.getPunktzindexu(i) * this.getPunktzindexu(i));

			}

		}
		return Math.sqrt(moc / this.getPunktyY_wykres().size());
	}

	public double obl_wariancja(rodzaj_sygnalu _rodzaj) {
		int i;
		double wariancja = 0;
		if (_rodzaj == rodzaj_sygnalu.DYSKRETNY) {
			for (i = 0; i < this.getPunktyY_wykres().size(); i++) {
				double _wartosc = this.getPunktzindexu(i)
						- this.obl_sredniawartosc(rodzaj_sygnalu.DYSKRETNY);
				wariancja = wariancja + (_wartosc * _wartosc);
			}
		}
		return wariancja / this.getPunktyY_wykres().size();
	}

	public boolean porownajSygnal(Sygnal _sygnalPorownywany) {
		return (this.t1 == _sygnalPorownywany.gett1() && this.getkrok() == _sygnalPorownywany
				.getkrok());
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
		return Math.sin(((2.0 * Math.PI) / (double)this.getT()) * (double)(_t - this.gett1()));
	}

	public double getkrok() {
		return this.krok;
	}

	public void setkrok(double _krok) {
		this.krok = _krok;
	}

	public void setrodzajciagly() {
		this.rodzaj = rodzaj_sygnalu.CIAGLY;
	}

	public void setrodzajdyskretny() {
		this.rodzaj = rodzaj_sygnalu.DYSKRETNY;
	}

	public rodzaj_sygnalu getrodzaj() {
		return this.rodzaj;
	}

	public void setRodzaj(rodzaj_sygnalu rodzaj) {
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

	public double getkroczek() {
		if (this.gettyp() == 10)
		{
			this.kroczek = (this.gett1() + this.getskok()) /50.0;
		}else if (this.gettyp() == 11)
		{
			this.kroczek = (this.gett1() + this.getd()) /100.0;
		}else
		if (this.getrodzaj() == rodzaj_sygnalu.CIAGLY)
			this.kroczek = (this.gett1() + this.getd()) / 1000.0;
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
	 * @param punkt
	 *            : double - wartość
	 * @param punkt2
	 *            : double - argument
	 * @return : double
	 */
	public double wykres_punkty(double punkt, double punkt2) {
		int a = this.gettyp();
		switch (a) {
		case 1:
			punkt = this.sygnalS1();
			break;

		case 2:
			punkt = this.sygnalS2();
			break;

		case 3:
			punkt = this.sygnalS3(punkt2);
			break;

		case 4:
			punkt = this.sygnalS4(punkt2);
			break;

		case 5:
			punkt = this.sygnalS5(punkt2);
			break;

		case 6:
			punkt = this.sygnalS6(punkt2);
			break;

		case 7:
			punkt = this.sygnalS7(punkt2);
			break;

		case 8:
			punkt = this.sygnalS8(punkt2);
			break;

		case 9:
			punkt = this.sygnalS9(punkt2);
			break;

		case 10:
			punkt = this.sygnalS10(punkt2);
			break;

		case 11:
			punkt = this.sygnalS11(punkt2);
			break;

		}
		return punkt;

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
	 * @param t
	 * @return
	 */
	public double sygnalS4(double t) {
		return 0.5 * this.getA() * (sin2piTtminT1(t) + Math.abs(sin2piTtminT1(t)));
	}

	/**
	 * Sygnał sinusoidalny wyprostowany dwupołówkowo
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

		if (t >= k*this.getT()+this.gett1() && t < this.getKw()*this.getT()+k*this.getT()+this.gett1())
			return this.getA();
		else
			return 0;
	}

	/**
	 * Sygnał prostokątny symetryczny
	 * @param t
	 * @return
	 */
	public double sygnalS7(double t) {
		double k = Math.floor((t - this.gett1()) / this.getT());

		if (t >= k*this.getT()+this.gett1() && t < this.getKw()*this.getT()+k*this.getT()+this.gett1())
			return this.getA();
		else
			return -this.getA();
	}

	/**
	 * Sygnał trójkątny
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
	 * @param t
	 * @return
	 */
	public double sygnalS10(double t) {

		//przybliżenie związane z nietokładnym trafieniem próbki w skok
		if (t >= this.skok-this.skok*0.001 && t < this.skok+this.skok*0.001) {
			return this.getA();
		} else {
			return 0;
		}

	}

	/**
	 * Szum impulsowy
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
	 * Sygnał dla zadania 4: S2
	 * S(t)=2*sin(2PI/2*t)+sin(2PI/1*t)+5sin(2PI/0,5*t) f_pr=16
	 * @param t
	 * @return
	 */
	public double sygnalS12(double t) {

		double PI2t = 2*Math.PI*t;
		return 2*Math.sin(PI2t*0.5*t)+Math.sin(PI2t*t)+5*Math.sin(PI2t*2*t);

	}

	/**
	 * Wyczyszczenie tablicy wartości.
	 */
	public void wyczyscPunkty(boolean _wykresu) {
		if (_wykresu) {
			this.getPunktyY_wykres().clear();
			this.getPunktyY_probkowanie().clear();
			this.getPunktyY_kwantyzacja().clear();
		} else {
			this.getPunktyY_probkowanie().clear();
			this.getPunktyY_kwantyzacja().clear();
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
		double f_p = 1.0D / ((_sygnal.getrodzaj() == rodzaj_sygnalu.CIAGLY) ? (_sygnal.getkroczek())
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
		double pom1 = (M - 1) / 2; // zmienna pomocnicza

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
				h_n = (Math.sin(2.0D * Math.PI * (_n - pom1) / K)) / (Math.PI * (_n - pom1));
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

		for (int k = 0; k < M; k++) {
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

		for (int k = 0; k < M; k++) {
			h_k = _sygnal2.getPunktzindexu(k);
			if (_ktoraOpcja == 0) {
				pom = _n - k;
			} else {
				pom = _n - k;
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
					dol = listaTemp.get(indexA).subtract(listaTemp.get(indexB))
							.multiply(W(j, N));
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
				suma = suma.add(W(m * n, punktyY.size()).multiply(
						punktyY.get(n)));
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