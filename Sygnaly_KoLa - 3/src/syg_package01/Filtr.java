package syg_package01;

public class Filtr {
	public enum filtr_przepustowosc {
		DOLNOPRZEPUSTOWY, GORNOPRZEPUSTOWY
	}

	public enum filtr_okno {
		PROSTOKATNE, HANNINGA
	}

	private filtr_przepustowosc przepustowosc;
	private filtr_okno okno;
	private int liczbaWspolczynnikow;
	private double czestotliwoscObciecia;

	public void setPrzepustowosc(filtr_przepustowosc przepustowosc) {
		this.przepustowosc = przepustowosc;
	}

	public filtr_przepustowosc getPrzepustowosc() {
		return przepustowosc;
	}

	public void setOkno(filtr_okno okno) {
		this.okno = okno;
	}

	public filtr_okno getOkno() {
		return okno;
	}

	public void setLiczbaWspolczynnikow(int liczbaWspolczynnikow) {
		this.liczbaWspolczynnikow = liczbaWspolczynnikow;
	}

	public int getLiczbaWspolczynnikow() {
		return liczbaWspolczynnikow;
	}

	public void setCzestotliwoscObciecia(double czestotliwoscObciecia) {
		this.czestotliwoscObciecia = czestotliwoscObciecia;
	}

	public double getCzestotliwoscObciecia() {
		return czestotliwoscObciecia;
	}

}
