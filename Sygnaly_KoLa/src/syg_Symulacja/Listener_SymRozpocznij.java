package syg_Symulacja;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Listener_SymRozpocznij implements ActionListener {
	
	AplikacjaSymulacji zrodlo;
	JButton wywolywacz;
	
	public Listener_SymRozpocznij(AplikacjaSymulacji _zrodlo, JButton _wywolywacz) {
		this.zrodlo = _zrodlo;
		this.wywolywacz = _wywolywacz;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//pobranie wpisanych wartosci i utworzenie obiektów
		this.zrodlo.radar = new UrzadzenieSprawdzajace();
	}

}
