package edu.caltech.parsec.teststand;

public class ServerClientInterface {

    // TODO: put in code here to handle messages

    private static Sensor getSensorByName(String sensorName) {
        return SensorManager.sensorMap.get(sensorName);
    }
}
