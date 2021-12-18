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

    private String quizName;
    private int teacherID;
    private static int quizId;

    /*
     * public User()
     * {}
     */

    public Quiz(String _quizName, int _teacherID, int _quizId) {
        quizName = _quizName;
        teacherID = _teacherID;
        quizId = _quizId;
    }

    public String getquizName() {
        return quizName;
    }
    public int getteacherID() {
        return teacherID;
    }
    public static int getquizId() {
        return quizId;
    }


}
