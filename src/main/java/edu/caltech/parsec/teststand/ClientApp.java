package edu.caltech.parsec.teststand;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientApp extends Application {

    private static ClientAppController controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ClientApp.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1600, 800);

        ServerClientInterface.init();
        SensorManager.init();
        ValveManager.init();
        controller = (ClientAppController) loader.getController();

        primaryStage.setTitle("PARSEC Test Stand Interface Client");
        primaryStage.setScene(scene);
        primaryStage.show();
        // Test code
        for(int i = 0; i < 100; i++) {
            handleSensorData(new Sensor("sensor1", "igniterTempChart", "series1", 100 - i));
        }
    }

    // TODO: Hook up this method to the data stream from the server
    public static void handleSensorData(Sensor sensor) {
        controller.handleSensorData(sensor);
    }

    private void setupMenuItems(Menu menu) {
        MenuItem manualValveControl = new MenuItem("Manual Valve Control");
        manualValveControl.setOnAction(event -> {
        });

        MenuItem valveSetup = new MenuItem("Manual Valve Control");
        valveSetup.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                // TODO: Update this with the correct resource name
                fxmlLoader.setLocation(getClass().getResource("ValveControl.fxml"));
                /*
                 * if "fx:controller" is not set in fxml
                 * fxmlLoader.setController(NewWindowController);
                 */
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                Stage stage = new Stage();
                stage.setTitle("Valve Control");
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

    public static ClientAppController getController() {
        return controller;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
