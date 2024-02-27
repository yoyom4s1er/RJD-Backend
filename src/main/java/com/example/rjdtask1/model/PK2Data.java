package com.example.rjdtask1.model;

public class PK2Data {

    private String vidK;
    private String sName;
    private String prinadl;
    private int containerCount;
    private float containerCountPercentage;
    private int mass3Count;
    private int mass5Count;
    private int stkCount;
    private float stkCountPercentage;
    private int mass10Count;
    private int mass20Count;
    private int mass24Count;
    private int mass25Count;
    private int mass30Count;
    private int ktkCount;
    private float ktkCountPercentage;

    public PK2Data(
            String vidK,
            String sName,
            String prinadl,
            int containerCount,
            int mass3Count,
            int mass5Count,
            int mass10Count,
            int mass20Count,
            int mass24Count,
            int mass25Count,
            int mass30Count) {
        this.vidK = vidK;
        this.sName = sName;
        this.prinadl = prinadl;
        this.containerCount = containerCount;
        this.mass3Count = mass3Count;
        this.mass5Count = mass5Count;
        this.mass10Count = mass10Count;
        this.mass20Count = mass20Count;
        this.mass24Count = mass24Count;
        this.mass25Count = mass25Count;
        this.mass30Count = mass30Count;

        stkCount = mass3Count + mass5Count;
        ktkCount = mass10Count + mass20Count + mass24Count + mass25Count + mass30Count;

        if (containerCount != 0) {
            stkCountPercentage = ((float) stkCount / containerCount) * 100;
            ktkCountPercentage = ((float) ktkCount / containerCount) * 100;
        }
    }

    public PK2Data() {
    }

    public String getVidK() {
        return vidK;
    }

    public void setVidK(String vidK) {
        this.vidK = vidK;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getPrinadl() {
        return prinadl;
    }

    public void setPrinadl(String prinadl) {
        this.prinadl = prinadl;
    }

    public int getContainerCount() {
        return containerCount;
    }

    public void setContainerCount(int containerCount) {
        this.containerCount = containerCount;
    }

    public float getContainerCountPercentage() {
        return containerCountPercentage;
    }

    public void setContainerCountPercentage(float containerCountPercentage) {
        this.containerCountPercentage = containerCountPercentage;
    }

    public int getMass3Count() {
        return mass3Count;
    }

    public void setMass3Count(int mass3Count) {
        this.mass3Count = mass3Count;
    }

    public int getMass5Count() {
        return mass5Count;
    }

    public void setMass5Count(int mass5Count) {
        this.mass5Count = mass5Count;
    }

    public int getStkCount() {
        return stkCount;
    }

    public void setStkCount(int stkCount) {
        this.stkCount = stkCount;
    }

    public float getStkCountPercentage() {
        return stkCountPercentage;
    }

    public void setStkCountPercentage(float stkCountPercentage) {
        this.stkCountPercentage = stkCountPercentage;
    }

    public int getMass10Count() {
        return mass10Count;
    }

    public void setMass10Count(int mass10Count) {
        this.mass10Count = mass10Count;
    }

    public int getMass20Count() {
        return mass20Count;
    }

    public void setMass20Count(int mass20Count) {
        this.mass20Count = mass20Count;
    }

    public int getMass24Count() {
        return mass24Count;
    }

    public void setMass24Count(int mass24Count) {
        this.mass24Count = mass24Count;
    }

    public int getMass25Count() {
        return mass25Count;
    }

    public void setMass25Count(int mass25Count) {
        this.mass25Count = mass25Count;
    }

    public int getMass30Count() {
        return mass30Count;
    }

    public void setMass30Count(int mass30Count) {
        this.mass30Count = mass30Count;
    }

    public int getKtkCount() {
        return ktkCount;
    }

    public void setKtkCount(int ktkCount) {
        this.ktkCount = ktkCount;
    }

    public float getKtkCountPercentage() {
        return ktkCountPercentage;
    }

    public void setKtkCountPercentage(float ktkCountPercentage) {
        this.ktkCountPercentage = ktkCountPercentage;
    }

    public void addFromOther(PK2Data row) {
        containerCount += row.containerCount;
        mass3Count += row.mass3Count;
        mass5Count += row.mass5Count;
        mass10Count += row.mass10Count;
        mass20Count += row.mass20Count;
        mass24Count += row.mass24Count;
        mass25Count += row.mass25Count;
        mass30Count += row.mass30Count;

        stkCount = mass3Count + mass5Count;
        ktkCount = mass10Count + mass20Count + mass24Count + mass25Count + mass30Count;

        if (containerCount != 0) {
            stkCountPercentage = ((float) stkCount / containerCount) * 100;
            ktkCountPercentage = ((float) ktkCount / containerCount) * 100;
        }
    }

    @Override
    public String toString() {
        return "PK_2Row{" +
                "vidK='" + vidK + '\'' +
                ", sName='" + sName + '\'' +
                ", prinadl='" + prinadl + '\'' +
                ", containerCount=" + containerCount +
                ", containerCountPercentage=" + containerCountPercentage +
                ", mass3Count=" + mass3Count +
                ", mass5Count=" + mass5Count +
                ", stkCount=" + stkCount +
                ", stkCountPercentage=" + stkCountPercentage +
                ", mass10Count=" + mass10Count +
                ", mass20Count=" + mass20Count +
                ", mass24Count=" + mass24Count +
                ", mass25Count=" + mass25Count +
                ", mass30Count=" + mass30Count +
                ", ktkCount=" + ktkCount +
                ", ktkCountPercentage=" + ktkCountPercentage +
                '}';
    }
}
