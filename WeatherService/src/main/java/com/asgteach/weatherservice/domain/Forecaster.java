// Forecaster.java - Weather forecaster
package com.asgteach.weatherservice.domain;

public class Forecaster {
    private static final String[] forecasts = {
       "Hot and sunny. Bring your sunscreen.",
       "It's gonna snow. Look out, winter is coming.",
       "Foggy and cold. Time for an espresso.",
       "Rain is on the way. Don't forget your umbrella.",
       "Breezy with a few fluffy clouds. Let's go sailing."           
    };
    
    public static String getForecast() {
       int index = (int)(Math.random() * forecasts.length);
       return forecasts[index];
    }  
}
