package edu.caltech.parsec.teststand;

import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

public class ServerClientInterface {
    public static final String SOCKET_URI = "http://localhost:5000/";

    private static Socket SOCKET;

    public static void init() {
        try {
            SOCKET = IO.socket(SOCKET_URI);
        } catch (URISyntaxException e) {
            // NOTE: This should not happen.
            System.err.println("Error initializing socket.");
            return;
        }

        SOCKET.on(Socket.EVENT_CONNECT, args -> {
            System.out.println("Connected to server.");
        }).on("sensor_data", args -> {

            String sensorName;
            double sensorValue;
            double time;
            try {
                JSONObject data = (JSONObject) args[0];
                sensorName = data.getString("name");
                sensorValue = data.getDouble("value");
                time = data.getDouble("time");
            } catch (Exception e) {
                System.out.println("error: " + e.getCause());
                System.out.println("message causing error: " + args[0]);
                return;
            }

            Sensor sensor = getSensorByName(sensorName);
            if (sensor != null) {
                sensor.setSensorValue(sensorValue);
                ClientApp.getController().handleSensorData(sensor, time);
            }
        }).on("valve_data", args -> {

            String valveName;
            double valveAngle;
            double time;
            try {
                JSONObject data = (JSONObject) args[0];
                valveName = data.getString("name");
                valveAngle = data.getDouble("angle");
                time = data.getDouble("time");
            } catch (Exception e) {
                System.out.println("error: " + e.getCause());
                System.out.println("message causing error: " + args[0]);
                return;
            }

            Valve valve = getValveByName(valveName);
            if (valve != null) {
                valve.setValveValue(valveAngle);
                ClientApp.getController().handleValveData(valve);
            }
        }).on("log_data", args -> {
            try {
                JSONObject data = (JSONObject) args[0];

                String message;
                message = data.getString("message");
                ClientApp.getController().handleLogData(message);
            } catch (Exception e) {
                System.out.println("error: " + e.getCause());
                System.out.println("message causing error: " + args[0]);
                return;
            }
        }).on(Socket.EVENT_DISCONNECT, args -> {
            System.out.println("Disconnected from server.");
            SOCKET.disconnect();
        });

        SOCKET.connect();
    }

    public static void sendSetSensor(String sensorName, float sensorValue) {
        JSONObject data = new JSONObject();

        try {
            data.put("name", sensorName);
            data.put("value", sensorValue);
        } catch (JSONException e) {
            System.out.println("Unable to write JSON data.");
        }

        SOCKET.emit("set_sensor", data);
    }

    public static void sendSetValve(String valveName, float valveAngle) {
        JSONObject data = new JSONObject();

        try {
            data.put("name", valveName);
            data.put("angle", valveAngle);
        } catch (JSONException e) {
            System.out.println("Unable to write JSON data.");
        }

        SOCKET.emit("set_valve", data);
    }

    private static Sensor getSensorByName(String sensorName) {
        return SensorManager.sensorMap.get(sensorName);
    }

    private static Valve getValveByName(String valveName) {
        return ValveManager.valveMap.get(valveName);
    }
}
