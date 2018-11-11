package edu.caltech.parsec.teststand;

import java.util.HashMap;

// Stores a mapping of string sensor name -> Sensor
public class ValveManager {
    public static HashMap<String, Valve> valveMap;

    public static void init() {
        valveMap = new HashMap<>();
        valveMap.put("valve1", new Valve("valve1",0));
    }
}
