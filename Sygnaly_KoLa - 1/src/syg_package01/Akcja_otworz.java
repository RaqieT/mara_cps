package syg_package01;

import java.io.File;
import java.io.FileInputStream;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import syg_package01.Sygnal.rodzaj_sygnalu;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Akcja_otworz {

	private Sygnal sygnal;
	private JMenuItem source;
	public boolean odczytPrawidlowy;

	public Akcja_otworz(Sygnal _sygnal, JMenuItem _source) {
		this.sygnal = _sygnal;
		this.source = _source;
		odczytajZPliku();
	}

	public Sygnal getSygnal() {
		return sygnal;
	}

	public void setSygnal(Sygnal _sygnal) {
		this.sygnal = _sygnal;
	}

	private void ustawAliasy(XStream _xstream) {
		_xstream.alias("Sygnał", Sygnal.class);

		// _xstream.aliasField("Parametry", Sygnal.class, "sygnal_parametry");

		_xstream.aliasField("typSygnału", Sygnal.class, "typ");
		_xstream.aliasField("amplituda", Sygnal.class, "A");
		_xstream.aliasField("czasPoczątkowy", Sygnal.class, "t1");
		_xstream.aliasField("czasTrwania", Sygnal.class, "d");
		_xstream.aliasField("okresPodstawowy", Sygnal.class, "T");
		_xstream.aliasField("współczynnikWypełnienia", Sygnal.class, "kw");
		_xstream.aliasField("punkty", Sygnal.class, "punktyY");
		_xstream.aliasField("kroczek", Sygnal.class, "kroczek");
		
		if (this.sygnal.getrodzaj() == rodzaj_sygnalu.CIAGLY) {
			_xstream.omitField(Sygnal.class, "ts");
			_xstream.omitField(Sygnal.class, "krok");
			_xstream.omitField(Sygnal.class, "kroczek");
			_xstream.omitField(Sygnal.class, "punktyY");
		}

		if (this.sygnal.getrodzaj() == rodzaj_sygnalu.DYSKRETNY) {
			//_xstream.omitField(Sygnal.class, "punktyY");
			//_xstream.omitField(Sygnal.class, "kroczek");
			//_xstream.omitField(Sygnal.class, "A");
			_xstream.omitField(Sygnal.class, "kw");
			_xstream.omitField(Sygnal.class, "ts");
		}

		// _xstream.alias("rodzajWartości", String.class);
		// _xstream.alias("ilośćPróbek", int.class);
		// _xstream.alias("próbka", Double.class);
		// _xstream.aliasAttribute("próbka", "nr");

	}

	private void odczytajZPliku() {
		odczytPrawidlowy = false;
		JFileChooser wyborPlikuDoOdczytu = new JFileChooser();
		wyborPlikuDoOdczytu.setFileSelectionMode(JFileChooser.FILES_ONLY);
		wyborPlikuDoOdczytu.addChoosableFileFilter(new FileFilter() {
			public String getDescription() {
				return ".bin";
			}

			public boolean accept(File arg0) {
				return arg0.getName().endsWith(".bin");
			}
		});
		wyborPlikuDoOdczytu.addChoosableFileFilter(new FileFilter() {
			public String getDescription() {
				return ".txt";
			}

			public boolean accept(File arg0) {
				return arg0.getName().endsWith(".txt");
			}
		});

		if (wyborPlikuDoOdczytu.showOpenDialog(source) == JFileChooser.APPROVE_OPTION) {
			File plikDoOdczytu = wyborPlikuDoOdczytu.getSelectedFile();

			try {
				if (!plikDoOdczytu.getName().endsWith(".bin")
						&& !plikDoOdczytu.getName().endsWith(".txt")) {
					JOptionPane
							.showMessageDialog(
									null,
									"Plik \""
											+ plikDoOdczytu.getName()
											+ "\" nie jest plikiem .bin ani .txt, wybierz plik z odpowiednim rozszerzeniem.",
									"Zły plik!", JOptionPane.WARNING_MESSAGE);
					return;
				}

				XStream xstream = new XStream(new DomDriver());

				ustawAliasy(xstream);

				Sygnal syg = (Sygnal) xstream.fromXML(new FileInputStream(
						plikDoOdczytu));
				// Sygnal syg = (Sygnal) xstream.fromXML(plikDoOdczytu);

				przygotujObiektPoDeserializacji(syg);

				JOptionPane.showMessageDialog(null,
						"Operacja odczytu zakończona powodzeniem.",
						"Odczyt powiódł się", JOptionPane.INFORMATION_MESSAGE);
				odczytPrawidlowy = true;
			}

			// catch (XStreamException _err)
			// {
			// System.err.println ("Błąd przy deserializacji:\n" +
			// _err.getMessage ());
			// JOptionPane.showMessageDialog (null,
			// "Błąd przy deserializacji pliku.\n"
			// + _err.getMessage (), "Błąd odczytu pliku - serializacja xml",
			// JOptionPane.ERROR_MESSAGE);
			// }
			catch (Exception _err) {
				System.err.println("Nieznany błąd.");
				JOptionPane.showMessageDialog(null,
						"Nieznany błąd.\nOpis błędu" + _err.getMessage(),
						"Błąd.", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void przygotujObiektPoDeserializacji(Sygnal _syg) {
		sygnal = _syg;
	}
}
