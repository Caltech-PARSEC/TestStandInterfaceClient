package edu.caltech.parsec.teststand;

import java.util.ArrayList;
import java.util.HashMap;

//
// @brief: A simple data wrapper class 
//
public class Valve {
    private String valveName;
    private double valveValue;

    public Valve(
        String valveName,
        double valveValue)
    {
        this.valveName = valveName;
        this.valveValue = valveValue;
    }

    public String getValveName() { return valveName; }

    public double getValveValue() { return valveValue; }

    public void setValveValue(double value) {
        this.valveValue = value;
    }

}
