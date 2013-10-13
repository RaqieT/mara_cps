package syg_package01;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;

public class Listener_wyborTransformacji implements ItemListener {
	JLabel lbl_opisMiejsce;
	String[] opisTresc;
	private PanelTransformacja transformacja;

	public Listener_wyborTransformacji(JLabel _lbl, String[] _lblOpis, PanelTransformacja _transformacja) {
		this.lbl_opisMiejsce = _lbl;
		this.opisTresc = _lblOpis;
		this.setTransformacja(_transformacja);
	}

	@Override
	public void itemStateChanged(ItemEvent evt) {
		@SuppressWarnings("unchecked")
		JComboBox<String> cb = (JComboBox<String>) evt.getSource();

		// Get the affected item
		// Object item = evt.getItem();

		if (evt.getStateChange() == ItemEvent.SELECTED) {
				lbl_opisMiejsce.setText(opisTresc[cb.getSelectedIndex()]);
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