package edu.caltech.parsec.teststand;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientApp extends Application {

    private final String GET_ALL_SENSORS_URL = "https://private-143c20-simulatorcontrol.apiary-mock.com/getSensors";

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ClientApp.fxml"));

        Scene scene = new Scene(root, 1600, 800);

        primaryStage.setTitle("PARSEC Test Stand Interface Client");
        primaryStage.setScene(scene);
        primaryStage.show();
        AnimatedLineChart chart;

        /*primaryStage.setTitle("Test Stand Interface");

        GridPane grid = new GridPane();
        setGridpaneColsRowsSameSize(grid, 3, 3, true);
        grid.setHgap(25);
        grid.setVgap(25);
        grid.setPadding(new Insets(25, 25, 25, 25));
*//*
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
        series3.getData().add(new XYChart.Data("Dec", 44));*//*

//        lineChart1.getData().addAll(series1);
//        lineChart2.getData().addAll(series2);
//        lineChart3.getData().addAll(series3);

        // Line charts

        AnimatedLineChart temperatureCharts = AnimatedLineChart.createChart(10, 1, 20,
                "Temperature of Engine", "Time (s)",
                "Temperature (Deg C)", new String[]{"Temp1", "Temp2"});
        AnimatedLineChart fuelRemainingCharts = AnimatedLineChart.createChart(10, 1, 20,
                "Fuel and Oxidizer Remaining", "Time (s)",
                "Amount (Gallons)", new String[]{"Fuel Remaining", "Oxidizer Remaining"});
        AnimatedLineChart fuelFlowCharts = AnimatedLineChart.createChart(10, 1, 20,
                "Fuel and Oxidizer Flow Rates", "Time (s)",
                "Flow Rate (Gallons / s)", new String[]{"Fuel Flow Rate", "Oxidizer Flow Rate"});
        AnimatedLineChart thrustImpulseCharts = AnimatedLineChart.createChart(10, 1, 20,
                "Thrust and Impulse", "Time (s)",
                "", new String[]{"Thrust (lbs)", "Impulse (Lbs*s)"});

        // ================= MENU ================================
        MenuBar menuBar = new MenuBar();

        Menu menuFile = new Menu("File");
        setupMenuItems(menuFile);

        menuBar.getMenus().addAll(menuFile);

        // ================= TextArea STUFF ======================

//        TextArea javascriptTextBox = new TextArea();
//        javascriptTextBox.setWrapText(true);
//
//        // =================== BUTTONS ========================
//
//        ScriptEngineManager manager = new ScriptEngineManager();
//        ScriptEngine engine = manager.getEngineByName("js");
//
        Button start_btn = new Button("Add 1");
        start_btn.setOnAction(event -> temperatureCharts.addValue("Temp1", 8));
        Button stop_btn = new Button("Add 2");
        stop_btn.setOnAction(event -> temperatureCharts.addValue("Temp2", 5));

        // ================= GRID STUFF ======================

//        grid.add(javascriptTextBox, 0,0, 3, 4);
//        grid.add(start_btn, 1, 1, 1, 1);
//        grid.add(stop_btn, 0, 1, 1, 1);
        grid.add(thrustImpulseCharts, 0, 0, 1, 1);
        grid.add(fuelRemainingCharts, 2, 0, 1, 1);
        grid.add(temperatureCharts, 0, 1, 1, 1);
        grid.add(fuelFlowCharts, 2, 1, 1, 1);
        grid.add(menuBar, 0, 0, 1, 1);

        Scene scene = new Scene(grid, 1024, 800);

//        ObjectMapper mapper = new ObjectMapper();
//        Sensors sensors = mapper.readValue(
//                HttpInterface.executeGet(GET_ALL_SENSORS_URL, new HashMap<>()), Sensors.class);
//        System.out.println(sensors);

        primaryStage.setScene(scene);
        primaryStage.show();*/
    }

    private static void setGridpaneColsRowsSameSize(GridPane grid, int rows, int cols, boolean skipFirstRow) {
        int actual_num_rows = rows - (skipFirstRow ? 1 : 0);
        // Save 5% for the menu bar
        double row_height = (100.0 / actual_num_rows) - (skipFirstRow ? 5 : 0);
        double col_width = 100.0 / cols;


        RowConstraints[] row_constraints = new RowConstraints[rows];
        if (skipFirstRow) {
            RowConstraints constraint = new RowConstraints();
            constraint.setPercentHeight(5);
            row_constraints[0] = constraint;
        }
        for (int row = (skipFirstRow ? 1 : 0); row < rows; row++){
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

    private void setupMenuItems(Menu menu) {
        MenuItem manualValveControl = new MenuItem("Manual Valve Control");
        manualValveControl.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                // TODO: Update this with the correct resource name
                fxmlLoader.setLocation(getClass().getResource("ManualValveControl.fxml"));
                /*
                 * if "fx:controller" is not set in fxml
                 * fxmlLoader.setController(NewWindowController);
                 */
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                Stage stage = new Stage();
                stage.setTitle("Manual Valve Control");
                stage.setScene(scene);
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });

        MenuItem valveSetup = new MenuItem("Manual Valve Control");
        valveSetup.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                // TODO: Update this with the correct resource name
                fxmlLoader.setLocation(getClass().getResource("ValveSetup.fxml"));
                /*
                 * if "fx:controller" is not set in fxml
                 * fxmlLoader.setController(NewWindowController);
                 */
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                Stage stage = new Stage();
                stage.setTitle("Valve Setup");
                stage.setScene(scene);
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });

        MenuItem showClientLogs = new MenuItem("Manual Valve Control");
        showClientLogs.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                // TODO: Update this with the correct resource name
                fxmlLoader.setLocation(getClass().getResource("ClientLogs.fxml"));
                /*
                 * if "fx:controller" is not set in fxml
                 * fxmlLoader.setController(NewWindowController);
                 */
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                Stage stage = new Stage();
                stage.setTitle("Client-side Logs");
                stage.setScene(scene);
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });

        MenuItem showServerLogs = new MenuItem("Manual Valve Control");
        showServerLogs.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                // TODO: Update this with the correct resource name
                fxmlLoader.setLocation(getClass().getResource("ServerLogs.fxml"));
                /*
                 * if "fx:controller" is not set in fxml
                 * fxmlLoader.setController(NewWindowController);
                 */
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                Stage stage = new Stage();
                stage.setTitle("Server-side Logs");
                stage.setScene(scene);
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });
        menu.getItems().addAll(valveSetup, manualValveControl, showClientLogs, showServerLogs);
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
