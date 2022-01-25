// WeatherService.java - Weather service
package com.asgteach.springignite.service;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {
   private final String[] forecasts = {
       "Hot and sunny. Bring your sunscreen.",
       "It's gonna snow. Look out, winter is coming.",
       "Foggy and cold. Time for an espresso.",
       "Rain is on the way. Don't forget your umbrella.",
       "Breezy with a few fluffy clouds. Let's go sailing."           
   };

   public String getWeatherForecast() {
       int index = (int)(Math.random() * forecasts.length);
       return forecasts[index];
   }
}
