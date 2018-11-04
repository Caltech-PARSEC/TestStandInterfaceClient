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

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ClientApp.fxml"));

        Scene scene = new Scene(root, 1600, 800);

        primaryStage.setTitle("PARSEC Test Stand Interface Client");
        primaryStage.setScene(scene);
        primaryStage.show();
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
