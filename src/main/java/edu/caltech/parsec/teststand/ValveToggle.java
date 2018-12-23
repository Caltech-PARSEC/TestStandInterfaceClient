package edu.caltech.parsec.teststand;

import javafx.event.ActionEvent;
import org.controlsfx.control.ToggleSwitch;

public class ValveToggle extends ToggleSwitch {
    @Override public void fire() {
        if (!isDisabled()) {
            setSelected(!isSelected());
            fireEvent(new ActionEvent());
        }
    }

}
