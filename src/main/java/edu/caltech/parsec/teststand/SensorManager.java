package edu.caltech.parsec.teststand;

import java.util.HashMap;

// Stores a mapping of string sensor name -> Sensor
public class SensorManager {
    public static HashMap<String, Sensor> sensorMap;

    public static void init() {
        sensorMap = new HashMap<>();
        sensorMap.put("sensor1", new Sensor("sensor1", "igniterTempChart", "series1", 0));
    }
}
