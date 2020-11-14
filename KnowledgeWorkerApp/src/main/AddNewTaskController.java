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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import static main.Database.conn;
import static main.Database.openConnection;
import javafx.scene.control.DatePicker;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.function.UnaryOperator;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import static main.LoginScreenController.userID;

/**
 * FXML Controller class
 *
 * @author asian
 */
//FXML IS MISSING???
public class AddNewTaskController implements Initializable {

    @FXML
    private TextField taskNameTxt;
    @FXML
    private DatePicker doDateTxt;
    @FXML
    private DatePicker dueDateTxt;
    @FXML
    private TextField descTxt;
    @FXML
    private TextField taskPriorityTxt;
    @FXML
    private Label lblError;
    @FXML
    private Button btnAddTask;

    Database database = new Database();
    PageSwitcher pageSwitcher = new PageSwitcher();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblError.setVisible(false);
        UnaryOperator<Change> filter = change -> {
            String text = change.getText();

            if (text.matches("[0-9]*")) {
                return change;
            }

            return null;
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        taskPriorityTxt.setTextFormatter(textFormatter);
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "Kanban.fxml");
    }
    
    @FXML
    private void handleCheckUserInput(ActionEvent event) throws IOException {
        LocalDate doDate = doDateTxt.getValue();
        LocalDate dueDate = dueDateTxt.getValue();
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
        LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS);

        if (taskNameTxt == null
                || descTxt == null
                || doDateTxt.getValue() == null
                || dueDateTxt.getValue() == null
                || taskPriorityTxt == null) {
            lblError.setText("Input all required textfields");
            lblError.setVisible(true);
        } else if (Integer.parseInt(taskPriorityTxt.getText()) < 1
                || Integer.parseInt(taskPriorityTxt.getText()) > 100) {
            lblError.setText("Task priority has to be between 1 - 100");
            lblError.setVisible(true);
        } else if (doDate.isBefore(today) || dueDate.isBefore(today)) {
            lblError.setText(
                    "Do Date and Due Date for a task cannot be before current date"
            );
            lblError.setVisible(true);
        } else if (dueDate.isBefore(doDate)) {
            lblError.setText(
                    "Due Date cannot be before Do Date"
            );
            lblError.setVisible(true);
        } else {
            lblError.setVisible(false);
            try {
                insertTaskToTable(event, doDate, dueDate, tomorrow);
                System.out.println("Success inserting task to table");
            } catch (SQLException e) {
                lblError.setText("Error inserting task");
                lblError.setVisible(true);
                System.out.println("Error inserting task to table");
                e.printStackTrace();
            }
        }
    }

    private void insertTaskToTable(ActionEvent event,
            LocalDate doDate, LocalDate dueDate, LocalDate tomorrow
    )
            throws SQLException, IOException {
        openConnection();
        PreparedStatement insertUserInput = null;
        LocalDate nextWeek = tomorrow.plus(1, ChronoUnit.WEEKS);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy");
        String myDoDate = doDate.format(formatter);
        String myDueDate = dueDate.format(formatter);

        if (doDate.equals(tomorrow)) {
            insertUserInput = conn.prepareStatement(
                    "INSERT INTO TOMORROWTASK("
                    + "NAME, DESCRIPTION, DO_DATE, "
                    + "DUE_DATE, TASK_PRIORITY, USER)"
                    + "VALUES(?,?,?,?,?,?);"
            );
        } else if (doDate.isAfter(tomorrow) && doDate.isBefore(nextWeek)) {
            insertUserInput = conn.prepareStatement(
                    "INSERT INTO WEEKLYTASK("
                    + "NAME, DESCRIPTION, DO_DATE, "
                    + "DUE_DATE, TASK_PRIORITY, USER)"
                    + "VALUES(?,?,?,?,?,?);"
            );
        } else if (doDate.equals(LocalDate.now())) {
            insertUserInput = conn.prepareStatement(
                    "INSERT INTO TODAYTASK("
                    + "NAME, DESCRIPTION, DO_DATE, "
                    + "DUE_DATE, TASK_PRIORITY, USER)"
                    + "VALUES(?,?,?,?,?,?);"
            );
        }
        insertUserInput.setString(1, taskNameTxt.getText());
        insertUserInput.setString(2, descTxt.getText());
        insertUserInput.setString(3, myDoDate);
        insertUserInput.setString(4, myDueDate);
        insertUserInput.setString(5, taskPriorityTxt.getText());
        insertUserInput.setString(6, userID);
        insertUserInput.executeUpdate();

        insertUserInput = conn.prepareStatement(
                "INSERT INTO TASK("
                + "NAME, DESCRIPTION, DO_DATE, DUE_DATE, TASK_PRIORITY, USER)"
                + "VALUES(?,?,?,?,?,?);"
        );
        insertUserInput.setString(1, taskNameTxt.getText());
        insertUserInput.setString(2, descTxt.getText());
        insertUserInput.setString(3, myDoDate);
        insertUserInput.setString(4, myDueDate);
        insertUserInput.setString(5, taskPriorityTxt.getText());
        insertUserInput.setString(6, userID);
        insertUserInput.executeUpdate();

        pageSwitcher.switcher(event, "Kanban.fxml");
    }
}
