/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import static main.Database.conn;
import static main.Database.openConnection;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.function.UnaryOperator;
import javafx.scene.control.TextFormatter;
import static main.LoginScreenController.userID;

/**
 * FXML Controller class
 *
 * @author Ian_R
 */
public class AddNewEntryController implements Initializable {

    PageSwitcher pageSwitcher = new PageSwitcher();
    @FXML
    private TextField startTimeTxt;
    @FXML
    private TextField endTimeTxt;
    @FXML
    private TextArea descTxt;
    @FXML
    private RadioButton workRadiobtn;
    @FXML
    private ToggleGroup Category;
    @FXML
    private RadioButton studyRadiobtn;
    @FXML
    private RadioButton leisureRadiobtn;
    @FXML
    private RadioButton exerciseRadiobtn;
    @FXML
    private RadioButton travelRadiobtn;
    @FXML
    private Label lblErrorOutput;
    @FXML
    private DatePicker dateTodayTxt;
    @FXML
    private Label noEmpty;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) {
                return change;
            } else if (text.matches(":")) {
                return change;
            }
            return null;
        };

        TextFormatter<String> textFormatter1 = new TextFormatter<>(filter);
        startTimeTxt.setTextFormatter(textFormatter1);
        TextFormatter<String> textFormatter2 = new TextFormatter<>(filter);
        endTimeTxt.setTextFormatter(textFormatter2);
        lblErrorOutput.setVisible(false);
        noEmpty.setVisible(false);
    }

    @FXML
    private void insertEntriesToTable(ActionEvent event)
            throws SQLException, IOException, ParseException {
        Date current = new Date();
        LocalDate todayDate = dateTodayTxt.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy");
        String myDateToday = todayDate.format(formatter);
        String category = selectCategory();
        PreparedStatement insertUserInput = null;
        Time startTime = StringToTime(startTimeTxt.getText());
        Time endTime = StringToTime(endTimeTxt.getText());
        // IF ELSE STATEMENT FOR IF ALL FIELDS ARE VALID AND FILLED   
        // CHECK FOR VALID USER INPUT FIRST
        Duration d = calculateDuration(startTime, endTime);
        int hours = (int) d.toHours();
        int minutes = (int) d.toMinutes();
        String hhPlusMm = LocalTime.MIDNIGHT.plus(d).format(
                DateTimeFormatter.ofPattern("HH:mm")
        );

        if (todayDate.isAfter(LocalDate.now())) {

            lblErrorOutput.setVisible(true);
        } else if (descTxt == null || startTimeTxt == null || endTimeTxt == null
                || Category == null || dateTodayTxt == null) {
 
            noEmpty.setVisible(true);
        } else if (descTxt != null && startTimeTxt != null && endTimeTxt != null
                && Category != null && dateTodayTxt != null) {
            insertUserInput = conn.prepareStatement(
                    "INSERT INTO ENTRIES("
                    + "DESCRIPTION, CATEGORY, START_TIME, END_TIME, "
                    + "DURATION,"
                            + " DURATION_HOURS,"
                            + " DURATION_MINUTES,"
                            + " DATE_TODAY,"
                            + " USER)"
                    + "VALUES(?,?,?,?,?,?,?,?,?)"
            );

            insertUserInput.setString(1, descTxt.getText());
            insertUserInput.setString(2, category);
            insertUserInput.setTime(3, startTime);
            insertUserInput.setTime(4, endTime);
            insertUserInput.setString(5, hhPlusMm);
            insertUserInput.setInt(6, hours);
            insertUserInput.setInt(7, minutes);
            insertUserInput.setString(8, myDateToday);
            insertUserInput.setString(9, userID);
            insertUserInput.executeUpdate();

            pageSwitcher.switcher(event, "Entries.fxml");

        }

    }

    private Duration calculateDuration(Time startTime, Time endTime) {
        LocalTime localStartTime = startTime.toLocalTime();
        LocalTime localEndTime = endTime.toLocalTime();

        Duration duration = Duration.between(localStartTime, localEndTime);
        return duration;
    }

    @FXML
    private void handleBackButton(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "Entries.fxml");
    }

    private Time StringToTime(String convert) throws ParseException {
        String s = convert;
        SimpleDateFormat time = new SimpleDateFormat("k:mm");
        long ms = time.parse(s).getTime();
        Time t = new Time(ms);
        return t;
    }

    private String selectCategory() {
        String category = null;
        if (studyRadiobtn.isSelected()) {
            category = studyRadiobtn.getText();
        } else if (leisureRadiobtn.isSelected()) {
            category = leisureRadiobtn.getText();
        } else if (workRadiobtn.isSelected()) {
            category = workRadiobtn.getText();
        } else if (exerciseRadiobtn.isSelected()) {
            category = exerciseRadiobtn.getText();
        } else if (travelRadiobtn.isSelected()) {
            category = exerciseRadiobtn.getText();
        }
        return category;
    }

}
