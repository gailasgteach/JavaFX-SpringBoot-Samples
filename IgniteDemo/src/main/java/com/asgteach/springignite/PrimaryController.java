// PrimaryController.java - JavaFX primary controller
package com.asgteach.springignite;
import com.asgteach.springignite.service.WeatherService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrimaryController implements Initializable {

    @FXML
    private Label weatherLabel; 
    
    @Autowired 
    private WeatherService weatherService;
    
    @Autowired
    private FXMLLoader fxmlLoader;
    
    @FXML
    public void loadWeatherForecast(ActionEvent actionEvent) {
        System.out.println("Load Weather Forecast Action");
        this.weatherLabel.setText(weatherService.getWeatherForecast());
    }
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("PrimaryController initialized!");
    }  

    @FXML
    private void switchToSecondary(ActionEvent event) throws IOException {
        System.out.println("Switch to Secondary Action");
        fxmlLoader.setLocation(getClass().getResource("secondary.fxml"));
        SpringBootIgniteApp.setRoot(fxmlLoader.load());
    }
}
