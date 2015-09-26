package com.weather.cherkassy;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 9/26/2015.
 */
public class WeatherData {

    private static final WeatherData sInstance = new WeatherData();

    public String weather;
    public String wind;
    public String pressure;
    public String country;
    public Bitmap icon;

    public static WeatherData getInstance() {
        return sInstance;
    }

    private WeatherData() {
    }
}