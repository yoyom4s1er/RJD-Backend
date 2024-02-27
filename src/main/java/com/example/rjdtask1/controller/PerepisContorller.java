package com.example.rjdtask1.controller;

import com.example.rjdtask1.model.PK17Data;
import com.example.rjdtask1.repository.ExcelFileRepository;
import com.example.rjdtask1.repository.PK17Repository;
import com.example.rjdtask1.service.excelExporter.*;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/perepis")
public class PerepisContorller {

    private final ExcelFileRepository excelFileRepository;

    private final PK17Repository pk17Repository;
    private final PK17Exporter pk17Exporter;
    private final PK4Exporter pk4Exporter;
    private final PK3Exporter pk3Exporter;
    private final PK2Exporter pk2Exporter;
    private final PK8Exporter pk_8_Exporter;

    @SneakyThrows
    @GetMapping("/pk17")
    public List<PK17Data> getPK2() {
        return pk17Repository.getData();
    }
    @GetMapping("/pk2/{year}/excel")
    public ResponseEntity<ByteArrayResource> downloadPK2Excel(@PathVariable String year) {

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=PK2_" + year + ".xlsx");
            pk2Exporter.exportToStream(stream, year);
            return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()),
                    header, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
            //return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/pk8/excel")
    public ResponseEntity<ByteArrayResource> downloadPK8Excel() {

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=PK8.xlsx");
            return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()),
                    header, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
            //return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pk3/{year}/excel")
    public ResponseEntity<ByteArrayResource> downloadPK3Excel(@PathVariable String year) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=PK3.xlsx");
            pk3Exporter.exportToStream(stream, year);
            return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()),
                    header, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
            //return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pk4/{year}/excel")
    public ResponseEntity<ByteArrayResource> downloadPK4Excel(@PathVariable String year) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=PK_4.xlsx");
            pk4Exporter.exportToStream(stream, year);
            return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()),
                    header, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
            //return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pk17/{year}/excel")
    public ResponseEntity<ByteArrayResource> downloadPK17Excel(@PathVariable String year) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=PK_17.xlsx");
            pk17Exporter.exportToStream(stream, year);
            return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()),
                    header, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
            //return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
