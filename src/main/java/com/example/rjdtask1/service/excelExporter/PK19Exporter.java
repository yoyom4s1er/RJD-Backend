package com.example.rjdtask1.service.excelExporter;

import com.example.rjdtask1.model.ExcelFile;
import com.example.rjdtask1.model.PK19Data;
import com.example.rjdtask1.model.compositeId.ExcelFileId;
import com.example.rjdtask1.repository.ExcelFileRepository;
import com.example.rjdtask1.repository.PK19Repository;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PK19Exporter {
    private final ExcelFileRepository excelFileRepository;
    private final PK19Repository pk19Repository;
    private final Map<String, Integer> rowIndexes;
    private final Map<Integer, Integer> columnIndexes;

    public PK19Exporter(ExcelFileRepository excelFileRepository, PK19Repository pk19Repository) {
        this.excelFileRepository = excelFileRepository;
        this.pk19Repository = pk19Repository;

        rowIndexes = new HashMap<>();
        rowIndexes.put("57", 9);
        rowIndexes.put("58", 10);
        rowIndexes.put("21", 11);
        rowIndexes.put("27", 12);
        rowIndexes.put("59", 13);
        rowIndexes.put("23", 14);
        rowIndexes.put("20", 15);
        rowIndexes.put("66", 16);
        rowIndexes.put("67", 17);
        rowIndexes.put("29", 18);
        rowIndexes.put("22", 19);
        rowIndexes.put("28", 20);
        rowIndexes.put("25", 21);
        rowIndexes.put("24", 22);
        rowIndexes.put("26", 23);
        rowIndexes.put(null, 24);

        columnIndexes = new HashMap<>();
    }

    public void exportToStream(ByteArrayOutputStream stream, String year, boolean forceGenerate) throws IOException {
        if (!forceGenerate) {
            ExcelFileId excelFileId = new ExcelFileId("PK19", year);
            if (excelFileRepository.existsById(excelFileId)) {
                stream.write(excelFileRepository.getReferenceById(excelFileId).getFileData());
                return;
            }
        }

        File file = ResourceUtils.getFile("classpath:PK_19.xlsx");
        InputStream inputStream = new FileInputStream(file);
        XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(inputStream);

        initColumnIndexes(Integer.parseInt(year));

        writeDataLines(workbook.getSheetAt(0), initObjects(pk19Repository.getQueryResult(year)), Integer.parseInt(year));
        writeDataLines(workbook.getSheetAt(1), initObjects(pk19Repository.selectByKTK(year)), Integer.parseInt(year));
        writeDataLines(workbook.getSheetAt(2), initObjects(pk19Repository.selectBySTK(year)), Integer.parseInt(year));

        XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);

        workbook.write(stream);
        excelFileRepository.save(new ExcelFile("PK19", year, stream.toByteArray()));
        workbook.close();
    }

    private void writeDataLines(XSSFSheet sheetAt, List<PK19Row> queryResult, int year) {
        int yearOffset = 21;
        sheetAt.getRow(6).getCell(2).setCellValue("до " + (year - yearOffset));
        for (int i = 3; i <= 23; i++) {
            yearOffset--;
            sheetAt.getRow(6).getCell(i).setCellValue(year - yearOffset);
        }
        for (var data:
             queryResult) {
            createCells(sheetAt.getRow(rowIndexes.get(data.sob)), data, year);
        }
    }

    private void initColumnIndexes(int year) {

        columnIndexes.clear();

        columnIndexes.put(year, 23);
        columnIndexes.put(year-1, 22);
        columnIndexes.put(year-2, 21);
        columnIndexes.put(year-3, 20);
        columnIndexes.put(year-4, 19);
        columnIndexes.put(year-5, 18);
        columnIndexes.put(year-6, 17);
        columnIndexes.put(year-7, 16);
        columnIndexes.put(year-8, 15);
        columnIndexes.put(year-9, 14);
        columnIndexes.put(year-10, 13);
        columnIndexes.put(year-11, 12);
        columnIndexes.put(year-12, 11);
        columnIndexes.put(year-13, 10);
        columnIndexes.put(year-14, 9);
        columnIndexes.put(year-15, 8);
        columnIndexes.put(year-16, 7);
        columnIndexes.put(year-17, 6);
        columnIndexes.put(year-18, 5);
        columnIndexes.put(year-19, 4);
        columnIndexes.put(year-20, 3);
        columnIndexes.put(year-21, 2);
    }

    private List<PK19Row> initObjects(List<PK19Data> queryResult) {

        Map<String, PK19Row> rows = new HashMap<>();
        for (var data:
             queryResult) {
            if (!rows.containsKey(data.getSob())) {
                rows.put(data.getSob(), new PK19Row(data.getSob()));
            }

            rows.get(data.getSob()).containers.add(data);
        }

        return rows.values().stream().toList();
    }

    private void createCells(XSSFRow row, PK19Row data, int year) {

        if (data.sob == null) {
            createCell(row, 1, data.containers.size());
            return;
        }

        createCell(row, columnIndexes.get(year-21), data.containers.stream().filter(el -> Integer.parseInt(el.getY_postr()) <= year - 21).count());

        for (int i = year-20; i <= year; i++) {
            int finalI = i;
            createCell(row, columnIndexes.get(finalI), data.containers.stream().filter(el -> Integer.parseInt(el.getY_postr()) == finalI).count());
        }
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

    private class PK19Row {
        String sob;
        List<PK19Data> containers;

        public PK19Row(String sob) {
            this.sob = sob;

            containers = new ArrayList<>();
        }
    }
}