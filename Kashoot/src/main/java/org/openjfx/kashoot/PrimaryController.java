package org.openjfx.kashoot;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.TextArea;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;

import javafx.scene.control.CheckBox;

public class PrimaryController implements Initializable {

    @FXML
    private TextField txtUsername1, txtUsername2, txtLog1Username, txtLog1Password, txtLog2Username, txtLog2Password, SvarmulighedA, SvarmulighedB, SvarmulighedC, SvarmulighedD, quiznavn, IDField;
    @FXML
    private PasswordField txtPassword1, txtPassword1Confirm, txtPassword2, txtPassword2Confirm;
    @FXML
    private ListView<String> OpgaveList, SpørgsmålList;
    @FXML
    private TextArea IndskrivSpm;
    @FXML
    private CheckBox AKorrekt, BKorrekt, CKorrekt, DKorrekt;
  
    @FXML
    private Text UserConfirm, QuizIDdis, SpørgsmålView, SpmNr;
    @FXML
    private Label verifyLogin;

    @FXML
    private Button SvarKnap1, SvarKnap2, SvarKnap3, SvarKnap4;

    public static String KodeQuiz;

    private int NrSpm;
    private String NrSpm_String;

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
        try {
            showSvarMuligheder();

        } catch (Exception e) {

        }
        try {
            SpørgsmålView.setText(DB.displaySpm(NrSpm));
            NrSpm_String = String.valueOf(NrSpm + 1);

            SpmNr.setText(NrSpm_String);

        } catch (Exception e) {

        }

    }

    // Funktioner til at skifte vindue inde på programmet
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
        Databasemetoder.pNumber = 3;
    }

    @FXML
    private void switchToThird() throws IOException {
        App.setRoot("third");
        Databasemetoder.pNumber = 4;
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

        if (IDField.getText().isBlank()) {
            System.out.println("Husk at skrive en kode buddy");
        } else {
            KodeQuiz = IDField.getText();

            try {

                if (DB.verifyQuiz(KodeQuiz)) {
                    System.out.println("Åbner quizID: " + KodeQuiz);

                    NrSpm = 0;

                    App.setRoot("spmSide");

                } else {

                    System.out.println("QuizKode findes ikke");
                }

            } catch (Exception e) {
                System.out.println("QuizKode findes ikke");
            }
        }
    }

    @FXML
    private void switchToRegistreElev() throws IOException {

        Databasemetoder.pNumber = 1;
        System.out.println(Databasemetoder.pNumber);
        System.out.println("Int pnumber = 1 ");

        App.setRoot("RegistreElev");

    }

    @FXML
    private void switchToRegistreLærer() throws IOException {

        Databasemetoder.pNumber = 2;
        System.out.println("Int pnumber = 2 ");
        App.setRoot("RegistreLærer");

    }

    @FXML
    private void switchToFourth() throws IOException {
        App.setRoot("fourth");
    }

    @FXML
    private void handleBtnAddUser() throws IOException, Exception {

        System.out.println(Databasemetoder.pNumber);
        if (Databasemetoder.pNumber == 1) {
            if ((!txtUsername1.getText().isBlank() && !txtPassword1.getText().isBlank())) {
                if (txtPassword1.getText().equals(txtPassword1Confirm.getText())) {
                    DB.saveUser(new User(-1, txtUsername1.getText(), txtPassword1.getText()));
                    UserConfirm.setText(Databasemetoder.cMessage);

                } else {
                    System.out.println("Kodeord matcher ikke");
                    UserConfirm.setText("Kodeord matcher ikke");
                }
            } else {
                UserConfirm.setText("Intet brugernavn eller kodeord inskrevet");
            }
        } else if (Databasemetoder.pNumber == 2) {
            if ((!txtUsername2.getText().isBlank() && !txtPassword2.getText().isBlank())) {
                if (txtPassword2.getText().equals(txtPassword2Confirm.getText())) {
                    DB.saveUser(new User(-1, txtUsername2.getText(), txtPassword2.getText()));
                    UserConfirm.setText(Databasemetoder.cMessage);

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

            if (Databasemetoder.pNumber == 3) {
                if (DB.verifyLogin(txtLog1Username.getText(), txtLog1Password.getText())) {
                    verifyLogin.setText("Successful login");
                    App.setRoot("spmID");
                    // Andre elev ting her
                } else {
                    verifyLogin.setText(Databasemetoder.cMessage);
                }

            } else if (Databasemetoder.pNumber == 4) {
                if (DB.verifyLogin(txtLog2Username.getText(), txtLog2Password.getText())) {
                    verifyLogin.setText("Successful login");
                    System.out.println("Successful login");
                    // Lærer kommandoer her
                    DB.updateQuizTabel();
                    App.setRoot("LærerQuizMenu");
                } else {
                    verifyLogin.setText(Databasemetoder.cMessage);
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
        DB.newQuiz(new Quiz(quiznavn.getText(), Databasemetoder.CurrentUser, DB.getQuizId()));

        App.setRoot("LærerOpretSpm");

    }

    @FXML
    // Tilbage fra spmmenu til quizmenu
    private void Tilbage() throws IOException {

        App.setRoot("lærerQuizMenu");
    }

    // Andre funktioner i dunno Anders....
    @FXML
    private void IndsætSpm() throws SQLException, Exception {

        DB.SaveSpm(new spm(DB.getQuizId(), IndskrivSpm.getText()),
                new Svar(DB.getSpmId(), SvarmulighedA.getText(), AKorrekt.isSelected()),
                new Svar(DB.getSpmId(), SvarmulighedB.getText(), BKorrekt.isSelected()),
                new Svar(DB.getSpmId(), SvarmulighedC.getText(), CKorrekt.isSelected()),
                new Svar(DB.getSpmId(), SvarmulighedD.getText(), DKorrekt.isSelected()));

        SpørgsmålList.getItems().add("Spørgsmål: " + IndskrivSpm.getText()
                + ", Svarmulighed A: " + SvarmulighedA.getText() + ":" + AKorrekt.isSelected()
                + ", Svarmulighed B: " + SvarmulighedB.getText() + ":" + BKorrekt.isSelected()
                + ", Svarmulighed C: " + SvarmulighedC.getText() + ":" + CKorrekt.isSelected()
                + ", Svarmulighed D: " + SvarmulighedD.getText() + ":" + DKorrekt.isSelected());

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

    @FXML
    private void showSvarMuligheder() throws Exception {
        List<String> SvarMList = DB.displaySvarMuligheder();
        SvarKnap1.setText(SvarMList.get(0));
        SvarKnap2.setText(SvarMList.get(1));
        SvarKnap3.setText(SvarMList.get(2));
        SvarKnap4.setText(SvarMList.get(3));

    }

    @FXML
    private void handleBtnValg1() throws Exception {
        Databasemetoder.bPressedNum = 1;
        DB.korrektSvarCheck();
        if (Databasemetoder.rSet.get(Databasemetoder.bPressedNum - 1).equals("1")) {
            System.out.println("Korrekt");
        } else {
            System.out.println("Forkert");
        }

    }

    @FXML
    private void handleBtnValg2() throws Exception {
        Databasemetoder.bPressedNum = 2;
        DB.korrektSvarCheck();
        if (Databasemetoder.rSet.get(Databasemetoder.bPressedNum - 1).equals("1")) {
            System.out.println("Korrekt");
        } else {
            System.out.println("Forkert");
        }

    }

    @FXML
    private void handleBtnValg3() throws Exception {
        Databasemetoder.bPressedNum = 3;
        DB.korrektSvarCheck();
        if (Databasemetoder.rSet.get(Databasemetoder.bPressedNum - 1).equals("1")) {
            System.out.println("Korrekt");
        } else {
            System.out.println("Forkert");
        }

    }

    @FXML
    private void handleBtnValg4() throws Exception {
        Databasemetoder.bPressedNum = 4;
        DB.korrektSvarCheck();
        if (Databasemetoder.rSet.get(Databasemetoder.bPressedNum - 1).equals("1")) {
            System.out.println("Korrekt");
        } else {
            System.out.println("Forkert");
        }

    }

    @FXML
    private void NæsteKnap() throws Exception {
        try {
            if (NrSpm < Databasemetoder.spmMængde-1) {
                NrSpm++;
                System.out.println(NrSpm);

                try {
                    showSvarMuligheder();

                } catch (Exception e) {

                }
                try {
                    SpørgsmålView.setText(DB.displaySpm(NrSpm));
                    NrSpm_String = String.valueOf(NrSpm + 1);

                    SpmNr.setText(NrSpm_String);

                } catch (Exception e) {

                }

            }
        } catch (Exception e) {

        }

    }

    @FXML
    private void ForrigeKnap() throws Exception {
        try {
            if (NrSpm > 0) {
                NrSpm--;
                System.out.println(NrSpm);
                try {
                    showSvarMuligheder();
    
                } catch (Exception e) {
    
                }
                try {
                    SpørgsmålView.setText(DB.displaySpm(NrSpm));
                    NrSpm_String = String.valueOf(NrSpm + 1);
    
                    SpmNr.setText(NrSpm_String);
    
                } catch (Exception e) {
    
                }
            }
            
        } catch (Exception e) {

        }

    }
}
