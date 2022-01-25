// ViewController.java - Controller for view
package com.asgteach.springboot;
import com.asgteach.springboot.domain.MusicCategory;
import com.asgteach.springboot.service.MusicRESTService;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
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
    private final int SLEEPCOUNT = 2500;
    private static final Logger log =
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
    @FXML
    private ProgressIndicator progressIndicator;

    private final BooleanProperty modifiedProperty = 
        new SimpleBooleanProperty(false);
    private MusicCategory selectedCategory;
    private final IntegerProperty backgroundActive = 
        new SimpleIntegerProperty(0);
    private final ObservableList<MusicCategory> musicCategoryData =
        FXCollections.observableArrayList();

    @FXML
    private void createButtonAction(ActionEvent actionEvent) {
        log.info("Create");
        backgroundActive.set(backgroundActive.get() + 1);
        String category = categoryTextField.getText();
        CreateTask task = new CreateTask(category);
        task.setOnSucceeded(t -> {
            musicCategoryData.setAll(
                FXCollections.observableList(task.getValue()));
            updateResult.setText("New category " + category + " created.");
            categoryTextField.setText("");
            backgroundActive.set(backgroundActive.get() - 1);
        });
        task.setOnFailed(t -> {
            backgroundActive.set(backgroundActive.get() - 1);
            updateResult.setText("Oops! " +
                task.getException().getLocalizedMessage());
        });
        new Thread(task).start();
    }

    @FXML
    private void removeButtonAction(ActionEvent actionEvent) {
        log.info("Remove " + selectedCategory);
        MusicCategory musicCategory = selectedCategory;
        RemoveTask task = new RemoveTask(musicCategory);
        backgroundActive.set(backgroundActive.get() + 1);
        task.setOnSucceeded(t -> {
            musicCategoryData.setAll(
                FXCollections.observableList(task.getValue()));
            updateResult.setText("Category " + 
                musicCategory.getMusicCategory() + " deleted.");
            categoryTextField.setText("");
            backgroundActive.set(backgroundActive.get() - 1);
        });
        task.setOnFailed(t -> {
            backgroundActive.set(backgroundActive.get() - 1);
            updateResult.setText("Unable to remove category " +
                musicCategory.getMusicCategory() + ".\n" +
                    "Foregin key constraint.");
        });
        new Thread(task).start();
    }

    @FXML
    private void updateButtonAction(ActionEvent actionEvent) {
        log.info("Update " + selectedCategory);
        MusicCategory musicCategory = 
            tableView.getSelectionModel().getSelectedItem();
        musicCategory.setMusicCategory(categoryTextField.getText());
        UpdateTask task = new UpdateTask(musicCategory);
        backgroundActive.set(backgroundActive.get() + 1);
        task.setOnSucceeded(t -> {
            musicCategoryData.setAll(
                FXCollections.observableList(task.getValue()));
            updateResult.setText("Category " + 
                musicCategory.getMusicCategory() + " updated.");
            categoryTextField.setText("");
            backgroundActive.set(backgroundActive.get() - 1);
            modifiedProperty.set(false);
        });
        task.setOnFailed(t -> {
            backgroundActive.set(backgroundActive.get() - 1);
            updateResult.setText("Oops! " +
                task.getException().getLocalizedMessage());
        });
        new Thread(task).start();    
    }

    @FXML
    private void handleKeyAction(KeyEvent keyEvent) {
        modifiedProperty.set(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Disable  Remove/Edit buttons if nothing is selected in ListView
        removeButton.disableProperty().bind(
            tableView.getSelectionModel().selectedItemProperty().isNull());
        updateButton.disableProperty().bind(
            tableView.getSelectionModel().selectedItemProperty().isNull()
                .or(modifiedProperty.not())
                    .or(categoryTextField.textProperty().isEmpty()));
        createButton.disableProperty().bind(
            tableView.getSelectionModel().selectedItemProperty().isNotNull()
                .or(categoryTextField.textProperty().isEmpty()));
        progressIndicator.visibleProperty().bind(
                backgroundActive.greaterThan(0));

        categoryNameColumn.setCellValueFactory(
            new PropertyValueFactory<>("musicCategory"));
        idColumn.setCellValueFactory(
            new PropertyValueFactory<>("musicCategoryId"));
        musicCategoryData.setAll(
            FXCollections.observableList(musicService.getMusicCategories()));
        tableView.setItems(musicCategoryData);
        tableView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                // newValue can be null if nothing is selected
                selectedCategory = newValue;
                modifiedProperty.set(false);
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

    private class CreateTask extends Task<List<MusicCategory>> {
        private final String category;

        private CreateTask(String category) {
            this.category = category;
        }

        @Override
        protected List<MusicCategory> call() throws Exception {
            Thread.sleep(SLEEPCOUNT);
            musicService.createMusicCategory(category);
            return musicService.getMusicCategories();
        }
    }
    
    private class RemoveTask extends Task<List<MusicCategory>> {
        private final MusicCategory musicCategory;

        private RemoveTask(MusicCategory category) {
            this.musicCategory = category;
        }

        @Override
        protected List<MusicCategory> call() throws Exception {
            Thread.sleep(SLEEPCOUNT);
            musicService.deleteMusicCategory(musicCategory);
            return musicService.getMusicCategories();
        }
    }
    
    private class UpdateTask extends Task<List<MusicCategory>> {
        private final MusicCategory musicCategory;

        private UpdateTask(MusicCategory category) {
            this.musicCategory = category;
        }

        @Override
        protected List<MusicCategory> call() throws Exception {
            Thread.sleep(SLEEPCOUNT);
            musicService.updateMusicCategory(musicCategory);
            return musicService.getMusicCategories();
        }
    }
}
