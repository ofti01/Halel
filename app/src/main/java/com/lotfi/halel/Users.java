package com.lotfi.halel;

/**
 * Created by Lotfi_pc on 15/08/2017.
 */

public class Users {

    private String pseudo,name,email,motpass;

    public Users(){

    }
    public Users(String pseudo,String email,String motpass){
        this.pseudo=pseudo;
        this.email=email;
        this.motpass=motpass;
    }



    public String getpseudo() {
        return pseudo;
    }

    public void setpseudo(String uid) {
        this.pseudo = uid;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getmotpass() {
        return motpass;
    }

    public void setpass(String pass) {
        this.motpass = pass;
    }
}