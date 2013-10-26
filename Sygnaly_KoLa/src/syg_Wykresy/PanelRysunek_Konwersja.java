package syg_Wykresy;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JOptionPane;

import org.jdesktop.application.Application;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import syg_package01.Sygnal;
import syg_package01.Sygnal.RodzajSygnalu;

@SuppressWarnings("serial")
public class PanelRysunek_Konwersja extends javax.swing.JPanel {

	/**
	 * Sygnał przed konwersją.
	 */
	private Sygnal sygnalPodstawowy = new Sygnal();

	public Sygnal getSygnalPodstawowy() {
		return sygnalPodstawowy;
	}

	public void setSygnalPodstawowy(Sygnal sygnalPodstawowy) {
		this.sygnalPodstawowy = sygnalPodstawowy;
		rysujPodstawowy();
	}

	/**
	 * Sygnał po konwersji.
	 */
	// private Sygnal sygnalPrzeksztalcony;
	private int nrPrzeksztalcenia;

	public int getNrPrzeksztalcenia() {
		return nrPrzeksztalcenia;
	}

	public void setNrPrzeksztalcenia(int nrPrzeksztalcenia) {
		this.nrPrzeksztalcenia = nrPrzeksztalcenia;
	}

	XYSeries series_0 = new XYSeries("Sygnał podstawowy");
	XYSeries series_s1 = new XYSeries("Próbki");
	XYSeries series_q2 = new XYSeries("Kwantyzacja równomierna z zaokrąglaniem");
	XYSeries series_r2 = new XYSeries("Interpolacja pierwszego rzędu");
	XYSeries series_r3 = new XYSeries("Rekonstrukcja w oparciu o funkcję sinc");
	JFreeChart chart;
	ChartPanel chartpanel;

	// private XYSeriesCollection dataset_r3;

	public PanelRysunek_Konwersja(Sygnal _sygnalPodstawowy, int _ktorePrzeksztalcenie) {
		super();
		this.setSygnalPodstawowy(_sygnalPodstawowy);
		// this.sygnalPrzeksztalcony = new Sygnal();
		this.nrPrzeksztalcenia = _ktorePrzeksztalcenie;
		initGUI();
	}

	private void rysujPodstawowy() {
		if (this.sygnalPodstawowy.getPunktyY_wykres().size() > 0) {
			double punkt = this.sygnalPodstawowy.getPunktzindexu(0);
			double ta = this.sygnalPodstawowy.gett1();
			int iloscProbek = (int) (this.sygnalPodstawowy.getPunktyY_wykres().size());

			for (int i = 0; i < iloscProbek; i++) {
				punkt = this.sygnalPodstawowy.getPunktzindexu(i);
				series_0.add(ta, punkt);
				ta = ta + this.sygnalPodstawowy.getkroczek();
			}
		}
	}

	public void rysuj() {
		if (this.sygnalPodstawowy == null) {
			JOptionPane.showMessageDialog(null, "brak sygnału", "Przekształcenie - rysunek",
					JOptionPane.ERROR_MESSAGE);
		} else {
			// rysujPodstawowy();
			if (this.nrPrzeksztalcenia == 0) {
				// próbkowanie: S1

				double punkt;
				double ta = this.sygnalPodstawowy.gett1();

				if (this.sygnalPodstawowy.getRodzaj() == RodzajSygnalu.CIAGLY
						|| sygnalPodstawowy.getPunktyY_wykres().size() <= 0) {
					punkt = this.sygnalPodstawowy.gett1();
					while (ta <= this.sygnalPodstawowy.gett1() + this.sygnalPodstawowy.getd()) {
						punkt = this.sygnalPodstawowy.wykres_punkty(punkt, ta);
						this.sygnalPodstawowy.setPunktyY_probkowanie((double) punkt);
						series_s1.add(ta, punkt);
						ta = ta + this.sygnalPodstawowy.getkrok();
					}
				}

				XYSeriesCollection dataset_s1 = new XYSeriesCollection(series_s1);
				// XYSeriesCollection dataset_0 = new XYSeriesCollection(
				// series_0);
				// dataset_s1.addSeries(series_0);

				chart = ChartFactory.createXYLineChart(null, null, null, dataset_s1,
						PlotOrientation.VERTICAL, true, true, true);

				// CategoryPlot plot = new CategoryPlot();
				final XYPlot plot_k = chart.getXYPlot();
				final XYDotRenderer renderer_s1 = new XYDotRenderer();
				renderer_s1.setDotHeight(5);
				renderer_s1.setDotWidth(5);

				// plot_k.setDataset(dataset_s1);
				plot_k.setRenderer(0, renderer_s1);

				final NumberAxis rangeAxis_s1 = (NumberAxis) plot_k.getRangeAxis();
				rangeAxis_s1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

				chartpanel = new ChartPanel(chart);

			} else if (this.nrPrzeksztalcenia == 1) {
				// kwantyzacja: Q2

				double punkt;
				double ta = this.sygnalPodstawowy.gett1();

				for (int i = 0; i < this.sygnalPodstawowy.getPunktyY_wykres().size(); i++) {

					punkt = this.sygnalPodstawowy.kwantyzacja(this.sygnalPodstawowy
							.getPunktzindexu(i));
					this.sygnalPodstawowy.setPunktyY_kwantyzacja(punkt);
					series_q2.add(ta, punkt);
					ta += this.sygnalPodstawowy.getkroczek();
				}

				XYSeriesCollection dataset_q2 = new XYSeriesCollection();
				dataset_q2.addSeries(series_0);
				dataset_q2.addSeries(series_q2);

				chart = ChartFactory.createXYLineChart(null, null, null, dataset_q2,
						PlotOrientation.VERTICAL, true, true, true);

				final XYLineAndShapeRenderer renderer_q2 = new XYLineAndShapeRenderer();
				renderer_q2.setSeriesLinesVisible(0, false);

				// ChartPanel chartpanel_q2 = new ChartPanel(chart_q2);
				// chartpanel_q2.setDomainZoomable(true);
				//
				// this.add(chartpanel_q2);
				// chartpanel = new ChartPanel(chart);

				// this.add(chartpanel);

			} else if (this.nrPrzeksztalcenia == 2) {
				// interpolacja R2

				this.remove(chartpanel);

				XYSeriesCollection dataset_r2 = new XYSeriesCollection();
				dataset_r2.addSeries(series_0);
				dataset_r2.addSeries(series_s1);

				chart = ChartFactory.createXYLineChart(null, null, null, dataset_r2,
						PlotOrientation.VERTICAL, true, true, true);

				final XYLineAndShapeRenderer renderer_r2 = new XYLineAndShapeRenderer();
				renderer_r2.setSeriesLinesVisible(0, false);

				chartpanel = new ChartPanel(chart);
				chartpanel.setDomainZoomable(true);
				this.add(chartpanel);

			} else if (this.nrPrzeksztalcenia == 3) {
				// Rekonstrukcja w oparciu o funkcję sinc - R3

				this.remove(chartpanel);

				double ta = this.sygnalPodstawowy.gett1();
				double punkt = this.sygnalPodstawowy.getPunktyY_probkowanie(0);
				// this.sygnalPodstawowy.getPunktyY_kwantyzacja().clear();

				for (int i = 0; i < this.sygnalPodstawowy.getPunktyY_wykres().size(); i++) {
					punkt = 0.0D;
					// double t2 = ta;
					for (int n = 0; n < this.sygnalPodstawowy.getPunktyY_probkowanie().size(); n++) {
						punkt += this.getSygnalPodstawowy().getPunktyY_probkowanie(n)
								* this.sygnalPodstawowy.sinc((ta / this.sygnalPodstawowy.getkrok())
										- n);
						// t2 += this.getSygnalPodstawowy().getkroczek();
					}
					this.sygnalPodstawowy.setPunktyY_kwantyzacja(punkt);
					series_r3.add(ta, punkt);
					ta += this.getSygnalPodstawowy().getkroczek();
				}

				XYSeriesCollection dataset_r3 = new XYSeriesCollection();

				dataset_r3.addSeries(series_r3);
				dataset_r3.addSeries(series_0);

				chart = ChartFactory.createXYLineChart(null, null, null, dataset_r3,
						PlotOrientation.VERTICAL, true, true, true);

				final XYLineAndShapeRenderer renderer_r2 = new XYLineAndShapeRenderer();
				renderer_r2.setSeriesLinesVisible(0, false);

				chartpanel = new ChartPanel(chart);
				chartpanel.setDomainZoomable(true);
				this.add(chartpanel);
			}
		}
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout(1, 1);
			thisLayout.setHgap(5);
			thisLayout.setVgap(5);
			thisLayout.setColumns(1);
			this.setLayout(thisLayout);
			setPreferredSize(new Dimension(400, 300));

			this.rysuj();

			if (nrPrzeksztalcenia < 2 && sygnalPodstawowy != null) {
				chartpanel = new ChartPanel(chart);
				chartpanel.setDomainZoomable(true);

				this.add(chartpanel);
			}

			Application.getInstance().getContext().getResourceMap(getClass())
					.injectComponents(this);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
