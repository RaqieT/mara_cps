package syg_Symulacja;

import java.util.Date;

import org.jfree.data.xy.XYSeries;

import syg_package01.Sygnal;

public class Nadajnik {
	public Sygnal sygnalNadawany;
	public double czestotliwoscNadawania;
	public Sygnal zapisanySygnalNadany;
	public int buforProbek;
	/**
	 * Nr próbki pierwszej
	 */
	public Date czasWypuszczeniaSygnalu;

	public Nadajnik(Sygnal _sygnalNadawany, double _czestotliwoscNadawania, boolean _czyStart) {
		this.sygnalNadawany = _sygnalNadawany;
		if (_czyStart)
			this.generujPoczatek();
		if (_czestotliwoscNadawania > 0)
			this.czestotliwoscNadawania = _czestotliwoscNadawania;
		else
			this.czestotliwoscNadawania = this.sygnalNadawany.getkroczek();
	}

	private void generujPoczatek() {
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
	
	public void zapiszWartoscProbki(Date _czasAktualny)
	{
		if (this.zapisanySygnalNadany.punktyNaWYkresie == null) this.zapisanySygnalNadany.punktyNaWYkresie = new XYSeries("Sygnał nadany");
		if (this.zapisanySygnalNadany.punktyNaWYkresie.getItemCount() >= this.buforProbek) this.zapisanySygnalNadany.punktyNaWYkresie.remove(0);
		this.zapisanySygnalNadany.punktyNaWYkresie.add(_czasAktualny.getTime() / 1000.0D, this.podajWartoscProbki(_czasAktualny));
	}
}
