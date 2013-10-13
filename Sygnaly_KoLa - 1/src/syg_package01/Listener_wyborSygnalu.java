package syg_package01;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;

public class Listener_wyborSygnalu implements ItemListener {
	JLabel opisCb;
	private PanelParametry parametry;

	public Listener_wyborSygnalu(JLabel _lbl, PanelParametry _parametry) {
		this.opisCb = _lbl;
		this.parametry = _parametry;
	}

	@Override
	public void itemStateChanged(ItemEvent evt) {
		@SuppressWarnings("unchecked")
		JComboBox<String> cb = (JComboBox<String>) evt.getSource();

		// Get the affected item
		// Object item = evt.getItem();

		if (evt.getStateChange() == ItemEvent.SELECTED) {
			opisCb.setText("S" + cb.getSelectedIndex());
			this.parametry
					.setTxt_WspolczynnikWypenienia(cb.getSelectedIndex() >= 6
							&& cb.getSelectedIndex() <= 8);
			this.parametry
			.setTxt_Skok(cb.getSelectedIndex() >= 9
					&& cb.getSelectedIndex() <= 11);
		} else if (evt.getStateChange() == ItemEvent.DESELECTED) {
			// Item is no longer selected
		}
	}

	public void actionPerformed() {

	}
}
