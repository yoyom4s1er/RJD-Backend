package com.example.rjdtask1.service.excelExporter;

import com.example.rjdtask1.model.Directory1;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Directory1Exporter extends TableExcelExporter<Directory1>{
    @Override
    protected List<String> getHeaderNames() {
        return List.of(
                "Id",
                "Column2",
                "Column3",
                "Column4",
                "Column5"
        );
    }

    @Override
    protected void createCells(XSSFRow row, Directory1 entity, XSSFCellStyle style) {
        createCell(row, 0, entity.getId(), style);
        createCell(row, 1, entity.getColumn2(), style);
        createCell(row, 2, entity.getColumn3(), style);
        createCell(row, 3, entity.getColumn4(), style);
        createCell(row, 4, entity.getColumn5(), style);
    }
}
