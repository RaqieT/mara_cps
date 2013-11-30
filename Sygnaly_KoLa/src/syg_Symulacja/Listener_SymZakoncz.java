package syg_Symulacja;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Listener_SymZakoncz implements ActionListener {

	AplikacjaSymulacji zrodlo;
	JButton wywolywacz;
	
	public Listener_SymZakoncz(AplikacjaSymulacji _zrodlo, JButton _wywolywacz) {
		this.zrodlo = _zrodlo;
		this.wywolywacz = _wywolywacz;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.zrodlo.radar.zakoncz();
	}

}
