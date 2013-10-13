package syg_Wykresy;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JSplitPane;

import syg_package01.Filtr;
import syg_package01.PanelFiltracja;
import syg_package01.Sygnal;

public class Listener_filtruj implements ActionListener {

	private Sygnal sygnal;
	private Sygnal sygnal2;

	public Sygnal getSygnal2() {
		return sygnal2;
	}

	public void setSygnal2(Sygnal sygnal2) {
		this.sygnal2 = sygnal2;
	}

	private PanelFiltracja filtracja;
	private JSplitPane splitPane;
	private PanelRysunek_Filtracja rysunek;
	private Filtr filtr;

	private String[] opcjeKorelacji = new String[] { "Implementacja bezpośrednia",
			"Implementacja z użyciem splotu" };

	public Listener_filtruj(Sygnal sygnal, Filtr _filtr, PanelFiltracja _filtracja,
			PanelRysunek_Filtracja _rysunek, JSplitPane _splitPane) {
		this.setSygnal(sygnal);
		this.filtr = _filtr;
		this.setFiltracja(_filtracja);
		this.rysunek = _rysunek;
		this.setSplitPane(_splitPane);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try {
			String msgPoprawnosc = this.filtracja.sprawdzPoprawnosc();
			if (msgPoprawnosc == "ok" || msgPoprawnosc == "ok + brak pól") {

				// if (!this.filtracja.getCb_syg1().isSelected()
				// && !this.filtracja.getCb_syg2().isSelected()) {
				// JOptionPane.showMessageDialog(null, "Musisz wybrać sygnał.",
				// "Błąd",
				// JOptionPane.ERROR_MESSAGE);
				// this.splitPane.add(new PanelRysunek_Filtracja(),
				// JSplitPane.RIGHT);
				// } else {

				// jeśli wybrano sygnał 1
				if (this.filtracja.getCb_syg1().isSelected()
						&& !this.filtracja.getCb_syg2().isSelected()) {
					this.setFiltr(this.filtracja);
					this.setSygnal(this.filtracja.getSygnalFiltrowany());
					if (this.filtr != null && this.sygnal != null)
						// filtracja
						this.wyswietlWykres(sygnal);
					else {
						JOptionPane.showMessageDialog(null, "Brak "
								+ (this.filtr == null ? "filtra." : "sygnału 1."), "Błąd",
								JOptionPane.ERROR_MESSAGE);
					}
				}

				// jeśli wybrano sygnał 2
				if (this.filtracja.getCb_syg2().isSelected()
						&& !this.filtracja.getCb_syg1().isSelected()) {
					this.setFiltr(this.filtracja);
					this.setSygnal2(this.filtracja.getSygnalDoKorelacji());
					if (this.filtr != null && this.sygnal2 != null)
						// filtracja
						this.wyswietlWykres(sygnal2);
					else {
						JOptionPane.showMessageDialog(null, "Brak "
								+ (this.filtr == null ? "filtra." : "sygnału 2."), "Błąd",
								JOptionPane.ERROR_MESSAGE);
					}
				}

				// jeśli wybrano oba sygnały
				if (this.filtracja.getCb_syg1().isSelected()
						&& this.filtracja.getCb_syg2().isSelected()) {
					this.setSygnal(this.filtracja.getSygnalFiltrowany());
					this.setSygnal2(this.filtracja.getSygnalDoKorelacji());
					if (this.sygnal != null && this.sygnal2 != null) {
						// filtracja
						int ktoraOpcja = -1;
						ktoraOpcja = JOptionPane.showConfirmDialog(null, this.opcjeKorelacji[0]
								+ " - Yes\n" + this.opcjeKorelacji[1] + " - No", "Wybór korelacji",
								JOptionPane.YES_NO_OPTION);
						if (ktoraOpcja > -1) {
							if (ktoraOpcja == 0
									|| (ktoraOpcja == 1 && msgPoprawnosc == "ok")) {
								if (ktoraOpcja == 1) {
									this.setFiltr(this.filtracja, ktoraOpcja);
								}
								this.wyswietlWykres(sygnal, sygnal2, ktoraOpcja);
							} else {
								JOptionPane.showMessageDialog(null, "Niepoprawnie wypełnione pola",
										"Nie można wyświetlić wykresu", JOptionPane.ERROR_MESSAGE);
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "Brak "
								+ (this.filtr == null ? "filtra." : "sygnałów ."), "Błąd",
								JOptionPane.ERROR_MESSAGE);
					}
				}
				// }

			} else {
				JOptionPane.showMessageDialog(null, msgPoprawnosc, "Nie można wyświetlić wykresu",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (HeadlessException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e1.getMessage(),
					"Nie można wyświetlić wykresu.\n\n" + e1.getMessage(),
					JOptionPane.ERROR_MESSAGE);
		}

	}

	private void setFiltr(PanelFiltracja _filtracja, int _czyOpcja1) {
		this.filtr = new Filtr();
		if (_filtracja != null) {
			this.filtr.setCzestotliwoscObciecia(_filtracja.getTxt_obciecie());
			if (_czyOpcja1 != 1) this.filtr.setLiczbaWspolczynnikow(_filtracja.getTxt_wspolczynniki());
			this.filtr.setOkno(_filtracja.getCb_okno());
			this.filtr.setPrzepustowosc(_filtracja.getCb_filtr());
		}
	}
	private void setFiltr(PanelFiltracja _filtracja) {
		setFiltr (_filtracja, -1);
	}

	private void wyswietlWykres(Sygnal _sygnal1, Sygnal _sygnal2, int _ktoraOpcja) {
		Sygnal[] sygnaly;
		if (_sygnal2 == null) {
			sygnaly = new Sygnal[1];
			sygnaly[0] = _sygnal1;
			this.rysunek = new PanelRysunek_Filtracja(sygnaly, filtr, _ktoraOpcja);
			this.splitPane.add(rysunek, JSplitPane.RIGHT);
		} else {
			sygnaly = new Sygnal[2];
			sygnaly[0] = _sygnal1;
			sygnaly[1] = _sygnal2;
			this.rysunek = new PanelRysunek_Filtracja(sygnaly, filtr, _ktoraOpcja);
			this.splitPane.add(rysunek, JSplitPane.RIGHT);
		}

	}

	private void wyswietlWykres(Sygnal _sygnal) {

		this.wyswietlWykres(_sygnal, null, 0);
	}

	public void setSygnal(Sygnal sygnal) {
		this.sygnal = sygnal;
	}

	public Sygnal getSygnal() {
		return sygnal;
	}

	public void setFiltracja(PanelFiltracja filtracja) {
		this.filtracja = filtracja;
	}

	public PanelFiltracja getFiltracja() {
		return filtracja;
	}

	public void setSplitPane(JSplitPane splitPane) {
		this.splitPane = splitPane;
	}

	public JSplitPane getSplitPane() {
		return splitPane;
	}

}
