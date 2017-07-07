package com.example.seniordesign.smartlighthub.Model;

import java.util.List;

/**
 * Created by jamesharrison on 7/6/17.
 */

public class Routine {

    private String Name;
    private String routineTime;
    private List<Boolean> Days;
    private List<Light> lightsLight;

    public Routine(String Name, String routineTime, List<Boolean> Days, List<Light> lightsLights) {

        this.Name = Name;
        this.routineTime = routineTime;
        this.Days = Days;
        this.lightsLight = lightsLights;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRoutineTime() {
        return routineTime;
    }

    public void setRoutineTime(String routineTime) {
        this.routineTime = routineTime;
    }

    public List<Boolean> getDays() {
        return Days;
    }

    public void setDays(List<Boolean> days) {
        Days = days;
    }

    public List<Light> getLightsLight() {
        return lightsLight;
    }

    public void setLightsLight(List<Light> lightsLight) {
        this.lightsLight = lightsLight;
    }
}
