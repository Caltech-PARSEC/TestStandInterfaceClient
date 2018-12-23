package edu.caltech.parsec.teststand;

import javafx.beans.NamedArg;
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
    private HashMap<String, Integer> series_num_points;

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
        series_num_points = new HashMap<>();
    }

    public AnimatedLineChart(NumberAxis xAxis, NumberAxis yAxis, String title, String[] series_names) {
        super(xAxis, yAxis);
        // setup chart
        super.setAnimated(false);
        super.setTitle(title);
//        chart.setLegendVisible(false);
        data_series = new HashMap<>();
        series_num_points = new HashMap<>();
        for (String name : series_names) {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName(name);
            data_series.put(name, series);
            series_num_points.put(name, 0);
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

//    private int getMaxNumPoints() {
//        int max = Integer.MIN_VALUE;
//        for (int num : series_num_points.values())
//            max = Math.max(max, num);
//        return max;
//    }

    public void addSeries(String name) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(name);
        data_series.put(name, series);
        series_num_points.put(name, 0);
        System.out.println(name);
        super.getData().add(series);
    }
    public void addValue(String series_name, double val) {
        addValue(series_name, series_num_points.get(series_name) + 1, val);
    }

    public void addValue(String series_name, double time, double val) {
        series_num_points.put(series_name, series_num_points.get(series_name) + 1);

        XYChart.Series<Number, Number> this_series = data_series.get(series_name);

        this_series.getData().add(new XYChart.Data<>(time, val));
        if (val > ((NumberAxis)getYAxis()).getUpperBound())
            ((NumberAxis)getYAxis()).setUpperBound(Math.ceil(val * 1.05));
        else if (val < ((NumberAxis)getYAxis()).getLowerBound())
            ((NumberAxis)getYAxis()).setUpperBound(Math.floor(val / 1.05));
        int cur_num_points = this_series.getData().size();

        // Once we have too many points, remove `step` many of them, then recalculate bounds
        if (cur_num_points >= num_display_points) {
            // Delete the old points
            System.out.println("Removing shit");
            for (XYChart.Series<Number, Number> series : data_series.values())
                for (int i = 0; i < step; i++)
                    series.getData().remove(0);

            // Recalculate the new upper/lower bounds
            double minimum = Double.MAX_VALUE;
            double maximum = -Double.MAX_VALUE;
            for (XYChart.Series<Number, Number> series : data_series.values()) {
                for (int i = 0; i < series.getData().size(); i++) {
                    Data<Number, Number> value = series.getData().get(i);
                    minimum = Math.min(minimum, value.getYValue().doubleValue());
                    maximum = Math.max(maximum, value.getYValue().doubleValue());
                }
            }
//            ((NumberAxis) getXAxis()).lowerBoundProperty().setValue(((NumberAxis)getXAxis()).getLowerBound() + step);
//            ((NumberAxis) getXAxis()).upperBoundProperty().setValue(((NumberAxis)getXAxis()).getUpperBound() + step);
            ((NumberAxis)getXAxis()).setUpperBound(((NumberAxis)getXAxis()).getUpperBound() + step);
            ((NumberAxis)getXAxis()).setLowerBound(((NumberAxis)getXAxis()).getLowerBound() + step);
            ((NumberAxis)getYAxis()).setLowerBound(Math.floor(minimum / 1.05));
            ((NumberAxis)getYAxis()).setUpperBound(Math.ceil(maximum * 1.05));
        }
    }

    public void setStepX(int step_x) {
        this.step = step_x;
    }

}
