package syg_package01;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class Listener_obliczWartosci implements ActionListener {

	private Sygnal sygnal;
	private PanelParametry parametry;
	
	public Listener_obliczWartosci (Sygnal _sygnal, PanelParametry _parametry)
	{
		this.setSygnal(_sygnal);
		this.setParametry (_parametry);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		try {
			String msgPoprawnosc = this.parametry.sprawdzPoprawnosc();
			if (msgPoprawnosc == "ok")
			{
				this.sygnal = this.parametry.zapiszParametryDoSygnalu();
				this.parametry.obliczenieWarosci(sygnal);
				this.parametry.btn_oblicz.setEnabled(false);
			}
			else{
				JOptionPane
				.showMessageDialog(
						null,
						msgPoprawnosc,
						"Nie można obliczyć wartości",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			JOptionPane
			.showMessageDialog(
					null,
					e.getMessage(),
					"Nie można wyświetlić wykresu",
					JOptionPane.ERROR_MESSAGE);
		}
		
	}

	public void setParametry(PanelParametry parametry) {
		this.parametry = parametry;
	}

	public PanelParametry getParametry() {
		return parametry;
	}

	public void setSygnal(Sygnal sygnal) {
		this.sygnal = sygnal;
	}

	public Sygnal getSygnal() {
		return sygnal;
	}

}
