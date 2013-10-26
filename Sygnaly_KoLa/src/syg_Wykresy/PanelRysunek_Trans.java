package syg_Wykresy;

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
	ChartPanel chartpanel;
		
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
			if (this.przeksztalcenie == 0) {
				// dyskretna transformacja Fouriera F2

				double punkt;
				double ta = this.sygnal.gett1();

				List<Complex> fft = this.sygnal.FFT(this.sygnal.getPunktyY_probkowanie());
				
				if (this.sygnal.getRodzaj() == RodzajSygnalu.CIAGLY
						|| sygnal.getPunktyY_wykres().size() <= 0) {
					punkt = this.sygnal.gett1();
					int index = 0;
					while (ta <= this.sygnal.gett1() + this.sygnal.getd()) {
						punkt = fft.get(index).getReal();
						series_w1g.add(ta, punkt);
						series_w1g.add(ta, fft.get(index).getImaginary());
						ta = ta + this.sygnal.getkrok();
						index++;
					}
				}

				XYSeriesCollection dataset_1 = new XYSeriesCollection(series_w1g);
				// XYSeriesCollection dataset_0 = new XYSeriesCollection(
				// series_0);
				// dataset_s1.addSeries(series_0);

				chart_g = ChartFactory.createXYLineChart(null, null, null, dataset_1,
						PlotOrientation.VERTICAL, true, true, true);

				// CategoryPlot plot = new CategoryPlot();
				final XYPlot plot_g = chart_g.getXYPlot();
				final XYDotRenderer renderer_g = new XYDotRenderer();
				renderer_g.setDotHeight(5);
				renderer_g.setDotWidth(5);

				// plot_k.setDataset(dataset_s1);
				plot_g.setRenderer(0, renderer_g);

				final NumberAxis rangeAxis_s1 = (NumberAxis) plot_g.getRangeAxis();
				rangeAxis_s1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

				chartpanel = new ChartPanel(chart_g);

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

			if (przeksztalcenie < 2 && przeksztalcenie > 0 && sygnal != null) {
				chartpanel = new ChartPanel(chart_g);
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
