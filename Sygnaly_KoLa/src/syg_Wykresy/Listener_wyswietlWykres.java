package syg_Wykresy;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import syg_package01.PanelFiltracja;
import syg_package01.PanelKonwersja;
import syg_package01.PanelParametry;
import syg_package01.Sygnal;
import syg_package01.Sygnal.rodzaj_sygnalu;

public class Listener_wyswietlWykres implements ActionListener {

	private Sygnal sygnal;
	private PanelParametry parametry;
	private JSplitPane splitPane;
	private PanelRysunek_Wykres rysunek;

	public Listener_wyswietlWykres(Sygnal sygnal, PanelParametry _parametry,
			PanelRysunek_Wykres _rysunek, JSplitPane _splitPane) {
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

				// przygotowanie tablicy punktów - wyczyszczenie sygnału
				if (this.sygnal.getrodzaj() == rodzaj_sygnalu.CIAGLY) {
					this.sygnal.wyczyscPunkty(true);
				}

				// jeśli powrót z panelu do konwersji
				if (this.sygnal.getrodzaj() != rodzaj_sygnalu.CIAGLY
						&& this.sygnal.getrodzaj() != rodzaj_sygnalu.DYSKRETNY) {
					this.sygnal.setRodzaj(rodzaj_sygnalu.CIAGLY);
					this.sygnal.wyczyscPunkty(true);
				}

				this.wyswietlWykres();

				// oczyszczenie innych tablic punktów (oprócz tych z wykresu)
				this.sygnal.wyczyscPunkty(false);

				// uaktywnienie wyświetlenia obliczeń
				this.parametry.btn_oblicz.setEnabled(true);

				// uaktywanienie paneli
				((JTabbedPane) this.splitPane.getComponent(1)).getTabComponentAt(1)
						.setEnabled(true);
				((JTabbedPane) this.splitPane.getComponent(1)).setEnabledAt(1, true);
				((JTabbedPane) this.splitPane.getComponent(1)).getTabComponentAt(2)
						.setEnabled(true);
				((JTabbedPane) this.splitPane.getComponent(1)).setEnabledAt(2, true);

				// przekazanie utworzonego sygnału do paneli
				((PanelKonwersja) ((JTabbedPane) this.splitPane.getComponent(1)).getComponentAt(1))
						.setSygnalKonwertowany(this.sygnal);
				// ((PanelFiltracja) ((JTabbedPane)
				// this.splitPane.getComponent(1))
				// .getComponentAt(2)).setSygnalFiltrowany(this.sygnal);

				// wyzerowanie kroku zmian
				((PanelKonwersja) ((JTabbedPane) this.splitPane.getComponent(1)).getComponentAt(1))
						.przestawKrokDoPoczatku();

			} else {
				JOptionPane.showMessageDialog(null, msgPoprawnosc, "Nie można wyświetlić wykresu",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage(), "Nie można wyświetlić wykresu",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	private void wyswietlWykres() {
		this.rysunek = new PanelRysunek_Wykres(this.sygnal);
		this.splitPane.add(rysunek, JSplitPane.RIGHT);
		sygnal = rysunek.getSygnalWyswietlany();
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
