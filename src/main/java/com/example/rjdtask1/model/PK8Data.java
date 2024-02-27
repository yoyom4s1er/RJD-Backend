package com.example.rjdtask1.model;

public class PK8Data {

    private String country;
    private String railWay;
    private String owner;
    private int wagonCount;

    public PK8Data(String country, String railWay, String owner, int wagonCount) {
        this.country = country;
        this.railWay = railWay;
        this.owner = owner;
        this.wagonCount = wagonCount;
    }

    public String getCountry() {
        return country;
    }

    public String getRailWay() {
        return railWay;
    }

    public String getOwner() {
        return owner;
    }

    public int getWagonCount() {
        return wagonCount;
    }
}
