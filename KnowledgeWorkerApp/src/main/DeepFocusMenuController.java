/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.sql.PreparedStatement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import static main.Database.conn;
import static main.Database.createDeepFocusTaskTable;
import static main.Database.statementInsert;
import static main.LoginScreenController.userID;

/**
 * FXML Controller class
 *
 * @author asian
 */
public class DeepFocusMenuController {

    Image image = new Image("Kando Logo.png");
    @FXML
    private Button viewTaskBtn;
    @FXML
    private AnchorPane main;
    @FXML
    private Button btnKanban;
    @FXML
    private Button btnDeepFocus;
    @FXML
    private Button btnLogout;
    @FXML
    private Button btnSettings;
    @FXML
    private ImageView myImage;
    @FXML
    private TableColumn<Task, String> taskNameColumn;
    @FXML
    private TableColumn<Task, String> priorityColumn;
    @FXML
    private TableColumn<Task, String> doDateColumn;
    @FXML
    private TableColumn<Task, String> dueDateColumn;
    @FXML
    private TableView<Task> deepFocusTable;
    @FXML
    private Button btnEntries;
    @FXML
    private Button btnActivityBreakdown;
    @FXML
    private Label lblUnselectedError;
    /**
     * Initializes the controller class.
     */

    PageSwitcher pageSwitcher = new PageSwitcher();
    Database database = new Database();

    public void initialize() throws SQLException {
        System.out.println("calling init");
        lblUnselectedError.setVisible(false);
        myImage.setImage(image);
        taskNameColumn.setCellValueFactory(
                new PropertyValueFactory<Task, String>("task")
        );
        priorityColumn.setCellValueFactory(
                new PropertyValueFactory<Task, String>("taskPriority")
        );
        doDateColumn.setCellValueFactory(
                new PropertyValueFactory<Task, String>("doDate")
        );
        dueDateColumn.setCellValueFactory(
                new PropertyValueFactory<Task, String>("dueDate")
        );
        ObservableList<Task> entries = getTaskData();
        deepFocusTable.setItems(entries);
    }

    private ObservableList<Task> getTaskData() {
        List<Task> Entries = new ArrayList<>();
        try {
            ResultSet rs = database.getResultSet("SELECT * FROM TASK WHERE USER = '"+userID+"' ORDER BY "
                    + "TASK_PRIORITY desc;");
            while (rs.next()) {
                Entries.add(
                        new Task(rs.getInt(1), rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6))
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println(Entries);
        return FXCollections.observableArrayList(Entries);
    }

    @FXML
    private void handleLogoutButtonAction(ActionEvent event)
            throws IOException {
        pageSwitcher.switcher(event, "LoginScreen.fxml");
    }

    @FXML
    private void handleAboutUsBtn(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "AboutUs.fxml");
    }

    @FXML
    private void handleKanbanButton(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "Kanban.fxml");
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
    private void handleEntriesButton(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "Entries.fxml");
    }

    @FXML
    private void handleViewButton(ActionEvent event)
            throws SQLException, IOException {
        Task select = deepFocusTable.getSelectionModel().getSelectedItem();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(
                    "INSERT INTO DEEPFOCUSTASK"
                    + "( NAME, DESCRIPTION) VALUES(?,?)"
            );
            preparedStatement.setString(1, select.getTask());
            preparedStatement.setString(2, select.getDescription());
            preparedStatement.executeUpdate();
            System.out.println(
                    "Selected values inserted into DEEPFOCUSTASK table"
            );
            pageSwitcher.switcher(event, "DeepFocusTask.fxml");
        } catch (Exception e) {
            System.out.println(
                    "Selected values failed to insert into DEEPFOCUSTASK table"
            );
            e.printStackTrace();
            lblUnselectedError.setVisible(true);
        }

    }

}
