package syg_package01;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import syg_Obliczenia.Listener_obliczMiary;
import syg_Wykresy.Listener_konwertuj;
import syg_Wykresy.PanelRysunek_Konwersja;
import syg_package01.Sygnal.rodzaj_sygnalu;

@SuppressWarnings("serial")
public class PanelKonwersja extends javax.swing.JPanel {

	private GridBagLayout thisLayout;
	private Sygnal sygnalKonwertowany;
	private JSplitPane splitPane;
	private String[] labels;

	public String[] getLabels() {
		return labels;
	}

	public void setLabels(String[] labels) {
		this.labels = labels;
	}

	private String[] labels2;
	private Dimension szerokosc_cb;
	private JButton btn_probkowanie;
	private int krokZmian = 0;

	public int getKrokZmian() {
		return krokZmian;
	}

	public void setKrokZmian(int krokZmian) {
		this.krokZmian = krokZmian;
	}

	public JComboBox<String> cb_wyborMiary;
	private JLabel lbl_tytul;
	private JLabel lbl_krok;
	private JLabel lbl_tytulKroku;
	private JSeparator separator;
	private JFormattedTextField txt_wartoscMiary;
	private String[] labels_btn;

	/**
	 * Auto-generated main method to display this JPanel inside a new JFrame.
	 * 
	 * @param _jSplitPane1
	 * @param _listaSygnalow
	 */

	public PanelKonwersja(Sygnal _sygnal, JSplitPane _splitPane) {
		super();
		this.setSygnalKonwertowany(_sygnal);
		this.setSplitPane(_splitPane);
		krokZmian = 0;
		initGUI();
	}

	private JLabel ustawLabel(JLabel _lbl, String _txt, int _id) {
		_lbl = new JLabel();
		this.add(_lbl, new GridBagConstraints(0, _id, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
		_lbl.setName(_lbl.toString());
		_lbl.setText(_txt);

		return _lbl;
	}

	private JButton ustawButton(JButton _btn, String _txt, int _id) {
		_btn = new JButton();
		this.add(_btn, new GridBagConstraints(0, _id, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		_btn.setName(_btn.toString());
		_btn.setText(_txt);

		return _btn;
	}

	private JFormattedTextField ustawTextField(JFormattedTextField _tf, String _txt, int _id) {

		_tf = new JFormattedTextField(Double.TYPE);
		_tf.setValue(new Double(0.00));
		_tf.setColumns(5);
		_tf.setEditable(true);
		this.add(_tf, new GridBagConstraints(0, _id, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		_tf.setMinimumSize(new Dimension(60, 20));
		_tf.setMaximumSize(new Dimension(60, 20));
		_tf.setName(_tf.toString());
		_tf.setText(_txt);

		return _tf;
	}

	@SuppressWarnings("unchecked")
	private JComboBox<String> ustawComboBox(JComboBox<String> _cb, String[] _labels, int _id,
			JLabel _lbl) {
		ComboBoxModel<String> cb_wyborSygnaluModel = new DefaultComboBoxModel<String>(_labels);
		_cb = new JComboBox<String>();
		this.add(_cb, new GridBagConstraints(0, _id, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		szerokosc_cb = new Dimension(140, 20);
		_cb.setMaximumSize(szerokosc_cb);
		_cb.setPreferredSize(szerokosc_cb);
		_cb.setPrototypeDisplayValue("X");
		_cb.setRenderer(new MyComboBoxRenderer(_labels));
		_cb.setModel(cb_wyborSygnaluModel);

		if (_lbl != null) {
			this.add(_lbl, new GridBagConstraints(1, _id, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 10, 5,
							5), 0, 0));
			_lbl.setName(_lbl.toString());
			_lbl.setText("S" + _cb.getSelectedIndex());
		}

		return _cb;
	}

	/**
	 * Rozszerzenie klasy dla comboboxa o dodanie tooltipów
	 */
	public class MyComboBoxRenderer extends BasicComboBoxRenderer {
		private String[] tipLabels;

		public MyComboBoxRenderer(String[] _tips) {
			tipLabels = _tips;
		}

		@SuppressWarnings("rawtypes")
		public Component getListCellRendererComponent(JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus) {
			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
				if (-1 < index) {
					list.setToolTipText(tipLabels[index]);
				}
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}
			setFont(list.getFont());
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}

	/**
	 * Zaokrąglenie lizby double do określonej precyzji.
	 * 
	 * @param _liczba
	 *            : double
	 * @param _precyzja
	 *            : int
	 * @return: double
	 */
	public double zaokragl(double _liczba, int _precyzja) {
		_liczba = new BigDecimal(_liczba).setScale(_precyzja, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
		if (_liczba < 0.00001 && _liczba > -0.00001)
			_liczba = 0;
		if (_liczba > 9999.99 || _liczba < -9999.99)
			_liczba = new BigDecimal(_liczba).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
		return _liczba;
	}

	private void ustawLayoutOgolny() {
		thisLayout = new GridBagLayout();
		thisLayout.columnWeights = new double[] { 0, 0 };
		thisLayout.columnWidths = new int[] { 130, 60 };
		thisLayout.rowWeights = new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		thisLayout.rowHeights = new int[] { 30, 5, 30, 20, 20, 20, 20, 20, 20, 5, 30, 30, 5, 20,
				20, 20, 20, 20 };
		this.setLayout(thisLayout);
	}

	private void initGUI() {
		try {
			ustawLayoutOgolny();
			this.setPreferredSize(new java.awt.Dimension(220, 500));

			labels_btn = new String[] { "próbkowanie/kwantyzacja", "próbkowanie/kwantyzacja",
					"interpolacja/rekonstrukcja", "interpolacja/rekonstrukcja" };

			labels = new String[] { "próbkowanie równomierne",
					"kwantyzacja równomierna z zaokrąglaniem", "interpolacja pierwszego rzędu",
					"rekonstrukcja w oparciu o funkcję sinc" };

			labels2 = new String[] { "<wybierz obliczenia>", "błąd średniokwadratowy (MSE)",
					"stosunek sygnał - szum (SNR)", "szczytowy stosunek sygnał - szum (PSNR)",
					"maksymalna różnica (MD)" };

			int nrTxt = 0;
			{
				lbl_tytul = ustawLabel(lbl_tytul, "Wyświetl:", nrTxt);
				nrTxt++;
			}
			{
				btn_probkowanie = ustawButton(btn_probkowanie, labels_btn[krokZmian] + " >>", nrTxt);
				btn_probkowanie.addActionListener(new Listener_konwertuj(this.sygnalKonwertowany,
						this, new PanelRysunek_Konwersja(this.sygnalKonwertowany, this.krokZmian),
						this.splitPane));
				nrTxt++;
			}
			{
				separator = new JSeparator();
				this.add(separator, new GridBagConstraints(0, nrTxt, 2, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0,
								0, 0), 0, 0));
				nrTxt++;
			}
			{
				lbl_tytulKroku = ustawLabel(lbl_tytulKroku, "", nrTxt);
				nrTxt++;
			}
			{
				lbl_krok = ustawLabel(lbl_krok, "", nrTxt);
				nrTxt++;
			}
			{
				separator = new JSeparator();
				this.add(separator, new GridBagConstraints(0, nrTxt, 2, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0,
								0, 0), 0, 0));
				nrTxt++;
			}

			{
				lbl_tytul = ustawLabel(lbl_tytul, "Oblicz:", nrTxt);
				nrTxt++;
			}
			{
				cb_wyborMiary = ustawComboBox(cb_wyborMiary, labels2, nrTxt, null);
				cb_wyborMiary.setEnabled(false);
				cb_wyborMiary.addItemListener(new Listener_obliczMiary(this));
				nrTxt++;
			}
			{
				txt_wartoscMiary = ustawTextField(txt_wartoscMiary, "", nrTxt);
				txt_wartoscMiary.setEditable(false);
				nrTxt++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setSygnalKonwertowany(Sygnal _sygnal) {
		this.sygnalKonwertowany = _sygnal;
	}

	public Sygnal getSygnalKonwertowany() {
		return sygnalKonwertowany;
	}

	public void setSplitPane(JSplitPane splitPane) {
		this.splitPane = splitPane;
	}

	public JSplitPane getSplitPane() {
		return splitPane;
	}

	/**
	 * Sprawdza spełnienie wymaganych dla danego przekształcenia warunków: <li>
	 * <b>S1 - Próbkowanie równomierne</b> (krok: 0)
	 * <ol>
	 * <li>Sygnał ciągły
	 * <li>częstotliwość sygnału: 100, 220 lub 440
	 * </ol> <li><b>Q2 - Kwantyzacja równomierna z zaokrąglaniem</b> (krok: 1)
	 * <ol>
	 * <li>Sygnał spróbkowany
	 * </ol>
	 * 
	 * @return
	 */
	public String sprawdzPoprawnosc() {
		String msgPoprawnosci = "<nie zdefiniowano poprawnych waruków>";

		// double f_0 = 1.0D / this.sygnalKonwertowany.getT();

		if (this.krokZmian == 0) {
			msgPoprawnosci = "ok";
			if (this.sygnalKonwertowany.getrodzaj() == rodzaj_sygnalu.CIAGLY) {
			} else {
				msgPoprawnosci = "Sygnał nie jest ciągły.\n";
			}
			// if (f_0 == 100 || (f_0 > 219 && f_0 < 221)
			// || (f_0 > 439 && f_0 < 441)) {
			// } else {
			// msgPoprawnosci +=
			// "Sygnał nie ma częstotliwości 100, 220 lub 440.\n";
			// }
			// if (this.sygnalKonwertowany.gettyp() == 3) {
			//
			// } else {
			// msgPoprawnosci += "Sygnał nie jest sinusoidalny.";
			// }
		}

		if (this.krokZmian == 1) {
			msgPoprawnosci = (this.sygnalKonwertowany.getrodzaj() == rodzaj_sygnalu.DYSKRETNY ? "ok"
					: "sygnał nie jest dyskretny");
		}

		if (this.krokZmian == 2) {
			msgPoprawnosci = (this.sygnalKonwertowany.getrodzaj() == rodzaj_sygnalu.SPROBKOWANY ? "ok"
					: "sygnał nie jest spróbkowany");
		}

		if (this.krokZmian == 3) {

			msgPoprawnosci = (this.sygnalKonwertowany.getrodzaj() == rodzaj_sygnalu.SPROBKOWANY ? "ok"
					: "sygnał nie jest spróbkowany");
		}

		return msgPoprawnosci;
	}

	/**
	 * Przestawienie możliwości wyświetlenia kolejnego przekształcenia<br>
	 * (krok i rodzaj sygnału w zależności od kroku)
	 * 
	 * @param _oIle
	 *            - o ile kroków dalej
	 */
	public void przestawKrok(int _oIle) {
		if (_oIle == 1) {
			switch (this.krokZmian) {
			case 0:
				this.sygnalKonwertowany.setRodzaj(rodzaj_sygnalu.SPROBKOWANY);
				break;
			case 1:
				this.sygnalKonwertowany.setRodzaj(rodzaj_sygnalu.SKWANTOWANY);
				break;
			case 2:
				this.sygnalKonwertowany.setRodzaj(rodzaj_sygnalu.ZINTERPOLOWANY);
				break;
			case 3:
				this.sygnalKonwertowany.setRodzaj(rodzaj_sygnalu.ZREKONSTRUOWANY);
				break;

			default:
				break;
			}
		}
		if (_oIle == 2) {
			switch (this.krokZmian) {
			case 0:
				this.sygnalKonwertowany.setRodzaj(rodzaj_sygnalu.SPROBKOWANY);
				break;
			case 1:
				this.sygnalKonwertowany.setRodzaj(rodzaj_sygnalu.SPROBKOWANY);
				break;
			case 2:
				this.sygnalKonwertowany.setRodzaj(rodzaj_sygnalu.ZINTERPOLOWANY);
				break;
			case 3:
				this.sygnalKonwertowany.setRodzaj(rodzaj_sygnalu.ZINTERPOLOWANY);
				break;

			default:
				break;
			}
		}

		if (krokZmian == 0 && _oIle == 0) {
			this.lbl_tytulKroku.setText("");
			this.lbl_krok.setText("");
			this.btn_probkowanie.setText(labels_btn[0] + " >>");
		} else if (_oIle > 0) {
			this.lbl_tytulKroku.setText("Wyświetlany wykres:");
			this.lbl_krok.setText(labels[krokZmian]);
			this.krokZmian += _oIle;
			this.krokZmian = this.krokZmian % labels.length;
			this.btn_probkowanie.setText(labels_btn[krokZmian] + " >>");
		} else if (_oIle < 0) {
			this.lbl_tytulKroku.setText("Wyświetlany wykres:");
			this.lbl_krok.setText(labels[krokZmian]);
			this.krokZmian = 1;
			this.btn_probkowanie.setText("Powrót");
		}

	}

	/**
	 * Ustawia możliwość wyboru pierwszej konwersji.
	 */
	public void przestawKrokDoPoczatku() {
		this.przestawKrok(this.labels.length - this.krokZmian);
		if (this.sygnalKonwertowany.getrodzaj() != rodzaj_sygnalu.DYSKRETNY)
			this.sygnalKonwertowany.setrodzajciagly();
	}

	public void obliczenieMiary(int _id, List<Double> _doPorownania) {
		double wynik = 0.0;
		switch (_id) {
		case 0: {
			break;
		}
		case 1: {
			wynik = this.sygnalKonwertowany.obl_MSE(_doPorownania);
			break;
		}
		case 2: {
			wynik = this.sygnalKonwertowany.obl_SNR(_doPorownania);
			break;
		}
		case 3: {
			wynik = this.sygnalKonwertowany.obl_PSNR(_doPorownania);
			break;
		}
		case 4: {
			wynik = this.sygnalKonwertowany.obl_MD(_doPorownania);
			break;
		}
		default:
			break;
		}
		wynik = zaokragl(wynik, 4);
		if (_id > 0)
			this.txt_wartoscMiary.setText(Double.toString(wynik));
		else
			this.txt_wartoscMiary.setText("");
	}

}
