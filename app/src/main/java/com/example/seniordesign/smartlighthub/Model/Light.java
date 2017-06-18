package com.example.seniordesign.smartlighthub.Model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public int getConvertedColor()
    {

        int newColor = -1;

        String regex = "(\\d+),\\s(\\d+),\\s(\\d+)";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(Color);

        if (matcher.find())
        {
            newColor = android.graphics.Color.rgb(Integer.valueOf(matcher.group(1)), Integer.valueOf(matcher.group(2)), Integer.valueOf(matcher.group(3)));
            return newColor;
        }
        return newColor;
    }

    public int getRed()
    {

        int red = -1;

        String regex = "(\\d+),\\s(\\d+),\\s(\\d+)";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(Color);

        if (matcher.find())
        {
            red = Integer.valueOf(matcher.group(1));

        }

        return red;


    }

    public int getGreen()
    {
        int green = -1;

        String regex = "(\\d+),\\s(\\d+),\\s(\\d+)";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(Color);

        if (matcher.find())
        {
            green = Integer.valueOf(matcher.group(2));

        }

        return green;
    }

    public int getBlue()
    {
        int blue = -1;

        String regex = "(\\d+),\\s(\\d+),\\s(\\d+)";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(Color);

        if (matcher.find())
        {
            blue = Integer.valueOf(matcher.group(3));

        }

        return blue;
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
