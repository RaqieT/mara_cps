package syg_package01;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import org.jdesktop.application.Application;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import syg_package01.Sygnal.rodzaj_sygnalu;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
@SuppressWarnings("serial")
public class PanelRysunek_Histogram extends javax.swing.JPanel {
	private Sygnal sygnalWyswietlany = new Sygnal();
	private boolean wykres;
	/**
	 * Dla 0 histogram generuje się nie dla ilości przedziałów, a płynnie.
	 */
	private int iloscPrzedzialowHistogramu = 5;

	public PanelRysunek_Histogram(Sygnal _sygnal, int _ilosc) {
		super();
		this.wykres = true;
		this.sygnalWyswietlany = _sygnal;
		this.iloscPrzedzialowHistogramu = _ilosc;
		initGUI();
	}

	public PanelRysunek_Histogram() {
		this.wykres = false;
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout(1, 1);
			thisLayout.setHgap(5);
			thisLayout.setVgap(5);
			thisLayout.setColumns(1);
			this.setLayout(thisLayout);
			setPreferredSize(new Dimension(400, 300));

			if (this.wykres) {
				double punkt = this.sygnalWyswietlany.gett1();
				HistogramDataset histogramDataset = new HistogramDataset();
				histogramDataset.setType(HistogramType.FREQUENCY);
				ArrayList<Double> punkty = new ArrayList<Double>();

				double ta = this.sygnalWyswietlany.gett1();

				if (this.sygnalWyswietlany.getrodzaj() == rodzaj_sygnalu.CIAGLY
						|| sygnalWyswietlany.getPunktyY().size() <= 0) {
					while (ta <= this.sygnalWyswietlany.gett1()
							+ this.sygnalWyswietlany.getd()) {
						punkt = this.sygnalWyswietlany.wykres_punkty(punkt, ta);
						// this.sygnalWyswietlany.setPunktyY (punkt);
						punkty.add(punkt);
						ta = ta + this.sygnalWyswietlany.getkroczek();
					}
				} else if (this.sygnalWyswietlany.getrodzaj() == rodzaj_sygnalu.DYSKRETNY) {
					int iloscProbek = (int) (this.sygnalWyswietlany.getT() / (Double) this.sygnalWyswietlany
							.getkroczek());
					for (int i = 0; i < iloscProbek; i++) {
						punkt = this.sygnalWyswietlany.getPunktzindexu(i);
						punkty.add(punkt);
						ta = ta + this.sygnalWyswietlany.getkroczek();
					}
				}

				JFreeChart chartHist;
				if (this.iloscPrzedzialowHistogramu > 0) {
					double[] tablicaHistogramu = new double[punkty.size()];
					for (int licznik = 0; licznik < punkty.size(); ++licznik)
						tablicaHistogramu[licznik] = punkty.get(licznik);

					histogramDataset.addSeries("Histogram (" + iloscPrzedzialowHistogramu + ")", tablicaHistogramu,
							this.iloscPrzedzialowHistogramu);

					chartHist = ChartFactory.createHistogram("Histogram", null,
							null, histogramDataset, PlotOrientation.VERTICAL,
							true, false, false);

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
