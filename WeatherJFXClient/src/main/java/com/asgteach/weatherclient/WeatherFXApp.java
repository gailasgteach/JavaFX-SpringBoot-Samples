// WeatherFXApp.java - WeatherFXApp interface
package com.asgteach.weatherclient;
import java.net.URL;

public interface WeatherFXApp {
    default URL getViewLocation() {
        return getClass().getResource("primary.fxml");
    }
}
