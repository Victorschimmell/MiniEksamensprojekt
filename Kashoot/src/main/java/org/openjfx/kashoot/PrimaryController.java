package org.openjfx.kashoot;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PrimaryController implements Initializable{
    @FXML private TextField txtUsername1;
    @FXML private TextField txtPassword1;
    @FXML private TextField txtLogUsername;
    @FXML private TextField txtLogPassword;
    @FXML private Label UserConfirm;
    @FXML private Label verifyLogin;

    //FXML

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
        
    }


 
    //Funktioner til at skifte vindue inde på programmet
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
     @FXML
    private void switchToThird() throws IOException {
        App.setRoot("third");
    }


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
    @FXML
    private void switchToRegistreLærer() throws IOException {
        App.setRoot("RegistreLærer");
    }
    
    @FXML
    private void switchToFourth() throws IOException {
        App.setRoot("fourth");
    }

     @FXML
    private void handleBtnAddUser() throws IOException, Exception {        
        if(!txtUsername1.getText().isBlank() && !txtPassword1.getText().isBlank())
        {            
            Databasemetoder DB = new Databasemetoder();
            DB.saveUser(new User(-1,txtUsername1.getText(),txtPassword1.getText()));
                 UserConfirm.setText(DB.cMessage);
        }
    }
    public Databasemetoder DB = new Databasemetoder();
            
         @FXML
    private void handleBtnLoginStudent(ActionEvent event) throws IOException, Exception {      
          try {
              //Tjekker om boolean er TRUE i første if statement, hvis den ikke er sand, så eksisterer navnet ikke og den giver en besked
            if (DB.verifyLogin(txtLogUsername.getText(), txtLogPassword.getText())) {
                verifyLogin.setText("Successful login");
                        } else {
                verifyLogin.setText("Username or password are incorrect");
                         
            }   } catch (SQLException e ) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
    
      /*  if(!txtLogUsername.getText().isBlank() && !txtLogPassword.getText().isBlank())
        {            
            
            if(DB.verifyLogin(txtUsername1.getText(), txtPassword1.getText()))
                verifyLogin.setText("Successful login");
                 
        }else{
            verifyLogin.setText(DB.verifyLogin);
        }
    }
}



*/