package syg_Wykresy;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import syg_package01.PanelKonwersja;
import syg_package01.Sygnal;

public class Listener_konwertuj implements ActionListener {

	private Sygnal sygnal;
	private PanelKonwersja konwersja;
	private JSplitPane splitPane;
	private PanelRysunek_Konwersja rysunek;

	public Listener_konwertuj(Sygnal sygnal, PanelKonwersja _konwersja,
			PanelRysunek_Konwersja _rysunek, JSplitPane _splitPane) {
		this.setSygnal(sygnal);
		this.setKonwersja(_konwersja);
		this.rysunek = _rysunek;
		this.splitPane = _splitPane;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		try {
			String msgPoprawnosc = this.konwersja.sprawdzPoprawnosc();
			if (this.konwersja.getKrokZmian() == 1) {

				// przestawienie na panel parametrów
				((JTabbedPane) this.splitPane.getComponent(1)).setSelectedIndex(0);
				((JTabbedPane) this.splitPane.getComponent(1)).getTabComponentAt(0)
						.setEnabled(true);
				((JTabbedPane) this.splitPane.getComponent(1)).setEnabledAt(0, true);
				((JTabbedPane) this.splitPane.getComponent(1)).getTabComponentAt(1).setEnabled(
						false);
				((JTabbedPane) this.splitPane.getComponent(1)).setEnabledAt(1, false);

				// wyzerowanie kroku zmian
				((PanelKonwersja) ((JTabbedPane) this.splitPane.getComponent(1)).getComponentAt(1))
						.przestawKrokDoPoczatku();
			} else {
				if (msgPoprawnosc == "ok") {
					// this.sygnal = this.parametry.zapiszParametryDoSygnalu();
					// if (!this.parametry.cb_wybor123.isEnabled()) {
					// this.sygnal.wyczyscPunkty();
					// this.sygnal.setRodzaj(rodzaj_sygnalu.CIAGLY);
					// }

					// blokada panelu z parametrami sygnału
					((JTabbedPane) this.splitPane.getComponent(1)).getTabComponentAt(0).setEnabled(
							false);
					((JTabbedPane) this.splitPane.getComponent(1)).setEnabledAt(0, false);

					this.setSygnal(this.konwersja.getSygnalKonwertowany());

					// próbkowanie czy kwantyzacja
					int czyProbkowanie = -1;
					if (this.konwersja.getKrokZmian() == 0) {

						czyProbkowanie = JOptionPane.showConfirmDialog(
								null,
								this.konwersja.getLabels()[0] + " - Yes\n"
										+ this.konwersja.getLabels()[1] + " - No",
								"Wybór konwersji", JOptionPane.YES_NO_OPTION);

					}

					int czyInterpolacja = -1;
					// interpolacja czy sinc
					if (this.konwersja.getKrokZmian() == 2) {

						czyInterpolacja = JOptionPane.showConfirmDialog(
								null,
								this.konwersja.getLabels()[2] + " - Yes\n"
										+ this.konwersja.getLabels()[3] + " - No",
								"Wybór konwersji", JOptionPane.YES_NO_OPTION);

					}

					// ustalenie częstotliwości próbkowania
					if (czyProbkowanie == 0) {
						double f_0 = 1.0D / this.sygnal.getT();
						JFormattedTextField f_s = new JFormattedTextField(Double.TYPE);
						f_s.setValue(new Double(1000.0));
						f_s.setColumns(5);

						JComponent[] inputs = new JComponent[] {
								new JLabel("Częstotliwość próbkowania:"), f_s };
						do {
							JOptionPane.showMessageDialog(null, inputs, "Próbkowanie",
									JOptionPane.PLAIN_MESSAGE);

							if (f_s.isValid() || !f_s.getText().isEmpty()) {
								this.sygnal.setkrok(this.sygnal.getT()
										/ (Double.parseDouble(f_s.getText()) / f_0));
							} else {
								JOptionPane.showMessageDialog(null, "Niewłaściwa wartość", "Błąd",
										JOptionPane.ERROR_MESSAGE);
							}
						} while (f_s.isValid() && !f_s.getText().isEmpty());
					}

					// ustalenie poziomu kwantyzacji
					if (czyProbkowanie == 1) {
						JFormattedTextField poziomKwantyzacji = new JFormattedTextField(
								Integer.TYPE);
						poziomKwantyzacji.setValue(new Integer(2));
						poziomKwantyzacji.setColumns(5);

						JComponent[] inputs = new JComponent[] { new JLabel("Poziom kwantyzacji:"),
								poziomKwantyzacji };
						do {
							JOptionPane.showMessageDialog(null, inputs, "Parametry kwantyzacji",
									JOptionPane.PLAIN_MESSAGE);

							if (poziomKwantyzacji.isValid()
									|| !poziomKwantyzacji.getText().isEmpty()) {
								this.sygnal.set_poziom_kwantyzacji(Integer
										.parseInt(poziomKwantyzacji.getText()));
							} else {
								JOptionPane.showMessageDialog(null, "Niewłaściwa wartość", "Błąd",
										JOptionPane.ERROR_MESSAGE);
							}
						} while (poziomKwantyzacji.isValid()
								&& !poziomKwantyzacji.getText().isEmpty());
					}

					if (czyProbkowanie == 0 || czyInterpolacja == 0)
						this.wyswietlWykres(this.konwersja.getKrokZmian());
					else
						this.wyswietlWykres(this.konwersja.getKrokZmian() + 1);
					// próbkowanie
					if (czyProbkowanie == 0) {

						// konwersja

						this.konwersja.przestawKrok(2);
					}

					// kwantyzacja
					if (czyProbkowanie == 1) {

						// konwersja
						this.konwersja.przestawKrok(-1);
					}

					// interpolacja
					if (czyInterpolacja == 0) {

						// konwersja
						this.konwersja.przestawKrok(-1);
					}

					// rekonstrukcja sinc
					if (czyInterpolacja == 1) {
						// konwersja
						this.konwersja.przestawKrok(-1);

					}

					this.konwersja.cb_wyborMiary.setEnabled(true);
					this.konwersja.cb_wyborMiary.setSelectedIndex(0);

				} else {
					JOptionPane.showMessageDialog(null, msgPoprawnosc,
							"Nie można wyświetlić wykresu", JOptionPane.ERROR_MESSAGE);
				}
			}
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage(), "Nie można wyświetlić wykresu",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void wyswietlWykres(int _ktorePrzeksztalcenie) {
		if (_ktorePrzeksztalcenie == 0 || _ktorePrzeksztalcenie == 1)
			this.rysunek = new PanelRysunek_Konwersja(this.sygnal, _ktorePrzeksztalcenie);
		else {
			this.rysunek.setSygnalPodstawowy(this.sygnal);
			this.rysunek.setNrPrzeksztalcenia(_ktorePrzeksztalcenie);
			this.rysunek.rysuj();
		}
		this.splitPane.add(rysunek, JSplitPane.RIGHT);
	}

	public void setSygnal(Sygnal _sygnal) {
		this.sygnal = _sygnal;
	}

	public Sygnal getSygnal() {
		return sygnal;
	}

	public void setKonwersja(PanelKonwersja _konwersja) {
		this.konwersja = _konwersja;
	}

	public PanelKonwersja getKonwersja() {
		return konwersja;
	}

}
