package org.openjfx.kashoot;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.TextArea;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.CheckBox;

public class PrimaryController implements Initializable {

    @FXML
    private TextField txtUsername1;
    @FXML
    private PasswordField txtPassword1;
    @FXML
    private PasswordField txtPassword1Confirm;
    @FXML
    private TextField txtUsername2;
    @FXML
    private PasswordField txtPassword2;
    @FXML
    private PasswordField txtPassword2Confirm;
    @FXML
    private TextField txtLog1Username;
    @FXML
    private TextField txtLog1Password;
    @FXML
    private TextField txtLog2Username;
    @FXML
    private TextField txtLog2Password;
    @FXML
    private ListView<String> OpgaveList;
    @FXML
    private ListView<String> SpørgsmålList;
    @FXML
    private TextArea IndskrivSpm;
    @FXML
    private TextField SvarmulighedA;
    @FXML
    private TextField SvarmulighedB;
    @FXML
    private TextField SvarmulighedC;
    @FXML
    private TextField SvarmulighedD;
    @FXML
    private TextField quiznavn;
    @FXML
    private CheckBox AKorrekt;
    @FXML
    private CheckBox BKorrekt;
    @FXML
    private CheckBox CKorrekt;
    @FXML
    private CheckBox DKorrekt;
    @FXML
    private Text UserConfirm;
    @FXML
    private Label verifyLogin;
    @FXML 
    private Text QuizIDdis;

    

    Databasemetoder DB = new Databasemetoder();

    // PLACEHOLDER FOR SQL KODE

    // FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            updateQuiz();
        } catch (Exception e) {
        }
        try {
            QuizIDdis.setText(String.valueOf(DB.getQuizId()));

        } catch (Exception e) {
           
        }

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
    private void switchToSpm() throws IOException {
        App.setRoot("spmSide");
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
                if (txtPassword1.getText().equals(txtPassword1Confirm.getText())) {
                    DB.saveUser(new User(-1, txtUsername1.getText(), txtPassword1.getText()));
                    UserConfirm.setText(DB.cMessage);

                } else {
                    System.out.println("Kodeord matcher ikke");
                    UserConfirm.setText("Kodeord matcher ikke");
                }
            } else {
                UserConfirm.setText("Intet brugernavn eller kodeord inskrevet");
            }
        } else if (DB.pNumber == 2) {
            if ((!txtUsername2.getText().isBlank() && !txtPassword2.getText().isBlank())) {
                if (txtPassword2.getText().equals(txtPassword2Confirm.getText())) {
                    DB.saveUser(new User(-1, txtUsername2.getText(), txtPassword2.getText()));
                    UserConfirm.setText(DB.cMessage);

                } else {
                    System.out.println("Kodeord matcher ikke");
                    UserConfirm.setText("Kodeord matcher ikke");
                }
            } else {
                UserConfirm.setText("Intet brugernavn eller kodeord inskrevet");
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
                    App.setRoot("spmID");
                    // Andre elev ting her
                } else {
                    verifyLogin.setText(DB.cMessage);
                }

            } else if (DB.pNumber == 4) {
                if (DB.verifyLogin(txtLog2Username.getText(), txtLog2Password.getText())) {
                    verifyLogin.setText("Successful login");
                    System.out.println("Successful login");
                    // Lærer kommandoer her
                    DB.updateQuizTabel();
                    App.setRoot("LærerQuizMenu");
                } else {
                    verifyLogin.setText(DB.cMessage);
                }

            }
        } catch (SQLException e) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    private void OpretQuiz() throws IOException, Exception {

        // PLACEHOLDER SHIT HERE; DOES NOT WORK AND SHOULD NOT STAY; WE NEED TO CONNECT
        // SQLITE
        DB.newQuiz(new Quiz(quiznavn.getText(), DB.CurrentUser, DB.getQuizId()));

        App.setRoot("LærerOpretSpm");

    }

    @FXML
    // Tilbage fra spmmenu til quizmenu
    private void Tilbage() throws IOException {

        App.setRoot("lærerQuizMenu");
    }

    @FXML
    private void IndsætSpm() throws IOException {

        SpørgsmålList.getItems().add("Spørgsmål: " + IndskrivSpm.getText() +
                ", Svarmulighed A: " + SvarmulighedA.getText() + ":" + AKorrekt.isSelected() +
                ", Svarmulighed B: " + SvarmulighedB.getText() + ":" + BKorrekt.isSelected() +
                ", Svarmulighed C: " + SvarmulighedC.getText() + ":" + CKorrekt.isSelected() +
                ", Svarmulighed D: " + SvarmulighedD.getText() + ":" + DKorrekt.isSelected());

    }

    @FXML
    private void updateQuiz() throws Exception {

        ArrayList<String> Names = new ArrayList<String>(DB.updateQuizTabel());

        try {
            OpgaveList.getItems().clear();
        } catch (Exception e) {
        }

        for (int i = 0; i < Names.size(); i++) {

            OpgaveList.getItems().add(Names.get(i));

        }

    }

}
