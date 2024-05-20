package com.example.rjdtask1.model;

public class PK21Data {

    private String adm;
    private String dor;
    private int mass3;
    private int mass5;
    private int mass10;
    private int mass20;
    private int mass24;
    private int mass25;
    private int mass30;

    public PK21Data(String adm, String dor, int mass3, int mass5, int mass10, int mass20, int mass24, int mass25, int mass30) {
        this.adm = adm;
        this.dor = dor;
        this.mass3 = mass3;
        this.mass5 = mass5;
        this.mass10 = mass10;
        this.mass20 = mass20;
        this.mass24 = mass24;
        this.mass25 = mass25;
        this.mass30 = mass30;
    }

    public String getAdm() {
        return adm;
    }

    public String getDor() {
        return dor;
    }

    public int getMass3() {
        return mass3;
    }

    public int getMass5() {
        return mass5;
    }

    public int getMass10() {
        return mass10;
    }

    public int getMass20() {
        return mass20;
    }

    public int getMass24() {
        return mass24;
    }

    public int getMass25() {
        return mass25;
    }

    public int getMass30() {
        return mass30;
    }

    @Override
    public String toString() {
        return "PK21Data{" +
                "adm='" + adm + '\'' +
                ", dor='" + dor + '\'' +
                ", mass3=" + mass3 +
                ", mass5=" + mass5 +
                ", mass10=" + mass10 +
                ", mass20=" + mass20 +
                ", mass24=" + mass24 +
                ", mass25=" + mass25 +
                ", mass30=" + mass30 +
                '}';
    }
}
