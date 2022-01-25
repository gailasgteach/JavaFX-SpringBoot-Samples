// MusicClientApp.java - Main application
package com.asgteach.springboot;
import com.gluonhq.ignite.spring.SpringContext;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
    "com.gluonhq.ignite.spring",
    "com.asgteach.springboot"
})
public class MusicClientApp extends Application implements MusicFXApp {
    public static void main(String[] args) {
        Application.launch(MusicClientApp.class, args);
    }
    
    @Autowired
    private FXMLLoader loader;

    private final SpringContext context = new SpringContext(this);
    private ConfigurableApplicationContext ctx;

    @Override
    public void start(Stage stage) throws IOException {
        context.init(() -> {
            return ctx = SpringApplication.run(MusicClientApp.class);
        });
        loader.setLocation(getViewLocation());
        Parent primaryView = loader.load();
        stage.setTitle("Spring Boot Ignite Music with REST MultiThread");
        stage.setScene(new Scene(primaryView));
        stage.show();
    }
    
    @Override
    public void stop() {        
        // Close this application context, 
        // destroys all beans in its bean factory 
        ctx.close();
    }
}
