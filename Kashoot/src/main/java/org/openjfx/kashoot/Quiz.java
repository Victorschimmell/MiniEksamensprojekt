/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.kashoot;

/**
 *
 * @author peter
 */
public class Quiz {
    
    private int idQuiz;
    private String quizName;
    private int teacherID;


    /*  public User()
    {}*/
    
    public Quiz(int _idQuiz, String _quizName, int _teacherID) {
        idQuiz= _idQuiz;
        quizName= _quizName;
        teacherID= _teacherID;
    }
    
    //Getter and setter

    public int getIdQuiz() {
        return idQuiz;
    }

    public void setIdUser(int _idQuiz) {
        this.idQuiz = _idQuiz;
    }

    public String getquizName() {
        return quizName;
    }
        

    public int getteacherID() {
        return teacherID;
    }
}


