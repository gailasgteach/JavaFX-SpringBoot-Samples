// SecondaryController.java - JavaFX secondary controller
package com.asgteach.weatherclient;
import com.asgteach.weatherclient.domain.Weather;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SecondaryController implements Initializable {
    private static final Logger logger = 
        LoggerFactory.getLogger(SecondaryController.class);

    @FXML
    private Label weatherLabel;
    
    @FXML
    private Label titleLabel;
    
    @Autowired
    private FXMLLoader fxmlLoader;
    
    @Autowired
    private Weather myweather;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("SecondaryController initialized!");
        titleLabel.setText("Latest Forecast:");
        weatherLabel.setText(myweather.getForecast());
    }

    @FXML
    private void switchToPrimary(ActionEvent event) throws IOException {
        logger.info("Switch to Primary Action");
        fxmlLoader.setLocation(getClass().getResource("primary.fxml"));
        WeatherClientApplication.setRoot(fxmlLoader.load());
    }
}
