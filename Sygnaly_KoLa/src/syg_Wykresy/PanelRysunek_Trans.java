package syg_Wykresy;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.JFrame;

import org.apache.commons.math.complex.Complex;
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
public class PanelRysunek_Trans extends javax.swing.JPanel {

	XYSeries series_w1g = new XYSeries("Część rzeczywista amplitudy w funkcji częstotliwości");
	XYSeries series_w1d = new XYSeries("Część urojona");
	XYSeries series_w2g = new XYSeries("Moduł liczby zespolonej");
	XYSeries series_w2d = new XYSeries("Liczby w funkcji częstotliwości");
	int widok, przeksztalcenie;
	Sygnal sygnal;
	JFreeChart chart_g;
	JFreeChart chart_d;
	ChartPanel chartpanel;
	ChartPanel chartpanel_d;

	public PanelRysunek_Trans(int _widok, int _przeksztalcenie, Sygnal _sygnal) {
		super();
		this.widok = _widok;
		this.przeksztalcenie = _przeksztalcenie;
		this.sygnal = _sygnal;
		initGUI();
	}

	public void rysuj() {
		if (this.sygnal == null) {
			JOptionPane.showMessageDialog(null, "brak sygnału", "Przekształcenie - rysunek",
					JOptionPane.ERROR_MESSAGE);
		} else {
			// rysujPodstawowy();
			XYSeriesCollection dataset_1 = null;
			XYSeriesCollection dataset_2 = null;
			List<Complex> complexList = null;
			if (this.przeksztalcenie == 1) { // dyskretna transformacja Fouriera
												// F2

				complexList = this.sygnal.FFT(this.sygnal.getPunktyY_probkowanie());

			} else if (this.przeksztalcenie == 2) { // transformacja
													// Walsha-Hadamarda
				complexList = this.sygnal.Hadamard();
			}

			if (complexList != null) {
				if (complexList.size() > 0) {
					for (int i = 0; i < complexList.size(); ++i) {
						if (this.widok == 1) {
							series_w1g.add(i, complexList.get(i).getReal());
							series_w1d.add(i, complexList.get(i).getImaginary());
							dataset_1 = new XYSeriesCollection(series_w1g);
							dataset_2 = new XYSeriesCollection(series_w1d);
						} else {
							series_w2g.add(i, complexList.get(i).abs());
							series_w2d.add(i, complexList.get(i).getArgument());
							dataset_1 = new XYSeriesCollection(series_w2g);
							dataset_2 = new XYSeriesCollection(series_w2d);
						}
					}
				}

				chart_g = ChartFactory.createXYLineChart(null, null, null, dataset_1,
						PlotOrientation.VERTICAL, true, true, true);

				// CategoryPlot plot = new CategoryPlot();
				final XYPlot plot_g = chart_g.getXYPlot();
				final XYDotRenderer renderer_g = new XYDotRenderer();
				renderer_g.setDotHeight(5);
				renderer_g.setDotWidth(5);

				// plot_k.setDataset(dataset_s1);
				plot_g.setRenderer(renderer_g);

				final NumberAxis rangeAxis_s1 = (NumberAxis) plot_g.getRangeAxis();
				rangeAxis_s1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

				// chartpanel = new ChartPanel(chart_g);
				chartpanel = new ChartPanel(chart_g);
				chartpanel.setDomainZoomable(true);
				this.add(chartpanel, BorderLayout.NORTH);

				chart_d = ChartFactory.createXYLineChart(null, null, null, dataset_2,
						PlotOrientation.VERTICAL, true, true, true);

				// CategoryPlot plot = new CategoryPlot();
				final XYPlot plot_d = chart_d.getXYPlot();
				final XYDotRenderer renderer_d = new XYDotRenderer();
				renderer_d.setDotHeight(5);
				renderer_d.setDotWidth(5);

				// plot_k.setDataset(dataset_s1);
				plot_d.setRenderer(renderer_d);

				final NumberAxis rangeAxis_s2 = (NumberAxis) plot_d.getRangeAxis();
				rangeAxis_s2.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

				// chartpanel = new ChartPanel(chart_g);
				chartpanel_d = new ChartPanel(chart_d);
				chartpanel_d.setDomainZoomable(true);
				this.add(chartpanel_d, BorderLayout.SOUTH);
			}
		}
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout(2, 1);
			thisLayout.setHgap(5);
			thisLayout.setVgap(5);
			thisLayout.setColumns(1);
			this.setLayout(thisLayout);
			setPreferredSize(new Dimension(400, 300));

			this.rysuj();

			Application.getInstance().getContext().getResourceMap(getClass())
					.injectComponents(this);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
