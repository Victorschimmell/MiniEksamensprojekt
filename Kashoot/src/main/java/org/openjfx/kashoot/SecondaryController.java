package org.openjfx.kashoot;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SecondaryController {

    @FXML
    private Button SecondaryView;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
   
         @FXML
    private void switchToSvarView() throws IOException {
        App.setRoot("secondary2");
    }
         @FXML
    private void switchToRegistreElev() throws IOException {
        App.setRoot("RegistreElev");
    }
}
    
