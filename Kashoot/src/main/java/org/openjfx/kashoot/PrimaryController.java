package org.openjfx.kashoot;

import java.io.IOException;
import java.lang.reflect.Array;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;
import javafx.scene.control.TextArea;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.CheckBox;

public class PrimaryController implements Initializable {

    @FXML
    private TextField txtUsername1, txtUsername2, txtLog1Username, txtLog1Password, txtLog2Username, txtLog2Password,
            SvarmulighedA, SvarmulighedB, SvarmulighedC, SvarmulighedD, quiznavn, IDField;
    @FXML
    private PasswordField txtPassword1, txtPassword1Confirm, txtPassword2, txtPassword2Confirm;
    @FXML
    private ListView<String> OpgaveList, SpørgsmålList;
    @FXML
    private TextArea IndskrivSpm;
    @FXML
    private CheckBox AKorrekt, BKorrekt, CKorrekt, DKorrekt;

    @FXML
    private Text UserConfirm, QuizIDdis, SpørgsmålView, SpmNr, xRigtige;
    @FXML
    private Label verifyLogin;

    @FXML
    private ToggleButton SvarKnap1, SvarKnap2, SvarKnap3, SvarKnap4;

    public static String KodeQuiz;

    private int NrSpm;
    private String NrSpm_String;

    private int knap1 = 0, knap2 = 1, knap3 = 2, knap4 = 3;

    private static ArrayList<String[]> ValgteSvar = new ArrayList<String[]>();

    private static ArrayList<String> MineSvar = new ArrayList<String>();

    private static int point;

    Databasemetoder DB = new Databasemetoder();

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

            SpmNr.setText(NrSpm_String + " / " + Databasemetoder.spmMængde);

            DB.korrektSvarCheck();

        } catch (Exception e) {

        }

        try {
            if (App.CurrentRoot.equals("fourth")) {

                point = 0;
                MineSvar.clear();

                try {
                    for (int i = 0; i < ValgteSvar.size(); i++) {
                        for (int j = 0; j < ValgteSvar.get(i).length; j++) {

                            MineSvar.add((String) Array.get(ValgteSvar.get(i), j));

                        }
                    }

                    System.out.println(MineSvar);
                    System.out.println(Databasemetoder.rSet);

                    try {
                        System.out.println(MineSvar.toArray());
                        for (int i = 0; i < Databasemetoder.rSet.size(); i++) {
                            if (MineSvar.get(i).equals(Databasemetoder.rSet.get(i))) {
                                if (MineSvar.get(i).equals("1") && Databasemetoder.rSet.get(i).equals("1")) {

                                    point++;
                                    System.out.println(point);
                                }
                            } else {
                                if (MineSvar.get(i).equals("1") && Databasemetoder.rSet.get(i).equals("0")
                                        || Databasemetoder.rSet.get(i).equals("1") && MineSvar.get(i).equals("0")) {

                                    if (point == 0) {

                                    } else {
                                        point--;
                                    }

                                    System.out.println(point);
                                }
                            }

                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage() + " Problemer med sammenligning");
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage() + " Problemer med MineSvar");
                }

                double perC;
                perC = point * 100 / Databasemetoder.AlleRigtige;
                xRigtige.setText(point + "/" + Databasemetoder.AlleRigtige + " Rigtige!" + "\n" + perC + "%");

            }

        } catch (

        Exception e) {
            System.out.println(e.getMessage() + " Hejsa");
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
        SvarKnap1.setText(SvarMList.get(knap1));
        SvarKnap2.setText(SvarMList.get(knap2));
        SvarKnap3.setText(SvarMList.get(knap3));
        SvarKnap4.setText(SvarMList.get(knap4));

    }

    @FXML
    private void handleValgBtn() throws Exception {
        ColorSwitch();

    }

    @FXML
    private void NæsteKnap() throws Exception {
        try {

            if (NrSpm < Databasemetoder.spmMængde) {

                try {
                    ValgteSvar.set(NrSpm,
                            new String[] { TrueOrFalse(SvarKnap1.isSelected()), TrueOrFalse(SvarKnap2.isSelected()),
                                    TrueOrFalse(SvarKnap3.isSelected()), TrueOrFalse(SvarKnap4.isSelected()) });
                    System.out.println("Arraylist Overwritten");

                } catch (Exception e) {

                    ValgteSvar.add(NrSpm,
                            new String[] { TrueOrFalse(SvarKnap1.isSelected()), TrueOrFalse(SvarKnap2.isSelected()),
                                    TrueOrFalse(SvarKnap3.isSelected()), TrueOrFalse(SvarKnap4.isSelected()) });
                    System.out.println("Arraylist added missing item");

                }
                /*
                 * DEBUG USE
                 * for (String i[] : ValgteSvar) {
                 * System.out.println(Arrays.toString(i));
                 * }
                 */
                try {
                    knap1 += 4;
                    knap2 += 4;
                    knap3 += 4;
                    knap4 += 4;

                    NrSpm++;

                } catch (Exception e) {
                    System.out.println(e);
                }

                System.out.println(NrSpm);

                try {
                    showSvarMuligheder();

                } catch (Exception e) {

                }
                try {
                    SpørgsmålView.setText(DB.displaySpm(NrSpm));
                    NrSpm_String = String.valueOf(NrSpm + 1);

                    SpmNr.setText(NrSpm_String + " / " + Databasemetoder.spmMængde);

                } catch (Exception e) {

                }
                if (NrSpm >= Databasemetoder.spmMængde) {
                    App.setRoot("fourth");
                }

            } else {
                App.setRoot("fourth");
            }

        } catch (Exception e) {

        }

        SvarKnap1.setSelected(false);
        SvarKnap2.setSelected(false);
        SvarKnap3.setSelected(false);
        SvarKnap4.setSelected(false);

        ColorSwitch();

    }

    @FXML
    private void ForrigeKnap() throws Exception {
        try {
            if (NrSpm > 0) {
                NrSpm--;
                try {
                    knap1 -= 4;
                    knap2 -= 4;
                    knap3 -= 4;
                    knap4 -= 4;

                } catch (Exception e) {
                    System.out.println(e);
                }

                System.out.println(NrSpm);
                try {
                    showSvarMuligheder();

                } catch (Exception e) {

                }
                try {
                    SpørgsmålView.setText(DB.displaySpm(NrSpm));
                    NrSpm_String = String.valueOf(NrSpm + 1);

                    SpmNr.setText(NrSpm_String + " / " + Databasemetoder.spmMængde);

                } catch (Exception e) {

                }
            }

        } catch (Exception e) {

        }
        SvarKnap1.setSelected(false);
        SvarKnap2.setSelected(false);
        SvarKnap3.setSelected(false);
        SvarKnap4.setSelected(false);

        ColorSwitch();

    }

    @FXML
    public void ColorSwitch() throws Exception {

        if (SvarKnap1.isSelected()) {
            SvarKnap1.setStyle("-fx-background-color: #4ca122");
        } else {
            SvarKnap1.setStyle("-fx-background-color: #66BF39");
        }

        if (SvarKnap2.isSelected()) {
            SvarKnap2.setStyle("-fx-background-color: #298acf");
        } else {
            SvarKnap2.setStyle("-fx-background-color: #45A3E5");
        }

        if (SvarKnap3.isSelected()) {
            SvarKnap3.setStyle("-fx-background-color: #e8ae07");
        } else {
            SvarKnap3.setStyle("-fx-background-color: #FFC00A");
        }

        if (SvarKnap4.isSelected()) {
            SvarKnap4.setStyle("-fx-background-color: #a10a22");
        } else {
            SvarKnap4.setStyle("-fx-background-color: #FF3355");
        }
    }

    public String TrueOrFalse(Boolean value) {
        String INT = "0";

        if (value) {
            INT = "1";
        } else if (!value) {
            INT = "0";
        }
        return INT;
    }

}
