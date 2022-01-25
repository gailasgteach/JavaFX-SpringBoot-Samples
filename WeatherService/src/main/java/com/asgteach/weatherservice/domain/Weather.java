// Weather.java - Weather domain class
package com.asgteach.weatherservice.domain;

public class Weather {
    private String forecast;

    public Weather() { }

    public Weather(String forecast) {
        this.forecast = forecast;
    }

    public String getForecast() {
        return forecast;
    }

    public void setForecast(String forecast) {
        this.forecast = forecast;
    }

    @Override
    public String toString() {
        return "< " + forecast + " >";
    } 
}
