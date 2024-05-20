package com.example.rjdtask1.service.excelExporter;

import com.example.rjdtask1.model.ExcelFile;
import com.example.rjdtask1.model.PK5_6_8Data;
import com.example.rjdtask1.model.compositeId.ExcelFileId;
import com.example.rjdtask1.repository.ExcelFileRepository;
import com.example.rjdtask1.repository.PK6Repository;
import com.example.rjdtask1.repository.PK8Repository;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PK6Exporter extends TableExcelExporter<PK5_6_8Data>{

    private final ExcelFileRepository excelFileRepository;
    private final PK6Repository pk6Repository;
    private final Map<String, Integer> columnIndexes;

    public PK6Exporter(ExcelFileRepository excelFileRepository, PK6Repository pk6Repository) {
        this.excelFileRepository = excelFileRepository;
        this.pk6Repository = pk6Repository;

        columnIndexes = new HashMap<>();
        columnIndexes.put("АЗ", 2);
        columnIndexes.put("АРМ", 3);
        columnIndexes.put("БЧ", 4);
        columnIndexes.put("ГР", 5);
        columnIndexes.put("КЗХ", 6);
        columnIndexes.put("КРГ", 7);
        columnIndexes.put("ЧФМ", 8);
        columnIndexes.put("РЖД", 9);
        columnIndexes.put("ТДЖ", 10);
        columnIndexes.put("ТРК", 11);
        columnIndexes.put("УТИ", 12);
        columnIndexes.put("УЗ", 13);
        columnIndexes.put("ЛДЗ", 14);
        columnIndexes.put("ЛГ", 15);
        columnIndexes.put("ЭВР", 16);
        columnIndexes.put("Б/пр", 17);
    }

    protected void createCells(XSSFRow row, PK5_6_8Data entity, XSSFCellStyle style) {
        if (entity.getCountry() == null) {
            createCell(row, 0, "Все администрации", style);
        } else if (entity.getRailWay() == null) {
            createCell(row, 0, entity.getCountry(), style);
        } else {
            createCell(row, 0, "    " + entity.getRailWay(), style);
        }

        createCell(row, 1, entity.getWagonCount(), style);

        for (int i = 2; i < 19; i++) {
            createCell(row, i, 0, style);
        }
        if (entity.getOwner() == null) {
            return;
        }
        if (entity.getOwner().equals("0")) {
            createCell(row, 18, entity.getWagonCount(), style);
        }
        else {
            createCell(row, columnIndexes.get(entity.getOwner()), entity.getWagonCount(), style);
        }
    }

    public void exportToStream(ByteArrayOutputStream stream, String year, boolean forceGenerate) throws IOException {
        if (!forceGenerate) {
            ExcelFileId excelFileId = new ExcelFileId("PK6", year);
            if (excelFileRepository.existsById(excelFileId)) {
                stream.write(excelFileRepository.getReferenceById(excelFileId).getFileData());
                return;
            }
        }

        File file = ResourceUtils.getFile("classpath:PK_6.xlsx");
        InputStream inputStream = new FileInputStream(file);
        XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(inputStream);

        //createAllAdmRow(workbook, initObjects(pk_8_2Repository.getQueryResult()));
        writeDataLines(workbook.getSheetAt(0), initObjects(pk6Repository.getQueryResult(year)));
        writeDataLines(workbook.getSheetAt(1), initObjects(pk6Repository.selectByKTK(year)));
        writeDataLines(workbook.getSheetAt(2), initObjects(pk6Repository.selectBySTK(year)));

        completeBorders(workbook);

        workbook.write(stream);
        excelFileRepository.save(new ExcelFile("PK6", year, stream.toByteArray()));
        workbook.close();
    }

    private List<AdministrationPK8_2> initObjects(List<PK5_6_8Data> entities) {

        List<AdministrationPK8_2> administrations = new ArrayList<>();

        for (var entity: entities) {
            if (entity.getCountry() != null && entity.getRailWay() != null && entity.getOwner() == null) {
                continue;
            }
            if (entity.getCountry() == null) {

            } else if (entity.getRailWay() == null) {
                administrations.add(new AdministrationPK8_2(entity.getCountry(), entity.getWagonCount()));
            } else {
                List<Railway> railwayList = administrations.get(administrations.size() - 1).railways;
                if (entity.getOwner().equals("0")) {

                    if (railwayList.isEmpty()) {
                        Railway railway = new Railway(entity.getRailWay(),entity.getWagonCount(),entity.getWagonCount());
                        railwayList.add(railway);
                    } else if (railwayList.get(railwayList.size() - 1).name.equals(entity.getRailWay())) {
                        railwayList.get(railwayList.size() - 1).noOwnerCount = entity.getWagonCount();
                        railwayList.get(railwayList.size() - 1).wagonCount += entity.getWagonCount();
                    }
                    else {
                        Railway railway = new Railway(entity.getRailWay(),entity.getWagonCount(),entity.getWagonCount());
                        railwayList.add(railway);
                    }
                }
                else {
                    Railway railway = new Railway(entity.getRailWay(),entity.getWagonCount());
                    railway.admOwners.put(entity.getOwner(), entity.getWagonCount());
                    railwayList.add(railway);
                }
            }
        }

        return administrations;
    }

    private void createAllAdmRow(XSSFWorkbook workbook, List<AdministrationPK8_2> data) {
        XSSFRow row = workbook.getSheetAt(0).getRow(8);
        createCell(row, 0, "Все администрации", getDataStyle(workbook));

        int allWagons = 0;
        int noOwnerCount = 0;
        Map<String,Integer> admOwners = new HashMap<>();

        for (var administration:
                data) {
            allWagons += administration.allWagonCount;
            noOwnerCount += administration.getNoOwnerCount();

            administration.getAdmOwners().forEach((key, value) -> admOwners.merge(key, value, Integer::sum));
        }
        createCell(row, 1, allWagons, getDataStyle(workbook));
        createCell(row, 18, noOwnerCount, getDataStyle(workbook));

        for (var column:
             columnIndexes.entrySet()) {
            if (admOwners.containsKey(column.getKey())) {
                createCell(row, columnIndexes.get(column.getKey()), admOwners.get(column.getKey()), getDataStyle(workbook));
            }
            else {
                createCell(row, column.getValue(), 0, getDataStyle(workbook));
            }
        }

        setDataRowBorders(row);
    }
    protected void writeDataLines(XSSFSheet sheet, List<AdministrationPK8_2> data) {
        int rowCount = 9;

        AdministrationPK8_2 allRow = new AdministrationPK8_2("All", 0);

        for (var administration:data) {
            XSSFRow row = sheet.createRow(rowCount++);
            row.setHeightInPoints(25.25f);
            createCells(row, administration, getDataStyle(sheet.getWorkbook()));
            setDataRowBorders(row);

            allRow.railways.addAll(administration.railways);
            allRow.allWagonCount += administration.allWagonCount;

            for (var railway :administration.railways) {
                row = sheet.createRow(rowCount++);
                row.setHeightInPoints(25.25f);
                createCells(row, railway, getDataStyle(sheet.getWorkbook()));
                setDataRowBorders(row);

            }
        }

        setAllRow(sheet.getRow(8), allRow, getDataStyle(sheet.getWorkbook()));
        setAllRowBorders(sheet.getRow(8));
    }

    private void createCells(XSSFRow row, Railway railway, XSSFCellStyle dataStyle) {
        createCell(row, 0, "    " + railway.name, dataStyle);
        createCell(row,1, railway.wagonCount, dataStyle);
        createCell(row,18, railway.noOwnerCount, dataStyle);

        for (var column: columnIndexes.entrySet()) {
            createCell(row, column.getValue(), 0, dataStyle);
        }

        for (var admOwner: railway.admOwners.entrySet()) {
            createCell(row, columnIndexes.get(admOwner.getKey()), admOwner.getValue(), dataStyle);
        }
    }

    private void createCells(XSSFRow row, AdministrationPK8_2 administration, XSSFCellStyle dataStyle) {
        createCell(row, 0, administration.name, dataStyle);
        createCell(row,1, administration.allWagonCount, dataStyle);
        createCell(row,18, administration.getNoOwnerCount(), dataStyle);

        for (var column: columnIndexes.entrySet()) {
            createCell(row, column.getValue(), 0, dataStyle);
        }

        for (var admOwner: administration.getAdmOwners().entrySet()) {
            createCell(row, columnIndexes.get(admOwner.getKey()), admOwner.getValue(), dataStyle);
        }
    }

    private void setAllRow(XSSFRow row, AdministrationPK8_2 administration, XSSFCellStyle dataStyle) {
        createCell(row,1, administration.allWagonCount, dataStyle);
        createCell(row,18, administration.getNoOwnerCount(), dataStyle);

        for (var column: columnIndexes.entrySet()) {
            createCell(row, column.getValue(), 0, dataStyle);
        }

        for (var admOwner: administration.getAdmOwners().entrySet()) {
            createCell(row, columnIndexes.get(admOwner.getKey()), admOwner.getValue(), dataStyle);
        }
    }
    @Override
    protected XSSFCellStyle getDataStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        style.setFont(getDocumentFont(workbook));

        return style;
    }

    @Override
    protected void setDataRowBorders(XSSFRow row) {
        XSSFSheet sheet = row.getSheet();

        for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
            CellRangeAddress cellAddressesOther = new CellRangeAddress(
                    row.getRowNum(),
                    row.getRowNum(),
                    i,
                    i
            );

            if (i == 0) {
                RegionUtil.setBorderLeft(BorderStyle.DOUBLE, cellAddressesOther, sheet);
                RegionUtil.setBorderRight(BorderStyle.DOUBLE, cellAddressesOther, sheet);
            }
            else if (i == 1 || i == 18) {
                RegionUtil.setBorderRight(BorderStyle.DOUBLE, cellAddressesOther, sheet);
            }
            else {
                RegionUtil.setBorderRight(BorderStyle.THIN, cellAddressesOther, sheet);
            }

            RegionUtil.setBorderBottom(BorderStyle.MEDIUM, cellAddressesOther, sheet);
        }
    }

    protected void setAllRowBorders(XSSFRow row) {
        XSSFSheet sheet = row.getSheet();

        for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
            CellRangeAddress cellAddressesOther = new CellRangeAddress(
                    row.getRowNum(),
                    row.getRowNum(),
                    i,
                    i
            );

            RegionUtil.setBorderLeft(BorderStyle.DOUBLE, cellAddressesOther, sheet);
            RegionUtil.setBorderBottom(BorderStyle.DOUBLE, cellAddressesOther, sheet);
            RegionUtil.setBorderRight(BorderStyle.DOUBLE, cellAddressesOther, sheet);
            RegionUtil.setBorderTop(BorderStyle.DOUBLE, cellAddressesOther, sheet);
        }
    }

    private void completeBorders(XSSFWorkbook workbook) {

        CellRangeAddress cellAddress1 = new CellRangeAddress(
                8,
                8,
                0,
                18
        );

        CellRangeAddress cellAddress2 = new CellRangeAddress(
                8,
                8,
                0,
                18
        );

        CellRangeAddress cellAddress3 = new CellRangeAddress(
                8,
                8,
                0,
                18
        );

        RegionUtil.setBorderTop(BorderStyle.DOUBLE, cellAddress1, workbook.getSheetAt(0));
        RegionUtil.setBorderTop(BorderStyle.DOUBLE, cellAddress2, workbook.getSheetAt(1));
        RegionUtil.setBorderTop(BorderStyle.DOUBLE, cellAddress3, workbook.getSheetAt(2));

        RegionUtil.setBorderBottom(BorderStyle.DOUBLE, cellAddress1, workbook.getSheetAt(0));
        RegionUtil.setBorderBottom(BorderStyle.DOUBLE, cellAddress2, workbook.getSheetAt(1));
        RegionUtil.setBorderBottom(BorderStyle.DOUBLE, cellAddress3, workbook.getSheetAt(2));
    }

    private Font getDocumentFont(XSSFWorkbook workbook) {
        return workbook.getSheetAt(0).getRow(5).getCell(0).getCellStyle().getFont();
    }

    @Override
    protected void createCell(XSSFRow row, int columnCount, Object value, XSSFCellStyle style) {
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
        cell.setCellStyle(style);
    }

    @Override
    public void exportToStream(ByteArrayOutputStream stream, String year) throws IOException {

    }

    class AdministrationPK8_2 {
        String name;
        int allWagonCount;
        List<Railway> railways = new ArrayList<>();

        public AdministrationPK8_2(String name, int allWagonCount) {
            this.name = name;
            this.allWagonCount = allWagonCount;
        }

        int getNoOwnerCount() {
            int count = 0;
            for (var railway:railways) {
                count += railway.noOwnerCount;
            }

            return count;
        }

        Map<String, Integer> getAdmOwners() {
            Map<String, Integer> admOwners = new HashMap<>();

            for (var railway : railways) {
                railway.admOwners.forEach((key, val) -> admOwners.merge(key, val,
                        Integer::sum));
            }

            return admOwners;
        }

        @Override
        public String toString() {
            return "Administration{" +
                    "name='" + name + '\'' +
                    ", allWagonCount=" + allWagonCount +
                    ", railways=" + railways +
                    '}';
        }
    }
    private class Railway {
        String name;
        int wagonCount;

        int noOwnerCount;

        Map<String, Integer> admOwners = new HashMap<>();

        public Railway(String name, int wagonCount) {
            this.name = name;
            this.wagonCount = wagonCount;
        }

        public Railway(String name, int wagonCount, int noOwnerCount) {
            this.name = name;
            this.wagonCount = wagonCount;
            this.noOwnerCount = noOwnerCount;
        }

        @Override
        public String toString() {
            return "Railway{" +
                    "name='" + name + '\'' +
                    ", wagonCount=" + wagonCount +
                    ", noOwnerCount=" + noOwnerCount +
                    ", admOwners=" + admOwners +
                    '}';
        }
    }
}


