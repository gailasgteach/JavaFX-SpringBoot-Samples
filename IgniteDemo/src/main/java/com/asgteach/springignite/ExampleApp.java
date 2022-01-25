// ExampleApp.java - ExampleApp interface
package com.asgteach.springignite;
import java.net.URL;

public interface ExampleApp {
    default URL getViewLocation() {
        return getClass().getResource("primary.fxml");
    }
}
