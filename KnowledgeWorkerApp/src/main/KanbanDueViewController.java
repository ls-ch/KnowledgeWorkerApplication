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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Ellis
 */
public class KanbanDueViewController {

    @FXML
    private AnchorPane main;
    @FXML
    private Button btnKanban;
    @FXML
    private Button btnDeepFocus;
    @FXML
    private Button btnActivityBreakdown;
    @FXML
    private Button btnEntries;
    @FXML
    private RadioButton btnDoDate;
    @FXML
    private RadioButton btnDueDate;
    @FXML
    private TableView<?> CompletedTable;
    @FXML
    private TableColumn<?, ?> taskCompletedTodayColumn;
    @FXML
    private TableColumn<?, ?> taskCompletedTodayColumn1;
    @FXML
    private TableColumn<?, ?> taskCompletedTodayColumn2;
    @FXML
    private TableColumn<?, ?> taskCompletedTodayColumn3;
    @FXML
    private Button addTaskBtn;
    @FXML
    private Button btnLogout;
    @FXML
    private Button btnSettings;
    @FXML
    private ImageView myImage;

    /**
     * Initializes the controller class.
     */
    PageSwitcher pageSwitcher = new PageSwitcher();
    Image image = new Image("Kando Logo.png");
    Database database = new Database();

    public void initialize() throws SQLException {
        System.out.println("calling init");
        myImage.setImage(image);

        //taskCompletedTodayColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("task"));
        //dueCompletedColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("dueDate"));
        ObservableList<Task> entries = getTaskData();
        //CompletedTable.setItems(entries);
    }

    @FXML
    private void handleEntriesButton(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "Entries.fxml");
    }

    @FXML
    private void handleLogoutButtonAction(ActionEvent event)
            throws IOException {
        pageSwitcher.switcher(event, "LoginScreen.fxml");
    }

    @FXML
    private void handleSettingsButton(ActionEvent event) throws IOException {
        //pageSwitcher.switcher(event, "Settings.fxml");
    }

    @FXML
    private void handleDeepFocusButton(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "DeepFocusMenu.fxml");
    }

    @FXML
    private void handleActivityButton(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "ActivityBreakdown.fxml");
    }

    @FXML
    private void handleAddTaskButton(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "AddNewTask.fxml");
    }

    @FXML
    private void handleDoDateButton(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "Kanban.fxml");
    }

    @FXML
    private void handleDueDateButton(ActionEvent event) throws IOException {

    }

    private ObservableList<Task> getTaskData() {
        List<Task> Entries = new ArrayList<>();
        try {
            ResultSet rs = database.getResultSet("SELECT * FROM TASK");
            while (rs.next()) {
                Entries.add(
                        new Task(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6))
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println(Entries);
        return FXCollections.observableArrayList(Entries);
    }

    @FXML
    private void handleDeleteButton(ActionEvent event) 
            throws SQLException, IOException {
        ResultSet rs = database.getResultSet("SELECT * FROM TASK");
        String deleteTask = "DELETE FROM TASK WHERE ID = " + rs.getInt(1) + ";";
        database.statementInsert(deleteTask);
        pageSwitcher.switcher(event, "Kanban.fxml");
    }
}
