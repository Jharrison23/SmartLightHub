package com.example.seniordesign.smartlighthub;

import android.media.Image;
import android.widget.ImageView;

import com.example.seniordesign.smartlighthub.models.Light;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamesharrison on 5/26/17.
 */

public class LightRecyclerData {

    private static final String[] lightNumbers = {"Light 1", "Light 2", "Light 3"};

    private static final String[] lightColors = {"#FFFFFF", "#EEEEEE", "#BBBBBB"};

    private static final boolean[] lightStates = {true, false, true};


    public static List<Light> getListData()
    {
        List<Light> data = new ArrayList<>();

        for (int i = 0; i < lightNumbers.length; i++)
        {
            Light light = new Light();

            light.setLightNumber(lightNumbers[i]);

            light.setLightColor(lightColors[i]);

            light.setLightState(lightStates[i]);

            data.add(light);

        }


        return data;
    }
}
