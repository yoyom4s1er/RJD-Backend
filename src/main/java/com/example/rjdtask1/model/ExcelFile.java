package com.example.rjdtask1.model;

import com.example.rjdtask1.model.compositeId.ExcelFileId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "excel_files", schema = "perkon")
@IdClass(ExcelFileId.class)
public class ExcelFile {

    @Id
    private String tableName;
    @Id
    private String year;

    @Column(name = "biranyArray")
    private byte[] fileData;
}
