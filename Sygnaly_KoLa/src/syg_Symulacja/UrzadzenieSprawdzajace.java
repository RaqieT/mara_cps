package syg_Symulacja;

import java.util.Date;

import org.jfree.data.xy.XYSeries;

import syg_Wykresy.PanelRysunek_Filtracja;
import syg_package01.Filtr;
import syg_package01.Filtr.filtr_przepustowosc;
import syg_package01.Sygnal;
import syg_package01.Filtr.filtr_okno;

public class UrzadzenieSprawdzajace {
	public Nadajnik nadajnik;
	public Odbiornik odbiornik;
	public XYSeries tablicaMaximow;
	public Sygnal sygnalSkorelowany;
	public double predkoscSygnalu = 20; // [m/s]
	public SYM_AKCJA aktualnaAkcja;
	public double ostatniaOdleglosc;
	/**
	 * Po ilu próbkach sygnał pierwszy raz powróci
	 */
	private int opoznienieOdbiornika = 20;
	public double jednostkaCzasowa = 1000.0D;

	public enum SYM_AKCJA {
		SYM_ROZPOCZNIJ, SYM_RAPORTUJ, SYM_ZATRZYMAJ, SYM_KONTYNUUJ
	}

	public UrzadzenieSprawdzajace() {
		this.sygnalSkorelowany = new Sygnal();
	}

	public UrzadzenieSprawdzajace(Nadajnik _nadajnik, Odbiornik _odbiornik) {
		this.sygnalSkorelowany = new Sygnal();
		this.nadajnik = _nadajnik;
		this.odbiornik = _odbiornik;
		this.wykonuj();
	}
	
	public void ustawStale (AplikacjaSymulacji _aplikacja)
	{
		this.opoznienieOdbiornika = _aplikacja.obiekt.opoznienieProbek;
	}

	public void wykonuj() {
		int i = 0;
		int iloscKorelacji = 0;
		System.out.println("Rozpoczęcie pętli...");
		while (this.aktualnaAkcja != SYM_AKCJA.SYM_ZATRZYMAJ) {
			switch (this.aktualnaAkcja) {
			case SYM_ROZPOCZNIJ:
				this.nadajnik.generujPoczatek();
				this.aktualnaAkcja = SYM_AKCJA.SYM_KONTYNUUJ;
				break;
			case SYM_KONTYNUUJ:
				double czasAktualny = ((this.nadajnik.getCzasWypuszczeniaSygnalu()) + i
						* this.nadajnik.czasPomiedzyProbkami);
				this.nadajnik.zapiszWartoscProbki(czasAktualny);
				if (odbiornik == null)
					this.odbiornik = new Odbiornik(this.nadajnik.czasPomiedzyProbkami,
							this.nadajnik.buforProbek, this.nadajnik.sygnalNadawany.getd(), this.jednostkaCzasowa);
				if (this.czyOdbierac(i, iloscKorelacji, this.nadajnik.buforProbek))
					this.odbiornik.zapiszProbke(this.nadajnik.podajWartoscProbki(czasAktualny, this.odbiornik.czasPierwszej),
							czasAktualny);
				break;
			default:
				break;
			}
			++i;
			if (this.aktualnaAkcja != SYM_AKCJA.SYM_ROZPOCZNIJ
					&& this.czyMoznaKorelowac(this.odbiornik)) {
				this.wykonajKorelacje(this.nadajnik, this.odbiornik);
				++iloscKorelacji;
				// this.aktualnaAkcja = SYM_AKCJA.SYM_ROZPOCZNIJ;
				if (iloscKorelacji > 5) {
					this.aktualnaAkcja = SYM_AKCJA.SYM_ZATRZYMAJ;
					System.out.println("STOP");
				}
			}
		}

	}

	private boolean czyMoznaKorelowac(Odbiornik _odbiornik) {
		if (_odbiornik != null)
			return _odbiornik.moznaKorelowac();
		else
			return false;
	}

	// public double sprawdzWartoscNalozenia(Nadajnik _nadajnik, Odbiornik
	// _odbiornik) {
	// Date czasSprawdzania = new Date();
	//
	// double wartosc = _nadajnik.podajWartoscProbki(czasSprawdzania)
	// + _odbiornik.podajOdebranaWartosc(czasSprawdzania);
	//
	// return wartosc;
	// }

	/**
	 * Czy wrócił sygnał po odbiciu
	 * 
	 * @param _iloscKorelacji
	 * @param _buforProbek
	 */
	private boolean czyOdbierac(int _lpProbki, int _iloscKorelacji, int _buforProbek) {
		return _lpProbki > (this.opoznienieOdbiornika  * (_iloscKorelacji + 1));
	}

	public void wykonajKorelacje(Nadajnik _nadajnik, Odbiornik _odbiornik) {
		Sygnal[] sygnaly = new Sygnal[2];
		sygnaly[0] = _nadajnik.zapisanySygnalNadany;
		sygnaly[1] = _odbiornik.sygnalOdebrany;
		PanelRysunek_Filtracja obliczenia = new PanelRysunek_Filtracja(sygnaly, null, 0, false);
		obliczenia.obliczPunkty(_nadajnik.zapisanySygnalNadany.punktyNaWYkresie,
				_odbiornik.sygnalOdebrany.punktyNaWYkresie);
		_nadajnik.zapisanySygnalNadany = obliczenia.getSygnaly()[0];
		this.sygnalSkorelowany = _nadajnik.zapisanySygnalNadany;
		// this.sygnalSkorelowany.punktyZrekonstruowane =
		// _nadajnik.zapisanySygnalNadany.punktyZrekonstruowane;
		XYSeries polowa = new XYSeries("polowa po korelacji");
		int ilosc = this.sygnalSkorelowany.punktyZrekonstruowane.getItemCount();
		for (int i = (int) ilosc / 2; i < ilosc; ++i) {
			polowa.add(this.sygnalSkorelowany.punktyZrekonstruowane.getDataItem(i));
		}
		this.znajdzMaximum(polowa);
		this.ostatniaOdleglosc = this.tablicaMaximow.getX(this.tablicaMaximow.getItemCount() - 1)
				.doubleValue() - polowa.getX(0).doubleValue();
		System.out.println("Odleglosc czasowa: " + this.ostatniaOdleglosc + " sek.");
		this.ostatniaOdleglosc = this.ostatniaOdleglosc * this.predkoscSygnalu;
		System.out.println("Droga sygnalu: " + this.ostatniaOdleglosc + " m");
		this.ostatniaOdleglosc = this.ostatniaOdleglosc / 2.0D;
		System.out.println("Droga obiektu: " + this.ostatniaOdleglosc + " m");

		_odbiornik.wyzerujPoKorelacji();
	}

	// public boolean czyMax(double _wartosc, Date _czas) {
	// boolean znalezioneMax = false;
	// if (tablicaMaximow == null)
	// this.tablicaMaximow = new XYSeries("Maksymalne wartosci");
	// if (this.tablicaMaximow.getItemCount() == 1) {
	// this.tablicaMaximow.add(_czas.getTime() / 1000.0D, _wartosc);
	// } else {
	// if (zaokragl(_czas.getTime() / 1000.0D) >
	// zaokragl(this.tablicaMaximow.getMinX())) {
	// if (zaokragl(_wartosc) > zaokragl(this.tablicaMaximow.getMaxY())) { //
	// poprzednia
	// // wartość
	// // mniejsza
	// // this.tablicaMaximow = new
	// // XYSeries("Maksymalne wartosci");
	// znalezioneMax = false;
	// } else if (zaokragl(_wartosc) == zaokragl(this.tablicaMaximow.getMaxY()))
	// {
	// znalezioneMax = true;
	// }
	// if (!(zaokragl(_wartosc) < zaokragl(this.tablicaMaximow.getMaxY()))) {
	// if (this.tablicaMaximow.getItemCount() > 1)
	// this.tablicaMaximow.remove(0);
	// this.tablicaMaximow.add(_czas.getTime() / 1000.0D, _wartosc);
	// }
	// }
	// }
	// return znalezioneMax;
	// }

	/**
	 * Znajduje max i wyrzuca na konsolę
	 */
	public void znajdzMaximum(XYSeries _punkty) {
		if (_punkty != null && _punkty.getItemCount() > 0) {
			if (this.tablicaMaximow == null)
				this.tablicaMaximow = new XYSeries("Maksymalne wartosci");
			double t = 0.0, x = _punkty.getMaxY();
			for (int i = 0; i < _punkty.getItemCount(); ++i) {
				if (_punkty.getY(i).doubleValue() == x) {
					t = _punkty.getX(i).doubleValue();
					break;
				}
			}
			this.tablicaMaximow.add(t, x);
			int lp = this.tablicaMaximow.getItemCount() - 1;
			System.out.println("Znalezione max: " + lp + " : " + this.tablicaMaximow.getX(lp)
					+ " : " + this.tablicaMaximow.getY(lp));
		} else {
			System.out
					.println("Brak punktów dla znalezienia max w: UrzadzenieSprawdzajace:znajdzMaximum");
		}
	}

	/**
	 * lp wypuszczonej probki z nadajnika
	 * 
	 * @param _któraProbka
	 * @return
	 */
	public boolean czyMoznaKorelowac(int _któraProbka) {
		return (boolean) ((_któraProbka % this.nadajnik.ileProbekNaokres()) == 0);
	}

	/**
	 * Zwraca różnicę w czase na podstawie ostatniego i przedostatniego maxima
	 * (z <i>tablicaMaximow</i>)
	 * 
	 * @return
	 */
	public double podajRozniceWCzasie() {
		// if (this.tablicaMaximow.getItemCount() > 1) {
		// return this.tablicaMaximow.getX(1).doubleValue() -
		// this.tablicaMaximow.getX(0).doubleValue();
		// } else
		// return 0;
		// return Math.abs(this.tablicaMaximow.getX(0).doubleValue()
		// - this.sygnalSkorelowany.punktyZrekonstruowane.getX(
		// this.sygnalSkorelowany.punktyZrekonstruowane.getItemCount() / 2)
		// .doubleValue());
		if (this.tablicaMaximow.getItemCount() > 1) {
			return Math.abs(this.tablicaMaximow.getMaxX()
					- this.tablicaMaximow.getX(this.tablicaMaximow.getItemCount() - 2)
							.doubleValue());
		} else
			return 0;
	}

	private double zaokragl(double _liczba) {
		return this.zaokragl(_liczba, 2);
	}

	private double zaokragl(double _liczba, int _miejscPoPrzec) {
		_liczba *= (10 * _miejscPoPrzec);
		_liczba = Math.round(_liczba) * 1.0;
		_liczba /= (10 * _miejscPoPrzec);
		return _liczba;
	}

	public void rozpocznij() {
		this.aktualnaAkcja = SYM_AKCJA.SYM_ROZPOCZNIJ;
		this.wykonuj();
	}
}
