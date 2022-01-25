// ViewController.java - Controller for view
package com.asgteach.springboot;
import com.asgteach.springboot.domain.MusicCategory;
import com.asgteach.springboot.service.MusicRESTService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ViewController implements Initializable {
    private static final Logger logger = 
        LoggerFactory.getLogger(ViewController.class);
    
    @Autowired
    private MusicRESTService musicService;

    @FXML
    private TableView<MusicCategory> tableView;
    @FXML
    private TableColumn<MusicCategory, Integer> idColumn;
    @FXML
    private TableColumn<MusicCategory, String> categoryNameColumn;
    @FXML
    private TextField categoryTextField;
    @FXML
    private Button removeButton;
    @FXML
    private Button createButton;
    @FXML
    private Button updateButton;
    @FXML
    private Label updateResult;

    private final BooleanProperty modifiedProperty = 
        new SimpleBooleanProperty(false);
    private MusicCategory selectedCategory;
    private final ObservableList<MusicCategory> musicCategoryData =
        FXCollections.observableArrayList();

    @FXML
    private void createButtonAction(ActionEvent actionEvent) {
        logger.info("Create");
        String category = categoryTextField.getText();
        try {
            musicService.createMusicCategory(category);
            musicCategoryData.setAll(musicService.getMusicCategories());
            // Select the first item
            tableView.getSelectionModel().clearSelection();
            updateResult.setText("New category " + category + " created.");
            categoryTextField.setText("");
        } catch (Exception de) {
            updateResult.setText("Oops! " + de.getLocalizedMessage());
        }
    }

    @FXML
    private void removeButtonAction(ActionEvent actionEvent) {
        logger.info("Remove " + selectedCategory);
        String category = selectedCategory.getMusicCategory();
        try {
            musicService.deleteMusicCategory(selectedCategory);
            musicCategoryData.setAll(musicService.getMusicCategories());
            // Clear selection
            tableView.getSelectionModel().clearSelection();
            updateResult.setText("Category " + category + " deleted.");
        } catch (Exception de) {
            updateResult.setText("Unable to remove category " + 
                category + ".\n" + "Foregin key constraint.");
        }
    }

    @FXML
    private void updateButtonAction(ActionEvent actionEvent) {
        logger.info("Update " + selectedCategory);
        MusicCategory musicCategory = 
            tableView.getSelectionModel().getSelectedItem();
        musicCategory.setMusicCategory(categoryTextField.getText());
        try {
            musicService.updateMusicCategory(musicCategory);
            musicCategoryData.setAll(musicService.getMusicCategories());
            // Clear selection
            tableView.getSelectionModel().clearSelection();
            updateResult.setText("Category " + 
                musicCategory.getMusicCategory() + " updated.");
        } catch (Exception de) {
            updateResult.setText("Oops! "  + de.getLocalizedMessage());
        }
        modifiedProperty.set(false);
    }

    @FXML
    private void handleKeyAction(KeyEvent keyEvent) {
        modifiedProperty.set(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Disable Remove/Edit buttons if nothing is selected in ListView
        removeButton.disableProperty().bind(
            tableView.getSelectionModel().selectedItemProperty().isNull());
        updateButton.disableProperty().bind(
            tableView.getSelectionModel().selectedItemProperty().isNull()
                .or(modifiedProperty.not())
                    .or(categoryTextField.textProperty().isEmpty()));
        createButton.disableProperty().bind(
            tableView.getSelectionModel().selectedItemProperty().isNotNull()
                .or(categoryTextField.textProperty().isEmpty()));

        categoryNameColumn.setCellValueFactory(
            new PropertyValueFactory<>("musicCategory"));
        idColumn.setCellValueFactory(
            new PropertyValueFactory<>("musicCategoryId"));
        musicCategoryData.setAll(musicService.getMusicCategories());                         
        tableView.setItems(musicCategoryData);

        tableView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                // newValue can be null if nothing is selected
                selectedCategory = newValue;
                modifiedProperty.set(false);
                updateResult.setText("");
                if (newValue != null) {
                    // Populate controls with selected Person
                    categoryTextField.setText(
                        selectedCategory.getMusicCategory());
                } else {
                    categoryTextField.setText("");
                }
            });

        // Clear selection
        tableView.getSelectionModel().clearSelection();
    }
}
