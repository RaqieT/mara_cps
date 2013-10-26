package syg_Wykresy;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import org.jdesktop.application.Application;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYStepRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

import syg_package01.Sygnal;
import syg_package01.Sygnal.rodzaj_sygnalu;

/**
 */
@SuppressWarnings("serial")
public class PanelRysunek_Histogram extends javax.swing.JPanel {
	private Sygnal sygnalWyswietlany = new Sygnal();
	private boolean rysuj;
	/**
	 * Dla 0 histogram generuje się nie dla ilości przedziałów, a płynnie.
	 */
	private int iloscPrzedzialowHistogramu = 5;

	public PanelRysunek_Histogram(Sygnal _sygnal, int _ilosc) {
		super();
		this.rysuj = true;
		this.sygnalWyswietlany = _sygnal;
		this.iloscPrzedzialowHistogramu = _ilosc;
		initGUI();
	}

	public PanelRysunek_Histogram() {
		this.rysuj = false;
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout(1, 1);
			thisLayout.setHgap(5);
			thisLayout.setVgap(5);
			thisLayout.setColumns(1);
			this.setLayout(thisLayout);
			setPreferredSize(new Dimension(400, 300));

			if (this.rysuj) {
				double punkt = this.sygnalWyswietlany.gett1();
				HistogramDataset histogramDataset = new HistogramDataset();
				histogramDataset.setType(HistogramType.FREQUENCY);
				ArrayList<Double> punkty = new ArrayList<Double>();

				double ta = this.sygnalWyswietlany.gett1();

				if (this.sygnalWyswietlany.getrodzaj() == rodzaj_sygnalu.CIAGLY
						|| sygnalWyswietlany.getPunktyY_wykres().size() <= 0) {
					punkt = this.sygnalWyswietlany.gett1();
					while (ta <= this.sygnalWyswietlany.gett1() + this.sygnalWyswietlany.getd()) {
						punkt = this.sygnalWyswietlany.wykres_punkty(punkt, ta);
						this.sygnalWyswietlany.setPunktyY_wykres(punkt);
						punkty.add(punkt);
						if (this.sygnalWyswietlany.gettyp() < 10)
							ta = ta + this.sygnalWyswietlany.getkroczek();
						else
							ta = ta + this.sygnalWyswietlany.getkroczek() * 10;
					}
				} else if (this.sygnalWyswietlany.getrodzaj() == rodzaj_sygnalu.DYSKRETNY
						&& sygnalWyswietlany.getPunktyY_wykres().size() > 0) {
					punkt = sygnalWyswietlany.getPunktzindexu(0);
					int iloscProbek = (int) (this.sygnalWyswietlany.getPunktyY_wykres().size());
					for (int i = 1; i < iloscProbek; i++) {
						punkt = this.sygnalWyswietlany.getPunktzindexu(i);
						punkty.add(punkt);
						// if (this.sygnalWyswietlany.gettyp() < 10)
						// ta = ta + this.sygnalWyswietlany.getkroczek();
						// else
						ta = ta + this.sygnalWyswietlany.getkroczek();
					}
				}

				JFreeChart chartHist;
				if (this.iloscPrzedzialowHistogramu > 0) {
					// JOptionPane.showMessageDialog(null,
					// "Rysowanie histogramu...",
					// "Listener_wyswietlHistogram",
					// JOptionPane.INFORMATION_MESSAGE);
					double[] tablicaHistogramu = new double[punkty.size()];
					for (int licznik = 0; licznik < punkty.size(); ++licznik)
						tablicaHistogramu[licznik] = punkty.get(licznik);

					histogramDataset.addSeries("Histogram (" + iloscPrzedzialowHistogramu + ")",
							tablicaHistogramu, this.iloscPrzedzialowHistogramu);

					chartHist = ChartFactory.createHistogram("Histogram", null, null,
							histogramDataset, PlotOrientation.VERTICAL, true, false, false);

					final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
					renderer.setSeriesLinesVisible(0, false);

					ChartPanel chartpanel1 = new ChartPanel(chartHist);
					chartpanel1.setDomainZoomable(true);

					this.add(chartpanel1);
				}
			}
			Application.getInstance().getContext().getResourceMap(getClass())
					.injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setIloscPrzedzialowHistogramu(int _iloscPrzedzialowHistogramu) {
		this.iloscPrzedzialowHistogramu = _iloscPrzedzialowHistogramu;
	}

}
