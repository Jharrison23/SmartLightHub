package com.example.seniordesign.smartlighthub.Model;

import java.util.List;

/**
 * Created by jamesharrison on 6/25/17.
 */

public class Preset {


    private String Name;
    private List<Light> lightList;

    public Preset()
    {

    }

    public Preset(String Name, List<Light> lightList)
    {
        this.Name = Name;

        this.lightList = lightList;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<Light> getLightList() {
        return lightList;
    }

    public void setLightList(List<Light> lightList) {
        this.lightList = lightList;
    }
}
