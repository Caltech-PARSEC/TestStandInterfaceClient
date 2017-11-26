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

import java.util.Random;
import java.util.function.DoubleSupplier;

public class AnimatedLineChart {

    private LineChart<Number, Number> chart;

    private XYChart.Series<Number, Number> dataSeries;

    private NumberAxis xAxis;

    public Timeline animation;

    private double sequence = 0;

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

        // create timeline to add new data every 100ms
        animation = new Timeline();
        animation.getKeyFrames()
                .add(new KeyFrame(Duration.millis(ms_refresh_rate),
                        (ActionEvent actionEvent) -> plotTime()));
        animation.setCycleCount(Animation.INDEFINITE);
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
                .add(new XYChart.Data<Number, Number>(++sequence, y));

        chart.getData().add(dataSeries);

        return chart;
    }

    public void plotTime(DoubleSupplier supplier) {
        dataSeries.getData().add(new XYChart.Data<Number, Number>(++sequence, supplier.getAsDouble()));

        // delete old data
        if (sequence > max_num_data_points) {
            dataSeries.getData().remove(0);
        }

        // every hour after 24 move range 1 hour
        if (sequence > max_num_data_points - 1 && sequence % step == 0) {
            xAxis.setLowerBound(xAxis.getLowerBound() + step);
            xAxis.setUpperBound(xAxis.getUpperBound() + step);
        }
    }

    public void plotTime() {
        dataSeries.getData().add(new XYChart.Data<Number, Number>(++sequence, getNextValue()));

        // delete old data
        if (sequence > max_num_data_points) {
            dataSeries.getData().remove(0);
        }

        // every hour after 24 move range 1 hour
        if (sequence > max_num_data_points - 1 && sequence % step == 0) {
            xAxis.setLowerBound(xAxis.getLowerBound() + step);
            xAxis.setUpperBound(xAxis.getUpperBound() + step);
        }
    }

    private double getNextValue() {
        Random rand = new Random();
        return rand.nextInt((MAX_Y - MIN_Y) - 1) + MIN_Y + 1;
    }

    public void play() {
        animation.play();
    }

    //    @Override
    public void stop() {
        animation.pause();
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