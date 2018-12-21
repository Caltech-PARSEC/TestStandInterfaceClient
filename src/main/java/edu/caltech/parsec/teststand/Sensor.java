package edu.caltech.parsec.teststand;

import java.util.ArrayList;
import java.util.HashMap;

//
// @brief: A simple data wrapper class 
//
public class Sensor {
    private String sensorName;
    // A map from the names of the graphs this sensor's data needs to be
    // placed in to a list of the names of the data series on that graph
    // to add them to.
    private HashMap<String, ArrayList<String>> graphSeries;
    private double sensorValue;

    // Single graph/series convenience overload.
    public Sensor(
        String sensorName,
        String graphName,
        String seriesName,
        double sensorValue)
    {
        this.sensorName = sensorName;
        this.sensorValue = sensorValue;
        HashMap<String, ArrayList<String>> graphs = new HashMap<>();
        ArrayList<String> series = new ArrayList<>();
        series.add(seriesName);
        graphs.put(graphName, series);
        this.graphSeries = graphs;
    }

    public Sensor(
        String sensorName,
        HashMap<String, ArrayList<String>> graphSeries,
        double sensorValue)
    {
        this.sensorName = sensorName;
        this.graphSeries = graphSeries;
        this.sensorValue = sensorValue;
    }

    public String getSensorName() { return sensorName; }

    public double getSensorValue() { return sensorValue; }

    public void setSensorValue(double value) {
        this.sensorValue = value;
    }

    public HashMap<String, ArrayList<String>> getGraphSeries() 
    {
        return graphSeries;
    }

}
