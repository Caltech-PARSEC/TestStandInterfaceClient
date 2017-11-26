package edu.caltech.parsec.teststand;

import com.sun.org.apache.xerces.internal.util.HTTPInputSource;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Map;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class ClientApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Line Chart Sample");

        GridPane grid = new GridPane();
        setGridpaneColsRowsSameSize(grid, 12, 12);
        grid.setHgap(25);
        grid.setVgap(25);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // ======== CHARTS =================================

        final CategoryAxis xAxis1 = new CategoryAxis();
        final NumberAxis yAxis1 = new NumberAxis();
        xAxis1.setLabel("Month");
        final LineChart<String, Number> lineChart1 =
                new LineChart<String, Number>(xAxis1, yAxis1);
        lineChart1.setTitle("Stock Monitoring, 2011");

        final CategoryAxis xAxis2 = new CategoryAxis();
        final NumberAxis yAxis2 = new NumberAxis();
        xAxis2.setLabel("Month");
        final LineChart<String, Number> lineChart2 =
                new LineChart<String, Number>(xAxis2, yAxis2);
        lineChart2.setTitle("Stock Monitoring, 2012");

        final CategoryAxis xAxis3 = new CategoryAxis();
        final NumberAxis yAxis3 = new NumberAxis();
        xAxis3.setLabel("Month");
        final LineChart<String, Number> lineChart3 =
                new LineChart<String, Number>(xAxis3, yAxis3);
        lineChart3.setTitle("Stock Monitoring, 2013");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Portfolio 1");

        series1.getData().add(new XYChart.Data("Jan", 23));
        series1.getData().add(new XYChart.Data("Feb", 14));
        series1.getData().add(new XYChart.Data("Mar", 15));
        series1.getData().add(new XYChart.Data("Apr", 24));
        series1.getData().add(new XYChart.Data("May", 34));
        series1.getData().add(new XYChart.Data("Jun", 36));
        series1.getData().add(new XYChart.Data("Jul", 22));
        series1.getData().add(new XYChart.Data("Aug", 45));
        series1.getData().add(new XYChart.Data("Sep", 43));
        series1.getData().add(new XYChart.Data("Oct", 17));
        series1.getData().add(new XYChart.Data("Nov", 29));
        series1.getData().add(new XYChart.Data("Dec", 25));

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Portfolio 2");
        series2.getData().add(new XYChart.Data("Jan", 33));
        series2.getData().add(new XYChart.Data("Feb", 34));
        series2.getData().add(new XYChart.Data("Mar", 25));
        series2.getData().add(new XYChart.Data("Apr", 44));
        series2.getData().add(new XYChart.Data("May", 39));
        series2.getData().add(new XYChart.Data("Jun", 16));
        series2.getData().add(new XYChart.Data("Jul", 55));
        series2.getData().add(new XYChart.Data("Aug", 54));
        series2.getData().add(new XYChart.Data("Sep", 48));
        series2.getData().add(new XYChart.Data("Oct", 27));
        series2.getData().add(new XYChart.Data("Nov", 37));
        series2.getData().add(new XYChart.Data("Dec", 29));

        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Portfolio 3");
        series3.getData().add(new XYChart.Data("Jan", 44));
        series3.getData().add(new XYChart.Data("Feb", 35));
        series3.getData().add(new XYChart.Data("Mar", 36));
        series3.getData().add(new XYChart.Data("Apr", 33));
        series3.getData().add(new XYChart.Data("May", 31));
        series3.getData().add(new XYChart.Data("Jun", 26));
        series3.getData().add(new XYChart.Data("Jul", 22));
        series3.getData().add(new XYChart.Data("Aug", 25));
        series3.getData().add(new XYChart.Data("Sep", 43));
        series3.getData().add(new XYChart.Data("Oct", 44));
        series3.getData().add(new XYChart.Data("Nov", 45));
        series3.getData().add(new XYChart.Data("Dec", 44));

        lineChart1.getData().addAll(series1);
        lineChart2.getData().addAll(series2);
        lineChart3.getData().addAll(series3);

        // =================== BUTTONS ========================

        Button start_btn = new Button("Begin Test");
        Button stop_btn = new Button("Stop Test");


        // ================= GRID STUFF ======================

        grid.add(lineChart1, 0, 0, 4, 4); // Col index, row index, colspan, rowspan
        grid.add(lineChart2, 4, 0, 4, 4);
        grid.add(lineChart3, 8, 0, 4, 4);
        grid.add(start_btn, 5, 10, 1, 1);
        grid.add(stop_btn, 6, 10, 1, 1);

        AnimatedLineChart animatedLineChart = new AnimatedLineChart(10, 1, 100, 100, "T1", "X1", "Y1");
        AnimatedLineChart animatedLineChart2 = new AnimatedLineChart(10, 1, 10, 1000, "T2", "X2", "Y2");

        grid.add(animatedLineChart.createContent(), 0, 4, 4, 4);
//        grid.add(animatedLineChart2.createContent(), 4, 4, 4, 4);

        Scene scene = new Scene(grid, 1024, 1024);

        Sensors sensors = new Sensors();
        addAnimatedLineChart(grid, animatedLineChart2, sensors::getEngineTempC, 4, 4, 4, 4);

        primaryStage.setScene(scene);
        primaryStage.show();
        animatedLineChart.play();
        animatedLineChart2.play();
    }

    private static void addAnimatedLineChart(GridPane grid, AnimatedLineChart chart, DoubleSupplier func,
                                             int col_index, int row_index, int col_span, int row_span) {
        Sensors sensors = new Sensors();
        chart.animation.getKeyFrames().remove(0);
        chart.animation.getKeyFrames()
                .add(new KeyFrame(Duration.millis(chart.getRefreshRate()),
                (ActionEvent actionEvent) -> chart.plotTime(func)));
        System.out.println(chart.animation.getKeyFrames());
        grid.add(chart.createContent(), col_index, row_index, col_span, row_span);
    }

    private static void setGridpaneColsRowsSameSize(GridPane grid, int rows, int cols) {
        double row_height = 100.0 / rows;
        double col_width = 100.0 / cols;

        RowConstraints[] row_constraints = new RowConstraints[cols];
        for (int row = 0; row < rows; row++){
            RowConstraints constraint = new RowConstraints();
            constraint.setPercentHeight(row_height);
            row_constraints[row] = constraint;
        }

        ColumnConstraints[] col_constraints = new ColumnConstraints[cols];
        for (int col = 0; col < cols; col++){
            ColumnConstraints constraint = new ColumnConstraints();
            constraint.setPercentWidth(col_width);
            col_constraints[col] = constraint;
        }

        grid.getRowConstraints().addAll(row_constraints);
        grid.getColumnConstraints().addAll(col_constraints);
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
