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
            System.out.println("changing valve: " +
                    ValveManager.valveMap.get(valve_1.getId()).getValveName());
            ServerClientInterface.sendSetValve(ValveManager.
                                                    valveMap.get(valve_1.getId())
                                                    .getValveName(),
                                               new_value);
        });
    }
}
