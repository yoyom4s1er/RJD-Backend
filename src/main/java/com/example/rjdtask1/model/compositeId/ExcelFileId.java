package com.example.rjdtask1.model.compositeId;

import java.io.Serializable;
import java.util.Objects;

public class ExcelFileId implements Serializable {

    private String tableName;
    private String year;

    public ExcelFileId() {
    }

    public ExcelFileId(String tableName, String year) {
        this.tableName = tableName;
        this.year = year;
    }

    public String getTableName() {
        return tableName;
    }

    public String getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExcelFileId that = (ExcelFileId) o;
        return Objects.equals(tableName, that.tableName) && Objects.equals(year, that.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableName, year);
    }
}
