package syg_package01;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JSplitPane;

import syg_package01.Sygnal.rodzaj_sygnalu;

public class Listener_wyswietlHistogram implements ActionListener {

	private Sygnal sygnal;
	private PanelParametry parametry;
	private JSplitPane splitPane;
	private PanelRysunek_Histogram rysunek;
	private int iloscPrzedzialow = 5;

	public Listener_wyswietlHistogram(Sygnal sygnal, PanelParametry _parametry,
			PanelRysunek_Histogram _rysunek, JSplitPane _splitPane) {
		this.setSygnal(sygnal);
		this.setParametry(_parametry);
		this.rysunek = _rysunek;
		this.splitPane = _splitPane;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		try {
			String msgPoprawnosc = this.parametry.sprawdzPoprawnosc();
			if (msgPoprawnosc == "ok") {
				this.sygnal = this.parametry.zapiszParametryDoSygnalu();
				if (!this.parametry.cb_wybor123.isEnabled()) {
					this.sygnal.wyczyscPunkty();
					this.sygnal.setRodzaj(rodzaj_sygnalu.CIAGLY);
				}
				this.wyswietlWykres();
				this.sygnal.setRodzaj(rodzaj_sygnalu.DYSKRETNY);
				this.parametry.btn_oblicz.setEnabled(true);
			} else {
				JOptionPane.showMessageDialog(null, msgPoprawnosc,
						"Nie można wyświetlić wykresu",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage(),
					"Nie można wyświetlić wykresu", JOptionPane.ERROR_MESSAGE);
		}

	}

	private void wyswietlWykres() {
		iloscPrzedzialow += 5;
		if (iloscPrzedzialow > 20) iloscPrzedzialow = 5;
		this.rysunek = new PanelRysunek_Histogram(this.sygnal, iloscPrzedzialow);
		this.splitPane.add(rysunek, JSplitPane.RIGHT);
	}

	public void setParametry(PanelParametry parametry) {
		this.parametry = parametry;
	}

	public PanelParametry getParametry() {
		return parametry;
	}

	public void setSygnal(Sygnal _sygnal) {
		this.sygnal = _sygnal;
	}

	public Sygnal getSygnal() {
		return sygnal;
	}

}
