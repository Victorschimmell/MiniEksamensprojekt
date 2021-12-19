/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.kashoot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Databasemetoder {

    private final String connectionString = "jdbc:sqlite:src/kashootDB.db";
    public static String cMessage;
    public String verifyLogin;
    public static int pNumber;
    public static int bPressedNum;

    public static int CurrentUser;

<<<<<<< HEAD
    public String SvarMValue;
    public static int preInject;
    public static int KorrektSvar;

    //public static int ActiveQuizID; kan sættes som kode i fremtiden, kan indsætte i sql kode under displaySvarMuligheder//
=======
    public static int ActiveQuizID;



>>>>>>> caf665322c62c03b9e7c5e703179d3336756ae70
    public void saveUser(User u) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");
        String sql = null;

        // Skab forbindelse til databasen...
        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            // Skriver fejlhåndtering her
            System.out.println("DB Error: " + e.getMessage());
        }

        if (u.getIdUser() != -1) {
            cMessage = "No username or password inserted";

        } else {
            try {
                if (pNumber == 1) {

                    sql = "INSERT INTO Elev(Navn,Kode) VALUES('" + u.getUsername() + "','" + u.getPassword() + "');";
                    cMessage = "Successful registration";

                } else if (pNumber == 2) {

                    sql = "INSERT INTO Lærer(Navn,Kode) VALUES('" + u.getUsername() + "','" + u.getPassword() + "');";
                    cMessage = "Successful registration";

                }

            } catch (Exception e) {
                System.out.println(e);
            }

            try ( PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.executeUpdate();

                if (pNumber == 1) {
                    App.setRoot("Secondary");
                    pNumber = 3;
                } else if (pNumber == 2) {
                    App.setRoot("third");
                    pNumber = 4;
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                cMessage = "Bruger findes allerede";

            } finally {
                conn.close();
            }
        }

    }

    public boolean verifyLogin(String navn, String kode) throws SQLException, Exception {

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        String query = null;

        if (pNumber == 3) {
            query = "SELECT * FROM elev WHERE  navn = ? AND kode = ?";
        } else if (pNumber == 4) {
            query = "SELECT * FROM lærer WHERE  navn = ? AND kode = ?";
        }

        // Skab forbindelse til databasen...
        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            // Skriver fejlhåndtering her
            System.out.println("DB Error: " + e.getMessage());
        }

        try {
            // Skaber en connection og indsætte sql kode, derefter sætter den de to værdier
            // til at være vores strings og derefter kører den vores query med
            // executeQuery();
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, navn);
            preparedStatement.setString(2, kode);
            rs = preparedStatement.executeQuery();

            // Tjekker hele databasen igennem om der eksisterer et navn og kode, hvis ikke
            // så returner den false
            if (rs.next()) {

                if (pNumber == 3) {
                    query = "SELECT ID FROM Elev WHERE  navn ='" + navn + "'";
                } else if (pNumber == 4) {
                    query = "SELECT ID FROM lærer WHERE  navn ='" + navn + "'";
                }

                try {

                    preparedStatement = conn.prepareStatement(query);
                    rs = preparedStatement.executeQuery();

                    CurrentUser = rs.getInt("ID");
                    System.out.println("Current user ID:    " + CurrentUser);

                } catch (Exception e) {
                    System.out.println(e + "\n" + "No Current user");

                } finally {
                    preparedStatement.close();
                    rs.close();
                    conn.close();

                }

                return true;

            } else {
                cMessage = "Brugernavn eller kodeord er forkert";

                return false;
            }

        } catch (SQLException e) {
            cMessage = "Bruger findes ikke";
            return false;

        } finally {
            preparedStatement.close();
            rs.close();
            conn.close();
        }

    }

    public ArrayList<String> updateQuizTabel() throws SQLException, Exception {
        ArrayList<String> Names = new ArrayList<String>();

        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            conn = DriverManager.getConnection(connectionString);

            ps = conn.prepareStatement("SELECT * FROM Quiz WHERE Lærer_ID ='" + CurrentUser + "'");

            rs = ps.executeQuery();

            try {
                while (rs.next()) {

                    Names.add("Quiznavn: " + rs.getString("navn") + " | " + "QuizID/OpgaveKode: " + rs.getInt("ID"));

                }

            } catch (Exception e) {
                System.out.println("Fejl 2" + e);
            }

        } catch (Exception e) {

        }

        return Names;
    }

    public void newQuiz(Quiz q) throws SQLException, Exception {
        Connection conn = null;
        String sql = null;

        // Skab forbindelse til databasen...
        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            // Skriver fejlhåndtering her
            System.out.println("DB Error: " + e.getMessage());
        }

        if (q.getquizName().isBlank()) {
            System.out.println("No quizname written");

        } else {
            sql = "INSERT INTO Quiz(Navn,Lærer_ID) VALUES('" + q.getquizName() + "','" + q.getteacherID() + "');";

        }
        // Skab forbindelse til databasen...
        try ( PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
            System.out.println("Successfully created a new quiz");
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            conn.close();
        }

    }

    public int getSpmId() {
        Connection conn = null;
        int SpmId = 0;
        String sql;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(connectionString);
            sql = "SELECT ID FROM Spørgsmål ORDER BY ID DESC LIMIT 1";
            try ( PreparedStatement pstmt = conn.prepareStatement(sql)) {
                rs = pstmt.executeQuery();
                SpmId = rs.getInt("ID");
            } catch (Exception e) {
                System.out.println("No ID");
            }
        } catch (Exception e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
        return SpmId;
    }

    public int getQuizId() {
        Connection conn = null;
        int QuizId = 0;
        String sql;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(connectionString);
            sql = "SELECT ID FROM Quiz ORDER BY ID DESC LIMIT 1";
            try ( PreparedStatement pstmt = conn.prepareStatement(sql)) {
                rs = pstmt.executeQuery();
                QuizId = rs.getInt("ID");
            } catch (Exception e) {
                System.out.println("No ID");
            }
        } catch (Exception e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
        return QuizId;
    }

    public void SaveSpm(spm Spm, Svar A, Svar B, Svar C, Svar D) throws SQLException, Exception {
        Connection conn = null;
        String sql = null;
        Svar[] Svar = {A, B, C, D};

        // Skab forbindelse til databasen...
        try {
            conn = DriverManager.getConnection(connectionString);
            sql = "INSERT INTO Spørgsmål(ID_Quiz, Spørgsmål) VALUES('" + Spm.getQuizId() + "','" + Spm.getSpm() + "')";
        } catch (SQLException e) {
            // Skriver fejlhåndtering her
            System.out.println("DB Error: " + e.getMessage());
        }
        // Skab forbindelse til databasen...
        try ( PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();

            try {
                for (int i = 0; i < 4; i++) {

                    sql = "INSERT INTO Svar_Muligheder(ID_Spørgsmål, Svar, Ksvar) VALUES('" + Svar[i].getSpmID() + 1 + "','" + Svar[i].getSvar() + "','" + Svar[i].getsvarK() + "')";
                    try ( PreparedStatement svarst = conn.prepareStatement(sql)) {
                        svarst.executeUpdate();
                        System.out.println("Successfully created a new svar");

                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }

                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println("Successfully created a new spørgsmål");
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            conn.close();
        }

    }
<<<<<<< HEAD

    public List<String> displaySvarMuligheder() throws SQLException, Exception {
        //ActiveQuizID = 7;//

        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        List<String> svarMuligheder = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(connectionString);

            ps = conn.prepareStatement("SELECT Svar_Muligheder.Svar FROM ((Quiz INNER JOIN Spørgsmål ON Spørgsmål.ID_Quiz = Quiz.ID) INNER JOIN Svar_Muligheder ON Svar_Muligheder.ID_Spørgsmål = Spørgsmål.ID) WHERE Svar_Muligheder.ID_Spørgsmål = Spørgsmål.ID AND Spørgsmål.ID_Quiz = Quiz.ID AND Quiz.ID = 29;");

            rs = ps.executeQuery();
            try {
                {
                    while (rs.next()) {
                        svarMuligheder.add(rs.getString("Svar"));
                    }

                }

            } catch (Exception e) {
                System.out.println("DB Error 1" + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("DB Error 1" + e.getMessage());
        } finally {
            conn.close();
            rs.close();
            ps.close();
        }
        return svarMuligheder;
    }

    public void korrektSvarCheck() throws SQLException, Exception {
        //ActiveQuizID = 7;//
        Connection conn = null;
        String sql = null;
        ResultSet rs = null;

        // Skab forbindelse til databasen...
        try {
            conn = DriverManager.getConnection(connectionString);

            if (bPressedNum == 1) {
                sql = "SELECT Svar_Muligheder.Ksvar FROM ((Quiz INNER JOIN Spørgsmål ON Spørgsmål.ID_Quiz = Quiz.ID) INNER JOIN Svar_Muligheder ON Svar_Muligheder.ID_Spørgsmål = Spørgsmål.ID) WHERE Svar_Muligheder.ID_Spørgsmål = Spørgsmål.ID AND Spørgsmål.ID_Quiz = Quiz.ID AND Quiz.ID = 29 AND Svar_Muligheder.ID = 1; ";
            } else if (bPressedNum == 2) {
                sql = "SELECT Svar_Muligheder.Ksvar FROM ((Quiz INNER JOIN Spørgsmål ON Spørgsmål.ID_Quiz = Quiz.ID) INNER JOIN Svar_Muligheder ON Svar_Muligheder.ID_Spørgsmål = Spørgsmål.ID) WHERE Svar_Muligheder.ID_Spørgsmål = Spørgsmål.ID AND Spørgsmål.ID_Quiz = Quiz.ID AND Quiz.ID = 29 AND Svar_Muligheder.ID = 2; ";
            } else if (bPressedNum == 3) {
                sql = "SELECT Svar_Muligheder.Ksvar FROM ((Quiz INNER JOIN Spørgsmål ON Spørgsmål.ID_Quiz = Quiz.ID) INNER JOIN Svar_Muligheder ON Svar_Muligheder.ID_Spørgsmål = Spørgsmål.ID) WHERE Svar_Muligheder.ID_Spørgsmål = Spørgsmål.ID AND Spørgsmål.ID_Quiz = Quiz.ID AND Quiz.ID = 29 AND Svar_Muligheder.ID = 3; ";
            } else if (bPressedNum == 4) {
                sql = "SELECT Svar_Muligheder.Ksvar FROM ((Quiz INNER JOIN Spørgsmål ON Spørgsmål.ID_Quiz = Quiz.ID) INNER JOIN Svar_Muligheder ON Svar_Muligheder.ID_Spørgsmål = Spørgsmål.ID) WHERE Svar_Muligheder.ID_Spørgsmål = Spørgsmål.ID AND Spørgsmål.ID_Quiz = Quiz.ID AND Quiz.ID = 29 AND Svar_Muligheder.ID = 4; ";
            }

            try ( PreparedStatement pstmt = conn.prepareStatement(sql)) {
                rs = pstmt.executeQuery();
                KorrektSvar = rs.getInt("Ksvar");
               
            } catch (Exception e) {
                System.out.println("Failure in program");
            }

        } catch (SQLException e) {
            // Skriver fejlhåndtering her
            System.out.println("DB Error: " + e.getMessage());
        }
            
        finally{
            rs.close();
        }
    }
=======

    public List<String> displaySvarMuligheder() throws SQLException, Exception {
        //ActiveQuizID = 7;//

        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        String s;

        List<String> svarMuligheder = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(connectionString);

            ps = conn.prepareStatement("SELECT Svar_Muligheder.Svar FROM ((Quiz INNER JOIN Spørgsmål ON Spørgsmål.ID_Quiz = Quiz.ID) INNER JOIN Svar_Muligheder ON Svar_Muligheder.ID_Spørgsmål = Spørgsmål.ID) WHERE Svar_Muligheder.ID_Spørgsmål = Spørgsmål.ID AND Spørgsmål.ID_Quiz = Quiz.ID;");

            rs = ps.executeQuery();
            try {
                {
                    while (rs.next()) {
                        svarMuligheder.add(rs.getString("Svar"));
                    }

                }

            } catch (Exception e) {
                System.out.println("DB Error 1" + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("DB Error 1" + e.getMessage());
        }
        return svarMuligheder;
    }

>>>>>>> caf665322c62c03b9e7c5e703179d3336756ae70
}
