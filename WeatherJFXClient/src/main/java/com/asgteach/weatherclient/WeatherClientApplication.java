// WeatherClientApplication.java - Main application
package com.asgteach.weatherclient;
import com.asgteach.weatherclient.domain.Weather;
import com.gluonhq.ignite.spring.SpringContext;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ComponentScan({
    "com.gluonhq.ignite.spring",
    "com.asgteach.weatherclient"
})
public class WeatherClientApplication extends Application 
        implements WeatherFXApp {

    public static void main(String[] args) {
        Application.launch(WeatherClientApplication.class, args);
    }
    
    @Autowired
    private FXMLLoader loader;

    private final SpringContext context = new SpringContext(this);
    private ConfigurableApplicationContext ctx;
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        context.init(() -> {
            return ctx = SpringApplication.run(WeatherClientApplication.class);
        });
        loader.setLocation(getViewLocation());
        Parent primaryView = loader.load();
        stage.setTitle("Spring Boot Reactive Web Service");
        stage.setScene(scene = new Scene(primaryView));
        stage.show();
    }
        
    @Override
    public void stop() {  
        Platform.exit();
        // Close this application context, 
        // destroys all beans in its bean factory 
        ctx.close();
    }

    static void setRoot(Parent view) {
        scene.setRoot(view);
    }
}

@Configuration
class SpringConfig  {
    @Bean
    public Weather getWeather() {
        return new Weather();
    }
}

