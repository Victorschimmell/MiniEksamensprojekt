package org.openjfx.kashoot;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
     @FXML
    private void switchToThird() throws IOException {
        App.setRoot("third");
    }
    
}
