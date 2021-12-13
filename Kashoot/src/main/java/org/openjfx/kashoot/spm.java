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

    private int idSpm;
    private int idQuiz;
    private String spørgsmål;
    private int Ksvar;

    /*
     * public User()
     * {}
     */

    public spm(int _idSpm, int _idQuiz, String _spørgsmål, int _Ksvar) {
        idSpm = _idSpm;
        idQuiz = _idQuiz;
        spørgsmål = _spørgsmål;
        Ksvar = _Ksvar;
    }

    // Getter and setter
    public int getIdSpm() {
        return idSpm;
    }

    public int getIdQuiz() {
        return idQuiz;
    }

    public void setIdSpm(int _idSpm) {
        this.idSpm = _idSpm;
    }

    public int getKsvar() {
        return Ksvar;
    }

    public String getSpm() {
        return spørgsmål;
    }
}
