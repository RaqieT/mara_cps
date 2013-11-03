package syg_Symulacja;

import java.util.Date;

import org.jfree.data.xy.XYSeries;

import syg_package01.Sygnal;

public class Nadajnik {
	/**
	 * szablon dla sygnału nadawanego (musi mieć określone: kroczek, d,
	 * punktyNaWykresie)
	 */
	public Sygnal sygnalNadawany;
	/**
	 * Czas do wysłania nastepnej próbki
	 */
	public double czasPomiedzyProbkami;
	/**
	 * sygnał wysłany zapisany do określonej wielkości bufora (ilości próbek)
	 */
	public Sygnal zapisanySygnalNadany;
	public int buforProbek;
	/**
	 * Nr próbki pierwszej
	 */
	public Date czasWypuszczeniaSygnalu;
	private double jednostkaCzasowa = 1000.0D;

	public double getCzasWypuszczeniaSygnalu() {
		return czasWypuszczeniaSygnalu.getTime() / this.jednostkaCzasowa;
	}

	public void setCzasWypuszczeniaSygnalu(Date czasWypuszczeniaSygnalu) {
		this.czasWypuszczeniaSygnalu = czasWypuszczeniaSygnalu;
	}

	/**
	 * 
	 * @param _sygnalNadawany
	 *            - ciągły sygnał z określonymi wartościami (długość,
	 *            częstotliwość próbek: "kroczek")
	 * @param _czasPomiedzyProbkami
	 *            - jeśli <= 0 to wielkość pobrana z częstotliwośi próbek
	 *            sygnału
	 * @param _czyStart
	 *            - czy ustawić znacznik czasu
	 */
	public Nadajnik(Sygnal _sygnalNadawany, double _czasPomiedzyProbkami, double _jednostkaCzasowa,
			boolean _czyStart) {
		this.sygnalNadawany = new Sygnal();
		this.sygnalNadawany.kopiuj(_sygnalNadawany);
		if (_czyStart)
			this.generujPoczatek();
		if (_czasPomiedzyProbkami > 0)
			this.czasPomiedzyProbkami = _czasPomiedzyProbkami;
		else
			this.czasPomiedzyProbkami = this.sygnalNadawany.getkroczek();
		this.buforProbek = (int) (_sygnalNadawany.getd() / _sygnalNadawany.getkroczek());
		this.jednostkaCzasowa = _jednostkaCzasowa;
	}

	/**
	 * ustawienie czasu wypuszczania próbek
	 */
	public void generujPoczatek() {
		this.czasWypuszczeniaSygnalu = new Date();
	}

	/**
	 * Wartość wypuszczanego sygnału przy znanym początku
	 * 
	 * @param _czasAktualny
	 *            - czas sprawdzenia wartości dla nadawanego sygnału
	 * @return
	 */
	public double podajWartoscProbki(Date _czasAktualny) {
		// różnica czasów
		double roznicaCzasow = (_czasAktualny.getTime() - this.czasWypuszczeniaSygnalu.getTime()) / 1e6;

		// znalezienie w zakresie trwania zapisanego sygnału
		double szukanyCzas = (roznicaCzasow / this.sygnalNadawany.getd());
		szukanyCzas = (szukanyCzas - Math.floor(szukanyCzas) * 1.0D) * this.sygnalNadawany.getd();

		// znalezienie wartości w znanym czasie trwania znanego sygnału
		return this.sygnalNadawany.znajdzWartoscNaWykresie(szukanyCzas,
				this.sygnalNadawany.punktyNaWYkresie);

	}

	public void zapiszWartoscProbki(Date _czasAktualny) {
		if (this.zapisanySygnalNadany == null) {
			this.zapisanySygnalNadany.kopiuj(this.sygnalNadawany);
			this.zapisanySygnalNadany.punktyNaWYkresie = null;
		}
		if (this.zapisanySygnalNadany.punktyNaWYkresie == null)
			this.zapisanySygnalNadany.punktyNaWYkresie = new XYSeries("Sygnał nadany");
		if (this.zapisanySygnalNadany.punktyNaWYkresie.getItemCount() >= this.buforProbek)
			this.zapisanySygnalNadany.punktyNaWYkresie.remove(0);
		this.zapisanySygnalNadany.punktyNaWYkresie.add(_czasAktualny.getTime()
				/ this.jednostkaCzasowa, this.podajWartoscProbki(_czasAktualny));
	}

	public int ileProbekNaokres() {
		return this.buforProbek;
	}

	public void zapiszWartoscProbki(double _czasAktualny) {

		if (this.zapisanySygnalNadany == null) {
			this.zapisanySygnalNadany = new Sygnal();
			this.zapisanySygnalNadany.kopiuj(this.sygnalNadawany);
			this.zapisanySygnalNadany.punktyNaWYkresie = null;
			this.zapisanySygnalNadany.punktyNaWYkresie = new XYSeries("Sygnał nadany");
			this.zapisanySygnalNadany.punktyNaWYkresie.add(_czasAktualny,
					this.sygnalNadawany.punktyNaWYkresie.getY(0));
		} else

		if (this.czyMoznaZapisac(_czasAktualny)) {
			if (this.zapisanySygnalNadany.punktyNaWYkresie == null)
				this.zapisanySygnalNadany.punktyNaWYkresie = new XYSeries("Sygnał nadany");
			if (this.zapisanySygnalNadany.punktyNaWYkresie.getItemCount() >= this.buforProbek)
				this.zapisanySygnalNadany.punktyNaWYkresie.remove(0);
			this.zapisanySygnalNadany.punktyNaWYkresie.add(_czasAktualny,
					this.podajWartoscProbki(_czasAktualny));
		}
	}

	private boolean czyMoznaZapisac(double _czasAktualny) {
		double roznicaCzasow = (_czasAktualny - this.getCzasWypuszczeniaSygnalu());

		// znalezienie w zakresie trwania zapisanego sygnału
		double szukanyCzas = (roznicaCzasow / this.sygnalNadawany.getd());
		szukanyCzas = (szukanyCzas - Math.floor(szukanyCzas) * 1.0D) * this.sygnalNadawany.getd();

		return szukanyCzas > 0;
	}

	double podajWartoscProbki(double _czasAktualny) {
		return this.podajWartoscProbki(_czasAktualny, this.getCzasWypuszczeniaSygnalu());
	}

	public double podajWartoscProbki(double _czasAktualny, double _czasPierwszej) {
		// różnica czasów
		double roznicaCzasow = (_czasAktualny - _czasPierwszej);

		// znalezienie w zakresie trwania zapisanego sygnału
		double szukanyCzas = (roznicaCzasow / this.sygnalNadawany.getd());
		szukanyCzas = (szukanyCzas - Math.floor(szukanyCzas) * 1.0D) * this.sygnalNadawany.getd();

		// znalezienie wartości w znanym czasie trwania znanego sygnału
		return this.sygnalNadawany.znajdzWartoscNaWykresie(szukanyCzas,
				this.sygnalNadawany.punktyNaWYkresie);
	}
}
