package com.example.seniordesign.smartlighthub.models;

/**
 * Created by jamesharrison on 5/26/17.
 */

public class Light {


    private String Name;
    private String Color;
    private boolean State;

    public Light() {
    }

    public Light(String name, String color, boolean state) {
        Name = name;
        Color = color;
        State = state;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public boolean isState() {
        return State;
    }

    public void setState(boolean state) {
        State = state;
    }
}
