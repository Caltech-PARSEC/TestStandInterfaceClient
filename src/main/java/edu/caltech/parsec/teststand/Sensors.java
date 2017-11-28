package edu.caltech.parsec.teststand;

/**
 * Created by Brendan on 11/26/2017.
 */
public class Sensors {
    public double thrustLbs, engineTempC, fuelFlowRateLitersPerSec, oxidizerFlowRateLitersPerSec;


    public double getThrustLbs() {
        return thrustLbs;
    }

    public void setThrustLbs(double thrustLbs) {
        this.thrustLbs = thrustLbs;
    }

    public double getEngineTempC() {
        return engineTempC;
    }

    public void setEngineTempC(double engineTempC) {
        this.engineTempC = engineTempC;
    }

    public double getFuelFlowRateLitersPerSec() {
        return fuelFlowRateLitersPerSec;
    }

    public void setFuelFlowRateLitersPerSec(double fuelFlowRateLitersPerSec) {
        this.fuelFlowRateLitersPerSec = fuelFlowRateLitersPerSec;
    }

    public double getOxidizerFlowRateLitersPerSec() {
        return oxidizerFlowRateLitersPerSec;
    }

    public void setOxidizerFlowRateLitersPerSec(double oxidizerFlowRateLitersPerSec) {
        this.oxidizerFlowRateLitersPerSec = oxidizerFlowRateLitersPerSec;
    }

    @Override
    public String toString() {
        return "Sensors{" +
                "thrustLbs=" + thrustLbs +
                ", engineTempC=" + engineTempC +
                ", fuelFlowRateLitersPerSec=" + fuelFlowRateLitersPerSec +
                ", oxidizerFlowRateLitersPerSec=" + oxidizerFlowRateLitersPerSec +
                '}';
    }

}
