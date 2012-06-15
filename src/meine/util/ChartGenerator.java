package meine.util;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class ChartGenerator {

    private Map<String, Map<String, Integer>> data;
    private  Map<String,Integer> serieTotals;
    private JFreeChart chart;
    private DefaultCategoryDataset dataset;
    private String titel;

    public ChartGenerator(String title, Map<String, Map<String, Integer>> data, Map<String,Integer> serieTotals) {
        this.titel = title;
        this.serieTotals = serieTotals;
        this.data = data;
        dataset = createDataset();
        chart = createChart(dataset);
    }

    public BufferedImage getImage(int width, int height) {
        BufferedImage image = chart.createBufferedImage(width, height);
        return image;
    }

    public JFreeChart getChart() {
        return chart;
    }

    private DefaultCategoryDataset createDataset() {
        //  String series1 = "";

        dataset = new DefaultCategoryDataset();
        Set<String> series = data.keySet();
        for (String serie : series) {
            Set<String> categorien = data.get(serie).keySet();
            for (String categorie : categorien) {
                Integer aantal = data.get(serie).get(categorie);
                double percentage = (aantal.doubleValue() / serieTotals.get(serie).doubleValue()) * 100;
                dataset.addValue(percentage, serie, categorie);
            }
        }
        return dataset;
    }

    private JFreeChart createChart(CategoryDataset dataset) {

        // create the chart...
        chart = ChartFactory.createBarChart(
                null, // chart title
                "Categorie", // domain axis label
                "Percentage", // range axis label
                dataset, // data
                PlotOrientation.VERTICAL, // orientation
                true, // include legend
                true, // tooltips?
                false // URLs?
                );

        //[252,220,147]
        chart.setBackgroundPaint(new Color(252, 220, 147));

        CategoryPlot plot = (CategoryPlot) chart.getPlot();



        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        //XYItemLabelGenerator g = new XYItemLabelGenerator();
        //   renderer.setSeriesItemLabelGenerator(0, null)
        CategoryItemLabelGenerator g = new StandardCategoryItemLabelGenerator();

        CategoryToolTipGenerator t = new StandardCategoryToolTipGenerator();
        int numSeries = data.keySet().size();
        for (int i = 0; i < numSeries; i++) {
            renderer.setSeriesToolTipGenerator(i, t);
            //  renderer.setSeries
            renderer.setSeriesItemLabelGenerator(i, g);
            renderer.setSeriesItemLabelsVisible(i, true);

        }
        // set up gradient paints for series...
        GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.blue,
                0.0f, 0.0f, new Color(0, 0, 64));
        GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, Color.green,
                0.0f, 0.0f, new Color(0, 64, 0));
        GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, Color.red,
                0.0f, 0.0f, new Color(64, 0, 0));
        renderer.setSeriesPaint(0, gp0);
        renderer.setSeriesPaint(1, gp1);
        renderer.setSeriesPaint(2, gp2);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(
                Math.PI / 6.0));

        return chart;

    }
}
