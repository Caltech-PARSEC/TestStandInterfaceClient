package edu.caltech.parsec.teststand;

import javafx.beans.NamedArg;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.HashMap;

// TODO: clean up this code in general.
// TODO: Also need to refactor the code to keep all the points on the graph.
public class AnimatedLineChart extends LineChart {
    private HashMap<String, XYChart.Series<Number, Number>> data_series;

    // num_display_points is the number of data points to show.
    private int num_display_points = 20, step = 2;

    public AnimatedLineChart(@NamedArg("xAxis") Axis xAxis, @NamedArg("yAxis") Axis yAxis) {
        super(xAxis, yAxis);
        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(false);
        ((NumberAxis) xAxis).lowerBoundProperty().setValue(0);
        ((NumberAxis) xAxis).upperBoundProperty().setValue(20);
        ((NumberAxis) yAxis).lowerBoundProperty().setValue(0);
        ((NumberAxis) yAxis).upperBoundProperty().setValue(20);
        data_series = new HashMap<>();
    }

    public AnimatedLineChart(NumberAxis xAxis, NumberAxis yAxis, String title, String[] series_names) {
        super(xAxis, yAxis);
        // setup chart
        super.setAnimated(false);
        super.setTitle(title);
//        chart.setLegendVisible(false);
        data_series = new HashMap<>();
        for (String name : series_names) {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName(name);
            data_series.put(name, series);
            System.out.println(name);
            super.getData().add(series);
        }
    }

    public static AnimatedLineChart createChart(
        int max_y, int min_y, int max_num_data_points, String title,
        String x_axis_label, String y_axis_label, String[] series_names)
    {
        NumberAxis x = 
          new NumberAxis(0, max_num_data_points, max_num_data_points / 10);
        NumberAxis y = new NumberAxis(min_y, max_y, 1);
        x.setLabel(x_axis_label);
        x.setForceZeroInRange(false);
        y.setLabel(y_axis_label);
        AnimatedLineChart lineChart = 
          new AnimatedLineChart(x, y, title, series_names);
        lineChart.setStepX(max_num_data_points / 10);
        return lineChart;
    }

    private double getMaxTime() {
        double max = -Double.MAX_VALUE;
        for (Series<Number, Number> s : data_series.values()) {
            ObservableList<Data<Number, Number>> data = s.getData();
            if (data.size() > 0)
                max = Math.max(max, (Double) data.get(data.size() - 1).getXValue());
        }
        return max;
    }

    public void addSeries(String name) {
        // Initialize the new series
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(name);

        // Add it to the series map and the graph itself, respectively
        data_series.put(name, series);
        super.getData().add(series);
    }

    // NOTE: This method assumes that time is a strictly increasing parameter
    // on a per-series basis.
    public void addValue(String series_name, double time, double val) {
        XYChart.Series<Number, Number> this_series = data_series.get(series_name);

        this_series.getData().add(new XYChart.Data<>(time, val));

        // If this is the first data point we are adding
        if (this_series.getData().size() == 1) {
            ((NumberAxis)getYAxis()).setUpperBound(Math.ceil(val * 1.05));
            ((NumberAxis)getYAxis()).setLowerBound(Math.floor(val / 1.05));

            // There are already reasonable defaults for if t=0
            if (time != 0) {
                ((NumberAxis) getXAxis()).setUpperBound(Math.ceil(time * 1.05));
                ((NumberAxis) getXAxis()).setLowerBound(Math.floor(time));
            }
        } else {
            // Set the y value upper and lower bounds if they need to be changed
            if (val * 1.02 > ((NumberAxis) getYAxis()).getUpperBound())
                ((NumberAxis) getYAxis()).setUpperBound(Math.ceil(val * 1.05));
            else if (val / 1.02 < ((NumberAxis) getYAxis()).getLowerBound())
                ((NumberAxis) getYAxis()).setUpperBound(Math.floor(val / 1.05));

            // Set the x upper bound
            double maxTime = getMaxTime();
            if (maxTime * 1.02 > ((NumberAxis) getXAxis()).getUpperBound())
                ((NumberAxis) getXAxis()).setUpperBound(Math.ceil(maxTime * 1.05));
        }

    }

    public void setStepX(int step_x) {
        this.step = step_x;
    }

}
