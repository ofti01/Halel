package com.lotfi.halel.Location;

/**
 * Created by Lotfi_pc on 09/09/2017.
 */
public class Halel {

    private String nomc,tel,type;
   private double lati,longi;
    public Halel(){

    }
    public Halel(double lati,double longi,String nomc,String tel,String type ){
        this.lati=lati;
        this.longi=longi;
        this.nomc=nomc;
        this.tel=tel;
        this.type=type;
    }

    public Double getlati() {
        return lati;
    }

    public void setlati(double uid) {
        this.lati = uid;
    }
    public Double getlongi() {
        return longi;
    }

    public void setlongi(Double uid) {
        this.longi = uid;
    }

    public String getnomc() {
        return nomc;
    }

    public void setnomc(String uid) {
        this.nomc = uid;
    }



    public String gettel() {
        return tel;
    }

    public void settel(String email) {
        this.tel = email;
    }
    public String gettype() {
        return type;
    }

    public void settype(String pass) {
        this.type = pass;
    }
}