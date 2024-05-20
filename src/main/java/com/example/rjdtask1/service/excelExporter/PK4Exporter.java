package com.example.rjdtask1.service.excelExporter;

import com.example.rjdtask1.model.ExcelFile;
import com.example.rjdtask1.model.PK4Data;
import com.example.rjdtask1.model.compositeId.ExcelFileId;
import com.example.rjdtask1.repository.ExcelFileRepository;
import com.example.rjdtask1.repository.PK4Repository;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PK4Exporter extends TableExcelExporter{

    private final ExcelFileRepository excelFileRepository;
    private final PK4Repository repository;

    private final Map<String, Integer> columnIndexes;
    private final Map<String, Integer> rowIndexes;

    public PK4Exporter(ExcelFileRepository excelFileRepository, PK4Repository repository) {
        this.excelFileRepository = excelFileRepository;
        this.repository = repository;

        columnIndexes = new HashMap<>();
        columnIndexes.put("57", 5);
        columnIndexes.put("58", 6);
        columnIndexes.put("21", 7);
        columnIndexes.put("28", 8);
        columnIndexes.put("27", 9);
        columnIndexes.put("59", 10);
        columnIndexes.put("23", 11);
        columnIndexes.put("20", 12);
        columnIndexes.put("66", 13);
        columnIndexes.put("67", 14);
        columnIndexes.put("29", 15);
        columnIndexes.put("22", 16);
        columnIndexes.put("25", 17);
        columnIndexes.put("24", 18);
        columnIndexes.put("26", 19);
        columnIndexes.put("0", 21);

        rowIndexes = new HashMap<>();
        rowIndexes.put("57", 16);
        rowIndexes.put("58", 23);
        rowIndexes.put("21", 30);
        rowIndexes.put("28", 103);
        rowIndexes.put("27", 37);
        rowIndexes.put("59", 49);
        rowIndexes.put("23", 56);
        rowIndexes.put("20", 63);
        rowIndexes.put("66", 70);
        rowIndexes.put("67", 77);
        rowIndexes.put("29", 84);
        rowIndexes.put("22", 96);
        rowIndexes.put("25", 110);
        rowIndexes.put("24", 117);
        rowIndexes.put("26", 124);
        rowIndexes.put("all", 9);
    }

    public void exportToStream(ByteArrayOutputStream stream, String year, boolean forceGenerate) throws IOException {
        if (!forceGenerate) {
            ExcelFileId excelFileId = new ExcelFileId("PK4", year);
            if (excelFileRepository.existsById(excelFileId)) {
                stream.write(excelFileRepository.getReferenceById(excelFileId).getFileData());
                return;
            }
        }

        File file = ResourceUtils.getFile("classpath:PK_4.xlsx");
        InputStream inputStream = new FileInputStream(file);
        XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(inputStream);

        writeData(workbook.getSheetAt(0), initObjects(repository.getQueryResult(year)));
        writeData(workbook.getSheetAt(1), initObjects(repository.selectByKTK(year)));
        writeData(workbook.getSheetAt(2), initObjects(repository.selectBySTK(year)));

        workbook.write(stream);
        excelFileRepository.save(new ExcelFile("PK4", year, stream.toByteArray()));
        workbook.close();
    }

    private List<Administration> initObjects(List<PK4Data> entities) {
        Map<String, Administration> administrations = new HashMap<>();

        for (var entity:
                entities) {
            if (!administrations.containsKey(entity.getAdm())) {
                administrations.put(entity.getAdm(), new Administration(entity.getAdm()));
            }

            administrations.get(entity.getAdm()).perepisList.add(new Perepis(
                    entity.getMesto(),
                    entity.getSob(),
                    entity.getCount()
            ));
        }

        return administrations.values().stream().toList();
    }

    private void writeData(XSSFSheet sheet, List<Administration> data) {

        Administration allAdmins = new Administration("all");
        data.forEach(el -> allAdmins.perepisList.addAll(el.perepisList));

        List<Administration> administrationList = new ArrayList<>(List.copyOf(data));
        administrationList.add(allAdmins);

        for (var entity:
                administrationList) {
            int counter = rowIndexes.get(entity.adm);

            XSSFRow row = sheet.getRow(counter++);
            createCells(row, entity.perepisList);

            row = sheet.getRow(counter++);
            createCells(row, entity.onRailway());

            row = sheet.getRow(counter++);
            createCells(row, entity.onMesto8());

            row = sheet.getRow(counter++);
            createCells(row, entity.onMesto0());

            row = sheet.getRow(counter++);
            createCells(row, entity.onMesto7());

            row = sheet.getRow(counter++);
            createCells(row, entity.onMesto6());
        }
    }

    private void createCells(XSSFRow row, List<Perepis> perepisList) {

        int count = 0;
        Map<String, Integer> columnCounts = new HashMap<>();
        for (var data:
             perepisList) {
            count += data.count;
            columnCounts.merge(data.sob, data.count, Integer::sum);
        }

        createCell(row, 4, count, null);

        for (var entry: columnCounts.entrySet()) {
            createCell(row, columnIndexes.get(entry.getKey()), entry.getValue(), null);
        }
    }

    @Override
    public void exportToStream(ByteArrayOutputStream stream, String year) throws IOException {

    }

    class Administration {

        String adm;
        List<Perepis> perepisList;

        public Administration(String adm) {
            this.adm = adm;

            perepisList = new ArrayList<>();
        }

        public List<Perepis> onRailway() {
            return perepisList
                    .stream()
                    .filter(el -> !el.mesto.equals("0") && !el.mesto.equals("6") && !el.mesto.equals("7") && !el.mesto.equals("9"))
                    .collect(Collectors.toList());
        }

        public List<Perepis> onMesto0() {
            return perepisList
                    .stream()
                    .filter(el -> el.mesto.equals("0"))
                    .collect(Collectors.toList());
        }

        public List<Perepis> onMesto7() {
            return perepisList
                    .stream()
                    .filter(el -> el.mesto.equals("7"))
                    .collect(Collectors.toList());
        }

        public List<Perepis> onMesto6() {
            return perepisList
                    .stream()
                    .filter(el -> el.mesto.equals("6"))
                    .collect(Collectors.toList());
        }

        public List<Perepis> onMesto8() {
            return perepisList
                    .stream()
                    .filter(el -> el.mesto.equals("8"))
                    .collect(Collectors.toList());
        }

        @Override
        public String toString() {
            return "Administration{" +
                    "adm='" + adm + '\'' +
                    ", perepisList=" + perepisList +
                    '}';
        }
    }

    class Perepis {
        String mesto;
        String sob;
        int count;
        public Perepis(String mesto, String sob, int count) {
            this.mesto = mesto;
            this.sob = sob;
            this.count = count;
        }

        @Override
        public String toString() {
            return "Perepis{" +
                    "mesto='" + mesto + '\'' +
                    ", sob='" + sob + '\'' +
                    ", count=" + count +
                    '}';
        }
    }
}
