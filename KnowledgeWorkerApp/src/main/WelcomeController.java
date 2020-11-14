/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Ellis
 */
public class WelcomeController implements Initializable {

    @FXML
    private Button btnStart;
    @FXML
    private ImageView myImage;

    
    PageSwitcher pageSwitcher = new PageSwitcher();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Calling Welcome Page");
    }    

    @FXML
    private void handleStartButtonAction(ActionEvent event) throws IOException {
        pageSwitcher.switcher(event, "Kanban.fxml");
    }
    
}
