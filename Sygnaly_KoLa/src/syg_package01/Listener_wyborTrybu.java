package syg_package01;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;

public class Listener_wyborTrybu implements ItemListener {
	JLabel opisCb;
	private PanelTransformacja transformacja;

	public Listener_wyborTrybu(JLabel _lbl, PanelTransformacja _transformacja) {
		this.opisCb = _lbl;
		this.setTransformacja(_transformacja);
		opisCb.setText("");
	}

	@Override
	public void itemStateChanged(ItemEvent evt) {
		@SuppressWarnings("unchecked")
		JComboBox<String> cb = (JComboBox<String>) evt.getSource();

		// Get the affected item
		// Object item = evt.getItem();

		if (evt.getStateChange() == ItemEvent.SELECTED) {
			if (cb.getSelectedIndex() > 0)
				opisCb.setText("W" + cb.getSelectedIndex());
			else
				opisCb.setText("");
		} else if (evt.getStateChange() == ItemEvent.DESELECTED) {
			
		}
	}

	public void actionPerformed() {

	}

	public void setTransformacja(PanelTransformacja transformacja) {
		this.transformacja = transformacja;
	}

	public PanelTransformacja getTransformacja() {
		return transformacja;
	}
}