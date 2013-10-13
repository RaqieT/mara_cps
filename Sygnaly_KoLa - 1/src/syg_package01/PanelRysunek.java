package syg_package01;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

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
public class PanelRysunek extends javax.swing.JPanel {
	private Sygnal sygnalWyswietlany = new Sygnal();
	private boolean wykres;

	/**
	 * Auto-generated main method to display this JPanel inside a new JFrame.
	 */

	public PanelRysunek(Sygnal _sygnal) {
		super();
		this.wykres = true;
		this.sygnalWyswietlany = _sygnal;
		initGUI();
	}

	public PanelRysunek() {
		// TODO Auto-generated constructor stub
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
				double punktZapisu = this.sygnalWyswietlany.gett1();
				XYSeries series = new XYSeries("Wykres");

				double krok;
				int typ = this.sygnalWyswietlany.gettyp();
				double f = 1.0 / this.sygnalWyswietlany.getT();
				double t = this.sygnalWyswietlany.gett1();

				// if (this.sygnalWyswietlany.gettyp()==1){
				/*
				 * for (double i = this.sygnalWyswietlany.gett1(); i <
				 * this.sygnalWyswietlany.t1+this.sygnalWyswietlany.getts();
				 * i++){ punkt= this.sygnalWyswietlany.wykres_punkty(punkt, i);
				 * series.add(i, punkt)} ;
				 */

				double ta = this.sygnalWyswietlany.gett1();

				if (this.sygnalWyswietlany.getrodzaj() == rodzaj_sygnalu.CIAGLY
						|| sygnalWyswietlany.getPunktyY().size() <= 0) {
					while (ta <= this.sygnalWyswietlany.gett1()
							+ this.sygnalWyswietlany.getd()) {
						// punkt=this.sygnalWyswietlany.sygnalS1();
						punkt = this.sygnalWyswietlany.wykres_punkty(punkt, ta);
						this.sygnalWyswietlany.setPunktyY(punkt);
						// punkt=this.sygnalWyswietlany.sygnalS3(ta);
						series.add(ta, punkt);
						if (this.sygnalWyswietlany.gettyp() < 10)
							ta = ta + this.sygnalWyswietlany.getkroczek();
						else
							ta = ta + this.sygnalWyswietlany.getkroczek() * 10;
					}
				} else if (this.sygnalWyswietlany.getrodzaj() == rodzaj_sygnalu.DYSKRETNY
						&& sygnalWyswietlany.getPunktyY().size() > 0) {
					int iloscProbek = (int) (this.sygnalWyswietlany
							.getPunktyY().size());
					for (int i = 0; i < iloscProbek; i++) {
						// punkt=this.sygnalWyswietlany.sygnalS1();
						punkt = this.sygnalWyswietlany.getPunktzindexu(i);
						// punkt=this.sygnalWyswietlany.sygnalS3(ta);
						series.add(ta, punkt);
						if (this.sygnalWyswietlany.gettyp() < 10)
							ta = ta + this.sygnalWyswietlany.getkroczek();
						else
							ta = ta + this.sygnalWyswietlany.getkroczek() * 10;
					}
				}

				// this.sygnalWyswietlany.ustawPunkty();
				/*
				 * int liczba= this.sygnalWyswietlany.punktyY.size(); int
				 * liczba2= 0;
				 * 
				 * while (liczba2<liczba) { ta= ta+
				 * this.sygnalWyswietlany.getkroczek();
				 * series.add(ta,this.sygnalWyswietlany
				 * .getPunktzindexu(liczba2));
				 * 
				 * }
				 */

				XYSeriesCollection dataset = new XYSeriesCollection(series);

				// JFreeChart chart = ChartFactory.createHistogram("Histogram",
				// "x", "y", dataset, PlotOrientation.VERTICAL, false, false,
				// false);

				JFreeChart chart;

				/*
				 * JFreeChart chart = ChartFactory.createScatterPlot("Wykres",
				 * "t[s]", "Wartość", dataset, PlotOrientation.VERTICAL, false,
				 * false, false);
				 */
				if ((this.sygnalWyswietlany.gettyp() < 10)
						|| (this.sygnalWyswietlany.getrodzaj() == rodzaj_sygnalu.CIAGLY)) {
					chart = ChartFactory
							.createXYLineChart(null, null, null, dataset,
									PlotOrientation.VERTICAL, true, true, true);

					final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
					renderer.setSeriesLinesVisible(0, false);
				} else {
					chart = ChartFactory
							.createXYLineChart(null, null, null, dataset,
									PlotOrientation.VERTICAL, true, true, true);

					final XYPlot plot = chart.getXYPlot();
					// plot.setBackgroundPaint(Color.lightGray);
					// plot.setDomainGridlinePaint(Color.white);
					// plot.setRangeGridlinePaint(Color.white);

					// final XYLineAndShapeRenderer renderer = new
					// XYLineAndShapeRenderer();
					final XYDotRenderer renderer = new XYDotRenderer();
					renderer.setDotHeight(3);
					renderer.setDotWidth(3);
					plot.setRenderer(renderer);

					final NumberAxis rangeAxis = (NumberAxis) plot
							.getRangeAxis();
					rangeAxis.setStandardTickUnits(NumberAxis
							.createIntegerTickUnits());
				}

				// ChartPanel chartpanel = new ChartPanel(wykres2);
				ChartPanel chartpanel = new ChartPanel(chart);
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
