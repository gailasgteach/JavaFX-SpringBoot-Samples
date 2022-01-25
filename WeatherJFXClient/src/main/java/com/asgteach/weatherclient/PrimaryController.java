// PrimaryController.java - JavaFX primary controller
package com.asgteach.weatherclient;
import com.asgteach.weatherclient.domain.Weather;
import com.asgteach.weatherclient.service.WeatherClient;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;

@Component
public class PrimaryController implements Initializable {
    private static final Logger logger = 
        LoggerFactory.getLogger(PrimaryController.class);

    @FXML
    private Label weatherLabel;

    @Autowired
    private WeatherClient weatherService;

    @Autowired
    private FXMLLoader fxmlLoader;

    @Autowired
    private Weather myweather;

    private Disposable subscription;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("PrimaryController initialized!");
        logger.info("Load Weather Forecast Action");
        subscription = weatherService.getWeatherForecast()
            .subscribe(new Consumer<Weather>() {
                @Override
                public void accept(Weather w) {
                    Platform.runLater(() -> {
                        weatherLabel.setText(w.getForecast());
                    });
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable ex) {
                    logger.error(ex.getLocalizedMessage());
                }
            });
    }

    @FXML
    private void switchToSecondary(ActionEvent event) throws IOException {
        logger.info("Switch to Secondary Action");
        myweather.setForecast(weatherLabel.getText());
        subscription.dispose(); //close the connection
        fxmlLoader.setLocation(getClass().getResource("secondary.fxml"));
        WeatherClientApplication.setRoot(fxmlLoader.load());
    }
}
