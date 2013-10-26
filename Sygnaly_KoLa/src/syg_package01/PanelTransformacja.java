package syg_package01;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;
import javax.swing.JFrame;

import syg_Szablony.PanelObslugi;
import syg_Wykresy.Listener_transformuj;
import syg_Wykresy.Listener_wyswietlWykres;
import syg_package01.Sygnal.RodzajSygnalu;

public class PanelTransformacja extends PanelObslugi {

	private String[] labelsTryb;
	private String[] labelsTytuly;
	private JLabel lbl_TytulTryb;
	private JComboBox<String> cb_tryb;

	public int getCb_tryb() {
		return cb_tryb.getSelectedIndex();
	}

	public void setCb_tryb(int _index) {
		this.cb_tryb.setSelectedIndex(_index);
	}

	private Sygnal sygnal;
	private JLabel lbl_TytulTransformacja;
	private JComboBox<String> cb_transformacja;

	public int getCb_transformacja() {
		return cb_transformacja.getSelectedIndex();
	}

	public void setCb_transformacja(int _index) {
		this.cb_transformacja.setSelectedIndex(_index);
	}

	private String[] labelsTransformacja;
	private JButton btn_transformacja;
	private JLabel lbl_nrTrybu;
	private Listener_wyborTrybu actionListener;
	private JLabel lbl_Opis;
	private String[] labelsOpis;
	private ItemListener actionListener2;
	private ActionListener actionListener_tr;

	/**
	 * Auto-generated main method to display this JPanel inside a new JFrame.
	 */

	public PanelTransformacja() {

	}

	public PanelTransformacja(Sygnal _sygnal, JSplitPane _jSplitPane1) {
		super();
		this.setSygnal(_sygnal);
		this.splitPane = _jSplitPane1;
		initGUI();
	}

	private void initGUI() {
		try {
			String style1_open = "<html><body><div "
					+ "style='margin-top: 4px; text-align: right; width: 100%; display: block; vertical-align: top;'>";
			String style1_close = "</div></body></html>";

			this.labelsTytuly = new String[] { "Tryb prezentacji wykresów:",
					"Rodzaj transformacji:" };

			this.labelsTryb = new String[] { "<wybierz tryb>", "część rzeczywista/urojona",
					"moduł liczby zespolonej/argument w funkcji częstotliwości" };
			this.labelsTransformacja = new String[] { "bez transformacji",
					"dyskretna transformacja Fouriera", "transformacja Walsha-Hadamarda" };
			this.labelsOpis = new String[] {
					style1_open + "" + style1_close,
					style1_open
							+ "<b>dyskretna transformacja Fouriera</b> – algorytm z definicji oraz szybka transformacja Fouriera z decymacją w dziedzinie częstotliwości (DIF FFT)"
							+ style1_close,
					style1_open
							+ "<b>transformacja Walsha-Hadamarda</b><br>oraz szybka transformacja Walsha-Hadamarda"
							+ style1_close };

			ustawLayoutOgolny();

			int nrTxt = 0;

			{
				this.lbl_TytulTryb = this.ustawLabel(lbl_TytulTryb, labelsTytuly[0], nrTxt, 1,
						GridBagConstraints.EAST);
				nrTxt++;
				lbl_nrTrybu = new JLabel();
				this.cb_tryb = this.ustawComboBox(this.cb_tryb, this.labelsTryb, nrTxt,
						lbl_nrTrybu, "W", 1, GridBagConstraints.CENTER);
				actionListener = new Listener_wyborTrybu(lbl_nrTrybu, this);
				cb_tryb.addItemListener(actionListener);
				nrTxt++;
				nrTxt++;
			}
			{
				this.lbl_TytulTransformacja = this.ustawLabel(lbl_TytulTransformacja,
						labelsTytuly[1], nrTxt, 1, GridBagConstraints.EAST);
				nrTxt++;
				this.cb_transformacja = this.ustawComboBox(this.cb_transformacja,
						this.labelsTransformacja, nrTxt, null, 1, GridBagConstraints.CENTER);
				nrTxt++;
				this.lbl_Opis = this.ustawLabel(lbl_Opis, labelsOpis[0], nrTxt, 1, 6,
						GridBagConstraints.EAST);
				nrTxt++;
				nrTxt++;
				nrTxt++;
				nrTxt++;
				nrTxt++;
				nrTxt++;
				actionListener2 = new Listener_wyborTransformacji(lbl_Opis, labelsOpis, this);
				cb_transformacja.addItemListener(actionListener2);
			}
			{
				JSeparator sep_01 = new JSeparator();
				this.add(sep_01, new GridBagConstraints(0, nrTxt, 2, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0,
								0, 0), 0, 0));
				nrTxt++;
			}
			{
				this.btn_transformacja = this.ustawButton(this.btn_transformacja,
						"Wykonaj transformację", nrTxt, 2, GridBagConstraints.CENTER);
				actionListener_tr = new Listener_transformuj(this.sygnal, this, this.splitPane);
				btn_transformacja.addActionListener(actionListener_tr);
				nrTxt++;
				nrTxt++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setSygnal(Sygnal sygnal) {
		this.sygnal = sygnal;
	}

	public Sygnal getSygnal() {
		return sygnal;
	}

	public String sprawdzPoprawnosc() {
		String msgPoprawnosci = "<nie zdefiniowano poprawnych waruków>";

		if (this.getCb_transformacja() > 0 && this.getCb_tryb() > 0) {
			if (sygnal.getRodzaj() == RodzajSygnalu.DYSKRETNY) {
				msgPoprawnosci = "ok";
			}
			else {
				msgPoprawnosci = "Sygnał nie jest zdyskretyzowany. ";
			}
		} else {
			msgPoprawnosci = "Nie wybrano trybu lub transformacji.";
		}
		
		return msgPoprawnosci;
	}

}
