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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import static main.Database.conn;
import static main.Database.openConnection;
import static main.LoginScreenController.userID;

/**
 * FXML Controller class
 *
 * @author Ellis
 */
public class SignUpController implements Initializable {

    @FXML
    private Button btnBack;
    @FXML
    private Button btnSignUp;
    @FXML
    private ImageView myImage;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblError;

    @FXML
    private TextField firstNameTxt;
    @FXML
    private TextField lastNameTxt;
    /**
     * Initializes the controller class.
     */

    @FXML
    private CheckBox chkTermsConditions;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;

    PageSwitcher pageSwitcher = new PageSwitcher();
    Database database = new Database();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblError.setVisible(false);// TODO
    }

    @FXML
    public void insertToUserTable(ActionEvent event) throws SQLException, IOException {
        PreparedStatement insertUserInput = null;
        if (!chkTermsConditions.isSelected()) {
            lblError.setText("Please accept the Terms and Conditions");
            lblError.setVisible(true);
        } else if (chkTermsConditions.isSelected()) {
            openConnection();

            insertUserInput = conn.prepareStatement(
                    "INSERT INTO USER("
                    + "FNAME, LNAME, USERNAME, PASSWORD)"
                    + "VALUES(?,?,?,?)"
            );
            insertUserInput.setString(1, firstNameTxt.getText());
            insertUserInput.setString(2, lastNameTxt.getText());
            insertUserInput.setString(3, txtUsername.getText());
            insertUserInput.setString(4, txtPassword.getText());
            insertUserInput.executeUpdate();
            
            System.out.println("Sign up success");
            userID = txtUsername.getText();

            pageSwitcher.switcher(event, "Welcome.fxml");
        }
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "LoginScreen.fxml");
    }
}
