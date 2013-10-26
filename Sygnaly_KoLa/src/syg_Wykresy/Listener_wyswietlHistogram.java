package syg_Wykresy;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import syg_package01.PanelKonwersja;
import syg_package01.PanelParametry;
import syg_package01.Sygnal;
import syg_package01.Sygnal.RodzajSygnalu;

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
		// JOptionPane.showMessageDialog(null, "Wyświetlenie...",
		// "Listener_wyswietlHistogram", JOptionPane.INFORMATION_MESSAGE);
		try {
			String msgPoprawnosc = this.parametry.sprawdzPoprawnosc();
			if (msgPoprawnosc == "ok") {
				this.sygnal = this.parametry.zapiszParametryDoSygnalu();
				if (!this.parametry.cb_wybor123.isEnabled()) {
					this.sygnal.wyczyscPunkty(true);
					// this.sygnal.setRodzaj(rodzaj_sygnalu.CIAGLY);
				}
				this.wyswietlWykres();
				// this.sygnal.setRodzaj(rodzaj_sygnalu.DYSKRETNY);

				// oczyszczenie innych tablic punktów (oprócz tych z wykresu)
				this.sygnal.wyczyscPunkty(false);

				// uaktywnienie wyświetlenia obliczeń
				this.parametry.btn_oblicz.setEnabled(true);

				// uaktywanienie panelu konwersji
				((JTabbedPane) this.splitPane.getComponent(1)).getTabComponentAt(1)
						.setEnabled(true);
				((JTabbedPane) this.splitPane.getComponent(1)).setEnabledAt(1, true);

				// przekazanie utworzonego sygnału do panelu konwersji
				((PanelKonwersja) ((JTabbedPane) this.splitPane.getComponent(1)).getComponentAt(1))
						.setSygnalKonwertowany(this.sygnal);
				// wyzerowanie kroku zmian
				((PanelKonwersja) ((JTabbedPane) this.splitPane.getComponent(1)).getComponentAt(1))
						.przestawKrokDoPoczatku();
			} else {
				JOptionPane.showMessageDialog(null, msgPoprawnosc,
						"Nie można wyświetlić histogramu", JOptionPane.ERROR_MESSAGE);
			}
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage(), "Nie można wyświetlić histogramu",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	private void wyswietlWykres() {
		iloscPrzedzialow += 5;
		if (iloscPrzedzialow > 20)
			iloscPrzedzialow = 5;
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
