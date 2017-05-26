package com.example.seniordesign.smartlighthub.models;

/**
 * Created by jamesharrison on 5/26/17.
 */

public class Light {


    private String lightNumber;
    private String lightColor;
    private boolean lightState;

    public Light() {
    }

    public Light(String lightNumber, String lightColor, boolean lightState)
    {
        this.lightNumber = lightNumber;
        this.lightColor = lightColor;
        this.lightState = lightState;
    }

    public String getLightNumber() {
        return lightNumber;
    }

    public void setLightNumber(String lightNumber) {
        this.lightNumber = lightNumber;
    }

    public String getLightColor() {
        return lightColor;
    }

    public void setLightColor(String lightColor) {
        this.lightColor = lightColor;
    }

    public boolean getLightState() {
        return lightState;
    }

    public void setLightState(boolean lightState) {
        this.lightState = lightState;
    }

}
