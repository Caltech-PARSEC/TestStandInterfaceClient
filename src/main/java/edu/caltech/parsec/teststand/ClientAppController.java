package edu.caltech.parsec.teststand;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import edu.caltech.parsec.teststand.AnimatedLineChart;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientAppController {
//    @FXML
//    private MenuBar menuBar;
    @FXML
    private AnimatedLineChart igniterTempChart;
    @FXML
    private AnimatedLineChart fuelTankTempChart;
    @FXML
    private AnimatedLineChart randomTemp1Chart;
    @FXML
    private AnimatedLineChart engineTempsChart;
    @FXML
    private AnimatedLineChart oxidizerTankTempChart;
    @FXML
    private AnimatedLineChart randomTemp2Chart;
    @FXML
    private AnimatedLineChart fuelTankPressureChart;
    @FXML
    private AnimatedLineChart fuelAuxillaryPressureChart;
    @FXML
    private AnimatedLineChart combustionChamberPressureChart;
    @FXML
    private AnimatedLineChart oxidizerTankPressureChart;
    @FXML
    private AnimatedLineChart oxAuxillaryPressureChart;
    @FXML
    private AnimatedLineChart internalPipePressuresChart;
    @FXML
    private AnimatedLineChart forceChart;
    @FXML
    private AnimatedLineChart totalImpulseChart;
    @FXML
    private AnimatedLineChart remainingFuelOxChart;
    @FXML
    private AnimatedLineChart predictedApexChart;
    @FXML
    private AnimatedLineChart fuelOxefficiencyChart;
    @FXML
    private AnimatedLineChart externalPipePressuresChart;

    private HashMap<String, AnimatedLineChart> graphMap;

    public void initialize()
    {
        graphMap = new HashMap<>();
        igniterTempChart.addSeries("series1");

        // To autogenerate more of these lines, copy paste 
        // all of the variables here,
        // then run this in vim:
        // '<,'>g/FXML/norm! dd
        // '<,'>s/private AnimatedLineChart \([a-zA-Z0-9]\+\)/graphMap.put("\1",\r\t\t\t\1)
        // set et | retab
        graphMap.put("igniterTempChart",
            igniterTempChart);
        graphMap.put("fuelTankTempChart",
            fuelTankTempChart);
        graphMap.put("randomTemp1Chart",
            randomTemp1Chart);
        graphMap.put("engineTempsChart",
            engineTempsChart);
        graphMap.put("oxidizerTankTempChart",
            oxidizerTankTempChart);
        graphMap.put("randomTemp2Chart",
            randomTemp2Chart);
        graphMap.put("fuelTankPressureChart",
            fuelTankPressureChart);
        graphMap.put("fuelAuxillaryPressureChart",
            fuelAuxillaryPressureChart);
        graphMap.put("combustionChamberPressureChart",
            combustionChamberPressureChart);
        graphMap.put("oxidizerTankPressureChart",
            oxidizerTankPressureChart);
        graphMap.put("oxAuxillaryPressureChart",
            oxAuxillaryPressureChart);
        graphMap.put("internalPipePressuresChart",
            internalPipePressuresChart);
        graphMap.put("forceChart",
            forceChart);
        graphMap.put("totalImpulseChart",
            totalImpulseChart);
        graphMap.put("remainingFuelOxChart",
            remainingFuelOxChart);
        graphMap.put("predictedApexChart",
            predictedApexChart);
        graphMap.put("fuelOxefficiencyChart",
            fuelOxefficiencyChart);
        graphMap.put("externalPipePressuresChart",
            externalPipePressuresChart);

    }

    @FXML
    public void handleLaunchValveController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ValveScreen.fxml"));
            /*
             * if "fx:controller" is not set in fxml
             * fxmlLoader.setController(NewWindowController);
             */
            Scene scene = new Scene(loader.load(), 800, 600);
            Stage stage = new Stage();
            stage.setTitle("Manual Valve Control");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Event Listener on MenuItem.onAction
    @FXML
    public void handleCloseClicked(ActionEvent event) {
        // TODO Autogenerated
    }

    public void handleSensorData(Sensor sensor)
    {
        HashMap<String, ArrayList<String>> graphSeries =  sensor.getGraphSeries();
        // In all the graphs containing this sensor...
        for (String graphName : graphSeries.keySet())
        {
            // For each of the series on the graph that need this sensor...
            for (String seriesName : graphSeries.get(graphName))
            {
                // Add the new sensor value
                graphMap.get(graphName)
                        .addValue(seriesName, sensor.getSensorValue());
            }
        }
    }

    public void handleValveData(Valve valve) { /* TODO: Implement */ }

    public void handleLogData(String message) {
        /* TODO: Implement proper logging here. */
        System.out.println("Message: " + message);
    }
}
