/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import static main.LoginScreenController.userID;

/**
 * FXML Controller class
 *
 * @author asian
 */
public class ActivityBreakdownController {

    Image image = new Image("Kando Logo.png");

    @FXML
    private AnchorPane main;
    @FXML
    private Button btnKanban;
    @FXML
    private Button btnDeepFocus;
    @FXML
    private Button btnActivityBreakdown;
    @FXML
    private Button btnLogout;
    @FXML
    private Button btnSettings;
    @FXML
    private ImageView myImage;
    @FXML
    private Button btnEntries;
    @FXML
    private PieChart pieChart;
    @FXML
    private BarChart<String, Double> chartDailyBreak;
    @FXML
    private CategoryAxis dailyCategories;
    @FXML
    private NumberAxis dailyDuration;
    @FXML
    private BarChart<String,Double> chartWeeklyBreak;
    @FXML
    private LineChart<String, Double> chartWeeklyTrends;
    /**
     * Initializes the controller class.
     */
    ObservableList<BarChart.Data> barChartData = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    

    
    PageSwitcher pageSwitcher = new PageSwitcher();
    Database database = new Database();

    public void initialize() {
        System.out.println("calling init");
        myImage.setImage(image);
        try {
            ResultSet rs = database.getResultSet("SELECT CATEGORY,DURATION_HOURS, DURATION_MINUTES,DURATION FROM ENTRIES WHERE USER = '"+userID+"';");
            ResultSet rs1 = database.getResultSet("SELECT DATE_TODAY, DURATION_HOURS, DURATION_MINUTES, DURATION FROM ENTRIES WHERE CATEGORY = 'Study' AND USER = '"+userID+"';");
            ResultSet rs2 = database.getResultSet("SELECT DATE_TODAY, DURATION_HOURS, DURATION_MINUTES,DURATION FROM ENTRIES WHERE CATEGORY = 'Leisure' AND USER = '"+userID+"';");
            ResultSet rs3 = database.getResultSet("SELECT DATE_TODAY, DURATION_HOURS, DURATION_MINUTES,DURATION FROM ENTRIES WHERE CATEGORY = 'Exercise' AND USER = '"+userID+"';");
            ResultSet rs4 = database.getResultSet("SELECT DATE_TODAY, DURATION_HOURS, DURATION_MINUTES,DURATION FROM ENTRIES WHERE CATEGORY = 'Work' AND USER = '"+userID+"';");
            ResultSet rs5 = database.getResultSet("SELECT DATE_TODAY, DURATION_HOURS, DURATION_MINUTES,DURATION FROM ENTRIES WHERE CATEGORY = 'Travel' AND USER = '"+userID+"';");
            ResultSet rs6 = database.getResultSet("SELECT DATE_TODAY,DURATION_HOURS, DURATION_MINUTES,DURATION FROM ENTRIES WHERE date('now') < date('now','+7 day') AND USER = '"+userID+"';");
            
            XYChart.Series<String, Double> barchart = new XYChart.Series<>();
            XYChart.Series<String,Double> linechart1 = new XYChart.Series<>();
            XYChart.Series<String, Double> linechart2 = new XYChart.Series<>();
            XYChart.Series<String, Double> linechart3 = new XYChart.Series<>();
            XYChart.Series<String, Double> linechart4 = new XYChart.Series<>();
            XYChart.Series<String, Double> linechart5 = new XYChart.Series<>();
            XYChart.Series<String, Double> barchart1 = new XYChart.Series<>();
            linechart1.setName("Exercise");
            linechart2.setName("Leisure");
            linechart3.setName("Work");
            linechart4.setName("Travel");
            linechart5.setName("Study");

            while (rs.next()) {
                String minutes = Integer.toString(rs.getInt(3));
                String hours = Integer.toString(rs.getInt(2));
                double mins = Double.parseDouble(minutes);
                double hrs = Double.parseDouble(hours) * 60;
                String category = rs.getString("category");
                double total = (mins + hrs);
                barchart.getData().add(new XYChart.Data<>(rs.getString(1), total));
                pieChartData.add(new PieChart.Data(rs.getString(1), total));
            } 
            
            while (rs6.next()) {
                String minutes = Integer.toString(rs6.getInt(3));
                String hours = Integer.toString(rs6.getInt(2));
                double mins = Double.parseDouble(minutes);
                double hrs = Double.parseDouble(hours) * 60;
                double total = (mins + hrs);
                barchart1.getData().add(new XYChart.Data<>(rs6.getString(1), total));
            }
            
            while(rs1.next()){
                String minutes = Integer.toString(rs1.getInt(3));
                String hours = Integer.toString(rs1.getInt(2));
                double mins = Double.parseDouble(minutes);
                double hrs = Double.parseDouble(hours) * 60;
                double total = (mins + hrs);
                linechart1.getData().add(new XYChart.Data<>(rs1.getString(1), total));
            }
            
            while (rs2.next()) {
                String minutes = Integer.toString(rs2.getInt(3));
                String hours = Integer.toString(rs2.getInt(2));
                double mins = Double.parseDouble(minutes);
                double hrs = Double.parseDouble(hours) * 60;
                double total = (mins + hrs);
                linechart2.getData().add(new XYChart.Data<>(rs2.getString(1), total));
            }

            while (rs3.next()) {
                String minutes = Integer.toString(rs3.getInt(3));
                String hours = Integer.toString(rs3.getInt(2));
                double mins = Double.parseDouble(minutes);
                double hrs = Double.parseDouble(hours) * 60;
                double total = (mins + hrs);
                linechart3.getData().add(new XYChart.Data<>(rs3.getString(1), total));
            }
            while (rs4.next()) {
                String minutes = Integer.toString(rs4.getInt(3));
                String hours = Integer.toString(rs4.getInt(2));
                double mins = Double.parseDouble(minutes);
                double hrs = Double.parseDouble(hours) * 60;
                double total = (mins + hrs);
                linechart4.getData().add(new XYChart.Data<>(rs4.getString(1), total));
            }
            while (rs5.next()) {
                String minutes = Integer.toString(rs5.getInt(3));
                String hours = Integer.toString(rs5.getInt(2));
                double mins = Double.parseDouble(minutes);
                double hrs = Double.parseDouble(hours) * 60;
                double total = (mins + hrs);
                linechart5.getData().add(new XYChart.Data<>(rs5.getString(1), total));
            }

            chartWeeklyTrends.getData().addAll(linechart1, linechart2,linechart3,linechart4,linechart5);
            chartDailyBreak.getData().add(barchart);
            pieChart.setData(pieChartData);
            chartWeeklyBreak.getData().add(barchart1);
        } catch (Exception e) {
            e.printStackTrace();

        }

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

    }

    @FXML
    private void handleEntriesButton(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "Entries.fxml");
    }
}
