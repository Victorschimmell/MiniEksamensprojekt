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
public class spm {

    private int IDQuiz;
    private String Spørgsmål;

    public spm(int _idQuiz, String _Spørgsmål) {
        IDQuiz = _idQuiz;
        Spørgsmål = _Spørgsmål;
    }

    public int getQuizId() {
        return IDQuiz;
    }

    public String getSpm() {
        return Spørgsmål;
    }
  
    }

