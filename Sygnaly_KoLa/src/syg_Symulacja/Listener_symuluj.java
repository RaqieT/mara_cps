package syg_Symulacja;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import syg_package01.PanelFiltracja;

public class Listener_symuluj implements ActionListener {

	private Object source;

	public Listener_symuluj(Object _source) {
		// super();
		this.source = _source;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		if (this.source.getClass() == PanelFiltracja.class) {
			AplikacjaSymulacji appSymulacja = new AplikacjaSymulacji((PanelFiltracja)this.source);
			appSymulacja.startup();
			//appSymulacja.setSygnalNadawany(((PanelFiltracja)this.source).getSygnalFiltrowany());
		}
	}

}
