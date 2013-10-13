package syg_package01;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//jfreechart
public class Sygnal {
	int typ;

	public enum rodzaj_sygnalu {
		CIAGLY, DYSKRETNY
	}

	rodzaj_sygnalu rodzaj = rodzaj_sygnalu.CIAGLY;
	double A, t1, d, T, kw, ts, krok, kroczek;
	double skok;
	Random gaus;
	List<Double> punktyY = new ArrayList<Double>();
	private static final Random random = new Random();

	public Sygnal() {
		this.typ = 4; // typ sygnału od S1 do S11
		this.A = 10;
		this.t1 = 0; // czas początkowy
		this.ts = 0;
		this.d = 20; // czas trwania sygnału
		this.T = 1; // okres podstawowy
		this.kw = 0.5;
		this.krok = 0.5;// wspłczynnik wypełnienia dla sygnału prostokątnego i
						// trójkątnego
		this.kroczek = (this.getd() - this.gett1()) / 1000.0;
		this.skok = 10;
	}

	public double obl_sredniawartosc(rodzaj_sygnalu _rodzaj) {
		int i;
		double srednia = 0;
		int size = 0;
		if (_rodzaj == rodzaj_sygnalu.DYSKRETNY) {

			if (this.gettyp() == 3 || this.gettyp() == 4 || this.gettyp() == 5
					|| this.gettyp() == 6 || this.gettyp() == 7
					|| this.gettyp() == 8) {
				// double now= this.getT();
				double _krok = this.gett1();
				int liczba = 0;

				while (_krok < this.getT()) {
					srednia = srednia + this.getPunktzindexu(liczba);
					liczba = liczba + 1;
					_krok = _krok + this.getkroczek();
				}
				size = liczba;
			} else {
				for (i = 0; i < this.punktyY.size(); i++) {
					srednia = srednia + this.getPunktzindexu(i);

				}

				size = this.punktyY.size();
			}

		}

		return srednia / size;
	}

	public double obl_sredniawartoscbezwzgledna(rodzaj_sygnalu _rodzaj) {
		int i;
		double srednia = 0;
		double liczba = 0;
		if (_rodzaj == rodzaj_sygnalu.DYSKRETNY) {
			for (i = 0; i < this.punktyY.size(); i++) {
				liczba = Math.abs(this.getPunktzindexu(i));
				srednia = srednia + liczba;

			}
		}

		return srednia / this.punktyY.size();
	}

	public double obl_mocsrednia(rodzaj_sygnalu _rodzaj) {
		int i;
		double moc = 0;
		if (_rodzaj == rodzaj_sygnalu.DYSKRETNY) {
			for (i = 0; i < this.punktyY.size(); i++) {
				moc = moc + (this.getPunktzindexu(i) * this.getPunktzindexu(i));

			}

		}
		return moc / this.punktyY.size();
	}

	public double obl_wartoscskuteczna(rodzaj_sygnalu _rodzaj) {
		int i;
		double moc = 0;
		if (_rodzaj == rodzaj_sygnalu.DYSKRETNY) {
			for (i = 0; i < this.punktyY.size(); i++) {
				moc = moc + (this.getPunktzindexu(i) * this.getPunktzindexu(i));

			}

		}
		return Math.sqrt(moc / this.punktyY.size());
	}

	public double obl_wariancja(rodzaj_sygnalu _rodzaj) {
		int i;
		double wariancja = 0;
		if (_rodzaj == rodzaj_sygnalu.DYSKRETNY) {
			for (i = 0; i < this.punktyY.size(); i++) {
				double _wartosc = this.getPunktzindexu(i)
						- this.obl_sredniawartosc(rodzaj_sygnalu.DYSKRETNY);
				wariancja = wariancja + (_wartosc * _wartosc);
			}
		}
		return wariancja / this.punktyY.size();
	}

	public boolean porownajSygnal(Sygnal _sygnalPorownywany) {
		return (this.t1 == _sygnalPorownywany.gett1()
				&& this.getkroczek() == _sygnalPorownywany.getkroczek() && this.d == _sygnalPorownywany
				.getd());
	}

	public void pobierzParametryUzytkownika(int _typ, double _A, double _t1,
			double _ts, double _d, double _T, double _kw, double _skok) {

		this.typ = _typ; // typ sygnału od S1 do S11
		this.A = _A;
		this.t1 = _t1; // czas początkowy
		this.ts = _ts;
		this.d = _d; // czas trwania sygnału
		this.T = _T; // okres podstawowy
		this.kw = _kw; // współczynnik wypełnienia dla sygnału prostokątnego i
						// trójkątnego

		this.skok = _skok;
		// this.kroczek = (this.t1() + this.d()) / 1000;
	}

	public double sin2piTtminT1(double _t) {
		return Math.sin(((2 * Math.PI) / this.getT()) * (_t - this.gett1()));
	}

	public double getkrok() {
		return this.krok;
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
			this.setPunktyY(punkt);
			ta = ta + this.getkroczek();
		}
	}

	public List<Double> getPunktyY() {
		return this.punktyY;
	}

	public double getPunktzindexu(int index) {
		return this.punktyY.get(index);
	}

	public void setPunktyY(double punkt) {
		this.punktyY.add(punkt);
	}

	public void setkroczek(double _kroczek) {
		this.kroczek = _kroczek;
	}

	public double getkroczek() {
		if (this.getrodzaj() == rodzaj_sygnalu.CIAGLY)
			this.kroczek = (this.gett1() + this.getd()) / 1000;
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
	 * @param punkt : double - wartość
	 * @param punkt2 : double - argument
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
	 * @return : double
	 */
	public double sygnalS1() {
		return (Math.random () * this.getA () * 2.0D) - this.getA ();
	}

	public void sygnalS1punkty(Double punktyY) {

		punktyY = (this.sygnalS1());

	}

	/**
	 * Szum gaussowski
	 * @return : double
	 */
	public double sygnalS2() {
		return random.nextGaussian () * this.getA ();
	}

	/**
	 * Sygnał sinusoidalny.
	 * @param t : double
	 * @return : double
	 */
	public double sygnalS3(double t) {

		return this.getA() * this.sin2piTtminT1(t);

	}

	public double sygnalS4(double t) {
		return (1.0 / 2.0)
				* ((this.getA() * sin2piTtminT1(t - this.gett1())) + Math
						.abs(sin2piTtminT1(t - this.gett1())));
	}

	public double sygnalS5(double t) {
		return this.getA() * Math.abs(sin2piTtminT1(t - this.gett1()));
	}

	/**
	 * Sygnał prostokątny
	 * @param t : double
	 * @return : double
	 */
	public double sygnalS6(double t) { // sygnał prostokątny

		double k = Math.floor((t - this.gett1()) / this.getT());

		// kw ustawione na sztywno
		if (k > (t - 0.5 * this.getT()) / this.getT()) {
			return this.getA();
		} else {
			return 0;
		}

	}

	public double sygnalS7(double t) {
		int k;

		for (k = -100; k < 100; k++) {
			if (t >= k * this.getT() - this.gett1()
					&& t < this.getKw() * this.getT() + k * this.getT()
							- this.gett1()) {
				return this.getA();

			} else if (t >= this.getKw() * this.getT() - this.gett1() + k
					* this.getT()
					&& t < this.getT() + k * this.getT() - this.gett1()) {
				return -this.getA();
			} else {

				continue;
			}
		}
		return 0;

	}

	public double sygnalS8(double t) {
		int k;
		for (k = -100; k < 100; k++) {
			if (t >= k * this.getT() - this.gett1()
					&& t < this.getKw() * this.getT() + k * this.getT()
							- this.gett1()) {
				return (this.getA() / (this.getKw() * this.getT()))
						* (t - k * this.getT() - this.gett1());

			} else if (t >= this.getKw() * this.getT() + this.gett1() - k
					* this.getT()
					&& t < this.getT() + k * this.getT() - this.gett1()) {
				return (-this.getA()) / (this.getT() * (1 - this.getKw()))
						* (t - k * this.getT() - this.gett1()) + this.getA()
						/ (1 - this.getKw());

			} else {

				continue;
			}
		}
		return 0;

	}

	/**
	 * Skok jednostkowy
	 * @param t : double
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

	public double sygnalS10(double t) {

		if (t == this.skok) {
			return this.getA();
		} else {
			return 0;
		}

	}

	public double sygnalS11(double t) {

		if (this.skok == 0) {
			return 0;
		} else if (random.nextInt(100) < this.skok) {
			return this.getA();
		} else {
			return 0;
		}

	}

	/**
	 * Wyczyszczenie tablicy wartości.
	 */
	public void wyczyscPunkty() {
		this.punktyY.clear();
	}

}