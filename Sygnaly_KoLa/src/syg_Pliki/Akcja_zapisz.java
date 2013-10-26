package syg_Pliki;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import syg_package01.Sygnal;
import syg_package01.Sygnal.RodzajSygnalu;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Akcja_zapisz {

	private Sygnal sygnal;
	private JMenuItem source;

	public Akcja_zapisz(Sygnal _sygnal, JMenuItem _source) {
		this.sygnal = _sygnal;
		this.source = _source;
		zapisDoPliku();
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
		_xstream.aliasField("punkty", Sygnal.class, "punktyY_wykres");
		_xstream.aliasField("kroczek", Sygnal.class, "kroczek");
		_xstream.aliasField("krok", Sygnal.class, "krok");

		if (this.sygnal.getRodzaj() == RodzajSygnalu.CIAGLY) {
			_xstream.omitField(Sygnal.class, "ts");
			_xstream.omitField(Sygnal.class, "krok");
			_xstream.omitField(Sygnal.class, "kroczek");
			_xstream.omitField(Sygnal.class, "punktyY_wykres");
			_xstream.omitField(Sygnal.class, "punktyY_probkowanie");
			_xstream.omitField(Sygnal.class, "punktyY_kwantyzacja");
			_xstream.omitField(Sygnal.class, "punktyY_zespolone");
			_xstream.omitField(Sygnal.class, "poziom_kwantyzacji_krok");
			_xstream.omitField(Sygnal.class, "poziomy_kwantyzacji");
			if (this.sygnal.gettyp() != 9 || this.sygnal.gettyp() != 10) {
				_xstream.omitField(Sygnal.class, "skok");
			}
		}

		if (this.sygnal.getRodzaj() == RodzajSygnalu.DYSKRETNY) {
			_xstream.omitField(Sygnal.class, "typ");
			_xstream.omitField(Sygnal.class, "A");
			_xstream.omitField(Sygnal.class, "skok");
			_xstream.omitField(Sygnal.class, "kroczek");
			_xstream.omitField(Sygnal.class, "kw");
			_xstream.omitField(Sygnal.class, "ts");
			_xstream.omitField(Sygnal.class, "T");
			_xstream.omitField(Sygnal.class, "punktyY_probkowanie");
			_xstream.omitField(Sygnal.class, "punktyY_kwantyzacja");
			_xstream.omitField(Sygnal.class, "poziom_kwantyzacji_krok");
			_xstream.omitField(Sygnal.class, "poziomy_kwantyzacji");
			_xstream.omitField(Sygnal.class, "punktyY_zespolone");
			if (this.sygnal.gettyp() != 9 || this.sygnal.gettyp() != 10) {
				_xstream.omitField(Sygnal.class, "skok");
			}
			
		}
		
		if (this.sygnal.getRodzaj() == RodzajSygnalu.ZESPOLONY)
		{
			_xstream.omitField(Sygnal.class, "typ");
			_xstream.omitField(Sygnal.class, "A");
			_xstream.omitField(Sygnal.class, "skok");
			_xstream.omitField(Sygnal.class, "kroczek");
			_xstream.omitField(Sygnal.class, "kw");
			_xstream.omitField(Sygnal.class, "ts");
			_xstream.omitField(Sygnal.class, "T");
			_xstream.omitField(Sygnal.class, "punktyY_probkowanie");
			_xstream.omitField(Sygnal.class, "punktyY_kwantyzacja");
			_xstream.omitField(Sygnal.class, "poziom_kwantyzacji_krok");
			_xstream.omitField(Sygnal.class, "poziomy_kwantyzacji");
			_xstream.omitField(Sygnal.class, "punktyY_wykres");
			if (this.sygnal.gettyp() != 9 || this.sygnal.gettyp() != 10) {
				_xstream.omitField(Sygnal.class, "skok");
			}
		}
		
	}

	private void zapisDoPliku() {
		JFileChooser wyborPlikuDoZapisu = new JFileChooser();

		wyborPlikuDoZapisu.setFileSelectionMode(JFileChooser.FILES_ONLY);
		wyborPlikuDoZapisu.addChoosableFileFilter(new FileFilter() {
			public String getDescription() {
				return ".txt";
			}

			@Override
			public boolean accept(File arg0) {
				return arg0.getName().endsWith(".txt");
			}

		});
		wyborPlikuDoZapisu.addChoosableFileFilter(new FileFilter() {
			public String getDescription() {
				return ".bin";
			}

			@Override
			public boolean accept(File arg0) {
				return arg0.getName().endsWith(".bin");
			}

		});

		if (wyborPlikuDoZapisu.showSaveDialog(source/* zapiszDoXmlButton */) == JFileChooser.APPROVE_OPTION) {
			File plikDoZapisu = wyborPlikuDoZapisu.getSelectedFile();

			try {
				// zapis pliku w wybranym formacie jeśli nie wpisano
				// rozszerzenia
				if (!plikDoZapisu.getName().endsWith(".txt")
						&& !plikDoZapisu.getName().endsWith(".bin")) {
					if (wyborPlikuDoZapisu.getFileFilter().getDescription() == ".txt"
							|| wyborPlikuDoZapisu.getFileFilter().getDescription() == ".bin") {
						plikDoZapisu = new File(plikDoZapisu.getParentFile(),
								plikDoZapisu.getName()
										+ wyborPlikuDoZapisu.getFileFilter().getDescription());
					} else {
						plikDoZapisu = new File(plikDoZapisu.getParentFile(),
								plikDoZapisu.getName() + ".txt");
					}

				}

				if (plikDoZapisu.exists()) {
					if (JOptionPane.showConfirmDialog(null, "Plik \"" + plikDoZapisu.getName()
							+ "\" już istnieje.\nCzy zapisać ponownie?", "Plik istnieje!",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION)
						return;
				}

				XStream xstream = new XStream(new DomDriver());

				ustawAliasy(xstream);

				String daneXmlPoSerializacji; // = xstream.toXML("");
				daneXmlPoSerializacji = xstream.toXML(this.sygnal);
				// daneXmlPoSerializacji +=
				// xstream.toXML((String)"rzeczywiste");
				// daneXmlPoSerializacji += xstream.toXML((Integer)4);

				Writer strumienZapisuDanych = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(plikDoZapisu), "UTF-8"));
				strumienZapisuDanych.write(daneXmlPoSerializacji);
				strumienZapisuDanych.close();

				JOptionPane.showMessageDialog(null, "Operacja zapisu zakończona powodzeniem.",
						"Zapis powiódł się", JOptionPane.INFORMATION_MESSAGE);
				// }
				// catch (XStreamException _err)
				// {
				// System.err.println ("Błąd przy serializacji:\n" +
				// _err.getMessage
				// ());
				// JOptionPane.showMessageDialog (null,
				// "Błąd przy serializacji pliku.\nZgłoś bład twórcy programu robiąc zdjęcie tego okienka lub kopiując poniższą treść:\n"
				// + _err.getMessage (), "Błąd zapisu pliku - serializacja xml",
				// JOptionPane.ERROR_MESSAGE);
			} catch (FileNotFoundException _err) {
				System.err.println("Plik do zapisu nie został znaleziony:\n" + _err.getMessage());
				JOptionPane
						.showMessageDialog(
								null,
								"Plik do zapisu nie został znaleziony.\n"
										+ _err.getMessage(), "Błąd zapisu pliku - brak pliku",
								JOptionPane.ERROR_MESSAGE);
			} catch (IOException _err) {
				System.err.println("Próba zapisu do pliku nie powiodła się:\n" + _err.getMessage());
				JOptionPane
						.showMessageDialog(
								null,
								"Próba zapisu do pliku nie powiodła się.\n"
										+ _err.getMessage(),
								"Błąd zapisu pliku - problem ze strumieniem zapisu",
								JOptionPane.ERROR_MESSAGE);
			} catch (Exception _err) {
				System.err.println("Nieznany błąd.");
			}
		}
	}
}
