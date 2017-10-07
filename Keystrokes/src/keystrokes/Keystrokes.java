
package keystrokes;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * Coach_e
 */
public class Keystrokes extends Application {
     
    private double initX = 0;
    private double initY = 0;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        
        primaryStage.initStyle(StageStyle.UNDECORATED);
   
        
        Parent root = FXMLLoader.load(getClass().getResource("/keystrokes/keystrokeFXML.fxml"));
        
         //when mouse button is pressed, save the initial position of screen
        root.setOnMousePressed((MouseEvent me) -> {

            initX = me.getScreenX() - primaryStage.getX();
            initY = me.getScreenY() - primaryStage.getY();
        });

        //when screen is dragged, translate it accordingly
        root.setOnMouseDragged((MouseEvent me) -> {
            primaryStage.setX(me.getScreenX() - initX);
            primaryStage.setY(me.getScreenY() - initY);
        });
        Scene scene = new Scene(root, 601, 566);
        
        primaryStage.setTitle("Keystroke dynamics");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
