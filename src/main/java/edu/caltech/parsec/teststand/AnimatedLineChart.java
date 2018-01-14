package edu.caltech.parsec.teststand;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.Duration;

public class AnimatedLineChart {

    private LineChart<Number, Number> chart;

    private XYChart.Series<Number, Number> dataSeries;

    private NumberAxis xAxis;

    private double num_points = 0;

    private double y = 5;

    // max_num_data_points is the number of data points to show.
    private int max_num_data_points, step;
    private long ms_refresh_rate;
    private final int MAX_Y, MIN_Y;

    private final String TITLE, X_AXIS_LABEL, Y_AXIS_LABEL;

    public AnimatedLineChart(int max_y, int min_y, int max_num_data_points, long ms_refresh_rate, String title, String x_axis, String y_axis) {

        MAX_Y = max_y;
        MIN_Y = min_y;
        this.max_num_data_points = max_num_data_points;
        step = max_num_data_points / 10;
        this.ms_refresh_rate = ms_refresh_rate;

        TITLE = title;
        X_AXIS_LABEL = x_axis;
        Y_AXIS_LABEL = y_axis;
    }

    public Parent createContent() {

        xAxis = new NumberAxis(0, max_num_data_points, step);
        final NumberAxis yAxis = new NumberAxis(MIN_Y, MAX_Y, 1);
        chart = new LineChart<>(xAxis, yAxis);

        // setup chart
        chart.setAnimated(false);
        chart.setLegendVisible(false);
        chart.setTitle(TITLE);
        xAxis.setLabel(X_AXIS_LABEL);
        xAxis.setForceZeroInRange(false);

        yAxis.setLabel(Y_AXIS_LABEL);
//        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis, "$", null));

        // add starting data
        dataSeries = new XYChart.Series<>();
        dataSeries.setName("Data");

        // create some starting data
        dataSeries.getData()
                .add(new XYChart.Data<Number, Number>(++num_points, y));

        chart.getData().add(dataSeries);

        return chart;
    }

    public void addValue(double val) {
        addValue(num_points + 1, val);
    }

    public void addValue(double time, double val) {
        num_points++;
        dataSeries.getData().add(new XYChart.Data<>(time, val));

        // delete old data
        if (num_points > max_num_data_points) {
            dataSeries.getData().remove(0);
        }

        // every $step$ number of points, move the x-axis
        if (num_points > max_num_data_points - 1 && num_points % step == 0) {
            xAxis.setLowerBound(xAxis.getLowerBound() + step);
            xAxis.setUpperBound(xAxis.getUpperBound() + step);
        }
    }

    public void setStepX(int step_x) {
        this.step = step_x;
    }

    public long getRefreshRate() {
        return ms_refresh_rate;
    }


//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        primaryStage.setScene(new Scene(createContent()));
//        primaryStage.setTitle("Animated Line Chart");
//        primaryStage.show();
//        play();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }

}