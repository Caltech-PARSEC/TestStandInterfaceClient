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
            JSONObject data = (JSONObject) args[0];

            String sensorName;
            double sensorValue;
            try {
                sensorName = data.getString("name");
                sensorValue = data.getDouble("value");
            } catch (JSONException e) {
                return;
            }

            Sensor sensor = getSensorByName(sensorName);
            if (sensor != null) {
                sensor.setSensorValue(sensorValue);
                ClientApp.getController().handleSensorData(sensor);
            }
        }).on("valve_data", args -> {
            JSONObject data = (JSONObject) args[0];

            String valveName;
            double valveAngle;
            try {
                valveName = data.getString("name");
                valveAngle = data.getDouble("angle");
            } catch (JSONException e) {
                return;
            }

            Valve valve = getValveByName(valveName);
            if (valve != null) {
                valve.setValveValue(valveAngle);
                ClientApp.getController().handleValveData(valve);
            }
        }).on("log_data", args -> {
            JSONObject data = (JSONObject) args[0];

            String message;
            try {
                message = data.getString("message");
            } catch (JSONException e) {
                return;
            }

            ClientApp.getController().handleLogData(message);
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
