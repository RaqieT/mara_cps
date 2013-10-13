package syg_Szablony;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

@SuppressWarnings("serial")
public abstract class PanelObslugi extends javax.swing.JPanel {
	protected GridBagLayout thisLayout;
	protected JSplitPane splitPane;
	protected Dimension szerokosc_cb;
	protected JSeparator separator;

	/**
	 * Auto-generated main method to display this JPanel inside a new JFrame.
	 */
	protected void ustawLayoutOgolny() {
		thisLayout = new GridBagLayout();
		thisLayout.columnWeights = new double[] { 0, 0 };
		thisLayout.columnWidths = new int[] { 130, 60 };
		thisLayout.rowWeights = new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		thisLayout.rowHeights = new int[] { 30, 5, 30, 20, 20, 20, 20, 20, 20, 5, 30, 30, 5, 20,
				20, 20, 20, 20 };
		this.setLayout(thisLayout);
		setPreferredSize(new Dimension(220, 500));
	}

	public enum zwracana_wartosc {
		IMPULSY, TRANSMISJA_FILTRU
	}

	protected JLabel ustawLabel(JLabel _lbl, String _txt, int _id, int _iloscKolumn, int _iloscWierszy, int _wyrowanie) {
		_lbl = new JLabel();
		this.add(_lbl, new GridBagConstraints(0, _id, _iloscKolumn, _iloscWierszy, 0.0, 0.0, _wyrowanie,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
		_lbl.setName(_lbl.toString());
		_lbl.setText(_txt);

		return _lbl;
	}
	
	protected JLabel ustawLabel(JLabel _lbl, String _txt, int _id, int _iloscKolumn, int _wyrowanie) {
		return ustawLabel(_lbl, _txt, _id, _iloscKolumn, 1, _wyrowanie);
	}

	protected JCheckBox ustawCheckBox(JCheckBox _cb, String _txt, int _id, int _ktoraKolumna) {
		_cb = new JCheckBox();
		this.add(_cb, new GridBagConstraints(_ktoraKolumna, _id, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0),
				0, 0));
		_cb.setName(_cb.toString());
		_cb.setText(_txt);

		return _cb;
	}

	protected JButton ustawButton(JButton _btn, String _txt, int _id, int _iloscKolumn,
			int _wyrowanie) {
		_btn = new JButton();
		this.add(_btn, new GridBagConstraints(0, _id, _iloscKolumn, 1, 0.0, 0.0, _wyrowanie,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		_btn.setName(_btn.toString());
		_btn.setText(_txt);

		return _btn;
	}

	protected JFormattedTextField ustawTextField(JFormattedTextField _tf, String _txt, int _id,
			int _iloscKolumn) {
		return ustawTextField(_tf, _txt, _id, _iloscKolumn, 1, GridBagConstraints.WEST, false);
	}

	protected JFormattedTextField ustawTextField(JFormattedTextField _tf, String _txt, int _id,
			int _iloscKolumn, int _wyrowanie) {
		return ustawTextField(_tf, _txt, _id, _iloscKolumn, 1, _wyrowanie, false);
	}

	protected JFormattedTextField ustawTextField(JFormattedTextField _tf, String _txt, int _id,
			int _iloscKolumn, int _lpKolumny, int _wyrowanie, boolean _czyInt) {

		if (_czyInt) {
			_tf = new JFormattedTextField(Integer.TYPE);
			_tf.setValue(new Integer(0));
		} else {
			_tf = new JFormattedTextField(Double.TYPE);
			_tf.setValue(new Double(0.00));
		}
		_tf.setColumns(5);
		_tf.setEditable(true);
		this.add(_tf, new GridBagConstraints(_lpKolumny, _id, _iloscKolumn, 1, 0.0, 0.0,
				_wyrowanie, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		_tf.setMinimumSize(new Dimension(40, 20));
		_tf.setMaximumSize(new Dimension(40, 20));
		_tf.setName(_tf.toString());
		_tf.setText(_txt);

		return _tf;
	}

	protected JSpinner ustawSpinner(JSpinner _spin, String _txt, int _id, int _iloscKolumn,
			int _lpKolumny, int _wyrowanie, boolean _czyInt) {
		
		SpinnerModel model = new SpinnerNumberModel(0, // initial value
				0, // min
				100000, // max
				1); // step
		_spin = new JSpinner(model);

		// _spin.setColumns(5);
		// _spin.setEditable(true);

		this.add(_spin, new GridBagConstraints(_lpKolumny, _id, _iloscKolumn, 1, 0.0, 0.0,
				_wyrowanie, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		_spin.setMinimumSize(new Dimension(40, 20));
		_spin.setMaximumSize(new Dimension(40, 20));
		_spin.setName(_spin.toString());
		// _spin.setText(_txt);

		return _spin;
	}
	
	/**
	 * Ustawienie kroku przy zmianie częstotliwości obcięcia strzałkami
	 * @param _krok
	 */
	public void setControlStep (JSpinner _kontrolka, double _krok)
	{
		SpinnerModel model = new SpinnerNumberModel(0, // initial value
				0, // min
				100000.000, // max
				_krok); // step
		Object tmpValue = _kontrolka.getValue();
		_kontrolka.setModel(model);
		_kontrolka.setValue(tmpValue);
	}
	
	public void setControlStep (JSpinner _kontrolka, int _krok)
	{
		SpinnerModel model = new SpinnerNumberModel(0, // initial value
				0, // min
				100000, // max
				_krok); // step
		Object tmpValue = _kontrolka.getValue();
		_kontrolka.setModel(model);
		_kontrolka.setValue(tmpValue);
	}
	
	protected JSpinner ustawSpinnerD(JSpinner _spin, String _txt, int _id, int _iloscKolumn,
			int _lpKolumny, int _wyrowanie, boolean _czyInt) {
		
		SpinnerModel model = new SpinnerNumberModel(0, // initial value
				0, // min
				100000.000, // max
				0.001); // step
		_spin = new JSpinner(model);

		// _spin.setColumns(5);
		// _spin.setEditable(true);

		this.add(_spin, new GridBagConstraints(_lpKolumny, _id, _iloscKolumn, 1, 0.0, 0.0,
				_wyrowanie, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		_spin.setMinimumSize(new Dimension(40, 20));
		_spin.setMaximumSize(new Dimension(40, 20));
		_spin.setName(_spin.toString());
		// _spin.setText(_txt);

		return _spin;
	}

	/**
	 * 
	 * @param _cb
	 *            - obiekt JComboBox
	 * @param _labels
	 *            - tablica nazw
	 * @param _id
	 *            - kolejny numer wiersza
	 * @param _lbl
	 *            - label z prawej strony
	 * @param _lblChar
	 *            - oznaczenie, po którym następuje nr na liście
	 * @param _iloscKolumn
	 *            - jak długi (na ile kolumn) ma być obiekt
	 * @param _wyrowanie
	 *            - np. GridBagConstraints.CENTER
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected JComboBox<String> ustawComboBox(JComboBox<String> _cb, String[] _labels, int _id,
			JLabel _lbl, String _lblChar, int _iloscKolumn, int _wyrowanie) {
		ComboBoxModel<String> cb_wyborSygnaluModel = new DefaultComboBoxModel<String>(_labels);
		_cb = new JComboBox<String>();
		this.add(_cb, new GridBagConstraints(0, _id, _iloscKolumn, 1, 1.0, 0.0, _wyrowanie,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		szerokosc_cb = new Dimension(140, 20);
		_cb.setMaximumSize(szerokosc_cb);
		_cb.setPreferredSize(szerokosc_cb);
		_cb.setPrototypeDisplayValue("X");
		_cb.setRenderer(new MyComboBoxRenderer(_labels));
		_cb.setModel(cb_wyborSygnaluModel);

		if (_lbl != null) {
			this.add(_lbl, new GridBagConstraints(1, _id, _iloscKolumn, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 10, 5,
							5), 0, 0));
			_lbl.setName(_lbl.toString());
			_lbl.setText(_lblChar + _cb.getSelectedIndex());
		}

		return _cb;
	}
	
	/**
	 * 
	 * @param _cb
	 *            - obiekt JComboBox
	 * @param _labels
	 *            - tablica nazw
	 * @param _id
	 *            - kolejny numer wiersza
	 * @param _lbl
	 *            - label z prawej strony
	 * @param _iloscKolumn
	 *            - jak długi (na ile kolumn) ma być obiekt
	 * @param _wyrowanie
	 *            - np. GridBagConstraints.CENTER
	 * @return
	 */
	protected JComboBox<String> ustawComboBox(JComboBox<String> _cb, String[] _labels, int _id,
			JLabel _lbl, int _iloscKolumn, int _wyrowanie)
			{
		return ustawComboBox(_cb, _labels, _id,
			_lbl, "S", _iloscKolumn, _wyrowanie);
			}

	/**
	 * Zaokrąglenie liczby do określonej ilości miejsc po przecinku.
	 * 
	 * @param _liczba
	 * @param _precyzja
	 * @return
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

	/**
	 * rozszerzenie klasy dla comboboxa o dodanie tooltipów
	 */
	protected class MyComboBoxRenderer extends BasicComboBoxRenderer {
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

	protected void setSplitPane(JSplitPane splitPane) {
		this.splitPane = splitPane;
	}

	protected JSplitPane getSplitPane() {
		return splitPane;
	}
	// public PanelObslugi() {
	// super();
	// initGUI();
	// }

	// private void initGUI() {
	// try {
	// setPreferredSize(new Dimension(400, 300));
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

}
