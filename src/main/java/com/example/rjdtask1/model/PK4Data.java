package com.example.rjdtask1.model;

public class PK4Data {

    private String  adm;
    private String mesto;
    private String sob;
    private int count;

    public PK4Data(String adm, String mesto, String sob, int count) {
        this.adm = adm;
        this.mesto = mesto;
        this.sob = sob;
        this.count = count;
    }

    public String getAdm() {
        return adm;
    }

    public String getMesto() {
        return mesto;
    }

    public String getSob() {
        return sob;
    }

    public int getCount() {
        return count;
    }
}
