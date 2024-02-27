package com.example.rjdtask1.repository;

import com.example.rjdtask1.model.ExcelFile;
import com.example.rjdtask1.model.compositeId.ExcelFileId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExcelFileRepository extends JpaRepository<ExcelFile, ExcelFileId> {
}
