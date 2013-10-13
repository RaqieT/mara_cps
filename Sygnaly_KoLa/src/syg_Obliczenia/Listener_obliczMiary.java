package syg_Obliczenia;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JComboBox;

import syg_package01.PanelKonwersja;

public class Listener_obliczMiary implements ItemListener {

	private Object source;

	// private PanelKwantyzacja kwantyzacja;

	public Listener_obliczMiary(Object _source) {
		this.source = _source;
		// this.setKwantyzacja(_kwantyzacja);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		int id = 0;
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		JComboBox<String> cb = (JComboBox<String>) e.getSource();
		if (e.getStateChange() == ItemEvent.SELECTED) {
			int ktorySygnlalKoncowy = ((PanelKonwersja) source).getKrokZmian();
			List<Double> doPorownania = null;
			switch (ktorySygnlalKoncowy) {
			case 1:
			case 0: {
				if (((PanelKonwersja) source).getSygnalKonwertowany().getPunktyY_probkowanie()
						.size() > 0)
					doPorownania = ((PanelKonwersja) source).getSygnalKonwertowany()
							.getPunktyY_probkowanie();
				if (((PanelKonwersja) source).getSygnalKonwertowany().getPunktyY_kwantyzacja()
						.size() > 0)
					doPorownania = ((PanelKonwersja) source).getSygnalKonwertowany()
							.getPunktyY_kwantyzacja();
				break;
			}
			case 2:
			case 3:
				doPorownania = ((PanelKonwersja) source).getSygnalKonwertowany()
						.getPunktyY_kwantyzacja();
				break;
			default:
				break;
			}

			id = cb.getSelectedIndex();
			((PanelKonwersja) source).obliczenieMiary(id, doPorownania);
		}
	}
}
