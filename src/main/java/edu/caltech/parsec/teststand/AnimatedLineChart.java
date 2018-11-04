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

public class AnimatedLineChart extends LineChart {
    private HashMap<String, XYChart.Series<Number, Number>> data_series;
    private HashMap<String, Integer> series_num_points;

    // max_allowed_num_data_points is the number of data points to show.
    private int max_allowed_num_data_points, step;

    public AnimatedLineChart(@NamedArg("xAxis") Axis xAxis, @NamedArg("yAxis") Axis yAxis) {
        super(xAxis, yAxis);
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

    private int getMaxNumPoints() {
        int max = Integer.MIN_VALUE;
        for (int num : series_num_points.values())
            max = Math.max(max, num);
        return max;
    }

    public void addValue(String series_name, double val) {
        addValue(series_name, series_num_points.get(series_name) + 1, val);
    }

    public void addValue(String series_name, double time, double val) {
        series_num_points.put(series_name, series_num_points.get(series_name) + 1);
        int max_num_points = series_num_points.get(series_name);
        max_num_points++;

        XYChart.Series<Number, Number> this_series = data_series.get(series_name);

        this_series.getData().add(new XYChart.Data<>(time, val));

        // delete old data
        if (max_num_points > max_allowed_num_data_points) {
            for (XYChart.Series<Number, Number> series : data_series.values())
                series.getData().remove(0);
        }

        // every $step$ number of points, move the x-axis
        if (max_num_points > max_allowed_num_data_points - 1 && max_num_points % step == 0) {
            max_allowed_num_data_points += step;
            ((NumberAxis)getXAxis()).setLowerBound(((NumberAxis)getXAxis()).getLowerBound() + step);
            ((NumberAxis)getXAxis()).setUpperBound(((NumberAxis)getXAxis()).getUpperBound() + step);
        }
    }

    public void setStepX(int step_x) {
        this.step = step_x;
    }

//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        primaryStage.setScene(new Scene(getChart()));
//        primaryStage.setTitle("Animated Line Chart");
//        primaryStage.show();
//        play();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }

}
