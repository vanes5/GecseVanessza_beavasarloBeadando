package com.example.beavasarlasbeadando;

public class Termekek {
    private int id;
    private String nev;
    private int egysegAr;
    private float mennyiseg;
    private String mertekegyseg;
    private double bruttoAr;

    public Termekek(String nev, int egysegAr, float mennyiseg, String mertekegyseg) {
        this.nev = nev;
        this.egysegAr = egysegAr;
        this.mennyiseg = mennyiseg;
        this.mertekegyseg = mertekegyseg;
        this.bruttoAr = (double) Math.round((this.egysegAr*this.mennyiseg)*100)/100;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public int getEgysegAr() {
        return egysegAr;
    }

    public void setEgysegAr(int egysegAr) {
        this.egysegAr = egysegAr;
    }

    public float getMennyiseg() {
        return mennyiseg;
    }

    public void setMennyiseg(float mennyiseg) {
        this.mennyiseg = mennyiseg;
    }

    public String getMertekegyseg() {
        return mertekegyseg;
    }

    public void setMertekegyseg(String mertekegyseg) {
        this.mertekegyseg = mertekegyseg;
    }

    public double getBruttoAr() {
        return bruttoAr;
    }

    public void setBruttoAr(double bruttoAr) {
        this.bruttoAr = bruttoAr;
    }
}
