package keystrokes;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import crox.helper.CloseMinimizeHelper;
import crox.pageLoader.PageLoader;
import java.net.URL;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import keystrokeDatabase.databaseHandler;

public class KeystrokeFXMLController implements Initializable {   
    ObservableList<Long> keypressList = FXCollections.observableArrayList();
    ObservableList<Long> InterKeyList = FXCollections.observableArrayList();
    int TrainCounter = 0;
     
    ObservableList<Long> resizer = FXCollections.observableArrayList();
    
    StringBuilder sb = new StringBuilder();
    StringBuilder sbInter = new StringBuilder();
    
    GregorianCalendar GcPress,GcRelease;
    Date DatePress,DateRelease;
    TimeUnit timeunit = TimeUnit.MILLISECONDS;
    Long TotalLit = 0l; //Variable to compute the total of TotalKeyPress and TotalInterval
    Long TotalKeyPress = 0l; //Variable get get the total Keypress
    Long TotalInterval = 0l; //Variable to get the total key interval
    Long diff = 0l; //Variable to compute the difference between succesive keypress
    String Train1,Train2,Train3;
    String Average;
    Long AverageLong;
    String Password=null;
    
    PageLoader pl = new PageLoader();

    @FXML
    private AnchorPane ResistrationMainAnchorPane;
    @FXML
    private JFXTextField ResgistrationusernameTextField;
    @FXML
    private Label keyPressLabel;
    @FXML
    private Label totalTimeLabel;
    @FXML
    private Label interKeyTimeLabel;
    @FXML
    private JFXPasswordField RegistrationpasswordField;
    @FXML
    private JFXButton TrainLabel;
    @FXML
    private JFXButton Signuplabel;
    @FXML
    private Label WarningLabel;
    
    databaseHandler driver;
            

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        driver = databaseHandler.getInstance();
        KeypressTime();
        KeyReleaseTime();   
        TrainToolTip();
        ClearWarning();
    }    

    @FXML
    private void trainBeforeRegistration(ActionEvent event) {
        if(ResgistrationusernameTextField.getText().isEmpty() && RegistrationpasswordField.getText().isEmpty()){
            WarningLabel.setText("Enter in all fields");
        }else if(RegistrationpasswordField.getText().isEmpty()){
            WarningLabel.setText("Enter Password");
        }else if(ResgistrationusernameTextField.getText().isEmpty()){
            WarningLabel.setText("Enter Username");
        }else{
        int a = sb.lastIndexOf(".");
        sb.deleteCharAt(a);
        keyPressLabel.setText(sb.toString());
        int i = sbInter.indexOf(".");
        int b = sbInter.lastIndexOf(".");
        sbInter.deleteCharAt(b);
        sbInter.delete(0, i+1);
        interKeyTimeLabel.setText(sbInter.toString());
        sb.delete(0, sb.length());
        sbInter.delete(0, sbInter.length());
        String TimeTaker = getTotalTime();
        totalTimeLabel.setText(TimeTaker + " Milliseconds");  
        TrainCounter++;
        if(TrainCounter == 1){
            ResgistrationusernameTextField.setDisable(true);
            Train1 = TimeTaker;
            this.Password = RegistrationpasswordField.getText();
            System.out.println(Password);
        
        }
        if(TrainCounter == 2){
            if(RegistrationpasswordField.getText().equals(Password)){
                Train2 = TimeTaker;
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Password Mismatch");
                alert.setContentText("Please make sure your passwork is\n consistent.");
                alert.showAndWait();
                TrainCounter--;
            }        
        }
        if(TrainCounter == 3){
            if(RegistrationpasswordField.getText().equals(Password)){
                TrainLabel.setDisable(true);
                Signuplabel.setDisable(false);
                RegistrationpasswordField.setDisable(true);
                Train3 = TimeTaker;
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Password Mismatch");
                alert.setContentText("Please make sure your password is\n consistent.");
                alert.showAndWait();
                TrainCounter--;
            }
           
        }
        }
        
        
     
    }

    @FXML
    private void signupRegistration(ActionEvent event) {
        AverageLong = (Long.parseLong(Train1) + Long.parseLong(Train2)+Long.parseLong(Train3))/3;
        Average = AverageLong.toString();
       // System.out.println(Train1 +","+ Train2+","+Train3);
        //System.out.println(Average);
        String nameX = ResgistrationusernameTextField.getText();
        String passX = Password;
        String Train1X = Train1;
        String Train2X = Train2;
        String Train3X = Train3;
        String AverageX = Average;
        
          String sql= "INSERT INTO users VALUES("
               + "'" + nameX + "',"
               + "'" + passX + "',"
               + "'" + Train1X + "',"
               + "'" + Train2X + "',"
                + "'" + Train3X + "',"
               + "'" + AverageX + "'"   
               +")";
       System.out.println(sql);
       
       if (driver.execAction(sql)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Success!!! Proceed to Login.");
            alert.showAndWait();
            pl.customloadWindow("/Login/loginFXML.fxml", 600,400);
            CloseMinimizeHelper.close(ResistrationMainAnchorPane);
            
            
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText(" The Username already exists. Please reset and select\n a new user name to continue");
            alert.showAndWait();
        }
    
                
        
    }

    @FXML
    private void goToLoginFromRegistrationStage(ActionEvent event) {
        pl.customloadWindow("/Login/loginFXML.fxml", 600, 400);
        CloseMinimizeHelper.close(ResistrationMainAnchorPane);
        
    }

    @FXML
    private void backToPreviewFromRegistrationStage(ActionEvent event) {
        pl.customloadWindow("/Preview/previewFXML.fxml", 471, 498);
        CloseMinimizeHelper.close(ResistrationMainAnchorPane);
    }

    @FXML
    private void minimizeRegistrationStage(ActionEvent event) {
        CloseMinimizeHelper.minimize(ResistrationMainAnchorPane);
    }

    @FXML
    private void closeRegistrationStage(ActionEvent event) {
        CloseMinimizeHelper.close(ResistrationMainAnchorPane);
    }
    
    public void KeypressTime(){
        RegistrationpasswordField.setOnKeyPressed((KeyEvent event) -> {
            GcPress = new GregorianCalendar();
            DatePress= GcPress.getTime();
            Long Actual = timeunit.convert(DatePress.getTime(), TimeUnit.MILLISECONDS);
            Long newActual = Actual - diff;
            diff = Actual;
            String newStringActual = newActual.toString();
            sbInter.append(newStringActual).append(".");
            InterKeyList.add(newActual);
            
            
        });
        
    }
    
    public void KeyReleaseTime(){
        RegistrationpasswordField.setOnKeyReleased((KeyEvent event) -> {
            GcRelease = new GregorianCalendar();
            DateRelease = GcRelease.getTime();
            Long diff = DateRelease.getTime() - DatePress.getTime(); //Differenc between the two dates
            Long actualDiff = timeunit.convert(diff, TimeUnit.MILLISECONDS); //Converting the delta miilisec to readable sec
            String actualStringDiff = actualDiff.toString(); //Casting it to a string
            sb.append(actualStringDiff).append(".");
            keypressList.add(actualDiff);
        });
    }
    
   String getTotalTime(){
        for (Long long1 : keypressList) {
            TotalKeyPress+=long1;
        }
        for (int i = 1; i < InterKeyList.size(); i++) {
            TotalInterval+=InterKeyList.get(i);       
        }
         this.keypressList.clear();
         this.InterKeyList.clear();
         TotalLit = TotalKeyPress + TotalInterval;
         this.TotalKeyPress = 0l;
         this.TotalInterval = 0l;
         return TotalLit.toString();      
    }
   
   Long getTotalLongTime(){
        for (Long long1 : keypressList) {
            TotalKeyPress+=long1;
        }
        for (int i = 1; i < InterKeyList.size(); i++) {
            TotalInterval+=InterKeyList.get(i);       
        }
         this.keypressList.clear();
         this.InterKeyList.clear();
         TotalLit = TotalKeyPress + TotalInterval;
         this.TotalKeyPress = 0l;
         this.TotalInterval = 0l;
         return TotalLit;      
    }

    private void TrainToolTip() {
        TrainLabel.setTooltip(new Tooltip("Train yourself three times"));
    }

    private void ClearWarning() {
         RegistrationpasswordField.setOnMouseClicked(e -> WarningLabel.setText(""));
         ResgistrationusernameTextField.setOnMouseClicked(e -> WarningLabel.setText(""));
    }

    @FXML
    private void reset(ActionEvent event) {
       pl.customloadWindow("/keystrokes/keystrokeFXML.fxml", 601, 566);
       CloseMinimizeHelper.close(ResistrationMainAnchorPane);
    
    }

}
