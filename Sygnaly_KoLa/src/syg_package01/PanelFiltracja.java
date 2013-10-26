package syg_package01;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import syg_Symulacja.Listener_symuluj;
import syg_Szablony.PanelObslugi;
import syg_Wykresy.Listener_filtruj;
import syg_Wykresy.PanelRysunek_Filtracja;
import syg_package01.Filtr.filtr_okno;
import syg_package01.Filtr.filtr_przepustowosc;
import syg_package01.Sygnal.RodzajSygnalu;

@SuppressWarnings("serial")
public class PanelFiltracja extends PanelObslugi {

	/**
	 * Auto-generated main method to display this JPanel inside a new JFrame.
	 */

	private Sygnal sygnalFiltrowany;
	private String[] labelsFiltry;
	private JComboBox<String> cb_filtr;

	public filtr_przepustowosc getCb_filtr() {
		switch (cb_filtr.getSelectedIndex()) {
		case 0:
			return filtr_przepustowosc.DOLNOPRZEPUSTOWY;
		case 1:
			return filtr_przepustowosc.GORNOPRZEPUSTOWY;
		default:
			return null;
		}
	}

	private String[] labelsOkna;
	private JComboBox<String> cb_okno;

	public filtr_okno getCb_okno() {
		switch (cb_okno.getSelectedIndex()) {
		case 0:
			return filtr_okno.PROSTOKATNE;
		case 1:
			return filtr_okno.HANNINGA;
		default:
			return null;
		}
	}

	private JLabel lbl_wspolczynniki;
	private JSpinner txt_wspolczynniki;

	public int getTxt_wspolczynniki() {
		return (Integer) txt_wspolczynniki.getValue();
	}

	public void setTxt_wspolczynniki(int _liczbaWsp) {
		this.txt_wspolczynniki.setValue(_liczbaWsp);
	}

	private JLabel lbl_obciecie;
	private JSpinner txt_obciecie;

	public double getTxt_obciecie() {
		return (Double) txt_obciecie.getValue();
	}

	public void setTxt_obciecie(double _czestotliwosc) {
		this.txt_obciecie.setValue(_czestotliwosc);
	}
	/**
	 * Ustawienie kroku przy zmianie częstotliwości obcięcia strzałkami
	 * @param _krok
	 */
	public void setControlStepDouble (double _krok)
	{
		setControlStep(this.txt_obciecie, _krok);
	}
	public void setControlStepInt (int _krok)
	{
		setControlStep(this.txt_wspolczynniki, _krok);
	}

	private JButton btn_filtracja;
	public JButton getBtn_filtracja() {
		return btn_filtracja;
	}

	public void setBtn_filtracja(JButton btn_filtracja) {
		this.btn_filtracja = btn_filtracja;
	}

	private String[] labelsTytuly;
	private JLabel lbl_TytulFltracja;
	private JLabel lbl_syg1;
	private JFormattedTextField txt_syg1;

	public String getTxt_syg1() {
		return txt_syg1.getText();
	}

	public void setTxt_syg1(String _txt) {
		this.txt_syg1.setText(_txt);
	}

	private JLabel lbl_syg2;
	private JFormattedTextField txt_syg2;
	private Sygnal sygnalDoKorelacji;
	private JCheckBox cb_syg2;

	public JCheckBox getCb_syg2() {
		return cb_syg2;
	}

	public void setCb_syg2(JCheckBox cb_syg2) {
		this.cb_syg2 = cb_syg2;
	}

	private JCheckBox cb_syg1;
	private JButton btn_symulacja;

	public JCheckBox getCb_syg1() {
		return cb_syg1;
	}

	public void setCb_syg1(JCheckBox cb_syg1) {
		this.cb_syg1 = cb_syg1;
	}

	public Sygnal getSygnalDoKorelacji() {
		return sygnalDoKorelacji;
	}

	public void setSygnalDoKorelacji(Sygnal sygnalDoKorelacji) {
		this.sygnalDoKorelacji = sygnalDoKorelacji;
	}

	public String getTxt_syg2() {
		return txt_syg2.getText();
	}

	public void setTxt_syg2(String _txt) {
		this.txt_syg2.setText(_txt);
	}

	public PanelFiltracja(Sygnal _sygnalFiltrowany, JSplitPane _splitPane) {
		super();
		this.setSygnalFiltrowany(_sygnalFiltrowany);
		this.setSplitPane(_splitPane);
		initGUI();
		this.btn_filtracja.setEnabled(sygnalFiltrowany!= null && sygnalFiltrowany.getRodzaj() != RodzajSygnalu.CIAGLY);
	}

	private void initGUI() {
		try {
			ustawLayoutOgolny();

			this.labelsFiltry = new String[] { "dolnoprzepustowy", "górnoprzepustowy" };
			this.labelsOkna = new String[] { "prostokątne", "Hanninga" };
			this.labelsTytuly = new String[] { "Filtr:", "Okno:" };

			int nrTxt = 0;

			this.lbl_TytulFltracja = this.ustawLabel(lbl_TytulFltracja, labelsTytuly[0], nrTxt, 1,
					GridBagConstraints.EAST);
			nrTxt++;

			this.cb_filtr = this.ustawComboBox(this.cb_filtr, this.labelsFiltry, nrTxt, null, 1,
					GridBagConstraints.CENTER);
			nrTxt++;
			nrTxt++;

			this.lbl_TytulFltracja = this.ustawLabel(lbl_TytulFltracja, labelsTytuly[1], nrTxt, 1,
					GridBagConstraints.EAST);
			nrTxt++;

			this.cb_okno = this.ustawComboBox(this.cb_okno, this.labelsOkna, nrTxt, null, 1,
					GridBagConstraints.CENTER);
			nrTxt++;
			nrTxt++;

			this.lbl_wspolczynniki = this.ustawLabel(lbl_wspolczynniki, "liczba współczynników",
					nrTxt, 1, GridBagConstraints.EAST);
			this.txt_wspolczynniki = this.ustawSpinner(txt_wspolczynniki, "", nrTxt, 1, 1,
					GridBagConstraints.WEST, true);
			nrTxt++;

			this.lbl_obciecie = new JLabel();
			this.lbl_obciecie = this.ustawLabel(lbl_obciecie, "częstotliwość obcięcia", nrTxt, 1,
					GridBagConstraints.EAST);
			this.txt_obciecie = this.ustawSpinnerD(txt_obciecie, "", nrTxt, 1, 1,
					GridBagConstraints.WEST, false);
			nrTxt++;

			{
				separator = new JSeparator();
				this.add(separator, new GridBagConstraints(0, nrTxt, 2, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0,
								0, 0), 0, 0));
				nrTxt++;
			}

			this.btn_filtracja = this.ustawButton(btn_filtracja, "Wyświetl", nrTxt, 2,
					GridBagConstraints.CENTER);
			btn_filtracja.addActionListener(new Listener_filtruj(this.sygnalFiltrowany,
					new Filtr(), this, new PanelRysunek_Filtracja(), this.splitPane));
			nrTxt++;

			this.lbl_syg1 = this
					.ustawLabel(lbl_syg1, "Sygnał 1", nrTxt, 1, GridBagConstraints.EAST);
			this.cb_syg1 = this.ustawCheckBox(cb_syg1, null, nrTxt, 1);
			this.cb_syg1.setSelected(true);
			nrTxt++;

			this.lbl_syg2 = this
					.ustawLabel(lbl_syg2, "Sygnał 2", nrTxt, 1, GridBagConstraints.EAST);
			this.cb_syg2 = this.ustawCheckBox(cb_syg2, null, nrTxt, 1);
			this.cb_syg2.setEnabled(false);
			nrTxt++;
			
			{
				separator = new JSeparator();
				this.add(separator, new GridBagConstraints(0, nrTxt, 2, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0,
								0, 0), 0, 0));
				nrTxt++;
			}

			this.btn_symulacja = this.ustawButton(btn_symulacja, "Symulacja", nrTxt, 2,
					GridBagConstraints.CENTER);
			btn_symulacja.addActionListener(new Listener_symuluj(this));
			nrTxt++;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setSygnalFiltrowany(Sygnal sygnalFiltrowany) {
		this.sygnalFiltrowany = sygnalFiltrowany;
	}

	public Sygnal getSygnalFiltrowany() {
		return sygnalFiltrowany;
	}

	/**
	 * Sprawdzenie: <li>poprawności pól do wypełnienia przy wybrarym jednym z
	 * sygnałów <li>czy wybrano jakikolwiek sygnał
	 * 
	 * @return
	 */
	public String sprawdzPoprawnosc() {
		String msgPoprawnosci = "<nie zdefiniowano poprawnych waruków>";

		if (this.cb_syg1.isSelected() && this.cb_syg2.isSelected()) {
			msgPoprawnosci = "ok + brak pól";
			if (this.getTxt_obciecie() > 0 || this.getTxt_wspolczynniki() > 0)
				msgPoprawnosci = "ok";
		} else if (!this.cb_syg1.isSelected() && !this.cb_syg2.isSelected()) {
			msgPoprawnosci = "Musisz wybrać sygnał lub sygnały.";
		} else {
			if (this.getTxt_obciecie() <= 0 || this.getTxt_wspolczynniki() <= 0)
				msgPoprawnosci = "Niepoprawnie wypełnione pola.";
			else {
				msgPoprawnosci = "ok";
			}
		}

		return msgPoprawnosci;
	}

}
