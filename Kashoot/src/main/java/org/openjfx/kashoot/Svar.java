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

    private int idQuiz;
    private String spørgsmål;

    public spm(int _idQuiz, String _spørgsmål ) {
        idQuiz = _idQuiz;
        spørgsmål = _spørgsmål;

    }

    public int getIdQuiz() {
        return idQuiz;
    }

    public String getSpm() {
        return spørgsmål;
    }
}
