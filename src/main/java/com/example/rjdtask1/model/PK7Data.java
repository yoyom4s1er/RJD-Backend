package com.example.rjdtask1.model;

public class PK7Data {

    private String sob;
    private int totalCount;
    private int mass3Count;
    private int mass5Count;
    private int mass10Count;
    private int mass20Count;
    private int mass24Count;
    private int mass25Count;
    private int mass30Count;

    public PK7Data(
            String sob,
            int totalCount,
            int mass3Count,
            int mass5Count,
            int mass10Count,
            int mass20Count,
            int mass24Count,
            int mass25Count,
            int mass30Count) {
        this.sob = sob;
        this.totalCount = totalCount;
        this.mass3Count = mass3Count;
        this.mass5Count = mass5Count;
        this.mass10Count = mass10Count;
        this.mass20Count = mass20Count;
        this.mass24Count = mass24Count;
        this.mass25Count = mass25Count;
        this.mass30Count = mass30Count;
    }

    public String getSob() {
        return sob;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getMass3Count() {
        return mass3Count;
    }

    public int getMass5Count() {
        return mass5Count;
    }

    public int getMass10Count() {
        return mass10Count;
    }

    public int getMass20Count() {
        return mass20Count;
    }

    public int getMass24Count() {
        return mass24Count;
    }

    public int getMass25Count() {
        return mass25Count;
    }

    public int getMass30Count() {
        return mass30Count;
    }
}
