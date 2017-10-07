package Login;

import MainPage.MainFXMLController;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import crox.helper.CloseMinimizeHelper;
import crox.pageLoader.PageLoader;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import keystrokeDatabase.databaseHandler;

public class LoginFXMLController implements Initializable {
    MainFXMLController mc = new MainFXMLController();
            

    ObservableList<Long> keypressList = FXCollections.observableArrayList();
    ObservableList<Long> InterKeyList = FXCollections.observableArrayList();
    GregorianCalendar GcPress, GcRelease;
    Date DatePress, DateRelease;
    TimeUnit timeunit = TimeUnit.MILLISECONDS;
    Long TotalLit = 0l; //Variable to compute the total of TotalKeyPress and TotalInterval
    Long TotalKeyPress = 0l; //Variable get get the total Keypress
    Long TotalInterval = 0l; //Variable to get the total key interval
    Long diff = 0l; //Variable to compute the difference between succesive keypress
    String Train1, Train2, Train3;
    String Average;
    Long AverageLong;
    String Password = null;

    PageLoader pl = new PageLoader();

    ObservableList<String> userList = FXCollections.observableArrayList();
    ObservableList<String> passwordList = FXCollections.observableArrayList();
    ObservableList<String> timeList = FXCollections.observableArrayList();
  

    @FXML
    private AnchorPane LoginMainAnchorPane;
    @FXML
    private JFXTextField loginUsernameTextField;
    @FXML
    private JFXPasswordField LoginPasswordField;

    databaseHandler driver;
    @FXML
    private Label warningLabel;

   
    private int neededIndex;
    private String collect;
    private static String user;
    private static final String HELPS = user;
    private String pass;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        driver = databaseHandler.getInstance();
        KeypressTime();
        KeyReleaseTime();
        ClearWarning();
    }

    @FXML
    private void goToSignUpFromLoginPage(ActionEvent event) {
        pl.customloadWindow("/keystrokes/keystrokeFXML.fxml", 601, 566);
        CloseMinimizeHelper.close(LoginMainAnchorPane);
    }

    @FXML
    private void openMainpageAfterLogin(ActionEvent event) {
        if (loginUsernameTextField.getText().isEmpty() && LoginPasswordField.getText().isEmpty()) {
            warningLabel.setText("Enter in all fields");
        } else if (LoginPasswordField.getText().isEmpty()) {
            warningLabel.setText("Enter Password");
        } else if (loginUsernameTextField.getText().isEmpty()) {
            warningLabel.setText("Enter Username");
        } else {
            try {
                String qu1 = "SELECT username FROM users";
                String qu2 = "SELECT password FROM users";
                String qu3 = "SELECT AverageTime FROM users";

                ResultSet myRs1 = databaseHandler.execQuery(qu1);
                ResultSet myRs2 = databaseHandler.execQuery(qu2);
                ResultSet myRs3 = databaseHandler.execQuery(qu3);

                while (myRs1.next()) {
                    String collectUser = myRs1.getString("username");
                    userList.add(collectUser);
                }

                while (myRs2.next()) {
                    String collectPassword = myRs2.getString("password");
                    passwordList.add(collectPassword);
                }

                while (myRs3.next()) {
                    String collectTime = myRs3.getString("AverageTime");
                    timeList.add(collectTime);
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            String TimeTaker = getTotalTime();
            pass = LoginPasswordField.getText();
            user = loginUsernameTextField.getText();
            mc.setAve(user);
            neededIndex = userList.indexOf(user);

            String correctPassword = passwordList.get(neededIndex);
            String correctTime = timeList.get(neededIndex);

            Long userTime = Long.parseLong(TimeTaker);
            Long recordedTime = Long.parseLong(correctTime);
            

            if (pass.equalsIgnoreCase(correctPassword) && (((recordedTime-500) <= userTime) && (userTime <= (recordedTime+500)))) {
                pl.DefaultloadWindow("/MainPage/mainFXML.fxml", 1366, 760);
                CloseMinimizeHelper.close(LoginMainAnchorPane);
          /*  }else if((pass.equalsIgnoreCase(correctPassword)) && ((userTime < (recordedTime-500)) && userTime > (recordedTime+500))){
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Issues");
                alert1.setContentText("TimeFrame Exceeded");
                alert1.showAndWait();
            }else if((!pass.equalsIgnoreCase(correctPassword)) && (((recordedTime-500) <= userTime) && (userTime <= (recordedTime+500)))){
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Issues");
                alert2.setContentText("Incorrect Password");
                alert2.showAndWait();
            }else if((!pass.equalsIgnoreCase(correctPassword)) && ((userTime < (recordedTime-500)) && userTime > (recordedTime+500))){
                Alert alert3 = new Alert(Alert.AlertType.ERROR);
                alert3.setTitle("Issues");
                alert3.setContentText("Password wrong and TimeFrame");
                alert3.showAndWait();
          */  }else{
                    Alert alert3 = new Alert(Alert.AlertType.ERROR);
                alert3.setTitle("Issues");
                alert3.setContentText("Access Declined");
                alert3.showAndWait();
            }
            
        }

    }

    @FXML
    private void backToPreviewFromLoginStage(ActionEvent event) {
        pl.customloadWindow("/Preview/previewFXML.fxml", 471, 498);
        CloseMinimizeHelper.close(LoginMainAnchorPane);
    }

    @FXML
    private void minimizeLoginStage(ActionEvent event) {
        CloseMinimizeHelper.minimize(LoginMainAnchorPane);
    }

    @FXML
    private void closeLoginStage(ActionEvent event) {
        CloseMinimizeHelper.close(LoginMainAnchorPane);
    }

    public void KeypressTime() {
        LoginPasswordField.setOnKeyPressed((KeyEvent event) -> {
            GcPress = new GregorianCalendar();
            DatePress = GcPress.getTime();
            Long Actual = timeunit.convert(DatePress.getTime(), TimeUnit.MILLISECONDS);
            Long newActual = Actual - diff;
            diff = Actual;
            String newStringActual = newActual.toString();
            InterKeyList.add(newActual);

        });

    }

    public void KeyReleaseTime() {
        LoginPasswordField.setOnKeyReleased((KeyEvent event) -> {
            GcRelease = new GregorianCalendar();
            DateRelease = GcRelease.getTime();
            Long diff = DateRelease.getTime() - DatePress.getTime(); //Differenc between the two dates
            Long actualDiff = timeunit.convert(diff, TimeUnit.MILLISECONDS); //Converting the delta miilisec to readable sec
            String actualStringDiff = actualDiff.toString(); //Casting it to a string
            keypressList.add(actualDiff);
        });
    }

    String getTotalTime() {
        for (Long long1 : keypressList) {
            TotalKeyPress += long1;
        }
        for (int i = 1; i < InterKeyList.size(); i++) {
            TotalInterval += InterKeyList.get(i);
        }
        this.keypressList.clear();
        this.InterKeyList.clear();
        TotalLit = TotalKeyPress + TotalInterval;
        this.TotalKeyPress = 0l;
        this.TotalInterval = 0l;
        return TotalLit.toString();
    }

    Long getTotalLongTime() {
        for (Long long1 : keypressList) {
            TotalKeyPress += long1;
        }
        for (int i = 1; i < InterKeyList.size(); i++) {
            TotalInterval += InterKeyList.get(i);
        }
        this.keypressList.clear();
        this.InterKeyList.clear();
        TotalLit = TotalKeyPress + TotalInterval;
        this.TotalKeyPress = 0l;
        this.TotalInterval = 0l;
        return TotalLit;
    }
    
     private void ClearWarning() {
         LoginPasswordField.setOnMouseClicked(e -> warningLabel.setText(""));
         loginUsernameTextField.setOnMouseClicked(e -> warningLabel.setText(""));
    }

     public String getUsername(){
         return HELPS;
     }
}
