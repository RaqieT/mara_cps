package syg_package01;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.text.DecimalFormat;

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
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import org.jdesktop.application.Application;

import syg_package01.Sygnal.rodzaj_sygnalu;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
@SuppressWarnings("serial")
public class PanelParametry extends javax.swing.JPanel {
	private JLabel lbl_Amplituda;
	private JLabel lbl_CzasPoczatkowy;
	private JLabel lbl_CzasTrwania;
	private JLabel lbl_OkresPodstawowy;
	private JLabel lbl_nrSygnalu = new JLabel();
	private JLabel lbl_WspolczynnikWypenienia;
	private JFormattedTextField txt_Amplituda;
	private JFormattedTextField txt_CzasPoczatkowy;
	private JFormattedTextField txt_CzasTrwania;
	private JFormattedTextField txt_OkresPodstawowy;
	private JFormattedTextField txt_WspolczynnikWypenienia;

	public boolean getTxt_WspolczynnikWypenienia() {
		return txt_WspolczynnikWypenienia.isEditable();
	}

	public void setTxt_WspolczynnikWypenienia(
			boolean txt_WspolczynnikWypenienia_editable) {
		this.txt_WspolczynnikWypenienia
				.setEditable(txt_WspolczynnikWypenienia_editable);
		if (!txt_WspolczynnikWypenienia_editable)
			this.txt_WspolczynnikWypenienia.setText("");
	}

	private JComboBox<String> cb_wyborSygnalu;
	private GridBagLayout thisLayout;
	private String[] labels;
	private Dimension szerokosc_cb;

	private Sygnal[] listaSygnaow;

	public Listener_wyborSygnalu actionListener;
	private JSeparator sep_01;
	private JSeparator sep_02;
	public JButton btn_wyswietl;
	private String[] labels123;
	public JComboBox<String> cb_wybor123;

	private Listener_wybor123 actionListener2;
	private Listener_wyswietlHistogram actionListener_h;
	private Litener_wyswietlWykres actionListener_w;
	private JSeparator sep_03;
	private JLabel lbl_srednia;
	private JFormattedTextField txt_srednia;
	private JLabel lbl_sredniaBezw;
	private JFormattedTextField txt_sredniaBezw;
	private JLabel lbl_wartSkuteczna;
	private JFormattedTextField txt_wartSkuteczna;
	private JLabel lbl_wariancja;
	private JFormattedTextField txt_wariancja;
	private JLabel lbl_moc;
	private JFormattedTextField txt_moc;
	private PanelRysunek_Wykres rysunek;
	private PanelRysunek_Histogram rysunek2;
	private JSplitPane splitPane;
	public JButton btn_oblicz;
	private JLabel lbl_Skok;
	private JFormattedTextField txt_Skok;
	private boolean rysujWykres;

	public boolean getTxt_Skok() {
		return txt_Skok.isEditable();
	}

	public void setTxt_Skok(boolean txt_Skok_editable) {
		this.txt_Skok.setEditable(txt_Skok_editable);
		if (!txt_Skok_editable)
			this.txt_Skok.setText("");
	}

	/**
	 * Auto-generated main method to display this JPanel inside a new JFrame.
	 * 
	 * @param _rysunek
	 */

	public PanelParametry(Sygnal[] _sygnaly, PanelRysunek_Wykres _rysunek,
			PanelRysunek_Histogram _rysunek2, JSplitPane _splitPane,
			boolean _rysujWykres) {
		super();
		this.listaSygnaow = _sygnaly;
		this.rysunek = _rysunek;
		this.rysunek2 = _rysunek2;
		this.splitPane = _splitPane;
		this.rysujWykres = _rysujWykres;
		initGUI();
	}

	private void ustawLayoutOgolny() {
		thisLayout = new GridBagLayout();
		thisLayout.columnWeights = new double[] { 0, 0 };
		thisLayout.columnWidths = new int[] { 130, 60 };
		thisLayout.rowWeights = new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0 };
		thisLayout.rowHeights = new int[] { 30, 5, 30, 20, 20, 20, 20, 20, 20,
				5, 30, 30, 5, 20, 20, 20, 20, 20 };
		this.setLayout(thisLayout);
	}

	private JLabel ustawLabel(JLabel _lbl, String _txt, int _id) {
		_lbl = new JLabel();
		this.add(_lbl, new GridBagConstraints(0, _id, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
						0, 0, 5), 0, 0));
		_lbl.setName(_lbl.toString());
		_lbl.setText(_txt);

		return _lbl;
	}

	private JButton ustawButton(JButton _btn, String _txt, int _id) {
		_btn = new JButton();
		this.add(_btn, new GridBagConstraints(0, _id, 2, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 5, 5), 0, 0));
		_btn.setName(_btn.toString());
		_btn.setText(_txt);

		return _btn;
	}

	private JFormattedTextField ustawTextField(JFormattedTextField _tf,
			String _txt, int _id) {

		_tf = new JFormattedTextField(Double.TYPE);
		_tf.setValue(new Double(0.00));
		_tf.setColumns(5);
		_tf.setEditable(true);
		this.add(_tf, new GridBagConstraints(1, _id, 0, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 5, 5), 0, 0));
		_tf.setMinimumSize(new Dimension(60, 20));
		_tf.setMaximumSize(new Dimension(60, 20));
		_tf.setName(_tf.toString());
		_tf.setText(_txt);

		return _tf;
	}

	@SuppressWarnings("unchecked")
	private JComboBox<String> ustawComboBox(JComboBox<String> _cb,
			String[] _labels, int _id, JLabel _lbl) {
		ComboBoxModel<String> cb_wyborSygnaluModel = new DefaultComboBoxModel<String>(
				_labels);
		_cb = new JComboBox<String>();
		this.add(_cb, new GridBagConstraints(0, _id, 1, 1, 1.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 5, 5), 0, 0));
		szerokosc_cb = new Dimension(140, 20);
		_cb.setMaximumSize(szerokosc_cb);
		_cb.setPreferredSize(szerokosc_cb);
		_cb.setPrototypeDisplayValue("X");
		_cb.setRenderer(new MyComboBoxRenderer(_labels));
		_cb.setModel(cb_wyborSygnaluModel);

		if (_lbl != null) {
			this.add(_lbl, new GridBagConstraints(1, _id, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
					new Insets(5, 10, 5, 5), 0, 0));
			_lbl.setName(_lbl.toString());
			_lbl.setText("S" + _cb.getSelectedIndex());
		}

		return _cb;
	}

	// rozszerzenie klasy dla comboboxa o dodanie tooltipów
	public class MyComboBoxRenderer extends BasicComboBoxRenderer {
		private String[] tipLabels;

		public MyComboBoxRenderer(String[] _tips) {
			tipLabels = _tips;
		}

		@SuppressWarnings("rawtypes")
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
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

	public void wyczyscPola() {
		this.cb_wybor123.setSelectedIndex(0);
		this.cb_wyborSygnalu.setSelectedIndex(0);
		this.txt_Amplituda.setText("");
		this.txt_CzasPoczatkowy.setText("");
		this.txt_CzasTrwania.setText("");
		this.txt_OkresPodstawowy.setText("");
		this.txt_WspolczynnikWypenienia.setText("");
		this.txt_Skok.setText("");

		this.txt_moc.setText("");
		this.txt_srednia.setText("");
		this.txt_sredniaBezw.setText("");
		this.txt_wariancja.setText("");
		this.txt_wartSkuteczna.setText("");
	}

	public void zablokujPola(boolean _czyBlokada) {
		this.cb_wyborSygnalu.setEnabled(!_czyBlokada);
		this.txt_Amplituda.setEnabled(!_czyBlokada);
		this.txt_CzasPoczatkowy.setEnabled(!_czyBlokada);
		this.txt_CzasTrwania.setEnabled(!_czyBlokada);
		this.txt_OkresPodstawowy.setEnabled(!_czyBlokada);
		this.txt_WspolczynnikWypenienia.setEnabled(!_czyBlokada);
		this.txt_Skok.setEnabled(!_czyBlokada);
	}

	public double zaokragl(double _liczba, int _precyzja) {
		_liczba = new BigDecimal(_liczba).setScale(_precyzja,
				BigDecimal.ROUND_HALF_UP).doubleValue();
		if (_liczba < 0.00001 && _liczba > -0.00001)
			_liczba = 0;
		if (_liczba > 9999.99 || _liczba < -9999.99)
			_liczba = new BigDecimal(_liczba).setScale(0,
					BigDecimal.ROUND_HALF_UP).doubleValue();
		return _liczba;
	}

	public void obliczenieWarosci(Sygnal _sygnal) {
		double wartosc;

		wartosc = _sygnal.obl_sredniawartosc(_sygnal.getrodzaj());
		wartosc = zaokragl(wartosc, 4);
		txt_srednia.setText(Double.toString(wartosc));

		wartosc = _sygnal.obl_sredniawartoscbezwzgledna(_sygnal.getrodzaj());
		wartosc = zaokragl(wartosc, 4);
		txt_sredniaBezw.setText(Double.toString(wartosc));

		wartosc = _sygnal.obl_wartoscskuteczna(_sygnal.getrodzaj());
		wartosc = zaokragl(wartosc, 4);
		txt_wartSkuteczna.setText(Double.toString(wartosc));

		wartosc = _sygnal.obl_wariancja(_sygnal.getrodzaj());
		wartosc = zaokragl(wartosc, 4);
		txt_wariancja.setText(Double.toString(wartosc));

		wartosc = _sygnal.obl_mocsrednia(_sygnal.getrodzaj());
		wartosc = zaokragl(wartosc, 4);
		txt_moc.setText(Double.toString(wartosc));

	}

	public void odczytajParametryZSygnalu(Sygnal _sygnal, int _id) {
		if (_id >= 0)
			this.listaSygnaow[_id] = _sygnal;
		
		if (_sygnal.getrodzaj() == rodzaj_sygnalu.CIAGLY)
			_sygnal.wyczyscPunkty();
		this.cb_wyborSygnalu.setSelectedIndex(_sygnal.gettyp());
		if (this.txt_Amplituda.isEditable())
			this.txt_Amplituda.setText(Double.toString(_sygnal.getA()));
		if (this.txt_CzasPoczatkowy.isEditable())
			this.txt_CzasPoczatkowy.setText(Double.toString(_sygnal.gett1()));
		if (this.txt_CzasTrwania.isEditable())
			this.txt_CzasTrwania.setText(Double.toString(_sygnal.getd()));
		if (this.txt_OkresPodstawowy.isEditable())
			this.txt_OkresPodstawowy.setText(Double.toString(_sygnal.getT()));
		if (this.txt_WspolczynnikWypenienia.isEditable())
			this.txt_WspolczynnikWypenienia.setText(Double.toString(_sygnal
					.getKw()));
		if (this.txt_Skok.isEditable())
			this.txt_Skok.setText(Double.toString(_sygnal.getskok()));
	}

	public Sygnal zapiszParametryDoSygnalu() {
		int id = this.cb_wybor123.getSelectedIndex();

		if (!cb_wybor123.isEnabled()) {
			if (txt_WspolczynnikWypenienia.isEditable()
					&& txt_Skok.isEditable()) {
				this.listaSygnaow[id].pobierzParametryUzytkownika(
						this.cb_wyborSygnalu.getSelectedIndex(), Double
								.parseDouble(this.txt_Amplituda.getText()),
						Double.parseDouble(this.txt_CzasPoczatkowy.getText()),
						this.listaSygnaow[id].getts(), Double
								.parseDouble(this.txt_CzasTrwania.getText()),
						Double.parseDouble(this.txt_OkresPodstawowy.getText()),
						Double.parseDouble(this.txt_WspolczynnikWypenienia
								.getText()), Double.parseDouble(this.txt_Skok
								.getText()));
			} else if (!txt_WspolczynnikWypenienia.isEditable()
					&& txt_Skok.isEditable()) {
				this.listaSygnaow[id].pobierzParametryUzytkownika(
						this.cb_wyborSygnalu.getSelectedIndex(),
						Double.parseDouble(this.txt_Amplituda.getText()),
						Double.parseDouble(this.txt_CzasPoczatkowy.getText()),
						this.listaSygnaow[id].getts(),
						Double.parseDouble(this.txt_CzasTrwania.getText()),
						Double.parseDouble(this.txt_OkresPodstawowy.getText()),
						this.listaSygnaow[id].getKw(),
						Double.parseDouble(this.txt_Skok.getText()));
			} else if (txt_WspolczynnikWypenienia.isEditable()
					&& !txt_Skok.isEditable()) {
				this.listaSygnaow[id].pobierzParametryUzytkownika(
						this.cb_wyborSygnalu.getSelectedIndex(), Double
								.parseDouble(this.txt_Amplituda.getText()),
						Double.parseDouble(this.txt_CzasPoczatkowy.getText()),
						this.listaSygnaow[id].getts(), Double
								.parseDouble(this.txt_CzasTrwania.getText()),
						Double.parseDouble(this.txt_OkresPodstawowy.getText()),
						Double.parseDouble(this.txt_WspolczynnikWypenienia
								.getText()), this.listaSygnaow[id].getskok());
			} else {
				this.listaSygnaow[id].pobierzParametryUzytkownika(
						this.cb_wyborSygnalu.getSelectedIndex(),
						Double.parseDouble(this.txt_Amplituda.getText()),
						Double.parseDouble(this.txt_CzasPoczatkowy.getText()),
						this.listaSygnaow[id].getts(),
						Double.parseDouble(this.txt_CzasTrwania.getText()),
						Double.parseDouble(this.txt_OkresPodstawowy.getText()),
						this.listaSygnaow[id].getKw(),
						this.listaSygnaow[id].getskok());
			}
		}

		return listaSygnaow[id];
	}

	public String sprawdzPoprawnosc() {
		if (this.txt_Amplituda.getText().isEmpty()
				|| !this.txt_Amplituda.isValid()
				|| this.txt_CzasPoczatkowy.getText().isEmpty()
				|| !this.txt_CzasPoczatkowy.isValid()
				|| this.txt_CzasTrwania.getText().isEmpty()
				|| !this.txt_CzasTrwania.isValid()
				|| this.txt_OkresPodstawowy.getText().isEmpty()
				|| !this.txt_OkresPodstawowy.isValid()
				|| (this.txt_WspolczynnikWypenienia.getText().isEmpty() && this.txt_WspolczynnikWypenienia
						.isEditable())
				|| (!this.txt_WspolczynnikWypenienia.isValid() && this.txt_WspolczynnikWypenienia
						.isEditable())
				|| (this.txt_Skok.getText().isEmpty() && this.txt_Skok
						.isEditable())
				|| (!this.txt_Skok.isValid() && this.txt_Skok.isEditable())) {
			return "Niepoprawnie wypełnione pola.";
		} else if (this.cb_wyborSygnalu.getSelectedIndex() <= 0) {
			return "Musisz wybrać rodzaj sygnału.";
		} else
			return "ok";
	}

	public void zmienRysunek(boolean _wykres, Object _rysunek) {
		rysujWykres = _wykres;
		// actionListener_h = new
		// Listener_wyswietlHistogram(this.listaSygnaow[0],
		// this, this.rysunek2, this.splitPane);
		// actionListener_w = new Litener_wyswietlWykres(this.listaSygnaow[0],
		// this, this.rysunek, this.splitPane);
		if (this.rysujWykres) {
			this.rysunek = (PanelRysunek_Wykres) _rysunek;
			btn_wyswietl.setText("Wyświetl wykres");
			btn_wyswietl.addActionListener(this.actionListener_w);
		} else {
			this.rysunek2 = (PanelRysunek_Histogram) _rysunek;
			btn_wyswietl.setText("Wyświetl histogram");
			btn_wyswietl.addActionListener(this.actionListener_h);
		}
	}

	private void initGUI() {
		try {

			ustawLayoutOgolny();
			this.setPreferredSize(new java.awt.Dimension(220, 500));

			labels = new String[] { "<wybierz sygnał>",
					"szum o rozkładzie jednostajnym", "szum gausowski",
					"sygnał sinusoidalny",
					"sygnał sinusoidalny wyprostowany jednopołówkowo",
					"sygnał sinusoidalny wyprostowany dwupołówkowo",
					"sygnał prostokątny", "sygnał prostokątny symetryczny",
					"sygnał trójkątny", "skok jednostkowy",
					"impuls jednostkowy", "szum impulsowy" };

			labels123 = new String[] { "podstawowy", "drugi", "wynikowy" };

			int nrTxt = 0;
			cb_wybor123 = ustawComboBox(cb_wybor123, labels123, nrTxt, null);
			cb_wybor123.setEnabled(false);
			actionListener2 = new Listener_wybor123(listaSygnaow, this);
			cb_wybor123.addItemListener(actionListener2);
			nrTxt++;

			{
				sep_02 = new JSeparator();
				this.add(sep_02, new GridBagConstraints(0, nrTxt, 2, 1, 0.0,
						0.0, GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0),
						0, 0));
			}
			nrTxt++;

			lbl_nrSygnalu = new JLabel();
			cb_wyborSygnalu = ustawComboBox(cb_wyborSygnalu, labels, nrTxt,
					lbl_nrSygnalu);
			// Create and register listener
			actionListener = new Listener_wyborSygnalu(lbl_nrSygnalu, this);
			cb_wyborSygnalu.addItemListener(actionListener);
			nrTxt++;

			lbl_Amplituda = ustawLabel(lbl_Amplituda, "amplituda (A)", nrTxt);
			txt_Amplituda = ustawTextField(txt_Amplituda, "", nrTxt);
			nrTxt++;

			lbl_CzasPoczatkowy = ustawLabel(lbl_CzasPoczatkowy,
					"czas początkowy (t_1)", nrTxt);
			txt_CzasPoczatkowy = ustawTextField(txt_CzasPoczatkowy, "", nrTxt);
			nrTxt++;

			lbl_CzasTrwania = ustawLabel(lbl_CzasTrwania, "czas trwania (d)",
					nrTxt);
			txt_CzasTrwania = ustawTextField(txt_CzasTrwania, "", nrTxt);
			nrTxt++;

			lbl_OkresPodstawowy = ustawLabel(lbl_OkresPodstawowy,
					"okres podstawowy (T)", nrTxt);
			txt_OkresPodstawowy = ustawTextField(txt_OkresPodstawowy, "", nrTxt);
			nrTxt++;

			lbl_WspolczynnikWypenienia = ustawLabel(lbl_WspolczynnikWypenienia,
					"współczynnik wypełnienia (k_W)", nrTxt);
			txt_WspolczynnikWypenienia = ustawTextField(
					txt_WspolczynnikWypenienia, "", nrTxt);
			txt_WspolczynnikWypenienia.setEditable(false);
			nrTxt++;

			lbl_Skok = ustawLabel(lbl_Skok, "skok", nrTxt);
			txt_Skok = ustawTextField(txt_Skok, "", nrTxt);
			txt_Skok.setEditable(false);
			nrTxt++;

			{
				sep_01 = new JSeparator();
				this.add(sep_01, new GridBagConstraints(0, nrTxt, 2, 1, 0.0,
						0.0, GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0),
						0, 0));
			}
			nrTxt++;

			actionListener_h = new Listener_wyswietlHistogram(
					this.listaSygnaow[0], this, this.rysunek2, this.splitPane);
			actionListener_w = new Litener_wyswietlWykres(this.listaSygnaow[0],
					this, this.rysunek, this.splitPane);
			if (this.rysujWykres) {
				btn_wyswietl = ustawButton(btn_wyswietl, "Wyświetl wykres",
						nrTxt);
				btn_wyswietl.addActionListener(actionListener_w);
				nrTxt++;
			} else {
				btn_wyswietl = ustawButton(btn_wyswietl, "Wyświetl histogram",
						nrTxt);
				btn_wyswietl.addActionListener(actionListener_h);
				nrTxt++;
			}

			btn_oblicz = ustawButton(btn_oblicz, "Oblicz wartości", nrTxt);
			btn_oblicz.addActionListener(new Listener_obliczWartosci(
					this.listaSygnaow[0], this));
			nrTxt++;

			{
				sep_03 = new JSeparator();
				this.add(sep_03, new GridBagConstraints(0, nrTxt, 2, 1, 0.0,
						0.0, GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0),
						0, 0));
			}
			nrTxt++;

			lbl_srednia = ustawLabel(lbl_srednia, "średnia", nrTxt);
			txt_srednia = ustawTextField(txt_srednia, "", nrTxt);
			txt_srednia.setEditable(false);
			nrTxt++;

			lbl_sredniaBezw = ustawLabel(lbl_sredniaBezw,
					"średnia bezwzględna", nrTxt);
			txt_sredniaBezw = ustawTextField(txt_sredniaBezw, "", nrTxt);
			txt_sredniaBezw.setEditable(false);
			nrTxt++;

			lbl_wartSkuteczna = ustawLabel(lbl_wartSkuteczna,
					"wartość skuteczna", nrTxt);
			txt_wartSkuteczna = ustawTextField(txt_wartSkuteczna, "", nrTxt);
			txt_wartSkuteczna.setEditable(false);
			nrTxt++;

			lbl_wariancja = ustawLabel(lbl_wariancja, "wariancja", nrTxt);
			txt_wariancja = ustawTextField(txt_wariancja, "", nrTxt);
			txt_wariancja.setEditable(false);
			nrTxt++;

			lbl_moc = ustawLabel(lbl_moc, "moc średnia", nrTxt);
			txt_moc = ustawTextField(txt_moc, "", nrTxt);
			txt_moc.setEditable(false);
			nrTxt++;

			Application.getInstance().getContext().getResourceMap(getClass())
					.injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
