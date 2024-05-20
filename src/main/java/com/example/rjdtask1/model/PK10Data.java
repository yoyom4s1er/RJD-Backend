package com.example.rjdtask1.model;

public class PK10Data {

    private String adm;
    private String num;
    private String masbr;
    private boolean kontrol;

    public PK10Data(String adm, String num, String masbr, boolean kontrol) {
        this.adm = adm;
        this.num = num;
        this.masbr = masbr;
        this.kontrol = kontrol;
    }

    public String getAdm() {
        return adm;
    }

    public String getNum() {
        return num;
    }

    public String getMasbr() {
        return masbr.replaceAll("\\s+","");
    }

    public boolean isKontrol() {
        return kontrol;
    }
}
