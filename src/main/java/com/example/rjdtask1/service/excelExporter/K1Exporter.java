package com.example.rjdtask1.service.excelExporter;

import com.example.rjdtask1.model.Classifier1;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class K1Exporter extends TableExcelExporter<Classifier1>{

    @Override
    protected List<String> getHeaderNames() {
        return List.of(
                "Id",
                "Column2",
                "Column3",
                "Column4",
                "Column5",
                "Column6",
                "Column7"
        );
    }

    @Override
    protected void createCells(XSSFRow row, Classifier1 entity, XSSFCellStyle style) {
        createCell(row, 0, entity.getId(), style);
        createCell(row, 1, entity.getColumn2(), style);
        createCell(row, 2, entity.getColumn3(), style);
        createCell(row, 3, entity.getColumn4(), style);
        createCell(row, 4, entity.getColumn5(), style);
        createCell(row, 5, entity.getColumn6(), style);
        createCell(row, 6, entity.getColumn7(), style);
    }
}
