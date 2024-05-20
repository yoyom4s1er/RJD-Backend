package com.example.rjdtask1.controller;

import com.example.rjdtask1.dto.TableEntity;
import com.example.rjdtask1.model.PK10Data;
import com.example.rjdtask1.model.PK17Data;
import com.example.rjdtask1.model.PK5_6_8Data;
import com.example.rjdtask1.model.PK7Data;
import com.example.rjdtask1.model.compositeId.ExcelFileId;
import com.example.rjdtask1.repository.*;
import com.example.rjdtask1.service.excelExporter.*;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/perepis")
public class PerepisContorller {

    private final PK17Exporter pk17Exporter;
    private final PK5Exporter pk5Exporter;
    private final PK6Exporter pk6Exporter;
    private final PK4Exporter pk4Exporter;
    private final PK3Exporter pk3Exporter;
    private final PK2Exporter pk2Exporter;
    private final PK8Exporter pk_8_Exporter;
    private final PK7Exporter pk7Exporter;
    private final PK10Exporter pk10Exporter;
    private final PK19Exporter pk19Exporter;
    private final PK21Exporter pk21Exporter;

    private final ExcelFileRepository excelFileRepository;
    private final PK10Repository pk10Repository;

    @GetMapping("/pk10/{year}")
    public List<PK10Data> getPK10() {
        return pk10Repository.getQueryResult("2020");
    }

    @DeleteMapping("/tables")
    public ResponseEntity deleteTables(@RequestBody List<TableEntity> tableEntities) {
        List<ExcelFileId> ids = new ArrayList<>();

        tableEntities.forEach(el -> {
            ids.add(new ExcelFileId(el.getTable(), el.getYear()));
        });

        System.out.println(ids);
        excelFileRepository.deleteAllById(ids);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/pk2/{year}/excel")
    public ResponseEntity<ByteArrayResource> downloadPK2Excel(
            @PathVariable
            String year,
            @RequestParam(name = "forceGenerate", defaultValue = "false", required = false)
            boolean forceGenerate) {

        if (year.length() != 4 || !year.matches("\\d+")) {
            throw new RuntimeException();
        }

        System.out.println(forceGenerate);

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=PK2_" + year + ".xlsx");
            pk2Exporter.exportToStream(stream, year, forceGenerate);
            return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()),
                    header, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
            //return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/pk8/{year}/excel")
    public ResponseEntity<ByteArrayResource> downloadPK8Excel(
            @PathVariable
            String year,
            @RequestParam(name = "forceGenerate", defaultValue = "false", required = false)
            boolean forceGenerate) {

        if (year.length() != 4 || !year.matches("\\d+")) {
            throw new RuntimeException();
        }

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=PK8_" + year + ".xlsx");
            pk_8_Exporter.exportToStream(stream, year, forceGenerate);
            return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()),
                    header, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
            //return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pk3/{year}/excel")
    public ResponseEntity<ByteArrayResource> downloadPK3Excel(
            @PathVariable
            String year,
            @RequestParam(name = "forceGenerate", defaultValue = "false", required = false)
            boolean forceGenerate) {

        if (year.length() != 4 || !year.matches("\\d+")) {
            throw new RuntimeException();
        }

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=PK3_" + year + ".xlsx");
            pk3Exporter.exportToStream(stream, year, forceGenerate);
            return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()),
                    header, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
            //return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pk4/{year}/excel")
    public ResponseEntity<ByteArrayResource> downloadPK4Excel(
            @PathVariable
            String year,
            @RequestParam(name = "forceGenerate", defaultValue = "false", required = false)
            boolean forceGenerate) {

        if (year.length() != 4 || !year.matches("\\d+")) {
            throw new RuntimeException();
        }

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=PK4_" + year + ".xlsx");
            pk4Exporter.exportToStream(stream, year, forceGenerate);
            return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()),
                    header, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
            //return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pk17/{year}/excel")
    public ResponseEntity<ByteArrayResource> downloadPK17Excel(
            @PathVariable
            String year,
            @RequestParam(name = "forceGenerate", defaultValue = "false", required = false)
            boolean forceGenerate) {

        if (year.length() != 4 || !year.matches("\\d+")) {
            throw new RuntimeException();
        }

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=PK17_" + year + ".xlsx");
            pk17Exporter.exportToStream(stream, year, forceGenerate);
            return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()),
                    header, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
            //return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pk5/{year}/excel")
    public ResponseEntity<ByteArrayResource> downloadPK5Excel(
            @PathVariable
            String year,
            @RequestParam(name = "forceGenerate", defaultValue = "false", required = false)
            boolean forceGenerate) {

        if (year.length() != 4 || !year.matches("\\d+")) {
            throw new RuntimeException();
        }

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=PK5_" + year + ".xlsx");
            pk5Exporter.exportToStream(stream, year, true);
            return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()),
                    header, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
            //return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pk6/{year}/excel")
    public ResponseEntity<ByteArrayResource> downloadPK6Excel(
            @PathVariable
            String year,
            @RequestParam(name = "forceGenerate", defaultValue = "false", required = false)
            boolean forceGenerate) {

        if (year.length() != 4 || !year.matches("\\d+")) {
            throw new RuntimeException();
        }

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=PK6_" + year + ".xlsx");
            pk6Exporter.exportToStream(stream, year, true);
            return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()),
                    header, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
            //return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pk7/{year}/excel")
    public ResponseEntity<ByteArrayResource> downloadPK7Excel(
            @PathVariable
            String year,
            @RequestParam(name = "forceGenerate", defaultValue = "false", required = false)
            boolean forceGenerate) {

        if (year.length() != 4 || !year.matches("\\d+")) {
            throw new RuntimeException();
        }

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=PK7_" + year + ".xlsx");
            pk7Exporter.exportToStream(stream, year, true);
            return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()),
                    header, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
            //return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pk10/{year}/excel")
    public ResponseEntity<ByteArrayResource> downloadPK10Excel(
            @PathVariable
            String year,
            @RequestParam(name = "forceGenerate", defaultValue = "false", required = false)
            boolean forceGenerate) {

        if (year.length() != 4 || !year.matches("\\d+")) {
            throw new RuntimeException();
        }

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=PK10_" + year + ".xlsx");
            pk10Exporter.exportToStream(stream, year, true);
            return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()),
                    header, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
            //return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pk19/{year}/excel")
    public ResponseEntity<ByteArrayResource> downloadPK19Excel(
            @PathVariable
            String year,
            @RequestParam(name = "forceGenerate", defaultValue = "false", required = false)
            boolean forceGenerate) {

        if (year.length() != 4 || !year.matches("\\d+")) {
            throw new RuntimeException();
        }

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=PK19_" + year + ".xlsx");
            pk19Exporter.exportToStream(stream, year, true);
            return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()),
                    header, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
            //return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pk21/{year}/excel")
    public ResponseEntity<ByteArrayResource> downloadPK21Excel(
            @PathVariable
            String year,
            @RequestParam(name = "forceGenerate", defaultValue = "false", required = false)
            boolean forceGenerate) {

        if (year.length() != 4 || !year.matches("\\d+")) {
            throw new RuntimeException();
        }

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=PK21_" + year + ".xlsx");
            pk21Exporter.exportToStream(stream, year, true);
            return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()),
                    header, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
            //return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
