package com.example.rjdtask1.service.excelExporter;

import com.example.rjdtask1.model.ExcelFile;
import com.example.rjdtask1.model.PK5_6_8Data;
import com.example.rjdtask1.model.compositeId.ExcelFileId;
import com.example.rjdtask1.repository.ExcelFileRepository;
import com.example.rjdtask1.repository.PK5Repository;
import com.example.rjdtask1.repository.PK8Repository;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
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
public class PK5Exporter {

    private final ExcelFileRepository excelFileRepository;
    private final PK5Repository pk5Repository;
    private final Map<String, Integer> columnIndexes;

    private final Map<String, Integer> rowIndexes;

    public PK5Exporter(ExcelFileRepository excelFileRepository, PK5Repository pk5Repository) {
        this.excelFileRepository = excelFileRepository;
        this.pk5Repository = pk5Repository;

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
        columnIndexes.put("ЛТГ", 15);
        columnIndexes.put("ЭВР", 16);
        columnIndexes.put("Б/пр", 17);
        columnIndexes.put("0", 18);

        rowIndexes = new HashMap<>();
        rowIndexes.put("Азербайджанская", 9);
        rowIndexes.put("Белорусская", 11);
        rowIndexes.put("Восточно-Сибирская", 30);
        rowIndexes.put("Горьковская", 20);
        rowIndexes.put("Грузинская", 56);
        rowIndexes.put("Дальневосточная", 32);
        rowIndexes.put("Донецкая", 55);
        rowIndexes.put("Забайкальская", 31);
        rowIndexes.put("ЗАО \"Южно-Кавказская железная дорога\"", 10);
        rowIndexes.put("Западно-Сибирская", 28);
        rowIndexes.put("Казахстанская", 12);
        rowIndexes.put("Калининградская", 18);
        rowIndexes.put("Красноярская", 29);
        rowIndexes.put("Куйбышевская", 25);
        rowIndexes.put("Кыргызская", 13);
        rowIndexes.put("Латвийская", 57);
        rowIndexes.put("Литовская", 58);
        rowIndexes.put("Львовская", 51);
        rowIndexes.put("Молдавская", 14);
        rowIndexes.put("Московская", 19);
        rowIndexes.put("ОАО АК \"Железные дороги Якутии\"", 60);
        rowIndexes.put("Одесская", 52);
        rowIndexes.put("Октябрьская", 17);
        rowIndexes.put("Приволжская", 24);
        rowIndexes.put("Приднепровская", 54);
        rowIndexes.put("Свердловская", 26);
        rowIndexes.put("Северная", 21);
        rowIndexes.put("Северо-Кавказская", 22);
        rowIndexes.put("Таджикская", 46);
        rowIndexes.put("Туркменская", 47);
        rowIndexes.put("Узбекская", 48);
        rowIndexes.put("ФГУП \"Крымская железная дорога\"", 45);
        rowIndexes.put("Эстонская", 59);
        rowIndexes.put("Юго-Восточная", 23);
        rowIndexes.put("Юго-Западная", 50);
        rowIndexes.put("Южная", 53);
        rowIndexes.put("Южно-Уральская", 27);
    }

    public void exportToStream(ByteArrayOutputStream stream, String year, boolean forceGenerate) throws IOException {
        if (!forceGenerate) {
            ExcelFileId excelFileId = new ExcelFileId("PK5", year);
            if (excelFileRepository.existsById(excelFileId)) {
                stream.write(excelFileRepository.getReferenceById(excelFileId).getFileData());
                return;
            }
        }

        File file = ResourceUtils.getFile("classpath:PK_5.xlsx");
        InputStream inputStream = new FileInputStream(file);
        XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(inputStream);

        //createAllAdmRow(workbook, initObjects(pk_8_2Repository.getQueryResult()));
        writeDataLines(workbook.getSheetAt(0), initObjects(pk5Repository.getQueryResult(year)));
        writeDataLines(workbook.getSheetAt(1), initObjects(pk5Repository.selectByKTK(year)));
        writeDataLines(workbook.getSheetAt(2), initObjects(pk5Repository.selectBySTK(year)));

        XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);

        workbook.write(stream);
        excelFileRepository.save(new ExcelFile("PK5", year, stream.toByteArray()));
        workbook.close();
    }

    private List<Railway> initObjects(List<PK5_6_8Data> entities) {

        List<Railway> administrations = new ArrayList<>();
        Railway current = null;
        for (var entity: entities) {

            if (administrations.stream().noneMatch(el -> el.dor.equals(entity.getRailWay()))) {
                current = new Railway(entity.getCountry(), entity.getRailWay());
                administrations.add(current);
            }
            current.sobs.put(entity.getOwner(), entity.getWagonCount());

        }

        return administrations;
    }

    protected void writeDataLines(XSSFSheet sheet, List<Railway> data) {
        for (var entity:
             data) {
            createCells(sheet.getRow(rowIndexes.get(entity.dor)), entity, null);
        }
    }

    private void createCells(XSSFRow row, Railway railway, XSSFCellStyle dataStyle) {
        createCell(row, 1, railway.sobsCount(), null);
        for (var sob: railway.sobs.entrySet()) {
            createCell(row, columnIndexes.get(sob.getKey()), sob.getValue(), dataStyle);
        }
    }

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
    }

    private class Railway {

        String adm;
        String dor;
        Map<String, Integer> sobs = new HashMap<>();

        public Railway(String adm, String dor) {
            this.adm = adm;
            this.dor = dor;
        }

        public int sobsCount() {
            return sobs.values().stream().reduce(0, Integer::sum);
        }

        @Override
        public String toString() {
            return "Railway{" +
                    "dor='" + dor + '\'' +
                    ", sobs=" + sobs +
                    '}';
        }
    }
}
