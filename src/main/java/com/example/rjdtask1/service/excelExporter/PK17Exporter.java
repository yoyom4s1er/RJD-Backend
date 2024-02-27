package com.example.rjdtask1.service.excelExporter;

import com.example.rjdtask1.model.ExcelFile;
import com.example.rjdtask1.model.PK17AbdData;
import com.example.rjdtask1.model.PK17Data;
import com.example.rjdtask1.model.compositeId.ExcelFileId;
import com.example.rjdtask1.repository.ExcelFileRepository;
import com.example.rjdtask1.repository.PK17Repository;
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

@Service
public class PK17Exporter extends TableExcelExporter{

    private final ExcelFileRepository excelFileRepository;
    private final PK17Repository repository;

    private final Map<String, Integer> rowIndexes;

    public PK17Exporter(ExcelFileRepository excelFileRepository, PK17Repository repository) {
        this.excelFileRepository = excelFileRepository;
        this.repository = repository;

        rowIndexes = new HashMap<>();
        rowIndexes.put("all", 10);
        rowIndexes.put("57", 11);
        rowIndexes.put("58", 12);
        rowIndexes.put("21", 13);
        rowIndexes.put("28", 14);
        rowIndexes.put("27", 15);
        rowIndexes.put("59", 16);
        rowIndexes.put("23", 17);
        rowIndexes.put("20", 18);
        rowIndexes.put("66", 19);
        rowIndexes.put("67", 20);
        rowIndexes.put("29", 21);
        rowIndexes.put("22", 22);
        rowIndexes.put("25", 23);
        rowIndexes.put("24", 24);
        rowIndexes.put("26", 25);
        rowIndexes.put("allABD", 27);
        rowIndexes.put("NoABD", 28);
    }

    @Override
    public void exportToStream(ByteArrayOutputStream stream, String year) throws IOException {
        ExcelFileId excelFileId = new ExcelFileId("PK17", "2021");
        if (excelFileRepository.existsById(excelFileId)) {
            stream.write(excelFileRepository.getReferenceById(excelFileId).getFileData());
            return;
        }

        File file = ResourceUtils.getFile("classpath:PK_17.xlsx");
        InputStream inputStream = new FileInputStream(file);
        XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(inputStream);

        List<Administration> list = InitObjects(repository.getData());

        writeData(workbook.getSheetAt(0), list);

        workbook.write(stream);
        excelFileRepository.save(new ExcelFile("PK17", "2021", stream.toByteArray()));
        workbook.close();
    }

    private List<Administration> InitObjects(List<PK17Data> data) {
        Map<String, Administration> administrations = new HashMap<>();
        for (var item:
             data) {
            if (!administrations.containsKey(item.getAdm())) {
                administrations.put(item.getAdm(), new Administration(item.getAdm()));
            }

            if (item.getB_ind() != null) {
                administrations.get(item.getAdm()).addContainer(
                        item.getYear(),
                        new Container(
                                item.getNum(),
                                !item.getB_ind().replaceAll("\\s+", "").isEmpty()
                        )
                );
            }
            else {
                administrations.get(item.getAdm()).addContainer(
                        item.getYear(),
                        new Container(
                                item.getNum(),
                                Integer.parseInt(item.getMasbr().replaceAll("\\s+", "")) >= 10
                        )
                );
            }
        }

        return administrations.values().stream().toList();
    }

    private void writeData(XSSFSheet sheet, List<Administration> data) {
        List<PK17AbdData> containersABDPK = repository.getContainersABDPK();

        for (var element:
             data) {
            createCells(sheet.getRow(rowIndexes.get(element.code)), element, containersABDPK);
        }

        createAllRow(sheet);
    }

    private void createAllRow(XSSFSheet sheet) {
        int[] allRow = new int[22];
        int[] allABD = new int[22];

        for (var entry:
             rowIndexes.entrySet()) {

            if (entry.getKey().equals("NoABD")) {
                for (int i = 0; i < 22; i++) {
                    allRow[i] += sheet.getRow(entry.getValue()).getCell(i + 1).getNumericCellValue();
                }
            }
            else {
                for (int i = 0; i < 22; i++) {
                    int val = (int)sheet.getRow(entry.getValue()).getCell(i + 1).getNumericCellValue();
                    allRow[i] += val;
                    allABD[i] += val;
                }
            }
        }

        XSSFRow row = sheet.getRow(rowIndexes.get("all"));
        XSSFRow rowABD = sheet.getRow(rowIndexes.get("allABD"));

        for (int i = 0; i < 22; i++) {
            row.getCell(i + 1).setCellValue(allRow[i]);
            rowABD.getCell(i + 1).setCellValue(allABD[i]);
        }
    }
    private void createCells(XSSFRow row, Administration element, List<PK17AbdData> containersABDPK) {
        createCell(row, 1, element.get2021Count(), null);
        createCell(row, 2, element.get2021CountKTK(), null);
        createCell(row, 3, element.get2020Count(), null);
        createCell(row, 4, element.get2020CountKTK(), null);
        createCell(row, 5, element.get2019Count(), null);
        createCell(row, 6, element.get2019CountKTK(), null);

        createCell(row, 7, element.get2021_2020_2019Matching(), null);
        createCell(row, 8, element.get2021_2020_2019MatchingKTK(), null);
        createCell(row, 9, element.get2021_2020Matching(), null);
        createCell(row, 10, element.get2021_2020MatchingKTK(), null);
        createCell(row, 11, element.get2021_2019Matching(), null);
        createCell(row, 12, element.get2021_2019MatchingKTK(), null);
        createCell(row, 13, element.get2021_2020Matching(), null);
        createCell(row, 14, element.get2021_2020MatchingKTK(), null);

        createCell(row, 15, element.get2021MatchingOnly(), null);
        createCell(row, 16, element.get2021MatchingOnlyKTK(), null);
        createCell(row, 17, element.get2020MatchingOnly(), null);
        createCell(row, 18, element.get2020MatchingOnlyKTK(), null);
        createCell(row, 19, element.get2019MatchingOnly(), null);
        createCell(row, 20, element.get2019MatchingOnlyKTK(), null);

        createCell(row, 21, element.getLastColumn(containersABDPK), null);
        createCell(row, 22, element.getLastColumnKTK(containersABDPK), null);
    }

    class Administration {
        private String code;
        private Map<String, List<Container>> containers;

        private int count2019;
        private int count2019KTK;
        private int count2020;
        private int count2020KTK;
        private int count2021;
        private int count2021KTK;

        private int count2019_2020_2021Matching;
        private int count2019_2020_2021MatchingKTK;
        private int count2021_2020Matching;
        private int count2021_2020MatchingKTK;
        private int count2021_2019Matching;
        private int count2021_2019MatchingKTK;
        private int count2020_2019Matching;
        private int count2020_2019MatchingKTK;
        private int count2019MatchingOnly;
        private int count2019MatchingOnlyKTK;
        private int count2020MatchingOnly;
        private int count2020MatchingOnlyKTK;
        private int count2021MatchingOnly;
        private int count2021MatchingOnlyKTK;
        private int countLastColumn;
        private int countLastColumnKTK;

        public Administration(String code) {
            this.code = code;

            containers = new HashMap<>();
            containers.put("2019", new ArrayList<>());
            containers.put("2020", new ArrayList<>());
            containers.put("2021", new ArrayList<>());
        }

        public void addContainer(String year, Container container) {
            containers.get(year).add(container);
        }

        public void addContainers(Map<String, List<Container>> containerMap) {
            for (var entry:
                 containerMap.entrySet()) {
                containers.get(entry.getKey()).addAll(entry.getValue());
            }
        }

        public int get2021Count() {
            return containers.get("2021").size();
        }

        public int get2021CountKTK() {
            return (int) containers.get("2021").stream().filter(el -> el.isKTK).count();
        }

        public int get2020Count() {
            return containers.get("2020").size();
        }

        public int get2020CountKTK() {
            return (int) containers.get("2020").stream().filter(el -> el.isKTK).count();
        }

        public int get2019Count() {
            return containers.get("2019").size();
        }

        public int get2019CountKTK() {
            return (int) containers.get("2019").stream().filter(el -> el.isKTK).count();
        }

        public int get2021_2020_2019Matching() {
            List<String> list = containers.get("2020").stream().map(el -> el.num).toList();
            List<String> list2 = containers.get("2019").stream().map(el -> el.num).toList();
            return (int) containers.get("2021").stream()
                    .filter(el -> list.contains(el.num))
                    .filter(el -> list2.contains(el.num))
                    .count();
        }

        public int get2021_2020_2019MatchingKTK() {
            List<String> list = containers.get("2020").stream().map(el -> el.num).toList();
            List<String> list2 = containers.get("2019").stream().map(el -> el.num).toList();
            return (int) containers.get("2021").stream()
                    .filter(el -> el.isKTK)
                    .filter(el -> list.contains(el.num))
                    .filter(el -> list2.contains(el.num))
                    .count();
        }
        public int get2021_2020Matching() {
            List<String> list = containers.get("2020").stream().map(el -> el.num).toList();
            return (int) containers.get("2021").stream().filter(el -> list.contains(el.num)).count();
        }

        public int get2021_2020MatchingKTK() {
            List<String> list = containers.get("2020").stream().map(el -> el.num).toList();
            return (int) containers.get("2021").stream()
                    .filter(el -> el.isKTK)
                    .filter(el -> list.contains(el.num)).count();
        }

        public int get2021_2019Matching() {
            List<String> list = containers.get("2019").stream().map(el -> el.num).toList();
            return (int) containers.get("2021").stream()
                    .filter(el -> list.contains(el.num)).count();
        }
        public int get2021_2019MatchingKTK() {
            List<String> list = containers.get("2019").stream().map(el -> el.num).toList();
            return (int) containers.get("2021").stream()
                    .filter(el -> el.isKTK)
                    .filter(el -> list.contains(el.num)).count();
        }

        public int get2020_2019Matching() {
            List<String> list = containers.get("2020").stream().map(el -> el.num).toList();
            return (int) containers.get("2019").stream()
                    .filter(el -> list.contains(el.num)).count();
        }
        public int get2020_2019MatchingKTK() {
            List<String> list = containers.get("2020").stream().map(el -> el.num).toList();
            return (int) containers.get("2019").stream()
                    .filter(el -> el.isKTK)
                    .filter(el -> list.contains(el.num)).count();
        }

        public int get2021MatchingOnly() {
            List<String> list = containers.get("2020").stream().map(el -> el.num).toList();
            List<String> list2 = containers.get("2019").stream().map(el -> el.num).toList();
            return (int) containers.get("2021").stream()
                    .filter(el -> !list.contains(el.num))
                    .filter(el -> !list2.contains(el.num))
                    .count();
        }
        public int get2021MatchingOnlyKTK() {
            List<String> list = containers.get("2020").stream().map(el -> el.num).toList();
            List<String> list2 = containers.get("2019").stream().map(el -> el.num).toList();
            return (int) containers.get("2021").stream()
                    .filter(el -> el.isKTK)
                    .filter(el -> !list.contains(el.num))
                    .filter(el -> !list2.contains(el.num))
                    .count();
        }

        public int get2020MatchingOnly() {
            List<String> list = containers.get("2021").stream().map(el -> el.num).toList();
            List<String> list2 = containers.get("2019").stream().map(el -> el.num).toList();
            return (int) containers.get("2020").stream()
                    .filter(el -> !list.contains(el.num))
                    .filter(el -> !list2.contains(el.num))
                    .count();
        }
        public int get2020MatchingOnlyKTK() {
            List<String> list = containers.get("2021").stream().map(el -> el.num).toList();
            List<String> list2 = containers.get("2019").stream().map(el -> el.num).toList();
            return (int) containers.get("2020").stream()
                    .filter(el -> el.isKTK)
                    .filter(el -> !list.contains(el.num))
                    .filter(el -> !list2.contains(el.num))
                    .count();
        }

        public int get2019MatchingOnly() {
            List<String> list = containers.get("2020").stream().map(el -> el.num).toList();
            List<String> list2 = containers.get("2021").stream().map(el -> el.num).toList();
            return (int) containers.get("2019").stream()
                    .filter(el -> !list.contains(el.num))
                    .filter(el -> !list2.contains(el.num))
                    .count();
        }

        public int get2019MatchingOnlyKTK() {
            List<String> list = containers.get("2020").stream().map(el -> el.num).toList();
            List<String> list2 = containers.get("2021").stream().map(el -> el.num).toList();
            return (int) containers.get("2019").stream()
                    .filter(el -> el.isKTK)
                    .filter(el -> !list.contains(el.num))
                    .filter(el -> !list2.contains(el.num))
                    .count();
        }

        public int getLastColumn(List<PK17AbdData> data) {
            List<String> containerNums = containers.values().stream()
                    .flatMap(List::stream)
                    .map(el -> el.num)
                    .toList();

            return (int)data.stream()
                    .filter(el -> el.getSob().equals(code))
                    .filter(el -> !containerNums.contains(el.getNum())).count();
        }

        public int getLastColumnKTK(List<PK17AbdData> data) {
            List<String> containerNums = containers.values().stream()
                    .flatMap(List::stream)
                    .map(el -> el.num)
                    .toList();

            return (int)data.stream()
                    .filter(el -> el.getSob().equals(code))
                    .filter(el -> !el.getB_ind().replaceAll("\\s+", "").isEmpty())
                    .filter(el -> !containerNums.contains(el.getNum())).count();
        }

        public void update() {
            containers.get("2019").forEach(el -> {
                if (containers.get("2020").contains(el)) {
                    if (el.isKTK) {
                        count2020_2019MatchingKTK++;
                    }
                    else {
                        count2020_2019Matching++;
                    }
                }
            });
        }
        @Override
        public String toString() {
            return "Administration{" +
                    "code='" + code + '\'' +
                    ", containers=" + containers +
                    '}';
        }
    }

    class Container {
        private String num;
        private boolean isKTK;

        public Container(String num, boolean isKTK) {
            this.num = num;
            this.isKTK = isKTK;
        }

    }
}
