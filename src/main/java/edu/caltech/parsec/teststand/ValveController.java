package edu.caltech.parsec.teststand;

import javafx.beans.value.ObservableBooleanValue;
import javafx.fxml.FXML;
import org.controlsfx.control.ToggleSwitch;

public class ValveController {
    @FXML
    private ToggleSwitch valve_1;
    // Precondition: the valve's fx_id is the same as its name in the ValveManager.

    public void initialize() {
        valve_1.selectedProperty().addListener((toggle, old_value, new_value) -> {
            System.out.println("hello");
            if (new_value) {
                System.out.println("Turning on: " +
                        ValveManager.valveMap.get(valve_1.getId()).getValveName());
            } else {
                System.out.println("Turning off: " +
                        ValveManager.valveMap.get(valve_1.getId()).getValveName());
            }});
    }
}
