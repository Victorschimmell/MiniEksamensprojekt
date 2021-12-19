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
public class Svar {

    private int SpmID;
    private String svarMulighed;
    private int svarK;

    public Svar(int _SpmID, String _svarMmulighed, Boolean _svarK) {
        SpmID = _SpmID;
        svarMulighed = _svarMmulighed;

        if(_svarK) {
            svarK = 1;
        } else if(!_svarK) {
            svarK= 0;
        } else {
            System.out.println("Something fukking wrong with svarK");
        }
        
    }
    public int getSpmID() {
        return SpmID+1;
    }
    public String getSvar() {
        return svarMulighed;
    }
    public int getsvarK() {
        return svarK;
    }
}
