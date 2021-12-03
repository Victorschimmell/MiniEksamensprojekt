/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.kashoot;
import java.sql.*;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
 *
 * @author peter
 */
public class Databasemetoder {
    
        private final String connectionString = "jdbc:sqlite:src/kashootDB.db";
        public String cMessage;
        public String verifyLogin;
        public static int pNumber;

        

    public void saveUser(User u) throws SQLException, Exception 
    {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");
        String sql = null;
        
        
        //Skab forbindelse til databasen...
        try {          
          conn = DriverManager.getConnection(connectionString);
        } 
        catch ( SQLException e ) {
          //Skriver fejlhåndtering her
          System.out.println("DB Error: " + e.getMessage());
        }
        

        if(u.getIdUser() != -1)
        {
                  cMessage = "No username or password inserted";  
                  
        }else{
            
            if(pNumber == 1){
             sql = "INSERT INTO Elev(Navn,Kode) VALUES('" + u.getUsername() + "','" + u.getPassword() + "');";
             cMessage = "Successful registration"; 
            }else if(pNumber == 2){
             sql = "INSERT INTO Lærer(Navn,Kode) VALUES('" + u.getUsername() + "','" + u.getPassword() + "');";
             cMessage = "Successful registration"; 
        
            }
        
        }
         
             
 
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            cMessage = "Username already exists";
        
    }
      
    }
    
    
    
        public boolean verifyLogin(String navn, String kode) throws SQLException, Exception{
        
            
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        String query = "select * from elev where navn = ? and kode = ?";
        
        //Skab forbindelse til databasen...
        try {          
          conn = DriverManager.getConnection(connectionString);
        } 
        catch ( SQLException e ) {
          //Skriver fejlhåndtering her
          System.out.println("DB Error: " + e.getMessage());
        }
        
        
            try{ 
            //Skaber en connection og indsætte sql kode, derefter sætter den de to værdier til at være vores strings og derefter kører den vores query med executeQuery();
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, navn);
            preparedStatement.setString(2, kode);  
            rs = preparedStatement.executeQuery();

            //Tjekker hele databasen igennem om der eksisterer et navn og kode, hvis ikke så returner den false    
            if(rs.next()) { 
            return true;
            }else{
            return false;
            } 
        
        }catch (Exception e) {
            return false;
            
            
            //finally gør så at man kan kører kode ligegyldigt om koden førhen har fejlet i en try catch, hvilket gør så at recordset og mit preparedstatement lukker ligegyldigt hvad
        } finally {
            preparedStatement.close();
            rs.close();
    }
    }
}
        
            /* while (rs.next()) {
                return true;
                
            }
            rs.close();
        }
        catch ( SQLException e ) {
            //Skrive fejlhåndtering her
            System.out.println("DB Error: " + e.getMessage());
            verifyLogin = "Username or password are incorrect";
            
        }
        //Luk forbindelsen til databasen
        conn.close();
        return false;
    

        } 
}*/