package syg_Wykresy;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

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

import syg_package01.Filtr;
import syg_package01.PanelFiltracja;
import syg_package01.Sygnal;
import syg_package01.Sygnal.RodzajSygnalu;

@SuppressWarnings("serial")
public class PanelRysunek_Filtracja extends javax.swing.JPanel {

	private Sygnal[] sygnaly;
	private Filtr filtr;

	String[] podpisyWykresu = new String[] { "Filtracja ze splotem", "Korelacja bezpośrednia",
			"Korelacja ze splotem", "Odpowiedź impulsowa" };
	XYSeries series = new XYSeries(podpisyWykresu[0]);
	XYSeries series2 = new XYSeries(podpisyWykresu[3]);
	JFreeChart chart;
	ChartPanel chartpanel;

	private JFreeChart chart2;
	private ChartPanel chartpanel2;
	private int ktoraOpcja;

	public PanelRysunek_Filtracja() {
	}

	/**
	 * 
	 * @param _sygnaly
	 * @param _filtr
	 * @param _ktoraOpcja
	 * @param _czyRysowac
	 *            - czy utworzyć wykres (jeśli nie to oblicznia w: obliczPunkty)
	 */
	public PanelRysunek_Filtracja(Sygnal[] _sygnaly, Filtr _filtr, int _ktoraOpcja,
			boolean _czyRysowac) {
		super();
		this.setSygnal(_sygnaly);
		this.setFiltr(_filtr);
		this.ktoraOpcja = _ktoraOpcja;
		if (_czyRysowac)
			initGUI();
	}

	/**
	 * 
	 * @param _sygnaly
	 * @param _filtr
	 * @param _ktoraOpcja
	 * <br>
	 *            0 - ...<br>
	 *            1 -
	 * 
	 */
	public PanelRysunek_Filtracja(Sygnal[] _sygnaly, Filtr _filtr, int _ktoraOpcja) {
		super();
		this.setSygnal(_sygnaly);
		this.setFiltr(_filtr);
		this.ktoraOpcja = _ktoraOpcja;
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout(this.sygnaly.length == 1 ? 2 : 1, 1);

			thisLayout.setHgap(5);
			thisLayout.setVgap(5);
			thisLayout.setColumns(1);
			this.setLayout(thisLayout);
			setPreferredSize(new Dimension(400, 300));

			this.obliczPunkty(series, series2);

			try {
				XYSeriesCollection dataset = new XYSeriesCollection(series);

				if (this.getSygnal().getRodzaj() == RodzajSygnalu.CIAGLY) {
					chart = ChartFactory.createXYLineChart(null, null, null, dataset,
							PlotOrientation.VERTICAL, true, true, true);

					final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
					renderer.setSeriesLinesVisible(0, true);

				} else {
					chart = ChartFactory.createXYLineChart(null, null, null, dataset,
							PlotOrientation.VERTICAL, true, true, true);

					final XYPlot plot = chart.getXYPlot();
					final XYDotRenderer renderer = new XYDotRenderer();
					renderer.setDotHeight(3);
					renderer.setDotWidth(3);
					plot.setRenderer(renderer);

					final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
					rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
				}

				chartpanel = new ChartPanel(chart);
				chartpanel.setDomainZoomable(true);
				this.add(chartpanel, BorderLayout.NORTH);

				if (!series2.isEmpty()) {
					XYSeriesCollection dataset2 = new XYSeriesCollection(series2);

					chart2 = ChartFactory.createXYLineChart(null, null, null, dataset2,
							PlotOrientation.VERTICAL, true, true, true);

					final XYPlot plot = chart2.getXYPlot();
					final XYDotRenderer renderer2 = new XYDotRenderer();
					renderer2.setDotHeight(3);
					renderer2.setDotWidth(3);
					plot.setRenderer(renderer2);

					final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
					rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

					chartpanel2 = new ChartPanel(chart2);
					chartpanel2.setDomainZoomable(true);
					this.add(chartpanel2, BorderLayout.SOUTH);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Application.getInstance().getContext().getResourceMap(getClass())
					.injectComponents(this);

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Obliczenie punktów wykresu na podstawie filtra i dla danego sygnału.<br>
	 * znane: <i>filtr</i> (jeśli <i>któraOpcja</i> = 0 to <i>filtr</i> nie
	 * potrzebny), <i>któraOpcja</i>
	 * 
	 * @param series
	 * 
	 * @param series2
	 */
	public void obliczPunkty(XYSeries _series, XYSeries _series2) {
		double czas = this.getSygnal().gett1() + (this.filtr != null ? this.filtr.getLiczbaWspolczynnikow()
				* this.getSygnal().gett1() : 0.0D);

		if (this.sygnaly.length == 1) {
			// int ileIndeksow = this.getSygnal().getPunktyY_wykres().size()
			// + this.filtr.getLiczbaWspolczynnikow() + 1;
			// int ileIndeksow = (this.getSygnal().getPunktyY_wykres().size()
			// + this.filtr.getLiczbaWspolczynnikow())/2 + 1;
			int ileIndeksow = this.getSygnal().getPunktyY_wykres().size();

			for (int i = this.filtr.getLiczbaWspolczynnikow(); i < ileIndeksow; i++) {
				_series.add(czas, this.getSygnal().splot(i, this.filtr));

				if (this.getSygnal().getRodzaj() == RodzajSygnalu.CIAGLY) {
					czas += this.getSygnal().getkroczek();
				} else {
					czas += this.getSygnal().getkrok();
				}
			}

			for (int i = 0; i < this.filtr.getLiczbaWspolczynnikow(); i++) {
				_series2.add(i,
						this.getSygnal().odpowiedzImpulsowa(i, this.filtr, false, this.getSygnal()));
			}

		} else {
			// obliczenie ilości indeksów i pierwszego indeksu
			int ileIndeksow = 0;
			int indexR = 0;

			// wybór: implementacja bezpośrednia czy ze splotem
			if (ktoraOpcja > -1) {

				_series.setKey(this.podpisyWykresu[ktoraOpcja + 1]);

				// obliczenie ilości indeksów i pierwszego indeksu
				if (ktoraOpcja == 0) {

					ileIndeksow = this.getSygnaly()[0].getPunktyY_wykres().size()
							+ this.getSygnaly()[1].getPunktyY_wykres().size() + 1;
					indexR = (((int) (ileIndeksow / 2)) + 1) - ileIndeksow;
				} else if (ktoraOpcja == 1) {
					ileIndeksow = this.getSygnaly()[0].getPunktyY_wykres().size()
							+ this.getSygnaly()[1].getPunktyY_wykres().size() + 1;
					indexR = 0;
				}

				for (int i = 0; i < ileIndeksow; ++i) {
					// korelacja
					_series.add(
							czas,
							this.getSygnaly()[0].korelacja(indexR + i, this.filtr,
									this.getSygnaly()[1], ktoraOpcja));

					// odstęp czasu
					if (this.getSygnaly()[0].getRodzaj() == RodzajSygnalu.CIAGLY
							&& this.getSygnaly()[1].getRodzaj() == RodzajSygnalu.CIAGLY) {
						czas += this.getSygnaly()[0].getkroczek();
					} else {
						czas += this.getSygnaly()[0].getkrok();
					}
				}

				this.getSygnaly()[0].punktyZrekonstruowane = _series;
			}
		}
	}

	public void setSygnal(Sygnal _sygnal) {
		this.setSygnal(_sygnal);
	}

	public Sygnal getSygnal() {
		return sygnaly[0];
	}

	public void setSygnal(Sygnal[] _sygnaly) {
		this.sygnaly = _sygnaly;
	}

	public Sygnal[] getSygnaly() {
		return sygnaly;
	}

	public void setFiltr(Filtr _filtr) {
		this.filtr = _filtr;
	}

	public Filtr getFiltr() {
		return filtr;
	}

}
