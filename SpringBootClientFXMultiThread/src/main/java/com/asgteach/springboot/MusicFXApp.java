// MusicFXApp.java - MusicFXApp interface
package com.asgteach.springboot;
import java.net.URL;

public interface MusicFXApp {
    default URL getViewLocation() {
        return getClass().getResource("view.fxml");
    }
}
