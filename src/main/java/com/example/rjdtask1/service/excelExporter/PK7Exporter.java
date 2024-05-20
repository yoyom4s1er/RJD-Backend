package com.example.rjdtask1.service.excelExporter;

import com.example.rjdtask1.model.ExcelFile;
import com.example.rjdtask1.model.PK7Data;
import com.example.rjdtask1.model.compositeId.ExcelFileId;
import com.example.rjdtask1.repository.ExcelFileRepository;
import com.example.rjdtask1.repository.PK7Repository;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PK7Exporter {

    private final PK7Repository pk7Repository;
    private final ExcelFileRepository excelFileRepository;

    private final Map<String, Integer> rowIndexes;

    public PK7Exporter(PK7Repository pk7Repository, ExcelFileRepository excelFileRepository) {
        this.pk7Repository = pk7Repository;
        this.excelFileRepository = excelFileRepository;

        rowIndexes = new HashMap<>();
        rowIndexes.put(null, 25);
        rowIndexes.put("57", 9);
        rowIndexes.put("58", 10);
        rowIndexes.put("21", 11);
        rowIndexes.put("28", 12);
        rowIndexes.put("27", 13);
        rowIndexes.put("59", 14);
        rowIndexes.put("23", 15);
        rowIndexes.put("20", 16);
        rowIndexes.put("66", 17);
        rowIndexes.put("67", 18);
        rowIndexes.put("29", 19);
        rowIndexes.put("22", 20);
        rowIndexes.put("25", 21);
        rowIndexes.put("24", 22);
        rowIndexes.put("26", 23);
        rowIndexes.put("", 24);
    }

    public void exportToStream(ByteArrayOutputStream stream, String year, boolean forceGenerate) throws IOException {
        if (!forceGenerate) {
            ExcelFileId excelFileId = new ExcelFileId("PK7", year);
            if (excelFileRepository.existsById(excelFileId)) {
                stream.write(excelFileRepository.getReferenceById(excelFileId).getFileData());
                return;
            }
        }

        File file = ResourceUtils.getFile("classpath:PK_7.xlsx");
        InputStream inputStream = new FileInputStream(file);
        XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(inputStream);

        writeDataLines(workbook.getSheetAt(0), pk7Repository.getQueryResult(year));

        XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);

        workbook.write(stream);
        excelFileRepository.save(new ExcelFile("PK7", year, stream.toByteArray()));
        workbook.close();
    }

    private void writeDataLines(XSSFSheet sheetAt, List<PK7Data> queryResult) {

        for (var data:
             queryResult) {
            createCells(sheetAt.getRow(rowIndexes.get(data.getSob())), data);
        }
    }

    private void createCells(XSSFRow row, PK7Data data) {
        createCell(row, 1, data.getTotalCount());
        createCell(row, 2, data.getMass3Count());
        createCell(row, 3, data.getMass5Count());
        createCell(row, 5, data.getMass10Count());
        createCell(row, 6, data.getMass20Count());
        createCell(row, 7, data.getMass24Count());
        createCell(row, 8, data.getMass25Count());
        createCell(row, 9, data.getMass30Count());
    }

    protected void createCell(XSSFRow row, int columnCount, Object value) {
        XSSFCell cell = row.getCell(columnCount);

        if (cell == null) {
            cell = row.createCell(columnCount);
        }
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else if (value instanceof Long){
            cell.setCellValue((Long) value);
        } else if (value instanceof Float){
            cell.setCellValue((Float) value);
        } else {
            cell.setCellValue((String) value);
        }
    }
}
