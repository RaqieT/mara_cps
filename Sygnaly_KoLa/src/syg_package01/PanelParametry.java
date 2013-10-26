package syg_package01;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;

import org.jdesktop.application.Application;

import syg_Obliczenia.Listener_obliczWartosci;
import syg_Szablony.PanelObslugi;
import syg_Wykresy.Listener_wyswietlHistogram;
import syg_Wykresy.Listener_wyswietlWykres;
import syg_Wykresy.PanelRysunek_Histogram;
import syg_Wykresy.PanelRysunek_Wykres;
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
public class PanelParametry extends PanelObslugi {
	private JLabel lbl_Amplituda;
	private JLabel lbl_CzasPoczatkowy;
	private JLabel lbl_CzasTrwania;
	@SuppressWarnings("unused")
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

	public void setTxt_WspolczynnikWypenienia(boolean txt_WspolczynnikWypenienia_editable) {
		this.txt_WspolczynnikWypenienia.setEditable(txt_WspolczynnikWypenienia_editable);
		if (!txt_WspolczynnikWypenienia_editable)
			this.txt_WspolczynnikWypenienia.setText("");
	}

	private JComboBox<String> cb_wyborSygnalu;
	private String[] labels;

	private Sygnal[] listaSygnaow;

	public Listener_wyborSygnalu actionListener;
	private JSeparator sep_01;
	private JSeparator sep_02;
	public JButton btn_wyswietl;
	private String[] labels123;
	public JComboBox<String> cb_wybor123;

	private Listener_wybor123 actionListener2;
	private Listener_wyswietlHistogram actionListener_h;
	private Listener_wyswietlWykres actionListener_w;
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
	private JComboBox<String> cb_OkresLubCzestotliwosc;
	private String[] labels_okrCzest;

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
			PanelRysunek_Histogram _rysunek2, JSplitPane _splitPane, boolean _rysujWykres) {
		super();
		this.listaSygnaow = _sygnaly;
		this.rysunek = _rysunek;
		this.rysunek2 = _rysunek2;
		this.rysujWykres = _rysujWykres;
		this.splitPane = _splitPane;
		initGUI();
	}

	private JLabel ustawLabel(JLabel _lbl, String _txt, int _id) {
		return ustawLabel(_lbl, _txt, _id, 1, GridBagConstraints.EAST);
	}

	private JButton ustawButton(JButton _btn, String _txt, int _id) {
		return ustawButton(_btn, _txt, _id, 2, GridBagConstraints.EAST);
	}

	private JFormattedTextField ustawTextField(JFormattedTextField _tf, String _txt, int _id) {
		return ustawTextField(_tf, _txt, _id, 1, 1, GridBagConstraints.CENTER, false);
	}

	private JComboBox<String> ustawComboBox(JComboBox<String> _cb, String[] _labels, int _id,
			JLabel _lbl) {
		return ustawComboBox(_cb, _labels, _id, _lbl, 1, GridBagConstraints.CENTER);
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
		this.cb_OkresLubCzestotliwosc.setEnabled(!_czyBlokada);
		this.txt_Skok.setEnabled(!_czyBlokada);
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

		if (_sygnal.getrodzaj() == rodzaj_sygnalu.CIAGLY) {
			_sygnal.wyczyscPunkty(true);
			// _sygnal.wyczyscPunkty(false);

			this.cb_wyborSygnalu.setSelectedIndex(_sygnal.gettyp());
			if (this.txt_Amplituda.isEditable())
				this.txt_Amplituda.setText(Double.toString(_sygnal.getA()));
			if (this.txt_CzasPoczatkowy.isEditable())
				this.txt_CzasPoczatkowy.setText(Double.toString(_sygnal.gett1()));
			if (this.txt_CzasTrwania.isEditable())
				this.txt_CzasTrwania.setText(Double.toString(_sygnal.getd()));
			if (this.txt_OkresPodstawowy.isEditable()) {
				if (this.cb_OkresLubCzestotliwosc.getSelectedIndex() == 1) {
					this.txt_OkresPodstawowy.setText(Double.toString(1.0D / _sygnal.getT()));
				} else {
					this.txt_OkresPodstawowy.setText(Double.toString(_sygnal.getT()));
				}
			}
			if (this.txt_WspolczynnikWypenienia.isEditable())
				this.txt_WspolczynnikWypenienia.setText(Double.toString(_sygnal.getKw()));
			if (this.txt_Skok.isEditable())
				this.txt_Skok.setText(Double.toString(_sygnal.getskok()));
		} else {
			this.txt_Amplituda.setText("");
			this.txt_CzasPoczatkowy.setText(Double.toString(_sygnal.gett1()));
			this.txt_CzasTrwania.setText(Double.toString(_sygnal.getd()));
			this.cb_OkresLubCzestotliwosc.setSelectedIndex(1);
			this.txt_OkresPodstawowy.setText(Double.toString(1.0D / _sygnal.getkrok()));
			this.txt_Skok.setText("");
			this.txt_WspolczynnikWypenienia.setText("");
		}
	}

	public Sygnal zapiszParametryDoSygnalu() {
		int id = this.cb_wybor123.getSelectedIndex();

		if (this.listaSygnaow[id].getrodzaj() == rodzaj_sygnalu.CIAGLY) {
			double okres = Double.parseDouble(this.txt_OkresPodstawowy.getText());

			if (this.cb_OkresLubCzestotliwosc.getSelectedIndex() == 1) {
				okres = 1.0D / okres;
			}

			if (txt_WspolczynnikWypenienia.isEditable() && txt_Skok.isEditable()) {
				this.listaSygnaow[id].pobierzParametryUzytkownika(
						this.cb_wyborSygnalu.getSelectedIndex(),
						Double.parseDouble(this.txt_Amplituda.getText()),
						Double.parseDouble(this.txt_CzasPoczatkowy.getText()),
						this.listaSygnaow[id].getts(),
						Double.parseDouble(this.txt_CzasTrwania.getText()), okres,
						Double.parseDouble(this.txt_WspolczynnikWypenienia.getText()),
						Double.parseDouble(this.txt_Skok.getText()));
			} else if (!txt_WspolczynnikWypenienia.isEditable() && txt_Skok.isEditable()) {
				this.listaSygnaow[id].pobierzParametryUzytkownika(
						this.cb_wyborSygnalu.getSelectedIndex(),
						Double.parseDouble(this.txt_Amplituda.getText()),
						Double.parseDouble(this.txt_CzasPoczatkowy.getText()),
						this.listaSygnaow[id].getts(),
						Double.parseDouble(this.txt_CzasTrwania.getText()), okres,
						this.listaSygnaow[id].getKw(), Double.parseDouble(this.txt_Skok.getText()));
			} else if (txt_WspolczynnikWypenienia.isEditable() && !txt_Skok.isEditable()) {
				this.listaSygnaow[id].pobierzParametryUzytkownika(
						this.cb_wyborSygnalu.getSelectedIndex(),
						Double.parseDouble(this.txt_Amplituda.getText()),
						Double.parseDouble(this.txt_CzasPoczatkowy.getText()),
						this.listaSygnaow[id].getts(),
						Double.parseDouble(this.txt_CzasTrwania.getText()), okres,
						Double.parseDouble(this.txt_WspolczynnikWypenienia.getText()),
						this.listaSygnaow[id].getskok());
			} else {
				this.listaSygnaow[id].pobierzParametryUzytkownika(
						this.cb_wyborSygnalu.getSelectedIndex(),
						Double.parseDouble(this.txt_Amplituda.getText()),
						Double.parseDouble(this.txt_CzasPoczatkowy.getText()),
						this.listaSygnaow[id].getts(),
						Double.parseDouble(this.txt_CzasTrwania.getText()), okres,
						this.listaSygnaow[id].getKw(), this.listaSygnaow[id].getskok());
			}
		}

		return listaSygnaow[id];
	}

	/**
	 * Sprawdzenie czy jest aktywne wpisywanie parametrów.
	 */
	public boolean czyAktywneParametry(boolean _wszystkie) {
		if (_wszystkie) {
			return (this.cb_wyborSygnalu.isEnabled() && this.txt_Amplituda.isEnabled());
		} else {
			return (this.cb_wyborSygnalu.isEnabled() || this.txt_Amplituda.isEnabled());
		}
	}

	/**
	 * Sprawdzenie czy wszystkie pola są właściwie wypełnione.
	 * 
	 * @return <ol>
	 *         <li>"Niepoprawnie wypełnione pola."
	 *         <li>"Musisz wybrać rodzaj sygnału."
	 *         <li>"ok"
	 *         </ol>
	 */
	public String sprawdzPoprawnosc() {
		if (listaSygnaow[cb_wybor123.getSelectedIndex()].getrodzaj() == rodzaj_sygnalu.CIAGLY) {
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
					|| (this.txt_Skok.getText().isEmpty() && this.txt_Skok.isEditable())
					|| (!this.txt_Skok.isValid() && this.txt_Skok.isEditable())) {
				return "Niepoprawnie wypełnione pola.";
			} else if (this.cb_wyborSygnalu.getSelectedIndex() <= 0) {
				return "Musisz wybrać rodzaj sygnału.";
			} else
				return "ok";
		} else {
			// if (listaSygnaow[cb_wybor123.getSelectedIndex()].getrodzaj() ==
			// rodzaj_sygnalu.DYSKRETNY) {
			if (listaSygnaow[cb_wybor123.getSelectedIndex()].getPunktyY_wykres().size() != 0)
				return "ok";
			else
				return "Brak punktów sygnału dyskretnego";
			// } else
			// return "Sygnał jest po konwersji";
		}
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
			btn_wyswietl.setText("Generuj sygnał");
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

			labels = new String[] { "<wybierz sygnał>", "szum o rozkładzie jednostajnym",
					"szum gausowski", "sygnał sinusoidalny",
					"sygnał sinusoidalny wyprostowany jednopołówkowo",
					"sygnał sinusoidalny wyprostowany dwupołówkowo", "sygnał prostokątny",
					"sygnał prostokątny symetryczny", "sygnał trójkątny", "skok jednostkowy",
					"impuls jednostkowy", "szum impulsowy"/*, "sygnał S2 z zad4"*/ };

			labels123 = new String[] { "podstawowy", "drugi", "wynikowy" };

			labels_okrCzest = new String[] { "okres podstawowy (T)", "częstotliwość (f_0)" };

			int nrTxt = 0;
			cb_wybor123 = ustawComboBox(cb_wybor123, labels123, nrTxt, null);
			cb_wybor123.setEnabled(false);
			actionListener2 = new Listener_wybor123(listaSygnaow, this);
			cb_wybor123.addItemListener(actionListener2);
			nrTxt++;

			{
				sep_02 = new JSeparator();
				this.add(sep_02, new GridBagConstraints(0, nrTxt, 2, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0,
								0, 0), 0, 0));
			}
			nrTxt++;

			lbl_nrSygnalu = new JLabel();
			cb_wyborSygnalu = ustawComboBox(cb_wyborSygnalu, labels, nrTxt, lbl_nrSygnalu);
			// Create and register listener
			actionListener = new Listener_wyborSygnalu(lbl_nrSygnalu, this);
			cb_wyborSygnalu.addItemListener(actionListener);
			nrTxt++;

			lbl_Amplituda = ustawLabel(lbl_Amplituda, "amplituda (A)", nrTxt);
			txt_Amplituda = ustawTextField(txt_Amplituda, "", nrTxt);
			nrTxt++;

			lbl_CzasPoczatkowy = ustawLabel(lbl_CzasPoczatkowy, "czas początkowy (t_1)", nrTxt);
			txt_CzasPoczatkowy = ustawTextField(txt_CzasPoczatkowy, "", nrTxt);
			nrTxt++;

			lbl_CzasTrwania = ustawLabel(lbl_CzasTrwania, "czas trwania (d)", nrTxt);
			txt_CzasTrwania = ustawTextField(txt_CzasTrwania, "", nrTxt);
			nrTxt++;

			cb_OkresLubCzestotliwosc = ustawComboBox(cb_OkresLubCzestotliwosc, labels_okrCzest,
					nrTxt, null);
			// lbl_OkresPodstawowy = ustawLabel(lbl_OkresPodstawowy,
			// "okres podstawowy (T)", nrTxt);
			txt_OkresPodstawowy = ustawTextField(txt_OkresPodstawowy, "", nrTxt);
			nrTxt++;

			lbl_WspolczynnikWypenienia = ustawLabel(lbl_WspolczynnikWypenienia,
					"współczynnik wypełnienia (k_W)", nrTxt);
			txt_WspolczynnikWypenienia = ustawTextField(txt_WspolczynnikWypenienia, "", nrTxt);
			txt_WspolczynnikWypenienia.setEditable(false);
			nrTxt++;

			lbl_Skok = ustawLabel(lbl_Skok, "skok/prawdop.[%]", nrTxt);
			txt_Skok = ustawTextField(txt_Skok, "", nrTxt);
			txt_Skok.setEditable(false);
			nrTxt++;

			{
				sep_01 = new JSeparator();
				this.add(sep_01, new GridBagConstraints(0, nrTxt, 2, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0,
								0, 0), 0, 0));
			}
			nrTxt++;

			actionListener_h = new Listener_wyswietlHistogram(this.listaSygnaow[0], this,
					this.rysunek2, this.splitPane);
			actionListener_w = new Listener_wyswietlWykres(this.listaSygnaow[0], this,
					this.rysunek, this.splitPane);
			if (this.rysujWykres) {
				btn_wyswietl = ustawButton(btn_wyswietl, "Generuj sygnał", nrTxt);
				btn_wyswietl.addActionListener(actionListener_w);
			} else {
				btn_wyswietl = ustawButton(btn_wyswietl, "Wyświetl histogram", nrTxt);
				btn_wyswietl.addActionListener(actionListener_h);
			}
			nrTxt++;

			btn_oblicz = ustawButton(btn_oblicz, "Oblicz wartości", nrTxt);
			btn_oblicz.addActionListener(new Listener_obliczWartosci(this.listaSygnaow[0], this));
			nrTxt++;

			{
				sep_03 = new JSeparator();
				this.add(sep_03, new GridBagConstraints(0, nrTxt, 2, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0,
								0, 0), 0, 0));
			}
			nrTxt++;

			lbl_srednia = ustawLabel(lbl_srednia, "średnia", nrTxt);
			txt_srednia = ustawTextField(txt_srednia, "", nrTxt);
			txt_srednia.setEditable(false);
			nrTxt++;

			lbl_sredniaBezw = ustawLabel(lbl_sredniaBezw, "średnia bezwzględna", nrTxt);
			txt_sredniaBezw = ustawTextField(txt_sredniaBezw, "", nrTxt);
			txt_sredniaBezw.setEditable(false);
			nrTxt++;

			lbl_wartSkuteczna = ustawLabel(lbl_wartSkuteczna, "wartość skuteczna", nrTxt);
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
