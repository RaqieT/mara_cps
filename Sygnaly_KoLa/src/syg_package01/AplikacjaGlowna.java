package syg_package01;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.ActionMap;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

import syg_Pliki.Akcja_otworz;
import syg_Pliki.Akcja_zapisz;
import syg_Wykresy.PanelRysunek_Histogram;
import syg_Wykresy.PanelRysunek_Wykres;
import syg_package01.Filtr.filtr_przepustowosc;
import syg_package01.Sygnal.rodzaj_sygnalu;
import syg_Symulacja.*;

/**
 * This code was edited or generated using CloudGarden's Jigloo
 * SWT/Swing GUI Builder, which is free for non-commercial
 * use. If Jigloo is being used commercially (ie, by a corporation,
 * company or business for any purpose whatever) then you
 * should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details.
 * Use of Jigloo implies acceptance of these licensing terms.
 * A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
 * THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
 * LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
/**
 * 
 */
public class AplikacjaGlowna extends SingleFrameApplication {
	private JPanel syg_panelGlowny;
	private JMenuBar syg_MenuGlowne;

	private JMenu syg_MenuGlowne_Sygnal;
	private JMenu syg_MenuGlowne_Operacje;
	private JMenu syg_MenuGlowne_Rysunek;

	private JMenuItem syg_MenuGlowne_Sygnal_Nowy;
	private JMenuItem syg_MenuGlowne_Sygnal_Otworz;
	private JMenuItem syg_MenuGlowne_Sygnal_Zapisz;
	private JMenuItem syg_MenuGlowne_Operacje_Dodaj;
	private JSplitPane jSplitPane1;
	private JMenuItem syg_MenuGlowne_Operacje_Odejmij;
	private JMenuItem syg_MenuGlowne_Operacje_Pomnoz;
	private JMenuItem syg_MenuGlowne_Operacje_Podziel;
	private JMenuItem syg_MenuGlowne_Rysunek_Wykres;
	private JTabbedPane syg_Tab1;
	private JMenuItem syg_MenuGlowne_Rysunek_Histogram;
	private GridLayout syg_LayoutGlowny;

	private Sygnal[] listaSygnalow;
	private PanelParametry syg_panelParametry;
	private PanelRysunek_Wykres syg_panelRysunek_Wykres;
	private PanelRysunek_Histogram syg_panelRysunek_Histogram;
	/**
	 * Czy wybrano rysowanie wykresu (a nie histogramu)?
	 */
	private boolean rysujWykres = true;
	private PanelKonwersja syg_panelKwantyzacja;
	private PanelFiltracja syg_panelFiltracja;
	private PanelTransformacja syg_panelTransformacja;
	/**
	 * Lista nazw zakładek.
	 */
	private String[] listaNazw_Tab;
	/**
	 * Lista podpowiedzi do zakładek.
	 */
	private String[] listaToolTips_Tab;
	private JMenu syg_MenuGlowne_OProgramie;
	private JMenuItem syg_MenuGlowne_Info1;
	private JMenuItem syg_MenuGlowne_Info2;
	private JMenu syg_MenuGlowne_Informacje;
	private JMenuItem syg_MenuGlowne_Info_Korelacja;
	private JMenuItem syg_MenuGlowne_Info_Rodzaj;
	private JMenu syg_MenuGlowne_Ustawienia;
	private JMenuItem syg_MenuGlowne_Ustawienia1;

	@Action
	/**
	 * Czyszczenie parametrów
	 */
	public void syg_AkcjaNowy() {
		this.syg_MenuGlowne_Operacje.setEnabled(false);

		this.syg_panelParametry.wyczyscPola();
		this.syg_panelParametry.cb_wybor123.setSelectedIndex(0);
		this.syg_panelParametry.cb_wybor123.setEnabled(false);
		this.syg_panelParametry.zablokujPola(false);

		this.listaSygnalow[0] = new Sygnal();
		this.listaSygnalow[1] = new Sygnal();
		this.listaSygnalow[2] = new Sygnal();

		// pusty wykres
		this.rysujWykres = true;
		this.syg_panelRysunek_Wykres = new PanelRysunek_Wykres();
		this.utworzJSplitPane();

		// wybór i blokada zakładek
		syg_Tab1.setSelectedIndex(0);
		syg_Tab1.getTabComponentAt(1).setEnabled(false);
		syg_Tab1.setEnabledAt(1, false);
		// syg_Tab1.getTabComponentAt(2).setEnabled(false);
		// syg_Tab1.setEnabledAt(2, false);
	}

	@Action
	public void syg_Akcja_Ustawienia1() throws HeadlessException {
		String[] labelsUstawien = new String[] { "Krok zmian strzałkami dla częstotliwości:\n",
				"Krok zmian strzałkami dla współczynników:\n" };

		JFormattedTextField[] us = new JFormattedTextField[2];

		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("src/syg_package01/resources/Ustawienia.properties"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		us[0] = new JFormattedTextField(Double.TYPE);
		us[1] = new JFormattedTextField(Integer.TYPE);

		// us[0].setValue(new Double(0.01));
		us[0].setText(prop.getProperty("Częstotliwość_step"));
		us[0].setColumns(5);
		// us[1].setValue(new Integer(1));
		us[1].setText(prop.getProperty("Współczynniki_step"));
		us[1].setColumns(3);

		JComponent[][] inputs = new JComponent[2][];

		boolean poprawne = true;
		do {
			poprawne = true;
			for (int i = 0; i < labelsUstawien.length; i++) {
				inputs[i] = new JComponent[] { new JLabel(labelsUstawien[i]), us[i] };
			}
			JOptionPane.showMessageDialog(null, inputs, "Filtracja", JOptionPane.PLAIN_MESSAGE);

			try {
				this.syg_panelFiltracja.setControlStepDouble(Double.parseDouble(us[0].getText()));
				this.syg_panelFiltracja.setControlStepInt(Integer.parseInt(us[1].getText()));
			} catch (NumberFormatException e) {

				poprawne = false;
				JOptionPane.showMessageDialog(null, "Niewłaściwa wartość", "Błąd",
						JOptionPane.ERROR_MESSAGE);
			}
		} while (!poprawne);

		try {
			prop.setProperty("Częstotliwość_step", us[0].getText());
			prop.setProperty("Współczynniki_step", us[1].getText());

			FileOutputStream fos = new FileOutputStream(
					"src/syg_package01/resources/Ustawienia.properties");

			prop.store(fos, "Properties for AplikacjaSymulacji");
			fos.flush();
			fos.close();
		} catch (IOException e) {
		}

	}

	@Action
	/**
	 * Informacja o korelacji.
	 */
	public void syg_Akcja_Info_Korelacja() throws HeadlessException {
		try {
			String msgFiltru = "Informacja o korelacji sygnałów:\n";
			String oddzielenie1 = ", ";

			double K = 0.0D;
			int N = 0;
			double f_p = 1.0D;

			if (this.syg_panelFiltracja.getSygnalFiltrowany().getrodzaj() == rodzaj_sygnalu.CIAGLY)
				f_p = 1.0D / this.syg_panelFiltracja.getSygnalFiltrowany().getkroczek();
			else
				f_p = 1.0D / this.syg_panelFiltracja.getSygnalFiltrowany().getkrok();
			K = f_p
					/ ((this.syg_panelFiltracja.getCb_filtr() == filtr_przepustowosc.DOLNOPRZEPUSTOWY) ? (this.syg_panelFiltracja
							.getTxt_obciecie()) : (f_p / 4.0D + (this.syg_panelFiltracja
							.getTxt_obciecie())));

			if (this.syg_panelFiltracja.getCb_syg1().isSelected()) {
				N = this.syg_panelFiltracja.getSygnalFiltrowany().getPunktyY_wykres().size();
			}
			if (this.syg_panelFiltracja.getCb_syg2().isSelected()) {
				N += this.syg_panelFiltracja.getSygnalDoKorelacji().getPunktyY_wykres().size();
				if (this.syg_panelFiltracja.getCb_syg1().isSelected())
					N += 1;
			}

			msgFiltru += "N = " + Integer.toString(N) + oddzielenie1;
			if (this.syg_panelFiltracja.getCb_syg1().isSelected()
					&& this.syg_panelFiltracja.getCb_syg2().isSelected()) {
				msgFiltru += "M = "
						+ Integer.toString(this.syg_panelFiltracja.getSygnalDoKorelacji()
								.getPunktyY_wykres().size()) + oddzielenie1;
			} else {
				msgFiltru += "M = "
						+ Integer.toString(this.syg_panelFiltracja.getTxt_wspolczynniki())
						+ oddzielenie1;
			}
			msgFiltru += "K = " + Double.toString(K);

			JOptionPane.showMessageDialog(null, msgFiltru, "O korelacji",
					JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Nie można wyświetlić informacji", "O korelacji",
					JOptionPane.ERROR_MESSAGE);
			// e.printStackTrace();
		}
	}

	@Action
	public void syg_Akcja_Info_Rodzaj() throws HeadlessException {
		String msgRodzaju = "Rodzaj sygnału:\n";

		if (this.listaSygnalow[this.syg_panelParametry.cb_wybor123.getSelectedIndex()].getrodzaj() == rodzaj_sygnalu.CIAGLY)
			msgRodzaju += "<html><b>ciągły</b></html>";
		else if (this.listaSygnalow[this.syg_panelParametry.cb_wybor123.getSelectedIndex()]
				.getrodzaj() == rodzaj_sygnalu.DYSKRETNY)
			msgRodzaju += "<html><b>dyskretny</b></html>";
		else
			msgRodzaju += "<html><b>nieokreślony</b></html>";

		JOptionPane.showMessageDialog(null, msgRodzaju, "O sygnale",
				JOptionPane.INFORMATION_MESSAGE);
	}

	@Action
	/**
	 * Informacja o wersji.
	 */
	public void syg_AkcjaInfo1() throws HeadlessException {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("src/syg_package01/resources/AplikacjaGlowna.properties"));
		} catch (IOException e) {
		}
		JOptionPane.showMessageDialog(
				null,
				"Aktualna wersja: " + prop.getProperty("Application.version") + "\nZmiany: "
						+ prop.getProperty("Application.changes"), "O wersjach",
				JOptionPane.INFORMATION_MESSAGE);
	}

	@Action
	/**
	 * Informacja o autorach.
	 */
	public void syg_AkcjaInfo2() throws HeadlessException {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("src/syg_package01/resources/AplikacjaGlowna.properties"));
		} catch (IOException e) {
		}
		JOptionPane.showMessageDialog(null, "Autorzy: " + prop.getProperty("Application.authors"),
				"O autorach", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Wyświetlanie pustego panelu wykresu.
	 */
	@Action
	public void syg_AkcjaWykres() {
		this.rysujWykres = true;

		// sczytanie poprzednich ustawień
		int id = syg_panelParametry.cb_wybor123.getSelectedIndex();
		boolean czyAktywne123 = syg_panelParametry.cb_wybor123.isEnabled();
		boolean czyAktywneParametry = syg_panelParametry.czyAktywneParametry(false);

		// nowe obiekty
		syg_panelRysunek_Wykres = new PanelRysunek_Wykres();
		this.utworzJSplitPane();

		// wczytanie do panelu poprzednich parametrów
		this.syg_panelParametry.odczytajParametryZSygnalu(listaSygnalow[id], id);
		this.syg_panelParametry.cb_wybor123.setSelectedIndex(id);
		this.syg_panelParametry.cb_wybor123.setEnabled(czyAktywne123);
		this.syg_panelParametry.zablokujPola(!czyAktywneParametry);
	}

	/**
	 * Wyświetlanie pustego panelu histogramu.
	 */
	@Action
	public void syg_AkcjaHistogram() {
		this.rysujWykres = false;

		// sczytanie poprzednich ustawień
		int id = syg_panelParametry.cb_wybor123.getSelectedIndex();
		boolean czyAktywne123 = syg_panelParametry.cb_wybor123.isEnabled();
		boolean czyAktywneParametry = syg_panelParametry.czyAktywneParametry(false);

		// nowe obiekty
		syg_panelRysunek_Histogram = new PanelRysunek_Histogram();
		this.utworzJSplitPane();

		// wczytanie do panelu poprzednich parametrów
		this.syg_panelParametry.odczytajParametryZSygnalu(listaSygnalow[id], id);
		this.syg_panelParametry.cb_wybor123.setSelectedIndex(id);
		syg_panelParametry.cb_wybor123.setEnabled(czyAktywne123);
		this.syg_panelParametry.zablokujPola(!czyAktywneParametry);
	}

	/**
	 * Wczytanie parametrów sygnału z pliku
	 */
	@Action
	public void syg_AkcjaOtworz() {

		Akcja_otworz otwarcie;
		int czyDrugi = 0;

		if (this.listaSygnalow[0].getPunktyY_wykres().size() <= 0) {
		} else {
			czyDrugi = JOptionPane.showConfirmDialog(null,
					"Czy nadpisać sygnał pierwszy?\n(Jeśli nie to sygnał będzie jako drugi)",
					"Pobieranie sygnału", JOptionPane.YES_NO_OPTION);
		}
		otwarcie = new Akcja_otworz(this.listaSygnalow[czyDrugi], syg_MenuGlowne_Sygnal_Otworz);

		if (otwarcie.odczytPrawidlowy) {
			this.listaSygnalow[czyDrugi] = otwarcie.getSygnal();
			if (czyDrugi > 0) {
				this.syg_panelFiltracja.setSygnalDoKorelacji(this.listaSygnalow[czyDrugi]);
				this.syg_panelFiltracja.getCb_syg2().setEnabled(true);
			} else {
				this.syg_panelFiltracja.setSygnalFiltrowany(this.listaSygnalow[czyDrugi]);
				this.syg_panelFiltracja.getCb_syg1().setEnabled(true);
			}
			// if (czyDrugi == 0)
			// this.syg_panelParametry.wyczyscPola();
			// this.syg_panelParametry.zablokujPola(false);
			this.syg_panelParametry.cb_wybor123.setEnabled(czyDrugi > 0);

			if (this.rysujWykres) {
				this.syg_panelRysunek_Wykres = new PanelRysunek_Wykres();
				this.jSplitPane1.add(syg_panelRysunek_Wykres, JSplitPane.RIGHT);
			} else {
				this.syg_panelRysunek_Histogram = new PanelRysunek_Histogram();
				this.jSplitPane1.add(syg_panelRysunek_Histogram, JSplitPane.RIGHT);
			}

			if (this.listaSygnalow[czyDrugi].getrodzaj() == rodzaj_sygnalu.CIAGLY) {
				this.syg_panelParametry.zablokujPola(false);
				this.syg_panelParametry.wyczyscPola();
				this.syg_panelParametry
						.odczytajParametryZSygnalu(listaSygnalow[czyDrugi], czyDrugi);
				this.syg_MenuGlowne_Operacje.setEnabled(false);
			} else {
				this.syg_panelParametry.zablokujPola(true);
				this.syg_panelParametry.wyczyscPola();
				this.syg_panelParametry
						.odczytajParametryZSygnalu(listaSygnalow[czyDrugi], czyDrugi);
				if (listaSygnalow[czyDrugi].getrodzaj() == rodzaj_sygnalu.DYSKRETNY) {
					this.syg_MenuGlowne_Operacje.setEnabled(true);
				}
			}

			this.syg_panelParametry.cb_wybor123.setSelectedIndex(czyDrugi);

			syg_Tab1.setSelectedIndex(0);

			syg_Tab1.getTabComponentAt(1).setEnabled(false);
			syg_Tab1.setEnabledAt(1, false);
			if (this.listaSygnalow[czyDrugi].getrodzaj() != rodzaj_sygnalu.CIAGLY)
				this.syg_panelFiltracja.getBtn_filtracja().setEnabled(true);
			// syg_Tab1.getTabComponentAt(2).setEnabled(false);
			// syg_Tab1.setEnabledAt(2, false);
		}
	}

	/**
	 * Zapisanie parametrów sygnału w wybranym formacie
	 */
	@Action
	public void syg_AkcjaZapisz() {
		String msgPoprawnosc = this.syg_panelParametry.sprawdzPoprawnosc();
		int id = 0;
		if (syg_panelParametry.cb_wybor123.getSelectedIndex() >= 0)
			id = syg_panelParametry.cb_wybor123.getSelectedIndex();

		if (msgPoprawnosc == "ok") {

			if (this.listaSygnalow[id].getrodzaj() == rodzaj_sygnalu.CIAGLY) {
				listaSygnalow[id] = this.syg_panelParametry.zapiszParametryDoSygnalu();

				// konwersja dla zapisu sygnału
				int czyDyskretny;
				czyDyskretny = JOptionPane.showConfirmDialog(null, "Czy zaisać jako dyskretny?",
						"Zapis sygnału", JOptionPane.YES_NO_OPTION);

				if (czyDyskretny == 0) {
					this.listaSygnalow[id].setRodzaj(rodzaj_sygnalu.DYSKRETNY);

					// ustalenie częstotliwości próbkowania
					JFormattedTextField f_s = new JFormattedTextField(Double.TYPE);
					f_s.setValue(new Double(1000.0));
					f_s.setColumns(5);

					JComponent[] inputs = new JComponent[] {
							new JLabel("Częstotliwość próbkowania:"), f_s };
					do {
						JOptionPane.showMessageDialog(null, inputs, "Próbkowanie",
								JOptionPane.PLAIN_MESSAGE);

						if (f_s.isValid() || !f_s.getText().isEmpty()) {
							this.listaSygnalow[id]
									.setkrok(1.0D / Double.parseDouble(f_s.getText()));
						} else {
							JOptionPane.showMessageDialog(null, "Niewłaściwa wartość", "Błąd",
									JOptionPane.ERROR_MESSAGE);
						}
					} while (f_s.isValid() && !f_s.getText().isEmpty());

					this.listaSygnalow[id].wyczyscPunkty(true);
					PanelRysunek_Wykres przeliczenie = new PanelRysunek_Wykres(listaSygnalow[id]);
					listaSygnalow[id] = przeliczenie.getSygnalWyswietlany();
				}
			} else {
				// this.listaSygnalow[id].setRodzaj(rodzaj_sygnalu.CIAGLY);
			}

			Akcja_zapisz zapis = new Akcja_zapisz(this.listaSygnalow[id],
					syg_MenuGlowne_Sygnal_Zapisz);
		} else {
			JOptionPane.showMessageDialog(null, msgPoprawnosc, "Błąd próby zapisu pliku",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Sprawdzanie czy można wykonać operacje na dwóch sygnałach
	 * 
	 * @param _sygnalA
	 *            : Sygnal
	 * @param _sygnalB
	 *            : Sygnal
	 * @return : String <br>
	 *         "ok" lub: "Sygnały nie mogą być dodane."
	 */
	private String sprawdzPoprawnoscOperacji(Sygnal _sygnalA, Sygnal _sygnalB) {
		// TODO Auto-generated method stub
		String msg = "";
		if (_sygnalA.porownajSygnal(_sygnalB) && _sygnalA.getrodzaj() == rodzaj_sygnalu.DYSKRETNY
				&& _sygnalB.getrodzaj() == rodzaj_sygnalu.DYSKRETNY) {
			msg = "ok";
		} else {
			if (_sygnalA.getrodzaj() == rodzaj_sygnalu.CIAGLY
					|| _sygnalB.getrodzaj() == rodzaj_sygnalu.CIAGLY) {
				msg = "Sygnały nie mogą być dodane. Przynajmniej jeden z nich jest ciągły.";
			} else
				msg = "Sygnały nie mogą być dodane. Odpowiednie wartości nie są równe.";
		}
		return msg;
	}

	/**
	 * Dodawanie sygnałów pobieranych z pliku.
	 */
	@Action
	public void syg_AkcjaOp_Dodaj() {
		// pobranie drugiego sygnału z pliku
		Akcja_otworz otwarcie = new Akcja_otworz(this.listaSygnalow[1],
				syg_MenuGlowne_Operacje_Dodaj);
		listaSygnalow[1] = otwarcie.getSygnal();
		String msgPoprawnosc = this.sprawdzPoprawnoscOperacji(listaSygnalow[0], listaSygnalow[1]);

		if (msgPoprawnosc == "ok") {
			int iloscProbek = 0;
			// pobranie mniejszej ilości próbek
			if (listaSygnalow[1].getPunktyY_wykres().size() > listaSygnalow[0].getPunktyY_wykres()
					.size())
				iloscProbek = listaSygnalow[0].getPunktyY_wykres().size();
			else
				iloscProbek = listaSygnalow[1].getPunktyY_wykres().size();

			if (listaSygnalow[2].getPunktyY_wykres() != null)
				listaSygnalow[2].wyczyscPunkty(true);

			// wpisanie wartości parametrów do nowego sygnału
			listaSygnalow[2].setRodzaj(rodzaj_sygnalu.DYSKRETNY);
			listaSygnalow[2].pobierzParametryUzytkownika(listaSygnalow[0].gettyp(),
					(listaSygnalow[0].getA() + listaSygnalow[1].getA()), listaSygnalow[0].gett1(),
					listaSygnalow[0].getts(), listaSygnalow[0].getd(), // d
					listaSygnalow[0].getT(), listaSygnalow[0].getKw(), listaSygnalow[0].getskok());
			listaSygnalow[2].setkrok(listaSygnalow[0].getkrok());

			// operacja
			for (int i = 0; i < iloscProbek; i++) {
				listaSygnalow[2].setPunktyY_wykres(listaSygnalow[0].getPunktzindexu(i)
						+ listaSygnalow[1].getPunktzindexu(i));
			}

			// zablokowanie nieedytowalnych wartości i odblokowanie innych
			syg_panelParametry.cb_wybor123.setEnabled(true);
			this.syg_MenuGlowne_Operacje.setEnabled(false);
			syg_panelParametry.zablokujPola(true);

		}
		if (msgPoprawnosc != "ok") {
			JOptionPane.showMessageDialog(null, msgPoprawnosc, "Błąd próby operacji na sygnałach",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	@Action
	public void syg_AkcjaOp_Odejmij() {
		// pobranie drugiego sygnału z pliku
		Akcja_otworz otwarcie = new Akcja_otworz(this.listaSygnalow[1],
				syg_MenuGlowne_Operacje_Dodaj);
		listaSygnalow[1] = otwarcie.getSygnal();
		String msgPoprawnosc = this.sprawdzPoprawnoscOperacji(listaSygnalow[0], listaSygnalow[1]);

		if (msgPoprawnosc == "ok") {
			int iloscProbek = 0;
			// pobranie mniejszej ilości próbek
			if (listaSygnalow[1].getPunktyY_wykres().size() > listaSygnalow[0].getPunktyY_wykres()
					.size())
				iloscProbek = listaSygnalow[0].getPunktyY_wykres().size();
			else
				iloscProbek = listaSygnalow[1].getPunktyY_wykres().size();

			if (listaSygnalow[2].getPunktyY_wykres() != null)
				listaSygnalow[2].wyczyscPunkty(true);

			// wpisanie wartości parametrów do nowego sygnału
			listaSygnalow[2].setRodzaj(rodzaj_sygnalu.DYSKRETNY);
			listaSygnalow[2].pobierzParametryUzytkownika(listaSygnalow[0].gettyp(),
					(listaSygnalow[0].getA() - listaSygnalow[1].getA()), listaSygnalow[0].gett1(),
					listaSygnalow[0].getts(), listaSygnalow[0].getd(), // d
					listaSygnalow[0].getT(), listaSygnalow[0].getKw(), listaSygnalow[0].getskok());
			listaSygnalow[2].setkrok(listaSygnalow[0].getkrok());

			// operacja
			for (int i = 0; i < iloscProbek; i++) {
				listaSygnalow[2].setPunktyY_wykres(listaSygnalow[0].getPunktzindexu(i)
						- listaSygnalow[1].getPunktzindexu(i));
			}

			// zablokowanie nieedytowalnych wartości i odblokowanie innych
			syg_panelParametry.cb_wybor123.setEnabled(true);
			this.syg_MenuGlowne_Operacje.setEnabled(false);
			syg_panelParametry.zablokujPola(true);

		}
		if (msgPoprawnosc != "ok") {
			JOptionPane.showMessageDialog(null, msgPoprawnosc, "Błąd próby operacji na sygnałach",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	@Action
	public void syg_AkcjaOp_Pomnoz() {
		// pobranie drugiego sygnału z pliku
		Akcja_otworz otwarcie = new Akcja_otworz(this.listaSygnalow[1],
				syg_MenuGlowne_Operacje_Dodaj);
		listaSygnalow[1] = otwarcie.getSygnal();
		String msgPoprawnosc = this.sprawdzPoprawnoscOperacji(listaSygnalow[0], listaSygnalow[1]);

		if (msgPoprawnosc == "ok") {
			int iloscProbek = 0;
			// pobranie mniejszej ilości próbek
			if (listaSygnalow[1].getPunktyY_wykres().size() > listaSygnalow[0].getPunktyY_wykres()
					.size())
				iloscProbek = listaSygnalow[0].getPunktyY_wykres().size();
			else
				iloscProbek = listaSygnalow[1].getPunktyY_wykres().size();

			if (listaSygnalow[2].getPunktyY_wykres() != null)
				listaSygnalow[2].wyczyscPunkty(true);

			// wpisanie wartości parametrów do nowego sygnału
			listaSygnalow[2].setRodzaj(rodzaj_sygnalu.DYSKRETNY);
			listaSygnalow[2].pobierzParametryUzytkownika(listaSygnalow[0].gettyp(),
					(listaSygnalow[0].getA() * listaSygnalow[1].getA()), listaSygnalow[0].gett1(),
					listaSygnalow[0].getts(), listaSygnalow[0].getd(), // d
					listaSygnalow[0].getT(), listaSygnalow[0].getKw(), listaSygnalow[0].getskok());
			listaSygnalow[2].setkrok(listaSygnalow[0].getkrok());

			// operacja
			for (int i = 0; i < iloscProbek; i++) {
				listaSygnalow[2].setPunktyY_wykres(listaSygnalow[0].getPunktzindexu(i)
						* listaSygnalow[1].getPunktzindexu(i));
			}

			// zablokowanie nieedytowalnych wartości i odblokowanie innych
			syg_panelParametry.cb_wybor123.setEnabled(true);
			this.syg_MenuGlowne_Operacje.setEnabled(false);
			syg_panelParametry.zablokujPola(true);

		}
		if (msgPoprawnosc != "ok") {
			JOptionPane.showMessageDialog(null, msgPoprawnosc, "Błąd próby operacji na sygnałach",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	@Action
	public void syg_AkcjaOp_Podziel() {
		// pobranie drugiego sygnału z pliku
		Akcja_otworz otwarcie = new Akcja_otworz(this.listaSygnalow[1],
				syg_MenuGlowne_Operacje_Dodaj);
		listaSygnalow[1] = otwarcie.getSygnal();
		String msgPoprawnosc = this.sprawdzPoprawnoscOperacji(listaSygnalow[0], listaSygnalow[1]);

		if (msgPoprawnosc == "ok") {
			int iloscProbek = 0;
			// pobranie mniejszej ilości próbek
			if (listaSygnalow[1].getPunktyY_wykres().size() > listaSygnalow[0].getPunktyY_wykres()
					.size())
				iloscProbek = listaSygnalow[0].getPunktyY_wykres().size();
			else
				iloscProbek = listaSygnalow[1].getPunktyY_wykres().size();

			if (listaSygnalow[2].getPunktyY_wykres() != null)
				listaSygnalow[2].wyczyscPunkty(true);

			// wpisanie wartości parametrów do nowego sygnału
			listaSygnalow[2].setRodzaj(rodzaj_sygnalu.DYSKRETNY);
			listaSygnalow[2].pobierzParametryUzytkownika(listaSygnalow[0].gettyp(),
					(listaSygnalow[0].getA() / listaSygnalow[1].getA()), listaSygnalow[0].gett1(),
					listaSygnalow[0].getts(), listaSygnalow[0].getd(), // d
					listaSygnalow[0].getT(), listaSygnalow[0].getKw(), listaSygnalow[0].getskok());
			listaSygnalow[2].setkrok(listaSygnalow[0].getkrok());

			// operacja
			for (int i = 0; i < iloscProbek; i++) {
				listaSygnalow[2].setPunktyY_wykres(listaSygnalow[0].getPunktzindexu(i)
						/ ((listaSygnalow[1].getPunktzindexu(i) != 0) ? listaSygnalow[1]
								.getPunktzindexu(i) : 0.0001));
			}

			// zablokowanie nieedytowalnych wartości i odblokowanie innych
			syg_panelParametry.cb_wybor123.setEnabled(true);
			this.syg_MenuGlowne_Operacje.setEnabled(false);
			syg_panelParametry.zablokujPola(true);

		}
		if (msgPoprawnosc != "ok") {
			JOptionPane.showMessageDialog(null, msgPoprawnosc, "Błąd próby operacji na sygnałach",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	private ActionMap getAppActionMap() {
		return Application.getInstance().getContext().getActionMap(this);
	}

	@Override
	protected void startup() {
		{
			listaNazw_Tab = new String[] { "Parametry", "Konwersje", "Filtracja", "Transformacja" };
			listaToolTips_Tab = new String[] {
					"Parametry sygnału, operacje, wykres/histogram, odczyt/zapis",
					"Próbkowanie, kwantyzacja, interpolacja, rekonstrukcja",
					"Splot, filtracja i korelacja",
					"Przekształcenie Fouriera, Walsha-Hadamarda, kosinusowe i falkowe, szybkie algorytmy"};

			listaSygnalow = new Sygnal[3];
			listaSygnalow[0] = new Sygnal();
			listaSygnalow[1] = new Sygnal();
			this.listaSygnalow[1].setRodzaj(rodzaj_sygnalu.DYSKRETNY);
			listaSygnalow[2] = new Sygnal();
			this.listaSygnalow[2].setRodzaj(rodzaj_sygnalu.DYSKRETNY);

			syg_MenuGlowne = new JMenuBar();
			getMainFrame().setJMenuBar(syg_MenuGlowne);
			{
				syg_MenuGlowne_Sygnal = new JMenu();
				syg_MenuGlowne.add(syg_MenuGlowne_Sygnal);
				syg_MenuGlowne_Sygnal.setName("syg_MenuGlowne_Sygnal");
				{
					{
						syg_MenuGlowne_Sygnal_Nowy = new JMenuItem();
						syg_MenuGlowne_Sygnal.add(syg_MenuGlowne_Sygnal_Nowy);
						syg_MenuGlowne_Sygnal_Nowy
								.setAction(getAppActionMap().get("syg_AkcjaNowy"));
					}
					{
						syg_MenuGlowne_Sygnal_Otworz = new JMenuItem();
						syg_MenuGlowne_Sygnal.add(syg_MenuGlowne_Sygnal_Otworz);
						syg_MenuGlowne_Sygnal_Otworz.setAction(getAppActionMap().get(
								"syg_AkcjaOtworz"));
					}
					{
						syg_MenuGlowne_Sygnal_Zapisz = new JMenuItem();
						syg_MenuGlowne_Sygnal.add(syg_MenuGlowne_Sygnal_Zapisz);
						syg_MenuGlowne_Sygnal_Zapisz.setAction(getAppActionMap().get(
								"syg_AkcjaZapisz"));
					}
				}
				syg_MenuGlowne_Operacje = new JMenu();
				syg_MenuGlowne.add(syg_MenuGlowne_Operacje);
				syg_MenuGlowne_Operacje.setName("syg_MenuGlowne_Operacje");
				syg_MenuGlowne_Operacje.setEnabled(false);
				{
					{
						syg_MenuGlowne_Operacje_Dodaj = new JMenuItem();
						syg_MenuGlowne_Operacje.add(syg_MenuGlowne_Operacje_Dodaj);
						syg_MenuGlowne_Operacje_Dodaj.setAction(getAppActionMap().get(
								"syg_AkcjaOp_Dodaj"));
					}
					{
						syg_MenuGlowne_Operacje_Odejmij = new JMenuItem();
						syg_MenuGlowne_Operacje.add(syg_MenuGlowne_Operacje_Odejmij);
						syg_MenuGlowne_Operacje_Odejmij.setAction(getAppActionMap().get(
								"syg_AkcjaOp_Odejmij"));
					}
					{
						syg_MenuGlowne_Operacje_Pomnoz = new JMenuItem();
						syg_MenuGlowne_Operacje.add(syg_MenuGlowne_Operacje_Pomnoz);
						syg_MenuGlowne_Operacje_Pomnoz.setAction(getAppActionMap().get(
								"syg_AkcjaOp_Pomnoz"));
					}
					{
						syg_MenuGlowne_Operacje_Podziel = new JMenuItem();
						syg_MenuGlowne_Operacje.add(syg_MenuGlowne_Operacje_Podziel);
						syg_MenuGlowne_Operacje_Podziel.setAction(getAppActionMap().get(
								"syg_AkcjaOp_Podziel"));
					}
				}
				syg_MenuGlowne_Rysunek = new JMenu();
				syg_MenuGlowne.add(syg_MenuGlowne_Rysunek);
				syg_MenuGlowne_Rysunek.setName("syg_MenuGlowne_Rysunek");
				{
					{
						syg_MenuGlowne_Rysunek_Wykres = new JMenuItem();
						syg_MenuGlowne_Rysunek.add(syg_MenuGlowne_Rysunek_Wykres);
						syg_MenuGlowne_Rysunek_Wykres.setAction(getAppActionMap().get(
								"syg_AkcjaWykres"));
					}
					{
						syg_MenuGlowne_Rysunek_Histogram = new JMenuItem();
						syg_MenuGlowne_Rysunek.add(syg_MenuGlowne_Rysunek_Histogram);
						syg_MenuGlowne_Rysunek_Histogram.setAction(getAppActionMap().get(
								"syg_AkcjaHistogram"));
					}
				}
				syg_MenuGlowne_Informacje = new JMenu();
				syg_MenuGlowne.add(syg_MenuGlowne_Informacje);
				syg_MenuGlowne_Informacje.setName("syg_MenuGlowne_Informacje");
				{
					{
						syg_MenuGlowne_Info_Korelacja = new JMenuItem();
						syg_MenuGlowne_Informacje.add(syg_MenuGlowne_Info_Korelacja);
						syg_MenuGlowne_Info_Korelacja.setAction(getAppActionMap().get(
								"syg_Akcja_Info_Korelacja"));
					}
					{
						syg_MenuGlowne_Info_Rodzaj = new JMenuItem();
						syg_MenuGlowne_Informacje.add(syg_MenuGlowne_Info_Rodzaj);
						syg_MenuGlowne_Info_Rodzaj.setAction(getAppActionMap().get(
								"syg_Akcja_Info_Rodzaj"));
					}
				}
				syg_MenuGlowne_Ustawienia = new JMenu();
				syg_MenuGlowne.add(syg_MenuGlowne_Ustawienia);
				syg_MenuGlowne_Ustawienia.setName("syg_MenuGlowne_Ustawienia");
				{
					{
						syg_MenuGlowne_Ustawienia1 = new JMenuItem();
						syg_MenuGlowne_Ustawienia.add(syg_MenuGlowne_Ustawienia1);
						syg_MenuGlowne_Ustawienia1.setAction(getAppActionMap().get(
								"syg_Akcja_Ustawienia1"));
					}
				}
				syg_MenuGlowne_OProgramie = new JMenu();
				syg_MenuGlowne.add(syg_MenuGlowne_OProgramie);
				syg_MenuGlowne_OProgramie.setName("syg_MenuGlowne_OProgramie");
				{
					{
						syg_MenuGlowne_Info1 = new JMenuItem();
						syg_MenuGlowne_OProgramie.add(syg_MenuGlowne_Info1);
						syg_MenuGlowne_Info1.setAction(getAppActionMap().get("syg_AkcjaInfo1"));
					}
					{
						syg_MenuGlowne_Info2 = new JMenuItem();
						syg_MenuGlowne_OProgramie.add(syg_MenuGlowne_Info2);
						syg_MenuGlowne_Info2.setAction(getAppActionMap().get("syg_AkcjaInfo2"));
					}
				}
			}

		}

		{
			{
				Dimension rozmiarOkna = new Dimension(800, 550);
				this.getMainFrame().setMinimumSize(rozmiarOkna);
				this.getMainFrame().setPreferredSize(rozmiarOkna);

				syg_panelGlowny = new JPanel();
				syg_LayoutGlowny = new GridLayout(1, 1);

				syg_panelGlowny.setLayout(syg_LayoutGlowny);
				syg_panelGlowny.setPreferredSize(rozmiarOkna);
				syg_panelGlowny.setMinimumSize(rozmiarOkna);

				jSplitPane1 = new JSplitPane();
				syg_panelGlowny.add(jSplitPane1);
				jSplitPane1.setPreferredSize(rozmiarOkna);

				syg_panelRysunek_Wykres = new PanelRysunek_Wykres();
				syg_panelRysunek_Histogram = new PanelRysunek_Histogram();

				// {
				// if (this.rysujWykres) {
				// jSplitPane1.add(syg_panelRysunek_Wykres, JSplitPane.RIGHT);
				// } else {
				// jSplitPane1.add(syg_panelRysunek_Histogram,
				// JSplitPane.RIGHT);
				// }
				// }
				// {
				// syg_Tab1 = new JTabbedPane();
				// syg_Tab1.setTabPlacement(JTabbedPane.TOP);
				// syg_Tab1.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
				// {
				// syg_panelParametry = new PanelParametry(listaSygnalow,
				// syg_panelRysunek_Wykres, syg_panelRysunek_Histogram,
				// this.jSplitPane1,
				// this.rysujWykres);
				// }
				// JLabel lblTab1 = new JLabel(this.listaNazw_Tab[0]);
				// syg_Tab1.addTab(this.listaNazw_Tab[0], syg_panelParametry);
				// syg_Tab1.setTabComponentAt(0, lblTab1);
				// syg_Tab1.setToolTipTextAt(0, this.listaToolTips_Tab[0]);
				// {
				// syg_panelKwantyzacja = new PanelKonwersja(listaSygnalow[0],
				// jSplitPane1);
				// }
				// JLabel lblTab2 = new JLabel(this.listaNazw_Tab[1]);
				// syg_Tab1.addTab(this.listaNazw_Tab[1], syg_panelKwantyzacja);
				// syg_Tab1.setTabComponentAt(1, lblTab2);
				// syg_Tab1.setToolTipTextAt(1, this.listaToolTips_Tab[1]);
				//
				// syg_Tab1.getTabComponentAt(1).setEnabled(false);
				// syg_Tab1.setEnabledAt(1, false);
				//
				// jSplitPane1.add(syg_Tab1, JSplitPane.LEFT);
				// // syg_panelParametry.setPreferredSize(new
				// // java.awt.Dimension(220, 500));
				// }
				this.utworzJSplitPane();

				jSplitPane1.setAlignmentX(2);
				syg_panelGlowny.add(jSplitPane1);
			}
		}

		show(syg_panelGlowny);
	}

	public void utworzJSplitPane() {
		{

			if (this.rysujWykres) {
				jSplitPane1.add(syg_panelRysunek_Wykres, JSplitPane.RIGHT);
			} else {
				jSplitPane1.add(syg_panelRysunek_Histogram, JSplitPane.RIGHT);
			}
		}
		{
			syg_Tab1 = new JTabbedPane();
			syg_Tab1.setTabPlacement(JTabbedPane.TOP);
			syg_Tab1.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
			{
				syg_panelKwantyzacja = new PanelKonwersja(listaSygnalow[0], jSplitPane1);
				syg_panelParametry = new PanelParametry(listaSygnalow, syg_panelRysunek_Wykres,
						syg_panelRysunek_Histogram, this.jSplitPane1, this.rysujWykres);
				syg_panelFiltracja = new PanelFiltracja(listaSygnalow[0], jSplitPane1);
				syg_panelTransformacja = new PanelTransformacja(listaSygnalow[0], jSplitPane1);
			}
			JLabel lblTab1 = new JLabel(this.listaNazw_Tab[0]);
			syg_Tab1.addTab(this.listaNazw_Tab[0], syg_panelParametry);
			syg_Tab1.setTabComponentAt(0, lblTab1);
			syg_Tab1.setToolTipTextAt(0, this.listaToolTips_Tab[0]);

			JLabel lblTab2 = new JLabel(this.listaNazw_Tab[1]);
			syg_Tab1.addTab(this.listaNazw_Tab[1], syg_panelKwantyzacja);
			syg_Tab1.setTabComponentAt(1, lblTab2);
			syg_Tab1.setToolTipTextAt(1, this.listaToolTips_Tab[1]);

			JLabel lblTab3 = new JLabel(this.listaNazw_Tab[2]);
			syg_Tab1.addTab(this.listaNazw_Tab[2], syg_panelFiltracja);
			syg_Tab1.setTabComponentAt(2, lblTab3);
			syg_Tab1.setToolTipTextAt(2, this.listaToolTips_Tab[2]);
			
			JLabel lblTab4 = new JLabel(this.listaNazw_Tab[3]);
			syg_Tab1.addTab(this.listaNazw_Tab[3], syg_panelTransformacja);
			syg_Tab1.setTabComponentAt(3, lblTab4);
			syg_Tab1.setToolTipTextAt(3, this.listaToolTips_Tab[3]);

			syg_Tab1.getTabComponentAt(1).setEnabled(false);
			syg_Tab1.setEnabledAt(1, false);

			// syg_Tab1.getTabComponentAt(2).setEnabled(false);
			// syg_Tab1.setEnabledAt(2, false);

			jSplitPane1.add(syg_Tab1, JSplitPane.LEFT);
			// syg_panelParametry.setPreferredSize(new
			// java.awt.Dimension(220, 500));
		}
	}

	public static void main(String[] args) {
		launch(AplikacjaGlowna.class, args);

	}

}
