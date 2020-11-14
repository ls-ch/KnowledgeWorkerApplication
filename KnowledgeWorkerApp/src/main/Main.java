
package main;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Ian_R
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        loadDatabase();
        Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        stage.setTitle("Kan-Do Activity Tracker");
        stage.getIcons().add(new Image("Kando Logo.png"));
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        
    }
    
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        launch(args);
    }
    
    
    private void loadDatabase() {
       Database.openConnection();
       Database.createCategoriesTable();
       Database.createEntriesTable();
       Database.createTaskTable();
       Database.createCompletedTaskTable();
       Database.createDeepFocusTaskTable();
       Database.createTodayTaskTable();
       Database.createWeeklyTaskTable();
       Database.createTomorrowTaskTable();
       Database.createUserTable();
    }
}
