package Preview;

import crox.helper.CloseMinimizeHelper;
import crox.pageLoader.PageLoader;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;


public class PreviewFXMLController implements Initializable {

    PageLoader pl = new PageLoader();

    @FXML
    private AnchorPane previewMainAnchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void openRegistrationStageFromPreview(ActionEvent event) {
        pl.customloadWindow("/keystrokes/keystrokeFXML.fxml", 601, 566);
        CloseMinimizeHelper.close(previewMainAnchorPane);
    }

    @FXML
    private void openLoginStageFromPreview(ActionEvent event) {
       pl.customloadWindow("/Login/loginFXML.fxml", 600, 400);
        CloseMinimizeHelper.close(previewMainAnchorPane);
    }

    @FXML
    private void minimizePreview(ActionEvent event) {
        CloseMinimizeHelper.minimize(previewMainAnchorPane);
    }

    @FXML
    private void closePreview(ActionEvent event) {
        CloseMinimizeHelper.close(previewMainAnchorPane);
    }
}
