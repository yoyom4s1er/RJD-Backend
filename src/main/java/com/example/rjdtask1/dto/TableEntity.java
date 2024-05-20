package com.example.rjdtask1.dto;

public class TableEntity {

    private String name;
    private String year;

    private String table;

    public TableEntity() {
    }

    public TableEntity(String name, String year, String table) {
        this.name = name;
        this.year = year;
        this.table = table;
    }

    public String getName() {
        return name;
    }

    public String getYear() {
        return year;
    }

    public String getTable() {
        return table.toUpperCase();
    }
}
