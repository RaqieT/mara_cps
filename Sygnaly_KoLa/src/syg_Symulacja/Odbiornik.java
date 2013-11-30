package syg_Symulacja;

import java.util.Date;

import org.jfree.data.xy.XYSeries;

import syg_package01.Sygnal;

public class Odbiornik {
	public double czasPomiedzyProbkami;
	public int buforProbek;
	public Sygnal sygnalOdebrany;
	public int aktualnaProbkaPoKorelacji;
	public double czasPierwszej;
	private double jednostkaCzasowa;
	
	Odbiornik (double _czasPomiedzyProbkami, int _buforProbek, double _czasTrwaniaSygnaluNadawanego, double _jednostkaCzasowa)
	{
		this.czasPomiedzyProbkami = _czasPomiedzyProbkami;
		this.buforProbek = _buforProbek;
		if (this.sygnalOdebrany == null) this.sygnalOdebrany = new Sygnal();
		this.sygnalOdebrany.setd(_czasTrwaniaSygnaluNadawanego);
		this.aktualnaProbkaPoKorelacji = 0;
		this.jednostkaCzasowa = _jednostkaCzasowa;
		this.czasPierwszej = new Date().getTime() / _jednostkaCzasowa;
	}
	
	/**
	 * zapisuje kolejną próbkę na końcu, jeśli przekroczono bufor to usuwana pierwsza próbka 
	 * @param _wartoscProbki
	 * @param _czasOdebrania
	 */
	public void zapiszProbke (double _wartoscProbki, Date _czasOdebrania)
	{
		//jeśli bufor pełen to usuwa pierwszą daną
		if (this.sygnalOdebrany.punktyNaWYkresie.getItemCount() >= this.buforProbek)
		{
			this.sygnalOdebrany.punktyNaWYkresie.remove(0);
		}
		//zapis próbki
		this.sygnalOdebrany.punktyNaWYkresie.add(_czasOdebrania.getTime() / this.jednostkaCzasowa, _wartoscProbki);
		++this.aktualnaProbkaPoKorelacji;
	}
	
	public double podajOdebranaWartosc (Date _czasOdebrania)
	{
		// różnica czasów
		double roznicaCzasow = (_czasOdebrania.getTime() - (long)(this.sygnalOdebrany.punktyNaWYkresie.getX(0).doubleValue() * 1000.0D)) / 1e6;

		// znalezienie w zakresie trwania zapisanego sygnału
		double szukanyCzas = (roznicaCzasow / this.sygnalOdebrany.getd());
		szukanyCzas = (szukanyCzas - Math.floor(szukanyCzas) * 1.0D) * this.sygnalOdebrany.getd();
		
		return this.sygnalOdebrany.znajdzWartoscNaWykresie(szukanyCzas, this.sygnalOdebrany.punktyNaWYkresie);
	}

	public void zapiszProbke(double _wartoscProbki, double _czasOdebrania) {
		if (this.aktualnaProbkaPoKorelacji == 0) this.czasPierwszej = _czasOdebrania;
		//jeśli bufor pełen to usuwa pierwszą daną
		if (this.sygnalOdebrany.punktyNaWYkresie.getItemCount() >= this.buforProbek)
		{
			this.sygnalOdebrany.punktyNaWYkresie.remove(0);
		}
		//zapis próbki
		this.sygnalOdebrany.punktyNaWYkresie.add(_czasOdebrania, _wartoscProbki);
//		System.out.print(" | Odbiór [" + this.sygnalOdebrany.punktyNaWYkresie.getItemCount() + "]: " + _czasOdebrania + " : " + _wartoscProbki);
		++this.aktualnaProbkaPoKorelacji;
	}
	public void wyzerujPoKorelacji()
	{
		this.aktualnaProbkaPoKorelacji = 0;
	}
	public boolean moznaKorelowac()
	{
		return this.aktualnaProbkaPoKorelacji >= this.buforProbek;
	}

	public void wyzeruj() {
		this.aktualnaProbkaPoKorelacji = 0;
		this.sygnalOdebrany = new Sygnal();
	}
}
