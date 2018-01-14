package edu.caltech.parsec.teststand;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.KeyFrame;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.HashMap;
import java.util.function.DoubleSupplier;

public class ClientApp extends Application {

    private final String GET_ALL_SENSORS_URL = "https://private-143c20-simulatorcontrol.apiary-mock.com/getSensors";

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Line Chart Sample");

        GridPane grid = new GridPane();
        setGridpaneColsRowsSameSize(grid, 12, 12);
        grid.setHgap(25);
        grid.setVgap(25);
        grid.setPadding(new Insets(25, 25, 25, 25));
/*
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
        series3.getData().add(new XYChart.Data("Dec", 44));*/

//        lineChart1.getData().addAll(series1);
//        lineChart2.getData().addAll(series2);
//        lineChart3.getData().addAll(series3);

        AnimatedLineChart animatedLineChart = new AnimatedLineChart(10, 1, 100, 1000000, "Temperature of Engine", "Time (s)", "Temperature (Deg C)");

        // ================= TextArea STUFF ======================

        TextArea javascriptTextBox = new TextArea();
        javascriptTextBox.setWrapText(true);

        // =================== BUTTONS ========================

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");

        Button start_btn = new Button("Begin Test");
        start_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    System.out.println(engine.eval(javascriptTextBox.getText()));
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
        Button stop_btn = new Button("Stop Test");

        // ================= GRID STUFF ======================

//        grid.add(lineChart1, 0, 0, 4, 4); // Col index, row index, colspan, rowspan
//        grid.add(lineChart2, 4, 0, 4, 4);
//        grid.add(lineChart3, 8, 0, 4, 4);
        grid.add(javascriptTextBox, 0,0, 3, 4);
        grid.add(start_btn, 5, 10, 1, 1);
        grid.add(stop_btn, 6, 10, 1, 1);
        grid.add(animatedLineChart.createContent(), 0, 4, 4, 4);

        Scene scene = new Scene(grid, 1024, 800);

        ObjectMapper mapper = new ObjectMapper();
        Sensors sensors = mapper.readValue(
                HttpInterface.executeGet(GET_ALL_SENSORS_URL, new HashMap<>()), Sensors.class);
        System.out.println(sensors);

        primaryStage.setScene(scene);
        primaryStage.show();
        Object result = engine.eval("4*5");
        System.out.println(result);
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
