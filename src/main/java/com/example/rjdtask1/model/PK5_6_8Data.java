package com.example.rjdtask1.model;

public class PK5_6_8Data {

    private String country;
    private String railWay;
    private String owner;
    private int wagonCount;

    public PK5_6_8Data(String country, String railWay, String owner, int wagonCount) {
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

    @Override
    public String toString() {
        return "PK5_6_8Data{" +
                "country='" + country + '\'' +
                ", railWay='" + railWay + '\'' +
                ", owner='" + owner + '\'' +
                ", wagonCount=" + wagonCount +
                '}';
    }
}
