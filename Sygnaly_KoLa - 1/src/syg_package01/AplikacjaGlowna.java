package syg_package01;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ActionMap;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

import syg_package01.Sygnal.rodzaj_sygnalu;

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
	private JMenuItem syg_MenuGlowne_Rysunek_Histogram;
	private GridLayout syg_LayoutGlowny;

	private Sygnal[] listaSygnalow;
	private PanelParametry syg_panelParametry;
	private PanelRysunek_Wykres syg_panelRysunek_Wykres;
	private PanelRysunek_Histogram syg_panelRysunek_Histogram;
	private boolean rysujWykres = true;

	@Action
	/**
	 * Czyszczenie parametrów
	 */
	public void syg_AkcjaNowy() {
		this.syg_panelParametry.wyczyscPola();
		this.syg_MenuGlowne_Operacje.setEnabled(false);
		this.syg_panelParametry.cb_wybor123.setSelectedIndex(0);
		this.syg_panelParametry.cb_wybor123.setEnabled(false);

		this.listaSygnalow[0] = new Sygnal();
		this.listaSygnalow[1] = new Sygnal();
		this.listaSygnalow[2] = new Sygnal();

		this.syg_panelParametry.zablokujPola(false);
	}

	@Action
	/**
	 * Czyszczenie parametrów
	 */
	public void syg_AkcjaWykres() {
		this.rysujWykres = true;
		// {
		// syg_panelRysunek = new PanelRysunek();
		// jSplitPane1.add(syg_panelRysunek, JSplitPane.RIGHT);
		// }
		// {
		// syg_panelParametry.zmienRysunek(true, syg_panelRysunek);
		// }
		{
			syg_panelRysunek_Wykres = new PanelRysunek_Wykres();
			jSplitPane1.add(syg_panelRysunek_Wykres, JSplitPane.RIGHT);
		}
		{
			syg_panelParametry = new PanelParametry(listaSygnalow,
					syg_panelRysunek_Wykres, syg_panelRysunek_Histogram,
					jSplitPane1, this.rysujWykres);
			jSplitPane1.add(syg_panelParametry, JSplitPane.LEFT);
			// syg_panelParametry.setPreferredSize(new
			// java.awt.Dimension(220, 500));
		}
		this.syg_panelParametry
				.odczytajParametryZSygnalu(
						listaSygnalow[syg_panelParametry.cb_wybor123
								.getSelectedIndex()],
						syg_panelParametry.cb_wybor123.getSelectedIndex());
	}

	/**
	 * Czyszczenie parametrów
	 */
	@Action
	public void syg_AkcjaHistogram() {
		this.rysujWykres = false;
		// {
		// syg_panelRysunek2 = new PanelRysunek2();
		// jSplitPane1.add(syg_panelRysunek2, JSplitPane.RIGHT);
		// }
		// {
		// syg_panelParametry.zmienRysunek(false, syg_panelRysunek2);
		// }
		{
			syg_panelRysunek_Histogram = new PanelRysunek_Histogram();
			jSplitPane1.add(syg_panelRysunek_Wykres, JSplitPane.RIGHT);
		}
		{
			syg_panelParametry = new PanelParametry(listaSygnalow,
					syg_panelRysunek_Wykres, syg_panelRysunek_Histogram,
					jSplitPane1, this.rysujWykres);
			jSplitPane1.add(syg_panelParametry, JSplitPane.LEFT);
			// syg_panelParametry.setPreferredSize(new
			// java.awt.Dimension(220, 500));
		}
		this.syg_panelParametry
				.odczytajParametryZSygnalu(
						listaSygnalow[syg_panelParametry.cb_wybor123
								.getSelectedIndex()],
						syg_panelParametry.cb_wybor123.getSelectedIndex());
	}

	/**
	 * Wczytanie parametrów sygnału z pliku
	 */
	@Action
	public void syg_AkcjaOtworz() {

		Akcja_otworz otwarcie = new Akcja_otworz(this.listaSygnalow[0],
				syg_MenuGlowne_Sygnal_Otworz);
		if (otwarcie.odczytPrawidlowy) {
			this.syg_panelParametry.wyczyscPola();
			this.syg_panelParametry.zablokujPola(false);
			this.syg_panelParametry.cb_wybor123.setSelectedIndex(0);
			this.syg_panelParametry.cb_wybor123.setEnabled(false);
			if (this.rysujWykres)
				this.syg_panelRysunek_Wykres = new PanelRysunek_Wykres();
			else
				this.syg_panelRysunek_Histogram = new PanelRysunek_Histogram();

			this.listaSygnalow[0] = otwarcie.getSygnal();
			this.syg_panelParametry.odczytajParametryZSygnalu(listaSygnalow[0],
					0);

			if (listaSygnalow[0].getrodzaj() == rodzaj_sygnalu.DYSKRETNY) {
				this.syg_MenuGlowne_Operacje.setEnabled(true);
			}
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
			listaSygnalow[id] = this.syg_panelParametry
					.zapiszParametryDoSygnalu();
			// listaSygnalow[id].setRodzaj(rodzaj_sygnalu.DYSKRETNY);
			Akcja_zapisz zapis = new Akcja_zapisz(this.listaSygnalow[id],
					syg_MenuGlowne_Sygnal_Zapisz);
			// Akcja_zapisz zapis = new
			// Akcja_zapisz(this.syg_panelParametry.zapiszParametryDoSygnalu(),
			// syg_MenuGlowne_Sygnal_Zapisz);
		} else {
			JOptionPane.showMessageDialog(null, msgPoprawnosc,
					"Błąd próby zapisu pliku", JOptionPane.ERROR_MESSAGE);
		}
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
		String msgPoprawnosc = this.sprawdzPoprawnoscOperacji(listaSygnalow[0],
				listaSygnalow[1]);

		if (msgPoprawnosc == "ok") {
			int iloscProbek = 0;
			// pobranie mniejszej ilości próbek
			if (listaSygnalow[1].punktyY.size() > listaSygnalow[0].punktyY
					.size())
				iloscProbek = listaSygnalow[0].punktyY.size();
			else
				iloscProbek = listaSygnalow[1].punktyY.size();

			if (listaSygnalow[2].getPunktyY() != null)
				listaSygnalow[2].wyczyscPunkty();

			// wpisanie wartości parametrów do nowego sygnału
			listaSygnalow[2].setRodzaj(rodzaj_sygnalu.DYSKRETNY);
			listaSygnalow[2].pobierzParametryUzytkownika(
					listaSygnalow[0].gettyp(),
					(listaSygnalow[0].getA() + listaSygnalow[1].getA()),
					listaSygnalow[0].gett1(),
					listaSygnalow[0].getts(),
					listaSygnalow[0].getd(), // d
					listaSygnalow[0].getT(), listaSygnalow[0].getKw(),
					listaSygnalow[0].getskok());
			listaSygnalow[2].setkroczek(listaSygnalow[0].getkroczek());

			// operacja
			for (int i = 0; i < iloscProbek; i++) {
				listaSygnalow[2].setPunktyY(listaSygnalow[0].getPunktzindexu(i)
						+ listaSygnalow[1].getPunktzindexu(i));
			}

			// zablokowanie nieedytowalnych wartości i odblokowanie innych
			syg_panelParametry.cb_wybor123.setEnabled(true);
			this.syg_MenuGlowne_Operacje.setEnabled(false);
			syg_panelParametry.zablokujPola(true);

		}
		if (msgPoprawnosc != "ok") {
			JOptionPane.showMessageDialog(null, msgPoprawnosc,
					"Błąd próby operacji na sygnałach",
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
		if (_sygnalA.porownajSygnal(_sygnalB)
				&& _sygnalA.getrodzaj() == rodzaj_sygnalu.DYSKRETNY
				&& _sygnalB.getrodzaj() == rodzaj_sygnalu.DYSKRETNY) {
			msg = "ok";
		} else {
			if (_sygnalA.getrodzaj() == rodzaj_sygnalu.CIAGLY
					|| _sygnalB.getrodzaj() == rodzaj_sygnalu.CIAGLY) {
				msg = "Sygnały nie mogą być dodane. Przynajmniej jeden z nich jest siągły.";
			} else
				msg = "Sygnały nie mogą być dodane. Odpowiednie wartości nie są równe.";
		}
		return msg;
	}

	@Action
	public void syg_AkcjaOp_Odejmij() {
		// pobranie drugiego sygnału z pliku
		Akcja_otworz otwarcie = new Akcja_otworz(this.listaSygnalow[1],
				syg_MenuGlowne_Operacje_Dodaj);
		listaSygnalow[1] = otwarcie.getSygnal();
		String msgPoprawnosc = this.sprawdzPoprawnoscOperacji(listaSygnalow[0],
				listaSygnalow[1]);

		if (msgPoprawnosc == "ok") {
			int iloscProbek = 0;
			// pobranie mniejszej ilości próbek
			if (listaSygnalow[1].punktyY.size() > listaSygnalow[0].punktyY
					.size())
				iloscProbek = listaSygnalow[0].punktyY.size();
			else
				iloscProbek = listaSygnalow[1].punktyY.size();

			if (listaSygnalow[2].getPunktyY() != null)
				listaSygnalow[2].wyczyscPunkty();

			// wpisanie wartości parametrów do nowego sygnału
			listaSygnalow[2].setRodzaj(rodzaj_sygnalu.DYSKRETNY);
			listaSygnalow[2].pobierzParametryUzytkownika(
					listaSygnalow[0].gettyp(),
					(listaSygnalow[0].getA() - listaSygnalow[1].getA()),
					listaSygnalow[0].gett1(),
					listaSygnalow[0].getts(),
					listaSygnalow[0].getd(), // d
					listaSygnalow[0].getT(), listaSygnalow[0].getKw(),
					listaSygnalow[0].getskok());
			listaSygnalow[2].setkroczek(listaSygnalow[0].getkroczek());

			// operacja
			for (int i = 0; i < iloscProbek; i++) {
				listaSygnalow[2].setPunktyY(listaSygnalow[0].getPunktzindexu(i)
						- listaSygnalow[1].getPunktzindexu(i));
			}

			// zablokowanie nieedytowalnych wartości i odblokowanie innych
			syg_panelParametry.cb_wybor123.setEnabled(true);
			this.syg_MenuGlowne_Operacje.setEnabled(false);
			syg_panelParametry.zablokujPola(true);

		}
		if (msgPoprawnosc != "ok") {
			JOptionPane.showMessageDialog(null, msgPoprawnosc,
					"Błąd próby operacji na sygnałach",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	@Action
	public void syg_AkcjaOp_Pomnoz() {
		// pobranie drugiego sygnału z pliku
		Akcja_otworz otwarcie = new Akcja_otworz(this.listaSygnalow[1],
				syg_MenuGlowne_Operacje_Dodaj);
		listaSygnalow[1] = otwarcie.getSygnal();
		String msgPoprawnosc = this.sprawdzPoprawnoscOperacji(listaSygnalow[0],
				listaSygnalow[1]);

		if (msgPoprawnosc == "ok") {
			int iloscProbek = 0;
			// pobranie mniejszej ilości próbek
			if (listaSygnalow[1].punktyY.size() > listaSygnalow[0].punktyY
					.size())
				iloscProbek = listaSygnalow[0].punktyY.size();
			else
				iloscProbek = listaSygnalow[1].punktyY.size();

			if (listaSygnalow[2].getPunktyY() != null)
				listaSygnalow[2].wyczyscPunkty();

			// wpisanie wartości parametrów do nowego sygnału
			listaSygnalow[2].setRodzaj(rodzaj_sygnalu.DYSKRETNY);
			listaSygnalow[2].pobierzParametryUzytkownika(
					listaSygnalow[0].gettyp(),
					(listaSygnalow[0].getA() * listaSygnalow[1].getA()),
					listaSygnalow[0].gett1(),
					listaSygnalow[0].getts(),
					listaSygnalow[0].getd(), // d
					listaSygnalow[0].getT(), listaSygnalow[0].getKw(),
					listaSygnalow[0].getskok());
			listaSygnalow[2].setkroczek(listaSygnalow[0].getkroczek());

			// operacja
			for (int i = 0; i < iloscProbek; i++) {
				listaSygnalow[2].setPunktyY(listaSygnalow[0].getPunktzindexu(i)
						* listaSygnalow[1].getPunktzindexu(i));
			}

			// zablokowanie nieedytowalnych wartości i odblokowanie innych
			syg_panelParametry.cb_wybor123.setEnabled(true);
			this.syg_MenuGlowne_Operacje.setEnabled(false);
			syg_panelParametry.zablokujPola(true);

		}
		if (msgPoprawnosc != "ok") {
			JOptionPane.showMessageDialog(null, msgPoprawnosc,
					"Błąd próby operacji na sygnałach",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	@Action
	public void syg_AkcjaOp_Podziel() {
		// pobranie drugiego sygnału z pliku
		Akcja_otworz otwarcie = new Akcja_otworz(this.listaSygnalow[1],
				syg_MenuGlowne_Operacje_Dodaj);
		listaSygnalow[1] = otwarcie.getSygnal();
		String msgPoprawnosc = this.sprawdzPoprawnoscOperacji(listaSygnalow[0],
				listaSygnalow[1]);

		if (msgPoprawnosc == "ok") {
			int iloscProbek = 0;
			// pobranie mniejszej ilości próbek
			if (listaSygnalow[1].punktyY.size() > listaSygnalow[0].punktyY
					.size())
				iloscProbek = listaSygnalow[0].punktyY.size();
			else
				iloscProbek = listaSygnalow[1].punktyY.size();

			if (listaSygnalow[2].getPunktyY() != null)
				listaSygnalow[2].wyczyscPunkty();

			// wpisanie wartości parametrów do nowego sygnału
			listaSygnalow[2].setRodzaj(rodzaj_sygnalu.DYSKRETNY);
			listaSygnalow[2].pobierzParametryUzytkownika(
					listaSygnalow[0].gettyp(),
					(listaSygnalow[0].getA() / listaSygnalow[1].getA()),
					listaSygnalow[0].gett1(),
					listaSygnalow[0].getts(),
					listaSygnalow[0].getd(), // d
					listaSygnalow[0].getT(), listaSygnalow[0].getKw(),
					listaSygnalow[0].getskok());
			listaSygnalow[2].setkroczek(listaSygnalow[0].getkroczek());

			// operacja
			for (int i = 0; i < iloscProbek; i++) {
				listaSygnalow[2].setPunktyY(listaSygnalow[0].getPunktzindexu(i)
								/ ((listaSygnalow[1].getPunktzindexu(i) != 0) ? listaSygnalow[1]
										.getPunktzindexu(i) : 0.0001));
			}

			// zablokowanie nieedytowalnych wartości i odblokowanie innych
			syg_panelParametry.cb_wybor123.setEnabled(true);
			this.syg_MenuGlowne_Operacje.setEnabled(false);
			syg_panelParametry.zablokujPola(true);

		}
		if (msgPoprawnosc != "ok") {
			JOptionPane.showMessageDialog(null, msgPoprawnosc,
					"Błąd próby operacji na sygnałach",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	private ActionMap getAppActionMap() {
		return Application.getInstance().getContext().getActionMap(this);
	}

	@Override
	protected void startup() {
		{
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
						syg_MenuGlowne_Sygnal_Nowy.setAction(getAppActionMap()
								.get("syg_AkcjaNowy"));
					}
					{
						syg_MenuGlowne_Sygnal_Otworz = new JMenuItem();
						syg_MenuGlowne_Sygnal.add(syg_MenuGlowne_Sygnal_Otworz);
						syg_MenuGlowne_Sygnal_Otworz
								.setAction(getAppActionMap().get(
										"syg_AkcjaOtworz"));
					}
					{
						syg_MenuGlowne_Sygnal_Zapisz = new JMenuItem();
						syg_MenuGlowne_Sygnal.add(syg_MenuGlowne_Sygnal_Zapisz);
						syg_MenuGlowne_Sygnal_Zapisz
								.setAction(getAppActionMap().get(
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
						syg_MenuGlowne_Operacje
								.add(syg_MenuGlowne_Operacje_Dodaj);
						syg_MenuGlowne_Operacje_Dodaj
								.setAction(getAppActionMap().get(
										"syg_AkcjaOp_Dodaj"));
					}
					{
						syg_MenuGlowne_Operacje_Odejmij = new JMenuItem();
						syg_MenuGlowne_Operacje
								.add(syg_MenuGlowne_Operacje_Odejmij);
						syg_MenuGlowne_Operacje_Odejmij
								.setAction(getAppActionMap().get(
										"syg_AkcjaOp_Odejmij"));
					}
					{
						syg_MenuGlowne_Operacje_Pomnoz = new JMenuItem();
						syg_MenuGlowne_Operacje
								.add(syg_MenuGlowne_Operacje_Pomnoz);
						syg_MenuGlowne_Operacje_Pomnoz
								.setAction(getAppActionMap().get(
										"syg_AkcjaOp_Pomnoz"));
					}
					{
						syg_MenuGlowne_Operacje_Podziel = new JMenuItem();
						syg_MenuGlowne_Operacje
								.add(syg_MenuGlowne_Operacje_Podziel);
						syg_MenuGlowne_Operacje_Podziel
								.setAction(getAppActionMap().get(
										"syg_AkcjaOp_Podziel"));
					}
				}
				syg_MenuGlowne_Rysunek = new JMenu();
				syg_MenuGlowne.add(syg_MenuGlowne_Rysunek);
				syg_MenuGlowne_Rysunek.setName("syg_MenuGlowne_Rysunek");
				{
					{
						syg_MenuGlowne_Rysunek_Wykres = new JMenuItem();
						syg_MenuGlowne_Rysunek
								.add(syg_MenuGlowne_Rysunek_Wykres);
						syg_MenuGlowne_Rysunek_Wykres
								.setAction(getAppActionMap().get(
										"syg_AkcjaWykres"));
					}
					{
						syg_MenuGlowne_Rysunek_Histogram = new JMenuItem();
						syg_MenuGlowne_Rysunek
								.add(syg_MenuGlowne_Rysunek_Histogram);
						syg_MenuGlowne_Rysunek_Histogram
								.setAction(getAppActionMap().get(
										"syg_AkcjaHistogram"));
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
				{
					jSplitPane1 = new JSplitPane();
					syg_panelGlowny.add(jSplitPane1);

					jSplitPane1.setPreferredSize(rozmiarOkna);
					{
						syg_panelRysunek_Wykres = new PanelRysunek_Wykres();
						syg_panelRysunek_Histogram = new PanelRysunek_Histogram();
						jSplitPane1.add(syg_panelRysunek_Wykres,
								JSplitPane.RIGHT);
					}
					{
						syg_panelParametry = new PanelParametry(listaSygnalow,
								syg_panelRysunek_Wykres,
								syg_panelRysunek_Histogram, jSplitPane1,
								this.rysujWykres);
						jSplitPane1.add(syg_panelParametry, JSplitPane.LEFT);
						// syg_panelParametry.setPreferredSize(new
						// java.awt.Dimension(220, 500));
					}
				}
				jSplitPane1.setAlignmentX(2);
				syg_panelGlowny.add(jSplitPane1);
			}
		}

		show(syg_panelGlowny);
	}

	public static void main(String[] args) {
		launch(AplikacjaGlowna.class, args);

	}

}
