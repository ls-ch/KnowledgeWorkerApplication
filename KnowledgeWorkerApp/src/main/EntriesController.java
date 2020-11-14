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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import static main.LoginScreenController.userID;

/**
 * FXML Controller class
 *
 * @author Ian_R
 */
public class EntriesController implements Initializable {

    @FXML
    private Button btnLogout;
    @FXML
    private Button btnSettings;
    @FXML
    private Button btnKanban;
    @FXML
    private Button btnDeepFocus;
    @FXML
    private TableView<Entry> EntryTable;
    @FXML
    private ImageView myImage;
    @FXML
    private Button btnEntries;
    @FXML
    private TableColumn<Entry, Integer> idColumn;
    @FXML
    private TableColumn<Entry, String> descriptionColumn;
    @FXML
    private TableColumn<Entry, String> categoryColumn;
    @FXML
    private TableColumn<Entry, String> startTimeColumn;
    @FXML
    private TableColumn<Entry, String> endTimeColumn;
    @FXML
    private TableColumn<Entry, String> durationColumn;
    @FXML
    private Button addEntriesBtn;
    @FXML
    private Button deleteEntrybtn;
    @FXML
    private Button btnActivityBreakdown;

    PageSwitcher pageSwitcher = new PageSwitcher();
    Image image = new Image("Kando Logo.png");
    Database database = new Database();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idColumn.setCellValueFactory(
                new PropertyValueFactory<Entry, Integer>("id")
        );
        descriptionColumn.setCellValueFactory(
                new PropertyValueFactory<Entry, String>("desc")
        );
        categoryColumn.setCellValueFactory(
                new PropertyValueFactory<Entry, String>("category")
        );
        startTimeColumn.setCellValueFactory(
                new PropertyValueFactory<Entry, String>("startTime")
        );
        endTimeColumn.setCellValueFactory(
                new PropertyValueFactory<Entry, String>("endTime")
        );
        durationColumn.setCellValueFactory(
                new PropertyValueFactory<Entry, String>("duration")
        );

        ObservableList<Entry> entries = getEntryData();
        EntryTable.setItems(entries);
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
    private void handleAddEntryButton(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "AddNewEntry.fxml");
    }

    private ObservableList<Entry> getEntryData() {
        List<Entry> Entries = new ArrayList<>();
        try {
            ResultSet rs = database.getResultSet("SELECT * FROM ENTRIES WHERE USER = '"+ userID+ "';");
            while (rs.next()) {
                Entries.add(new Entry(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getTime(4),
                        rs.getTime(5),
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
    private void handleDeleteButton(ActionEvent event) throws SQLException {

        Entry selected = EntryTable.getSelectionModel().getSelectedItem();
        EntryTable.getItems().removeAll(
                EntryTable.getSelectionModel().getSelectedItem()
        );
        ResultSet rs = database.getResultSet("SELECT * FROM ENTRIES");
        String deleteTask
                = "DELETE FROM ENTRIES WHERE ENTRYID = " + selected.getId() + ";";
        database.statementInsert(deleteTask);

    }

}
