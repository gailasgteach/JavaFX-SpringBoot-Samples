// SecondaryController.java - JavaFX secondary controller
package com.asgteach.springignite;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SecondaryController implements Initializable {

    @Autowired
    private FXMLLoader fxmlLoader;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("SecondaryController initialized!");
    }

    @FXML
    private void switchToPrimary(ActionEvent event) throws IOException {
        System.out.println("Switch to Primary Action");
        fxmlLoader.setLocation(getClass().getResource("primary.fxml"));
        SpringBootIgniteApp.setRoot(fxmlLoader.load());
    }

}
