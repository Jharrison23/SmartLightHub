package com.example.seniordesign.smartlighthub.Model;

import java.util.List;

/**
 * Created by jamesharrison on 7/6/17.
 */

public class Routine {

    private String Name;
    private String routineHour;
    private String routineMinute;
    private List<Boolean> Days;
    private List<Light> lightsLight;

    public Routine(String Name, String routineHour, String routineMinute, List<Boolean> Days, List<Light> lightsLights) {

        this.Name = Name;
        this.routineHour = routineHour;
        this.routineMinute = routineMinute;
        this.Days = Days;
        this.lightsLight = lightsLights;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRoutineHour() {
        return routineHour;
    }

    public void setRoutineHour(String routineHour) {
        this.routineHour = routineHour;
    }

    public String getRoutineMinute() {
        return routineMinute;
    }

    public void setRoutineMinute(String routineMinute) {
        this.routineMinute = routineMinute;
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
