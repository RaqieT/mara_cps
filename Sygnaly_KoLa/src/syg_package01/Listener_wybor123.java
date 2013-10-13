package syg_package01;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import syg_package01.Sygnal.rodzaj_sygnalu;

public class Listener_wybor123 implements ItemListener {

	private Object source;
	private Sygnal[] listaSygnalow;

	public Listener_wybor123(Sygnal[] _s, Object _source) {
		// super(_source);
		// TODO Auto-generated constructor stub
		this.source = _source;
		this.listaSygnalow = _s;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		int id = 0;
		@SuppressWarnings("unchecked")
		JComboBox<String> cb = (JComboBox<String>) e.getSource();

		if (e.getStateChange() == ItemEvent.SELECTED) {
			id = cb.getSelectedIndex();
			if (listaSygnalow[id].getrodzaj() != rodzaj_sygnalu.CIAGLY) {
				((PanelParametry) source).zablokujPola(true);
				// ((PanelParametry) source).wyczyscPola();
			} else {
				((PanelParametry) source).zablokujPola(false);
			}

		}

		try {
			if (listaSygnalow[id] != null)
				// if (listaSygnalow[id].getrodzaj() == rodzaj_sygnalu.CIAGLY) {
				((PanelParametry) source).odczytajParametryZSygnalu(listaSygnalow[id], id);
			// }

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "niepoprawne wartości sygnału\n" + e1.getMessage(),
					"Błąd próby odczytu sygnału", JOptionPane.ERROR_MESSAGE);
		}
	}

}
