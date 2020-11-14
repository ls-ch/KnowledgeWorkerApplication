/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.GridPane;
import java.sql.PreparedStatement;
import static main.Database.conn;
import static main.Database.statementInsert;
import static main.LoginScreenController.userID;

/**
 * FXML Controller class
 *
 * @author Ellis
 */
public class KanbanController {

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
    private TableView<CompletedTask> CompletedTable;
    @FXML
    private TableColumn<CompletedTask, String> taskCompletedTodayColumn;
    @FXML
    private Button addTaskBtn;
    @FXML
    private Button btnEntries;
    @FXML
    private Button btnActivityBreakdown;
    @FXML
    private TableView<Task> TodayTable;
    @FXML
    private TableColumn<Task, String> todayToDoListColumn;
    @FXML
    private TableView<TomorrowTask> TomorrowTable;
    @FXML
    private TableColumn<TomorrowTask, String> tomorrowToDoListColumn;
    @FXML
    private TableView<WeeklyTask> WeeklyTable;
    @FXML
    private TableColumn<WeeklyTask, String> weekly;

    PageSwitcher pageSwitcher = new PageSwitcher();
    Image image = new Image("Kando Logo.png");
    Database database = new Database();
    @FXML
    private RadioButton btnDoDate;
    @FXML
    private RadioButton btnDueDate;
    @FXML
    private GridPane TableViewGrid;

    boolean completed = false;
    boolean checker = completed;
    @FXML
    private Button markBtn;

    public void initialize() throws SQLException {
        System.out.println("calling init");
        myImage.setImage(image);

        todayToDoListColumn.setCellValueFactory(
                new PropertyValueFactory<Task, String>("task")
        );
        taskCompletedTodayColumn.setCellValueFactory(
                new PropertyValueFactory<CompletedTask, String>("task")
        );
        tomorrowToDoListColumn.setCellValueFactory(
                new PropertyValueFactory<TomorrowTask, String>("task")
        );
        weekly.setCellValueFactory(
                new PropertyValueFactory<WeeklyTask, String>("task")
        );

        ObservableList<CompletedTask> completed = getCompletedData();
        CompletedTable.setItems(completed);

        ObservableList<Task> today = getTaskData();
        TodayTable.setItems(today);

        ObservableList<TomorrowTask> tomorrow = getTomorrowTaskData();
        TomorrowTable.setItems(tomorrow);

        ObservableList<WeeklyTask> weekly = getWeeklyTaskData();
        WeeklyTable.setItems(weekly);

    }

    @FXML
    private void handleEntriesButton(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "Entries.fxml");
    }

    @FXML
    private void handleLogoutButtonAction(ActionEvent event)
            throws IOException, SQLException {
        pageSwitcher.switcher(event, "LoginScreen.fxml");
    }

    @FXML
    private void handleAboutUsBtn(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "AboutUs.fxml");
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
    private void handleDoDateButton(ActionEvent event) throws IOException {

    }

    @FXML
    private void handleDueDateButton(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "KanbanDueView.fxml");
    }

    @FXML
    private void handleAddTaskButton(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "AddNewTask.fxml");
    }

    private ObservableList<Task> getTaskData() {
        List<Task> Entries = new ArrayList<>();
        try {
            ResultSet rs = database.getResultSet("SELECT * FROM TASK WHERE USER = '"+ userID+ "';");
            while (rs.next()) {
                Entries.add(new Task(
                        rs.getInt(1),
                        rs.getString(2),
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

    private ObservableList<WeeklyTask> getWeeklyTaskData() {
        List<WeeklyTask> Entries = new ArrayList<>();
        try {
            ResultSet rs = database.getResultSet("SELECT * FROM WEEKLYTASK WHERE USER = '"+ userID+ "';");
            while (rs.next()) {
                Entries.add(new WeeklyTask(
                        rs.getInt(1),
                        rs.getString(2),
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

    private ObservableList<TomorrowTask> getTomorrowTaskData() {
        List<TomorrowTask> Entries = new ArrayList<>();
        try {
            ResultSet rs = database.getResultSet("SELECT * FROM TOMORROWTASK WHERE USER = '"+ userID+ "';");
            while (rs.next()) {
                Entries.add(new TomorrowTask(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println(Entries);
        return FXCollections.observableArrayList(Entries);
    }

    private ObservableList<CompletedTask> getCompletedData() {
        List<CompletedTask> completeTask = new ArrayList<>();
        try {
            ResultSet rs = database.getResultSet("SELECT * FROM COMPLETEDTASK WHERE USER = '"+ userID+ "';");
            while (rs.next()) {
                completeTask.add(new CompletedTask(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println(completeTask);
        return FXCollections.observableArrayList(completeTask);
    }

    @FXML
    private void handleCompletedTaskButton(ActionEvent event)
            throws SQLException, IOException {

        PreparedStatement stmt = null;

        Task selected = TodayTable.getSelectionModel().getSelectedItem();
        stmt = conn.prepareStatement(
                "INSERT INTO COMPLETEDTASK("
                + "NAME,DESCRIPTION,"
                + "DO_DATE,"
                + "DUE_DATE,"
                + "TASK_PRIORITY, USER)"
                + "VALUES(?,?,?,?,?,?)"
        );

        stmt.setString(1, selected.getTask());
        stmt.setString(2, selected.getDescription());
        stmt.setString(3, selected.getDoDate());
        stmt.setString(4, selected.getDueDate());
        stmt.setString(5, selected.getTaskPriority());
        stmt.setString(6, userID);

        TodayTable.getItems().removeAll(
                TodayTable.getSelectionModel().getSelectedItem()
        );
        ResultSet rs = database.getResultSet("SELECT * FROM TASK");

        String deleteTask
                = "DELETE FROM TASK WHERE ID = " + selected.getId() + ";";
        stmt.executeUpdate();
        statementInsert(deleteTask);

        pageSwitcher.switcher(event, "Kanban.fxml");
    }

    @FXML
    private void handleWeeklyCompletedButton(ActionEvent event)
            throws SQLException, IOException {

        PreparedStatement stmt = null;

        WeeklyTask selected = WeeklyTable.getSelectionModel().getSelectedItem();
        stmt = conn.prepareStatement("INSERT INTO COMPLETEDTASK"
                + "(NAME,DESCRIPTION,DO_DATE,DUE_DATE,TASK_PRIORITY,USER)"
                + "VALUES(?,?,?,?,?,?)"
        );

        stmt.setString(1, selected.getTask());
        stmt.setString(2, selected.getDesc());
        stmt.setString(3, selected.getDoDate());
        stmt.setString(4, selected.getDueDate());
        stmt.setString(5, selected.getTaskPriority());
        stmt.setString(6, userID);

        WeeklyTable.getItems().removeAll(
                WeeklyTable.getSelectionModel().getSelectedItem()
        );
        ResultSet rs = database.getResultSet("SELECT * FROM TASK");

        String deleteTask = 
                "DELETE FROM WEEKLYTASK WHERE ID = " + selected.getId() + ";";
        stmt.executeUpdate();
        statementInsert(deleteTask);

        pageSwitcher.switcher(event, "Kanban.fxml");
    }

    @FXML
    private void handleTomorrowCompletedButton(ActionEvent event) 
            throws SQLException, IOException {

        PreparedStatement stmt = null;

        TomorrowTask selected = 
                TomorrowTable.getSelectionModel().getSelectedItem();
        stmt = conn.prepareStatement("INSERT INTO COMPLETEDTASK"
                + "(NAME,DESCRIPTION,DO_DATE,DUE_DATE,TASK_PRIORITY,USER)"
                + "VALUES(?,?,?,?,?,?)");

        stmt.setString(1, selected.getTask());
        stmt.setString(2, selected.getDesc());
        stmt.setString(3, selected.getDoDate());
        stmt.setString(4, selected.getDueDate());
        stmt.setString(5, selected.getTaskPriority());
        stmt.setString(6, userID);

        TodayTable.getItems().removeAll(
                TodayTable.getSelectionModel().getSelectedItem()
        );
        ResultSet rs = database.getResultSet("SELECT * FROM TASK");

        String deleteTask = 
                "DELETE FROM TOMORROWTASK WHERE ID = " + selected.getId() + ";";
        stmt.executeUpdate();
        statementInsert(deleteTask);

        pageSwitcher.switcher(event, "Kanban.fxml");
    }

    @FXML
    private void handleTodayToWeeklyButton(ActionEvent event) 
            throws SQLException, IOException {

        PreparedStatement stmt = null;

        Task selected = TodayTable.getSelectionModel().getSelectedItem();
        stmt = conn.prepareStatement("INSERT INTO WEEKLYTASK"
                + "(NAME,DESCRIPTION,DO_DATE,DUE_DATE,TASK_PRIORITY,USER)"
                + "VALUES(?,?,?,?,?,?)");

        stmt.setString(1, selected.getTask());
        stmt.setString(2, selected.getDescription());
        stmt.setString(3, selected.getDoDate());
        stmt.setString(4, selected.getDueDate());
        stmt.setString(5, selected.getTaskPriority());
        stmt.setString(6, userID);
        
        TodayTable.getItems().removeAll(
                TodayTable.getSelectionModel().getSelectedItem()
        );

        String deleteTask = 
                "DELETE FROM TASK WHERE ID = " + selected.getId() + ";";
        stmt.executeUpdate();
        statementInsert(deleteTask);

        pageSwitcher.switcher(event, "Kanban.fxml");
    }

    @FXML
    private void handleWeeklyToTodayButton(ActionEvent event) 
            throws SQLException, IOException {

        PreparedStatement stmt = null;

        WeeklyTask selected = WeeklyTable.getSelectionModel().getSelectedItem();
        stmt = conn.prepareStatement("INSERT INTO TASK"
                + "(NAME,DESCRIPTION,DO_DATE,DUE_DATE,TASK_PRIORITY, USER)"
                + "VALUES(?,?,?,?,?,?)");

        stmt.setString(1, selected.getTask());
        stmt.setString(2, selected.getDesc());
        stmt.setString(3, selected.getDoDate());
        stmt.setString(4, selected.getDueDate());
        stmt.setString(5, selected.getTaskPriority());
        stmt.setString(6, userID);
        
        TodayTable.getItems().removeAll(
                TodayTable.getSelectionModel().getSelectedItem()
        );

        String deleteTask = 
                "DELETE FROM WEEKLYTASK WHERE ID = " + selected.getId() + ";";
        stmt.executeUpdate();
        statementInsert(deleteTask);

        pageSwitcher.switcher(event, "Kanban.fxml");
    }

    @FXML
    private void handleTodayToTomorrowButton(ActionEvent event) 
            throws SQLException, IOException {

        PreparedStatement stmt = null;

        Task selected = TodayTable.getSelectionModel().getSelectedItem();
        stmt = conn.prepareStatement("INSERT INTO TOMORROWTASK"
                + "(NAME, DESCRIPTION,DO_DATE,DUE_DATE,TASK_PRIORITY, USER)"
                + "VALUES(?,?,?,?,?,?)");

        stmt.setString(1, selected.getTask());
        stmt.setString(2, selected.getDescription());
        stmt.setString(3, selected.getDoDate());
        stmt.setString(4, selected.getDueDate());
        stmt.setString(5, selected.getTaskPriority());
        stmt.setString(6, userID);
        stmt.executeUpdate();

        TomorrowTable.getItems().removeAll(
                TomorrowTable.getSelectionModel().getSelectedItem()
        );
        String deleteTask = 
                "DELETE FROM TASK WHERE ID = " + selected.getId() + ";";

        statementInsert(deleteTask);

        pageSwitcher.switcher(event, "Kanban.fxml");
    }

    @FXML
    private void handleTomorrowToTodayButton(ActionEvent event) 
            throws SQLException, IOException {

        PreparedStatement stmt = null;

        TomorrowTask selected = 
                TomorrowTable.getSelectionModel().getSelectedItem();
        stmt = conn.prepareStatement("INSERT INTO TASK"
                + "(NAME, DESCRIPTION,DO_DATE,DUE_DATE,TASK_PRIORITY, USER)"
                + "VALUES(?,?,?,?,?,?)");

        stmt.setString(1, selected.getTask());
        stmt.setString(2, selected.getDesc());
        stmt.setString(3, selected.getDoDate());
        stmt.setString(4, selected.getDueDate());
        stmt.setString(5, selected.getTaskPriority());
        stmt.setString(6, userID);
        stmt.executeUpdate();

        TomorrowTable.getItems().removeAll(
                TomorrowTable.getSelectionModel().getSelectedItem());
        String deleteTask = 
                "DELETE FROM TOMORROWTASK WHERE ID = " + selected.getId() + ";";

        statementInsert(deleteTask);

        pageSwitcher.switcher(event, "Kanban.fxml");
    }

    @FXML
    private void handleWeeklyToTomorrowButton(ActionEvent event) 
            throws SQLException, IOException {

        PreparedStatement stmt = null;

        WeeklyTask selected = WeeklyTable.getSelectionModel().getSelectedItem();
        stmt = conn.prepareStatement("INSERT INTO TOMORROWTASK("
                + "NAME,DESCRIPTION,DO_DATE,DUE_DATE,TASK_PRIORITY, USER)"
                + "VALUES(?,?,?,?,?,?)");

        stmt.setString(1, selected.getTask());
        stmt.setString(2, selected.getDesc());
        stmt.setString(3, selected.getDoDate());
        stmt.setString(4, selected.getDueDate());
        stmt.setString(5, selected.getTaskPriority());
        stmt.setString(6, userID);
        WeeklyTable.getItems().removeAll(
                WeeklyTable.getSelectionModel().getSelectedItem()
        );

        String deleteTask = 
                "DELETE FROM WEEKLYTASK WHERE ID = " + selected.getId() + ";";
        stmt.executeUpdate();
        statementInsert(deleteTask);

        pageSwitcher.switcher(event, "Kanban.fxml");
    }

    @FXML
    private void handleTomorrowToWeeklyButton(ActionEvent event) 
            throws SQLException, IOException {

        PreparedStatement stmt = null;

        TomorrowTask selected = 
                TomorrowTable.getSelectionModel().getSelectedItem();
        stmt = conn.prepareStatement("INSERT INTO WEEKLYTASK"
                + "(NAME,DESCRIPTION,DO_DATE,DUE_DATE,TASK_PRIORITY,USER)"
                + "VALUES(?,?,?,?,?,?)");

        stmt.setString(1, selected.getTask());
        stmt.setString(2, selected.getDesc());
        stmt.setString(3, selected.getDoDate());
        stmt.setString(4, selected.getDueDate());
        stmt.setString(5, selected.getTaskPriority());
        stmt.setString(6, userID);
        TomorrowTable.getItems().removeAll(
                TomorrowTable.getSelectionModel().getSelectedItem()
        );

        String deleteTask = 
                "DELETE FROM TOMORROWTASK WHERE ID = " + selected.getId() + ";";
        stmt.executeUpdate();
        statementInsert(deleteTask);

        pageSwitcher.switcher(event, "Kanban.fxml");
    }

    @FXML
    private void handleTodayDeleteButton(ActionEvent event) 
            throws SQLException, IOException {

        Task selected = TodayTable.getSelectionModel().getSelectedItem();
        ResultSet rs = database.getResultSet("SELECT * FROM TASK");
        String deleteTask = 
                "DELETE FROM TASK WHERE ID = " + selected.getId() + ";";
        database.statementInsert(deleteTask);
        TodayTable.getItems().removeAll(
                TodayTable.getSelectionModel().getSelectedItem()
        );
        pageSwitcher.switcher(event, "Kanban.fxml");
    }

    @FXML
    private void handleWeeklyDeleteButton(ActionEvent event) 
            throws SQLException, IOException {

        WeeklyTask selected = WeeklyTable.getSelectionModel().getSelectedItem();
        WeeklyTable.getItems().removeAll(
                WeeklyTable.getSelectionModel().getSelectedItem()
        );
        ResultSet rs = database.getResultSet("SELECT * FROM WEEKLYTASK");
        String deleteTask = 
                "DELETE FROM WEEKLYTASK WHERE ID = " + selected.getId() + ";";
        database.statementInsert(deleteTask);

        pageSwitcher.switcher(event, "Kanban.fxml");
    }

    @FXML
    private void handleTomorrowDeleteButton(ActionEvent event) 
            throws SQLException, IOException {

        TomorrowTask selected = 
                TomorrowTable.getSelectionModel().getSelectedItem();
        TomorrowTable.getItems().removeAll(
                TomorrowTable.getSelectionModel().getSelectedItem()
        );
        ResultSet rs = database.getResultSet("SELECT * FROM TOMORROWTASK");
        String deleteTask = 
                "DELETE FROM TOMORROWTASK WHERE ID = " + selected.getId() + ";";
        database.statementInsert(deleteTask);

        pageSwitcher.switcher(event, "Kanban.fxml");
    }

    @FXML
    private void handleCompletedDeleteButton(ActionEvent event) 
            throws SQLException, IOException {

        CompletedTask select = 
                CompletedTable.getSelectionModel().getSelectedItem();
        ResultSet rs = database.getResultSet("SELECT * FROM COMPLETEDTASK");
        String deleteTask = 
                "DELETE FROM COMPLETEDTASK WHERE NAME = '" 
                + select.getTask() + "';";
        CompletedTable.getItems().removeAll(
                CompletedTable.getSelectionModel().getSelectedItem()
        );
        database.statementInsert(deleteTask);

        pageSwitcher.switcher(event, "Kanban.fxml");
    }

}
