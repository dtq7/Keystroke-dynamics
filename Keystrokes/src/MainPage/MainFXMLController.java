/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPage;

import Login.LoginFXMLController;

import crox.helper.CloseMinimizeHelper;
import crox.pageLoader.PageLoader;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Temitayo
 */
public class MainFXMLController implements Initializable {
    PageLoader pl = new PageLoader();
    @FXML
    private BorderPane mainPageBorderPane;
    @FXML
    private Label userAvater;
    private String ave =null;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       userAvater.setText(ave);
    }    

    @FXML
    private void logOutToLoginPageFromMainPage(ActionEvent event) {
       
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log Out");
        alert.setContentText("Are you sure you want to log out of the system");
        
        Optional<ButtonType> response = alert.showAndWait();
        if(response.get() == ButtonType.OK){
            pl.customloadWindow("/Login/loginFXML.fxml", 600, 400);
            CloseMinimizeHelper.close(mainPageBorderPane);
        }
        
    }
    
    public void setAve(String name){
        ave = name;
    }

}
