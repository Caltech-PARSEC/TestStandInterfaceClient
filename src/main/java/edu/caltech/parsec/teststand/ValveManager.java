package edu.caltech.parsec.teststand;

import java.util.HashMap;

// Stores a mapping of string sensor name -> Sensor
public class ValveManager {
    public static HashMap<String, Valve> valveMap;

    public static void init() {
        valveMap = new HashMap<>();
        valveMap.put("valve_1", new Valve("I am valve1",0));
    }
}
