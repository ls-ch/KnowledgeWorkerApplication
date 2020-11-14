/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Ellis
 */
public class LoginScreenController {

    @FXML
    private Button btnSignUp;
    @FXML
    private ImageView myImage;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnBack;
    @FXML
    public static String userID;
    @FXML
    private Button btnLogin;
    @FXML
    private CheckBox chkRememberDetails;
    @FXML
    private Label lblLoginError;

    Database database = new Database();
    PageSwitcher pageSwitcher = new PageSwitcher();

    public void initialize() {
        System.out.println("calling Log-in Screen");
        lblLoginError.setVisible(false);
    }

    private void handleBackButtonAction(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "LoginScreen.fxml");

    }
    @FXML
    private void handleSignUpButtonAction(ActionEvent event) throws IOException {
        System.out.println("Calling SignUp Screen");
        pageSwitcher.switcher(event, "SignUp.fxml");
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws IOException, SQLException {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText();

        if (database.CheckLoginDetails(username, password)) {
            System.out.println("Login Successful");
            lblLoginError.setVisible(false);
            pageSwitcher.switcher(event, "Welcome.fxml");
            userID = txtUsername.getText();
            database.openConnection();

        } else {
            lblLoginError.setText("Incorrect username or password");
            lblLoginError.setVisible(true);
        }
    }
}
