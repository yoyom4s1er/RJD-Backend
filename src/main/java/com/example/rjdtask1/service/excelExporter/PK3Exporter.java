package com.example.rjdtask1.service.excelExporter;

import com.example.rjdtask1.model.ExcelFile;
import com.example.rjdtask1.model.PK3Data;
import com.example.rjdtask1.model.compositeId.ExcelFileId;
import com.example.rjdtask1.repository.ExcelFileRepository;
import com.example.rjdtask1.repository.PK3Repository;
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
public class PK3Exporter extends TableExcelExporter<PK3Data>{

    private final ExcelFileRepository excelFileRepository;
    private final PK3Repository pk3Repository;

    private final Map<String, Integer> rowIndexes;

    public PK3Exporter(ExcelFileRepository excelFileRepository, PK3Repository pk3Repository) {
        this.excelFileRepository = excelFileRepository;
        this.pk3Repository = pk3Repository;

        rowIndexes = new HashMap<>();
        rowIndexes.put("", 10);
        rowIndexes.put("57", 20);
        rowIndexes.put("58", 30);
        rowIndexes.put("21", 49);
        rowIndexes.put("28", 59);
        rowIndexes.put("27", 69);
        rowIndexes.put("59", 88);
        rowIndexes.put("23", 98);
        rowIndexes.put("20", 108);
        rowIndexes.put("66", 127);
        rowIndexes.put("67", 137);
        rowIndexes.put("29", 147);
        rowIndexes.put("22", 166);
        rowIndexes.put("25", 176);
        rowIndexes.put("24", 186);
        rowIndexes.put("26", 205);
    }

    public void exportToStream(ByteArrayOutputStream stream, String year, boolean forceGenerate) throws IOException {
        if (!forceGenerate) {
            ExcelFileId excelFileId = new ExcelFileId("PK3", year);
            if (excelFileRepository.existsById(excelFileId)) {
                stream.write(excelFileRepository.getReferenceById(excelFileId).getFileData());
                return;
            }
        }

        File file = ResourceUtils.getFile("classpath:PK_3.xlsx");
        InputStream inputStream = new FileInputStream(file);
        XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(inputStream);

        List<Administration> data = initObjects(year);
        writeData(workbook, data);

        workbook.write(stream);
        excelFileRepository.save(new ExcelFile("PK3", year, stream.toByteArray()));
        workbook.close();
    }

    private void writeData(XSSFWorkbook workbook, List<Administration> entities) {
         XSSFSheet sheet = workbook.getSheetAt(0);

        for (var entity:
             entities) {
            entity.update();
            int rowIndex = rowIndexes.get(entity.name);

            XSSFRow row = sheet.getRow(rowIndex++);
            createCellsNumbers(row, entity.admData, getDataStyle(workbook), true);
            row = sheet.getRow(rowIndex++);
            createCellsNumbers(row, entity.mestoRailway, getDataStyle(workbook), false);
            row = sheet.getRow(rowIndex++);
            createCellsPercents(row, entity.mestoRailway, getDataStyle(workbook));
            row = sheet.getRow(rowIndex++);
            createCellsNumbers(row, entity.mesto0, getDataStyle(workbook), false);
            row = sheet.getRow(rowIndex++);
            createCellsPercents(row, entity.mesto0, getDataStyle(workbook));
            row = sheet.getRow(rowIndex++);
            createCellsNumbers(row, entity.mesto7, getDataStyle(workbook), false);
            row = sheet.getRow(rowIndex++);
            createCellsPercents(row, entity.mesto7, getDataStyle(workbook));
            row = sheet.getRow(rowIndex++);
            createCellsNumbers(row, entity.mesto6, getDataStyle(workbook), false);
            row = sheet.getRow(rowIndex);
            createCellsPercents(row, entity.mesto6, getDataStyle(workbook));
        }
    }

    private void createCellsNumbers(XSSFRow row, PlaceData placeData, XSSFCellStyle style, boolean isAdm) {
        if (placeData == null) {
            return;
        }

        createCell(row, 5, placeData.allCount, style);
        if (isAdm)
            createCell(row, 6, placeData.allCountPercentage, style);
        createCell(row, 7, placeData.mass3Count, style);
        createCell(row, 8, placeData.mass5Count, style);
        createCell(row, 9, placeData.stkCount, style);
        createCell(row, 10, placeData.stkPercentage, style);
        createCell(row, 11, placeData.mass10Count, style);
        createCell(row, 12, placeData.mass20Count, style);
        createCell(row, 13, placeData.mass24Count, style);
        createCell(row, 14, placeData.mass25Count, style);
        createCell(row, 15, placeData.mass30Count, style);
        createCell(row, 16, placeData.ktkCount, style);
        createCell(row, 17, placeData.ktkPercentage, style);
    }

    private void createCellsPercents(XSSFRow row, PlaceData placeData, XSSFCellStyle style) {
        if (placeData == null) {
            return;
        }
        createCell(row, 6, placeData.allCountPercentage, style);
        createCell(row, 7, placeData.mass3CountPercentage, style);
        createCell(row, 8, placeData.mass5CountPercentage, style);
        createCell(row, 9, placeData.stkPercentageByColumn, style);
        createCell(row, 11, placeData.mass10CountPercentage, style);
        createCell(row, 12, placeData.mass20CountPercentage, style);
        createCell(row, 13, placeData.mass24CountPercentage, style);
        createCell(row, 14, placeData.mass25CountPercentage, style);
        createCell(row, 15, placeData.mass30CountPercentage, style);
        createCell(row, 16, placeData.ktkPercentageByColumn, style);
    }

    private List<Administration> initObjects(String year) {
        List<PK3Data> rows = pk3Repository.getQueryResult(year);

        List<Administration> administrations = new ArrayList<>();
        Administration current = null;
        Administration allAdmData = null;

        for (var row:
             rows) {
            PlaceData placeData = new PlaceData(
                    row.getMesto(),
                    row.getCount(),
                    row.getMass3Count(),
                    row.getMass5Count(),
                    row.getMass10Count(),
                    row.getMass20Count(),
                    row.getMass24Count(),
                    row.getMass25Count(),
                    row.getMass30Count()
            );
            if (row.getAdmPer() != null) {
                if (row.getMesto() == null) {
                    placeData.allCountPercentage = (float) placeData.allCount * 100 / allAdmData.admData.allCount;
                    current = new Administration(row.getAdmPer(), placeData);
                    administrations.add(current);
                }
                else {

                    if (row.getMesto().equals("0")) {
                        current.mesto0 = placeData;
                        allAdmData.mesto0.merge(placeData);
                    } else if (row.getMesto().equals("7")) {
                        current.mesto7 = placeData;
                        allAdmData.mesto7.merge(placeData);
                    } else if (row.getMesto().equals("6")) {
                        current.mesto6 = placeData;
                        allAdmData.mesto6.merge(placeData);
                    } else {
                        current.addMestoRailway(placeData);
                        allAdmData.mestoRailway.merge(placeData);
                    }
                }
            }
            else {
                allAdmData = new Administration("", placeData);
                allAdmData.mestoRailway = new PlaceData();
                allAdmData.admData.allCountPercentage = 100;
                allAdmData.mesto0 = new PlaceData();
                allAdmData.mesto7 = new PlaceData();
                allAdmData.mesto6 = new PlaceData();
                administrations.add(allAdmData);
            }
        }

        return administrations;
    }

    @Override
    protected void createCell(XSSFRow row, int columnCount, Object value, XSSFCellStyle style) {
        XSSFCell cell = row.getCell(columnCount);
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

    @Override
    public void exportToStream(ByteArrayOutputStream stream, String year) throws IOException {

    }

    class Administration {
        public String name;
        public PlaceData admData;
        public PlaceData mestoRailway;
        public PlaceData mesto0;
        public PlaceData mesto7;
        public PlaceData mesto6;

        public Administration(String name, PlaceData admData) {
            this.name = name;
            this.admData = admData;
        }

        public void addMestoRailway(PlaceData placeData) {
            if (mestoRailway == null) {
                mestoRailway = placeData;
            }
            else {
                mestoRailway.merge(placeData);
            }
        }

        @Override
        public String toString() {
            return "Administration{" +
                    "name='" + name + '\'' +
                    ", admData=" + admData +
                    ", mestoRailway=" + mestoRailway +
                    ", mesto0=" + mesto0 +
                    ", mesto7=" + mesto7 +
                    ", mesto6=" + mesto6 +
                    '}';
        }

        void update() {
            if (mestoRailway != null)
               mestoRailway.calculatePercentage(admData);
            if (mesto0 != null)
                mesto0.calculatePercentage(admData);
            if (mesto7 != null)
                mesto7.calculatePercentage(admData);
            if (mesto6 != null)
                mesto6.calculatePercentage(admData);
        }
    }

    class PlaceData {

        private String mesto;
        private int allCount;
        private float allCountPercentage;
        private int mass3Count;
        private float mass3CountPercentage;
        private int mass5Count;
        private float mass5CountPercentage;
        private int mass10Count;
        private float mass10CountPercentage;
        private int stkCount;
        private float stkPercentageByColumn;
        private float stkPercentage;
        private int mass20Count;
        private float mass20CountPercentage;
        private int mass24Count;
        private float mass24CountPercentage;
        private int mass25Count;
        private float mass25CountPercentage;
        private int mass30Count;
        private float mass30CountPercentage;
        private int ktkCount;
        private float ktkPercentageByColumn;
        private float ktkPercentage;

        public PlaceData(String mesto, int allCount, int mass3Count, int mass5Count, int mass10Count, int mass20Count, int mass24Count, int mass25Count, int mass30Count) {
            this.mesto = mesto;
            this.allCount = allCount;
            this.mass3Count = mass3Count;
            this.mass5Count = mass5Count;
            this.mass10Count = mass10Count;
            this.mass20Count = mass20Count;
            this.mass24Count = mass24Count;
            this.mass25Count = mass25Count;
            this.mass30Count = mass30Count;

            stkCount = mass3Count + mass5Count;
            ktkCount = mass10Count + mass20Count + mass24Count + mass25Count + mass30Count;

            stkPercentage = (float) stkCount * 100 / allCount;
            ktkPercentage = (float) ktkCount * 100 / allCount;
        }

        public PlaceData() {
        }

        public void merge(PlaceData placeData) {
            this.allCount += placeData.allCount;
            this.mass3Count += placeData.mass3Count;
            this.mass5Count += placeData.mass5Count;
            this.stkCount += placeData.stkCount;
            this.mass10Count += placeData.mass10Count;
            this.mass20Count += placeData.mass20Count;
            this.mass24Count += placeData.mass24Count;
            this.mass25Count += placeData.mass25Count;
            this.mass30Count += placeData.mass30Count;
            this.ktkCount += placeData.ktkCount;

            stkPercentage = (float) stkCount * 100 / allCount;
            ktkPercentage = (float) ktkCount * 100 / allCount;
        }

        public void calculatePercentage(PlaceData placeData) {
            if (placeData.allCount > 0)
                allCountPercentage = (float) (allCount * 100) / placeData.allCount;
            if (placeData.mass3Count > 0)
                mass3CountPercentage = (float) (mass3Count * 100) / placeData.mass3Count;
            if (placeData.mass5Count > 0)
                mass5CountPercentage = (float) (mass5Count * 100) / placeData.mass5Count;
            if (placeData.stkCount > 0)
                stkPercentageByColumn = (float) (stkCount * 100) / placeData.stkCount;
            if (placeData.mass10Count > 0)
                mass10CountPercentage = (float) (mass10Count * 100) / placeData.mass10Count;
            if (placeData.mass20Count > 0)
                mass20CountPercentage = (float) (mass20Count * 100) / placeData.mass20Count;
            if (placeData.mass24Count > 0)
                mass24CountPercentage = (float) (mass24Count * 100) / placeData.mass24Count;
            if (placeData.mass25Count > 0)
                mass25CountPercentage = (float) (mass25Count * 100) / placeData.mass25Count;
            if (placeData.mass30Count > 0)
                mass30CountPercentage = (float) (mass30Count * 100) / placeData.mass30Count;
            if (placeData.ktkCount > 0)
                ktkPercentageByColumn = (float) (ktkCount * 100) / placeData.ktkCount;
        }

        @Override
        public String toString() {
            return "PlaceData{" +
                    "mesto='" + mesto + '\'' +
                    ", allCount=" + allCount +
                    ", allCountPercentage=" + allCountPercentage +
                    ", mass3Count=" + mass3Count +
                    ", mass5Count=" + mass5Count +
                    ", mass10Count=" + mass10Count +
                    ", stkCount=" + stkCount +
                    ", stkPercentage=" + stkPercentage +
                    ", mass20Count=" + mass20Count +
                    ", mass24Count=" + mass24Count +
                    ", mass25Count=" + mass25Count +
                    ", mass30Count=" + mass30Count +
                    ", ktkCount=" + ktkCount +
                    ", ktkPercentage=" + ktkPercentage +
                    '}';
        }
    }
}

