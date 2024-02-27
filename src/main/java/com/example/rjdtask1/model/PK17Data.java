package com.example.rjdtask1.model;

public class PK17Data {
    private String year;
    private String adm;
    private String num;
    private String b_ind;
    private String masbr;

    public PK17Data(String year, String adm, String num, String b_ind, String masbr) {
        this.year = year;
        this.adm = adm;
        this.num = num;
        this.b_ind = b_ind;
        this.masbr = masbr;
    }

    public String getYear() {
        return year;
    }

    public String getAdm() {
        return adm;
    }

    public String getNum() {
        return num;
    }

    public String getB_ind() {
        return b_ind;
    }

    public String getMasbr() {
        return masbr;
    }
}
