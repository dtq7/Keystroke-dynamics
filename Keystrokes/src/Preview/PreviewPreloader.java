/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Preview;

import crox.helper.ResizeHelper;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Temitayo
 */
public class PreviewPreloader extends Application {

        
    private double initX = 0;
    private double initY = 0;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        
        primaryStage.initStyle(StageStyle.UNDECORATED);
        //ResizeHelper.addResizeListener(primaryStage);
   
        
        Parent root = FXMLLoader.load(getClass().getResource("/Preview/previewFXML.fxml"));
        
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
        Scene scene = new Scene(root, 471, 498);
        
        primaryStage.setTitle("Keystroke dynamics");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
