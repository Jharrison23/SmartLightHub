package com.example.seniordesign.smartlighthub.models;

/**
 * Created by jamesharrison on 5/26/17.
 */

public class Light {


    private String lightName;
    private String lightColor;
    private boolean lightState;

    public Light() {
    }

    public Light(String lightName, String lightColor, boolean lightState)
    {
        this.lightName = lightName;
        this.lightColor = lightColor;
        this.lightState = lightState;
    }

    public String getlightName() {
        return lightName;
    }

    public void setlightName(String lightName) {
        this.lightName = lightName;
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
