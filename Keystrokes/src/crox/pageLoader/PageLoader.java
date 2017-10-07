/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crox.pageLoader;

import Preview.PreviewFXMLController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Temitayo
 */
public class PageLoader {
    
        
    private double initX = 0;
    private double initY = 0;
    
     public void customloadWindow(String loc, int width, int height) {

        try {
           
            Parent root = FXMLLoader.load(getClass().getResource(loc));
             Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setResizable(false);
            //when mouse button is pressed, save the initial position of screen
            root.setOnMousePressed((MouseEvent me) -> {

                initX = me.getScreenX() - stage.getX();
                initY = me.getScreenY() - stage.getY();
            });

            //when screen is dragged, translate it accordingly
            root.setOnMouseDragged((MouseEvent me) -> {
                stage.setX(me.getScreenX() - initX);
                stage.setY(me.getScreenY() - initY);
            });
            Scene scene = new Scene(root, width, height);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(PreviewFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
      public void DefaultloadWindow(String loc, int width, int height) {

        try {
           
            Parent root = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setResizable(true);
            Scene scene = new Scene(root, width, height);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(PreviewFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
