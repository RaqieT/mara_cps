package syg_Symulacja;

import java.util.Date;

import org.jfree.data.xy.XYSeries;

import syg_package01.Sygnal;

public class Odbiornik {
	public double czestotliwoscOdbierania;
	public int buforProbek;
	public Sygnal sygnalOdebrany;
	
	Odbiornik (double _czestotliwoscOdbierania, int _buforProbek, double _czasTrwaniaSygnaluNadawanego)
	{
		this.czestotliwoscOdbierania = _czestotliwoscOdbierania;
		this.buforProbek = _buforProbek;
		this.sygnalOdebrany.setd(_czasTrwaniaSygnaluNadawanego);
	}
	
	public void zapiszProbke (double _wartoscProbki, Date _czasOdebrania)
	{
		//jeśli bufor pełen to usuwa pierwszą daną
		if (this.sygnalOdebrany.punktyNaWYkresie.getItemCount() >= this.buforProbek)
		{
			this.sygnalOdebrany.punktyNaWYkresie.remove(0);
		}
		//zapis próbki
		this.sygnalOdebrany.punktyNaWYkresie.add(_czasOdebrania.getTime() / 1000.0D, _wartoscProbki);
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
}
