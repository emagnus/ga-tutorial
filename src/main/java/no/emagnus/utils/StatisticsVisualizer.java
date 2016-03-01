package no.emagnus.utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class StatisticsVisualizer {

    public static void visualize(Statistics statistics) {
        XYSeries avgSeries = new XYSeries("Average");
        XYSeries maxSeries = new XYSeries("Maximum");
        double[] avgFitness = statistics.getAvgFitness();
        double[] maxFitness = statistics.getMaxFitness();
        for (int i = 0; i < avgFitness.length; i++) {
            avgSeries.add(i, avgFitness[i]);
            maxSeries.add(i, maxFitness[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(avgSeries);
        dataset.addSeries(maxSeries);

        JFreeChart xyLineChart = ChartFactory.createXYLineChart("GA statistics", "Generation", "Fitness", dataset, PlotOrientation.VERTICAL, true, false, false);

        ChartPanel chartPanel = new ChartPanel(xyLineChart);
        chartPanel.setPreferredSize(new Dimension(640, 480));

        XYPlot plot = xyLineChart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesPaint(1, Color.RED);
        plot.setRenderer(renderer);
        plot.getDomainAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        JFrame jFrame = new JFrame("GA statistics");
        jFrame.setContentPane(chartPanel);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);

    }

}
