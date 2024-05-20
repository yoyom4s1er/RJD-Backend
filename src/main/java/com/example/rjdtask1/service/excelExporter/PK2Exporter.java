package com.example.rjdtask1.service.excelExporter;

import com.example.rjdtask1.model.ExcelFile;
import com.example.rjdtask1.model.PK2Data;
import com.example.rjdtask1.model.compositeId.ExcelFileId;
import com.example.rjdtask1.repository.ExcelFileRepository;
import com.example.rjdtask1.repository.PK2Repository;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class PK2Exporter {

    private final Map<String, Integer> rowIndexes;
    private final Map<String, Integer> countryCodes;

    private final ExcelFileRepository excelFileRepository;
    private final PK2Repository pk2Repository;
    public PK2Exporter(ExcelFileRepository excelFileRepository, PK2Repository pk2Repository) {
        this.excelFileRepository = excelFileRepository;
        this.pk2Repository = pk2Repository;

        rowIndexes = new HashMap<>();
        rowIndexes.put("АЗЕРБАЙДЖАН", 11);
        rowIndexes.put("АРМЕНИЯ", 12);
        rowIndexes.put("БЕЛАРУСЬ", 13);
        rowIndexes.put("ГРУЗИЯ", 14);
        rowIndexes.put("КАЗАХСТАН", 15);
        rowIndexes.put("КЫРГЫЗСТАН", 16);
        rowIndexes.put("МОЛДОВА", 17);
        rowIndexes.put("РОССИЯ", 18);
        rowIndexes.put("ТАДЖИКИСТАН", 19);
        rowIndexes.put("ТУРКМЕНИСТАН", 20);
        rowIndexes.put("УЗБЕКИСТАН", 21);
        rowIndexes.put("УКРАИНА", 22);
        rowIndexes.put("ЛАТВИЯ", 23);
        rowIndexes.put("ЛИТВА", 24);
        rowIndexes.put("ЭСТОНИЯ", 25);
        rowIndexes.put("Без принадлежности", 26);
        rowIndexes.put("НеАБД", 27);
        rowIndexes.put("Собственных", 28);

        countryCodes = new HashMap<>();
        countryCodes.put("Азербайджан", 57);
        countryCodes.put("Армения", 58);
        countryCodes.put("Белоруссия", 21);
        countryCodes.put("Грузия", 28);
        countryCodes.put("Казахстан", 27);
        countryCodes.put("Киргизия", 59);
        countryCodes.put("Молдавия", 23);
        countryCodes.put("Россия", 20);
        countryCodes.put("Таджикистан", 66);
        countryCodes.put("Туркмения", 67);
        countryCodes.put("Узбекистан", 29);
        countryCodes.put("Украина", 22);
        countryCodes.put("Латвия", 25);
        countryCodes.put("Литва", 24);
        countryCodes.put("Эстония", 26);
    }

    protected void createCells(XSSFRow row, PK2Data entity, XSSFCellStyle style) {
        createCell(row, 3, entity.getContainerCount(), style);
        createCell(row, 4, entity.getContainerCountPercentage(), style);
        createCell(row, 5, entity.getMass3Count(), style);
        createCell(row, 6, entity.getMass5Count(), style);
        createCell(row, 7, entity.getStkCount(), style);
        createCell(row, 8, entity.getStkCountPercentage(), style);
        createCell(row, 9, entity.getMass10Count(), style);
        createCell(row, 10, entity.getMass20Count(), style);
        createCell(row, 11, entity.getMass24Count(), style);
        createCell(row, 12, entity.getMass25Count(), style);
        createCell(row, 13, entity.getMass30Count(), style);
        createCell(row, 14, entity.getKtkCount(), style);
        createCell(row, 15, entity.getKtkCountPercentage(), style);
    }

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

    public void exportToStream(ByteArrayOutputStream stream) throws IOException {
        ExcelFileId excelFileId = new ExcelFileId("PK2", "2021");
        if (excelFileRepository.existsById(excelFileId)) {
            stream.write(excelFileRepository.getReferenceById(excelFileId).getFileData());
            return;
        }

        File file = ResourceUtils.getFile("classpath:PK_2.xlsx");
        InputStream inputStream = new FileInputStream(file);
        XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(inputStream);

        //long time = System.currentTimeMillis();
        /*writeDataLines(workbook, "Всего", pk2Repository.getPerepis2021());
        for (var entry:
                countryCodes.entrySet()) {
            String countyName = entry.getKey();
            writeDataLines(workbook, countyName, pk2Repository.selectBySob(entry.getValue()));
        }*/

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(() -> writeDataLines(workbook, "Всего", pk2Repository.select("2021")));
        for (var entry:
             countryCodes.entrySet()) {
            executorService.execute(() -> {
                List<PK2Data> filtered = pk2Repository.selectBySob(entry.getValue(), "2021");
                writeDataLines(workbook, entry.getKey(), filtered);
            });
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        workbook.write(stream);
        excelFileRepository.save(new ExcelFile("PK2", "2021", stream.toByteArray()));
        //System.out.println(System.currentTimeMillis() - time);
        workbook.close();
    }

    public void exportToStream(ByteArrayOutputStream stream, String year, boolean forceGenerate) throws IOException {
        if (!forceGenerate) {
            ExcelFileId excelFileId = new ExcelFileId("PK2", year);
            if (excelFileRepository.existsById(excelFileId)) {
                stream.write(excelFileRepository.getReferenceById(excelFileId).getFileData());
                return;
            }
        }

        File file = ResourceUtils.getFile("classpath:PK_2.xlsx");
        InputStream inputStream = new FileInputStream(file);
        XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(inputStream);

        //long time = System.currentTimeMillis();
        /*writeDataLines(workbook, "Всего", pk2Repository.getPerepis2021());
        for (var entry:
                countryCodes.entrySet()) {
            String countyName = entry.getKey();
            writeDataLines(workbook, countyName, pk2Repository.selectBySob(entry.getValue()));
        }*/

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(() -> writeDataLines(workbook, "Всего", pk2Repository.select(year)));
        for (var entry:
                countryCodes.entrySet()) {
            executorService.execute(() -> {
                List<PK2Data> filtered = pk2Repository.selectBySob(entry.getValue(), year);
                writeDataLines(workbook, entry.getKey(), filtered);
            });
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        workbook.write(stream);
        excelFileRepository.save(new ExcelFile("PK2", year, stream.toByteArray()));
        //System.out.println(System.currentTimeMillis() - time);
        workbook.close();
    }

    protected void writeDataLines(XSSFWorkbook workbook, String sheetName, List<PK2Data> entities) {
        XSSFSheet sheet = workbook.getSheet(sheetName);

        PK2Data rowSpecial = new PK2Data();
        PK2Data universalContainersRow = new PK2Data();
        PK2Data allContainersRow = new PK2Data();
        PK2Data noPrinadl = new PK2Data();

        XSSFCellStyle style = getDataStyle(workbook);

        for (var entity: entities) {
            if (entity.getVidK().equals("1")) {
                universalContainersRow.addFromOther(entity);
                if (!entity.getPrinadl().equals("1")) {
                    noPrinadl.addFromOther(entity);
                }
            }
            else {
                rowSpecial.addFromOther(entity);
            }
        }
        allContainersRow.addFromOther(universalContainersRow);
        allContainersRow.addFromOther(rowSpecial);
        allContainersRow.setContainerCountPercentage(100);

        universalContainersRow.setContainerCountPercentage(
                (float) universalContainersRow.getContainerCount() * 100 / allContainersRow.getContainerCount()
        );
        rowSpecial.setContainerCountPercentage(
                (float) rowSpecial.getContainerCount() * 100 / allContainersRow.getContainerCount()
        );
        noPrinadl.setContainerCountPercentage(
                (float) noPrinadl.getContainerCount() * 100 / universalContainersRow.getContainerCount()
        );

        for (PK2Data entity : entities) {

            if (entity.getVidK().equals("1")) {
                if (entity.getPrinadl().equals("1")) {
                    XSSFRow row = sheet.getRow(rowIndexes.get(entity.getsName()));
                    entity.setContainerCountPercentage((float) entity.getContainerCount() * 100 / universalContainersRow.getContainerCount());
                    createCells(row, entity, style);
                }
            }
        }
        createCells(sheet.getRow(8), allContainersRow, style);
        createCells(sheet.getRow(9), universalContainersRow, style);
        createCells(sheet.getRow(27), rowSpecial, style);
        createCells(sheet.getRow(rowIndexes.get("Собственных")), noPrinadl, style);
    }

    protected XSSFCellStyle getDataStyle(XSSFWorkbook workbook) {
        return workbook.getSheetAt(0).getRow(5).getCell(0).getCellStyle();
    }


}
