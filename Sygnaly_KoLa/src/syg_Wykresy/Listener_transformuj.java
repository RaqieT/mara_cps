package syg_Wykresy;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import syg_package01.PanelKonwersja;
import syg_package01.PanelTransformacja;
import syg_package01.Sygnal;

public class Listener_transformuj implements ActionListener {
	
	private Sygnal sygnal;
	private PanelTransformacja transformacja;
	private JSplitPane splitPane;
	private PanelRysunek_Trans rysunek;
	
	public Listener_transformuj(Sygnal sygnal, PanelTransformacja _transformacja, JSplitPane _splitPane) {
		this.setSygnal(sygnal);
		this.setTransformacja(_transformacja);
		//this.rysunek = _rysunek;
		this.setSplitPane(_splitPane);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			String msgPoprawnosc = this.transformacja.sprawdzPoprawnosc();
			
				if (msgPoprawnosc == "ok") {
					
					this.setSygnal(this.transformacja.getSygnal());
					this.wyswietlWykres(transformacja.getCb_tryb(), transformacja.getCb_transformacja());

				} else {
					JOptionPane.showMessageDialog(null, msgPoprawnosc,
							"Nie można wyświetlić wykresu", JOptionPane.ERROR_MESSAGE);
				}
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage(), "Nie można wyświetlić wykresu",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	private void wyswietlWykres(int _widok, int _przeksztalcenie) {
		this.rysunek = new PanelRysunek_Trans(_widok, _przeksztalcenie, this.getSygnal());
		
		//this.rysunek.setSygnalPodstawowy(this.sygnal);
		//this.rysunek.setWidok(_widok);
		//this.rysunek.setNrPrzeksztalcenia(_przeksztalcenie);
		//this.rysunek.rysuj();
	}

	public void setSygnal(Sygnal sygnal) {
		this.sygnal = sygnal;
	}

	public Sygnal getSygnal() {
		return sygnal;
	}

	public void setTransformacja(PanelTransformacja transformacja) {
		this.transformacja = transformacja;
	}

	public PanelTransformacja getTransformacja() {
		return transformacja;
	}

	public void setSplitPane(JSplitPane splitPane) {
		this.splitPane = splitPane;
	}

	public JSplitPane getSplitPane() {
		return splitPane;
	}

}
