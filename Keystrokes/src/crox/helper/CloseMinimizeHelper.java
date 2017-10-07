/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crox.helper;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Temitayo
 */
public class CloseMinimizeHelper {
    
    
    public static void close(Pane pane){
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }
    
    public static void minimize(Pane pane){
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setIconified(true);
    
    }
    
}
