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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.ListView;

public class PrimaryController implements Initializable {

    @FXML
    private TextField txtUsername1;
    @FXML
    private TextField txtPassword1;
    @FXML
    private TextField txtUsername2;
    @FXML
    private TextField txtPassword2;
    @FXML
    private TextField txtLog1Username;
    @FXML
    private TextField txtLog1Password;
    @FXML
    private TextField txtLog2Username;
    @FXML
    private TextField txtLog2Password;
    @FXML
    private ListView OpgaveList;
    @FXML
    private ListView SpørgsmålList;
    @FXML
    private TextArea IndskrivSpm;

    int x = 1;

    @FXML
    private Label UserConfirm;
    @FXML
    private Label verifyLogin;

    Databasemetoder DB = new Databasemetoder();

    // FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    // Funktioner til at skifte vindue inde på programmet
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");

        DB.pNumber = 3;

    }

    @FXML
    private void switchToThird() throws IOException {
        App.setRoot("third");

        DB.pNumber = 4;

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

        DB.pNumber = 1;
        System.out.println(DB.pNumber);
        System.out.println("Int pnumber = 1 ");

        App.setRoot("RegistreElev");

    }

    @FXML
    private void switchToRegistreLærer() throws IOException {

        DB.pNumber = 2;
        System.out.println("Int pnumber = 2 ");
        App.setRoot("RegistreLærer");

    }

    @FXML
    private void switchToFourth() throws IOException {
        App.setRoot("fourth");
    }

    @FXML
    private void handleBtnAddUser() throws IOException, Exception {

        System.out.println(DB.pNumber);
        if (DB.pNumber == 1) {
            if ((!txtUsername1.getText().isBlank() && !txtPassword1.getText().isBlank())) {
                DB.saveUser(new User(-1, txtUsername1.getText(), txtPassword1.getText()));
                UserConfirm.setText(DB.cMessage);
                App.setRoot("secondary");
                DB.pNumber = 3;
            }
        } else if (DB.pNumber == 2) {
            if ((!txtUsername2.getText().isBlank() && !txtPassword2.getText().isBlank())) {
                DB.saveUser(new User(-1, txtUsername2.getText(), txtPassword2.getText()));
                UserConfirm.setText(DB.cMessage);
                App.setRoot("third");
                DB.pNumber = 4;
            }

        } else {
            System.out.println("Lortet virker ikke");
        }
    }

    @FXML
    private void handleBtnLogin(ActionEvent event) throws IOException, Exception {
        try {
            // Tjekker om boolean er TRUE i første if statement, hvis den ikke er sand, så
            // eksisterer navnet ikke og den giver en besked

            if (DB.pNumber == 3) {
                if (DB.verifyLogin(txtLog1Username.getText(), txtLog1Password.getText())) {
                    verifyLogin.setText("Successful login");
                    // Andre elev ting her
                } else {
                    verifyLogin.setText("Username or password are incorrect");
                }

            } else if (DB.pNumber == 4) {
                if (DB.verifyLogin(txtLog2Username.getText(), txtLog2Password.getText())) {
                    verifyLogin.setText("Successful login");
                    System.out.println("Successful login");
                    // Lærer kommandoer her
                    App.setRoot("LærerQuizMenu");
                } else {
                    verifyLogin.setText("Username or password are incorrect");
                }

            }
        } catch (SQLException e) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    private void OpretQuiz() throws IOException {

        // PLACEHOLDER SHIT HERE; DOES NOT WORK AND SHOULD NOT STAY; WE NEED TO CONNECT
        // SQLITE
        // OpgaveList.getItems().add(x);
        // x++;
        App.setRoot("LærerOpretSpm");

    }

    @FXML
    private void IndsetSpm() throws IOException {

        SpørgsmålList.getItems().add(IndskrivSpm.getText());

    }

}
