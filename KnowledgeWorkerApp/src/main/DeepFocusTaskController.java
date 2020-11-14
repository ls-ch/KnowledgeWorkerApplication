/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author asian
 */
public class DeepFocusTaskController implements Initializable {

    PageSwitcher pageSwitcher = new PageSwitcher();
    @FXML
    private Text lblTaskName;
    @FXML
    private Label lblTaskDescription;
    @FXML
    Label dateTime;
    @FXML
    private ComboBox<String> musicSelector;
    @FXML
    private Button btnBack;

    Database database = new Database();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    ObservableList<String> list = FXCollections.observableArrayList(
            "No Music", "Chill", "Peaceful", "Lounge", "Classical", "Nature"
    );

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        taskDisplay();
        initClock();
        musicSelector.setItems(list);
        musicSelector.setVisible(true);
    }

    private void taskDisplay() {
        try {
            ResultSet resultName = database.getResultSet(
                    "SELECT NAME from DEEPFOCUSTASK;"
            );
            lblTaskName.setText(resultName.getString("NAME"));
            ResultSet resultDescription = database.getResultSet(
                    "SELECT DESCRIPTION from DEEPFOCUSTASK;"
            );
            lblTaskDescription.setText(
                    resultDescription.getString("DESCRIPTION")
            );
        } catch (SQLException ex) {
            Logger.getLogger(
                    DeepFocusTaskController.class.getName()).log(
                    Level.SEVERE, null, ex
            );
        }
    }

    private void initClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter
                    = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss");
            dateTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    @FXML
    private void handleBackButton(ActionEvent event)
            throws IOException, SQLException {
        player.stop();
        String deleteTask = "DELETE FROM DEEPFOCUSTASK;";
        database.statementInsert(deleteTask);
        System.out.println("DEEPFOCUSTASK content deleted");
        pageSwitcher.switcher(event, "DeepFocusMenu.fxml");
    }

    Status status = Status.STOPPED;
    MediaPlayer player;

    public void playTrack(String fileName) {
        switch (status) {
            case STOPPED:
                Media song = new Media(
                        "file:///"
                        + System.getProperty("user.dir").replace('\\', '/')
                        + "/"
                        + fileName
                );
                System.out.println("Playing media");
                player = new MediaPlayer(song);
                player.play();
                status = Status.PLAYING;
                break;
            case PLAYING:
                player.stop();
                song = new Media(
                        "file:///"
                        + System.getProperty("user.dir").replace('\\', '/')
                        + "/"
                        + fileName
                );
                System.out.println("Changing song track");
                player = new MediaPlayer(song);
                player.play();
                status = Status.PLAYING;
                break;
        }
    }

    @FXML
    public void comboChanged(ActionEvent event) {
        String selected = musicSelector.getValue();

        if (selected.matches("No Music") && status == Status.PLAYING) {
            player.stop();
            status = Status.STOPPED;
        } else if (selected.matches("Chill")) {
            playTrack("bensound-dreams.mp3");
        } else if (selected.matches("Peaceful")) {
            playTrack("quiettime-davidfesliyan.mp3");
        } else if (selected.matches("Lounge")) {
            playTrack("thelounge-davidrenda.mp3");
        } else if (selected.matches("Classical")) {
            playTrack("rhapsodyNo2InGMinor-brahms.mp3");
        } else if (selected.matches("Nature")) {
            playTrack("healingwater-davidrenda.mp3");
        }
    }
}
