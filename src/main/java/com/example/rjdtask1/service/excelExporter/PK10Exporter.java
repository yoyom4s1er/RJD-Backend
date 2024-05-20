package com.example.rjdtask1.service.excelExporter;

import com.example.rjdtask1.model.ExcelFile;
import com.example.rjdtask1.model.PK10Data;
import com.example.rjdtask1.model.PK7Data;
import com.example.rjdtask1.model.compositeId.ExcelFileId;
import com.example.rjdtask1.repository.ExcelFileRepository;
import com.example.rjdtask1.repository.PK10Repository;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.*;

@Service
public class PK10Exporter {

    private final PK10Repository pk10Repository;
    private final ExcelFileRepository excelFileRepository;
    private final Map<String, Integer> rowIndexes;

    public PK10Exporter(PK10Repository pk10Repository, ExcelFileRepository excelFileRepository) {
        this.pk10Repository = pk10Repository;
        this.excelFileRepository = excelFileRepository;

        rowIndexes = new HashMap<>();
        rowIndexes.put("57", 8);
        rowIndexes.put("58", 9);
        rowIndexes.put("21", 10);
        rowIndexes.put("27", 11);
        rowIndexes.put("59", 12);
        rowIndexes.put("23", 13);
        rowIndexes.put("20", 14);
        rowIndexes.put("66", 15);
        rowIndexes.put("67", 16);
        rowIndexes.put("29", 17);
        rowIndexes.put("22", 18);
        rowIndexes.put("28", 19);
        rowIndexes.put("25", 20);
        rowIndexes.put("24", 21);
        rowIndexes.put("26", 22);
    }

    public void exportToStream(ByteArrayOutputStream stream, String year, boolean forceGenerate) throws IOException {
        if (!forceGenerate) {
            ExcelFileId excelFileId = new ExcelFileId("PK10", year);
            if (excelFileRepository.existsById(excelFileId)) {
                stream.write(excelFileRepository.getReferenceById(excelFileId).getFileData());
                return;
            }
        }

        File file = ResourceUtils.getFile("classpath:PK_10.xlsx");
        InputStream inputStream = new FileInputStream(file);
        XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(inputStream);

        writeDataLines(workbook.getSheetAt(0), initObjects(pk10Repository.getQueryResult(year)));

        XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);

        workbook.write(stream);
        excelFileRepository.save(new ExcelFile("PK10", year, stream.toByteArray()));
        workbook.close();
    }

    private void writeDataLines(XSSFSheet sheetAt, List<TableRow> tableRows) {

        for (var data:
                tableRows) {
            createCells(sheetAt.getRow(rowIndexes.get(data.adm)), data);
        }
    }

    private void createCells(XSSFRow row, TableRow data) {
        createCell(row, 5, data.stkKontrol);
        createCell(row, 6, data.ktkKontrol);
        createCell(row, 8, data.stkNoKontrol);
        createCell(row, 9, data.ktkNoKontrol);
        createCell(row, 11, data.stkDuplicates);
        createCell(row, 12, data.ktkDuplicates);
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
    private List<TableRow> initObjects(List<PK10Data> queryResult) {

        Map<String, TableRow> rows = new HashMap<>();

        for (var data:
             queryResult) {
            if (rows.get(data.getAdm()) == null) {
                rows.put(data.getAdm(), new TableRow(data.getAdm()));
            }

            rows.get(data.getAdm()).addData(data);
        }

        return rows.values().stream().toList();
    }

    private class TableRow {
        private String adm;
        private int stkKontrol;
        private int ktkKontrol;
        private int stkNoKontrol;
        private int ktkNoKontrol;
        private int stkDuplicates;
        private int ktkDuplicates;

        Set<String> nums = new HashSet<>();

        public TableRow(String adm) {
            this.adm = adm;
        }

        public void addData(PK10Data data) {
            if (Integer.parseInt(data.getMasbr()) < 10) {
                if (data.isKontrol()) {
                    stkKontrol++;
                }
                else  {
                    stkNoKontrol++;
                }

                if (nums.contains(data.getNum()))
                    stkDuplicates++;
            }
            else {
                if (data.isKontrol()) {
                    ktkKontrol++;
                }
                else  {
                    ktkNoKontrol++;
                }

                if (nums.contains(data.getNum()))
                    ktkDuplicates++;
            }

            nums.add(data.getNum());
        }

        @Override
        public String toString() {
            return "TableRow{" +
                    "adm='" + adm + '\'' +
                    ", stkKontrol=" + stkKontrol +
                    ", ktkKontrol=" + ktkKontrol +
                    ", stkNoKontrol=" + stkNoKontrol +
                    ", ktkNoKontrol=" + ktkNoKontrol +
                    ", stkDuplicates=" + stkDuplicates +
                    ", ktkDuplicates=" + ktkDuplicates +
                    '}';
        }
    }
}
