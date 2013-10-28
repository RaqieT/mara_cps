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
	
	public UrzadzenieSprawdzajace()
	{
		this.sygnalSkorelowany = new Sygnal();
	}

//	public double sprawdzWartoscNalozenia(Nadajnik _nadajnik, Odbiornik _odbiornik) {
//		Date czasSprawdzania = new Date();
//
//		double wartosc = _nadajnik.podajWartoscProbki(czasSprawdzania)
//				+ _odbiornik.podajOdebranaWartosc(czasSprawdzania);
//
//		return wartosc;
//	}
	
	public void wykonajKorelacje(Nadajnik _nadajnik, Odbiornik _odbiornik)
	{
		Sygnal[] sygnaly = new Sygnal[2];
		sygnaly[0] = _nadajnik.zapisanySygnalNadany;
		sygnaly[1] = _odbiornik.sygnalOdebrany;
		PanelRysunek_Filtracja obliczenia = new PanelRysunek_Filtracja(sygnaly, null, 0);
		_nadajnik.zapisanySygnalNadany = obliczenia.getSygnaly()[0];
		this.sygnalSkorelowany = _nadajnik.zapisanySygnalNadany;
		//this.znajdzMaximum(this.sygnalSkorelowany.punktyZrekonstruowane);
	}

//	public boolean czyMax(double _wartosc, Date _czas) {
//		boolean znalezioneMax = false;
//		if (tablicaMaximow == null)
//			this.tablicaMaximow = new XYSeries("Maksymalne wartosci");
//		if (this.tablicaMaximow.getItemCount() == 1) {
//			this.tablicaMaximow.add(_czas.getTime() / 1000.0D, _wartosc);
//		} else {
//			if (zaokragl(_czas.getTime() / 1000.0D) > zaokragl(this.tablicaMaximow.getMinX())) {
//				if (zaokragl(_wartosc) > zaokragl(this.tablicaMaximow.getMaxY())) { // poprzednia
//																					// wartość
//																					// mniejsza
//					// this.tablicaMaximow = new
//					// XYSeries("Maksymalne wartosci");
//					znalezioneMax = false;
//				} else if (zaokragl(_wartosc) == zaokragl(this.tablicaMaximow.getMaxY())) {
//					znalezioneMax = true;
//				}
//				if (!(zaokragl(_wartosc) < zaokragl(this.tablicaMaximow.getMaxY()))) {
//					if (this.tablicaMaximow.getItemCount() > 1)
//						this.tablicaMaximow.remove(0);
//					this.tablicaMaximow.add(_czas.getTime() / 1000.0D, _wartosc);
//				}
//			}
//		}
//		return znalezioneMax;
//	}
	
	public void znajdzMaximum(XYSeries _punkty)
	{
		this.tablicaMaximow = new XYSeries("Maksymalne wartosci");
		this.tablicaMaximow.add(_punkty.getX(_punkty.indexOf(_punkty.getMaxY())), _punkty.getMaxY());
	}

	public double podajRozniceWCzasie() {
//		if (this.tablicaMaximow.getItemCount() > 1) {
//			return this.tablicaMaximow.getX(1).doubleValue() - this.tablicaMaximow.getX(0).doubleValue();
//		} else
//			return 0;
		return Math.abs(this.tablicaMaximow.getX(0).doubleValue() - this.sygnalSkorelowany.punktyZrekonstruowane.getX(this.sygnalSkorelowany.punktyZrekonstruowane.getItemCount() / 2).doubleValue());
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
}
