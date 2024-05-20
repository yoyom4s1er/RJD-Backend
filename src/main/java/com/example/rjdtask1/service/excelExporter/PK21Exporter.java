package com.example.rjdtask1.service.excelExporter;

import com.example.rjdtask1.model.ExcelFile;
import com.example.rjdtask1.model.PK21Data;
import com.example.rjdtask1.model.compositeId.ExcelFileId;
import com.example.rjdtask1.repository.ExcelFileRepository;
import com.example.rjdtask1.repository.PK21Repository;
import com.example.rjdtask1.repository.PK5Repository;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class PK21Exporter {

    private final ExcelFileRepository excelFileRepository;
    private final PK21Repository pk21Repository;

    public void exportToStream(ByteArrayOutputStream stream, String year, boolean forceGenerate) throws IOException {
        if (!forceGenerate) {
            ExcelFileId excelFileId = new ExcelFileId("PK21", year);
            if (excelFileRepository.existsById(excelFileId)) {
                stream.write(excelFileRepository.getReferenceById(excelFileId).getFileData());
                return;
            }
        }

        File file = ResourceUtils.getFile("classpath:PK_21.xlsx");
        InputStream inputStream = new FileInputStream(file);
        XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(inputStream);

        //createAllAdmRow(workbook, initObjects(pk_8_2Repository.getQueryResult()));
        //writeDataLines(workbook.getSheetAt(0), initObjects(pk21Repository.getQueryResult(year)));

        System.out.println(initObjects(pk21Repository.getQueryResult(year)));

        XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);

        workbook.write(stream);
        excelFileRepository.save(new ExcelFile("PK21", year, stream.toByteArray()));
        workbook.close();
    }

    private List<Administration> initObjects(List<PK21Data> queryResult) {

        Map<String, Administration> administrations = new HashMap<>();
        for (var data:
             queryResult) {
            if (!administrations.containsKey(data.getAdm())) {
                administrations.put(data.getAdm(), new Administration());
            }

            administrations.get(data.getAdm()).roads.add(data);
        }

        return administrations.values().stream().toList();
    }

    private class Administration {
        private List<PK21Data> roads = new ArrayList<>();
    }
}
