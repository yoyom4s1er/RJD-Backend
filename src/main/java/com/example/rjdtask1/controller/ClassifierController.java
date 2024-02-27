package com.example.rjdtask1.controller;

import com.example.rjdtask1.model.Classifier1;
import com.example.rjdtask1.model.Classifier2;
import com.example.rjdtask1.repository.Classifier1Repository;
import com.example.rjdtask1.repository.Classifier2Repository;
import com.example.rjdtask1.repository.TableNamesRepository;
import com.example.rjdtask1.service.excelExporter.TableExcelExporter;
import lombok.AllArgsConstructor;
import org.hibernate.query.Order;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("api/classifiers")
public class ClassifierController {

    private Classifier1Repository classifier1Repository;
    private Classifier2Repository classifier2Repository;
    private TableNamesRepository tableNames;

    @GetMapping("/k1")
    public List<Classifier1> k1GetAll() {
        return classifier1Repository.findAll(Sort.by("id"));
    }

    @GetMapping("k1/{id}")
    public Classifier1 getK1(@PathVariable long id) {
        return classifier1Repository.findById(id).get();
    }

    @PostMapping("/k1")
    public ResponseEntity addK1(@RequestBody Classifier1 classifier1) {
        classifier1Repository.save(classifier1);

        return ResponseEntity.ok(classifier1);
    }

    @PutMapping("/k1")
    public ResponseEntity updateK1(@RequestBody Classifier1 classifier1) {
        classifier1Repository.save(classifier1);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("k1/{id}")
    public ResponseEntity deleteK1(@PathVariable long id) {
        classifier1Repository.deleteById(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("k1/excel")
    public ResponseEntity<ByteArrayResource> downloadK1Excel(
            @RequestParam(name = "sortColumnName", required = false) Optional<String> sortColumnName,
            @RequestParam(name = "sortOrder", required = false, defaultValue = "ASC") Optional<String> sortOrder) {

        Sort.Direction direction = Sort.Direction.ASC;
        String sortColumn = "id";

        if (sortOrder.isPresent()) {
            direction = Sort.Direction.valueOf(sortOrder.get());
        }

        if (sortColumnName.isPresent()) {
            sortColumn = sortColumnName.get();
        }

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Klassifikator1.xlsx");
            return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()),
                    header, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
            //return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/k2")
    public List<Classifier2> k2GetAll() {
        return classifier2Repository.findAll();
    }

    @PostMapping("/k2")
    public ResponseEntity addK2(@RequestBody Classifier2 classifier2) {
        classifier2Repository.save(classifier2);

        return ResponseEntity.ok(classifier2);
    }

    @PutMapping("/k2")
    public ResponseEntity updateK2(@RequestBody Classifier2 classifier2) {
        classifier2Repository.save(classifier2);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("k2/{id}")
    public ResponseEntity deleteK2(@PathVariable long id) {
        classifier2Repository.deleteById(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("k2/excel")
    public ResponseEntity<ByteArrayResource> downloadK2Excel(
            @RequestParam(name = "sortColumnName", required = false) Optional<String> sortColumnName,
            @RequestParam(name = "sortOrder", required = false, defaultValue = "ASC") Optional<String> sortOrder) {

        Sort.Direction direction = Sort.Direction.ASC;
        String sortColumn = "id";

        if (sortOrder.isPresent()) {
            direction = Sort.Direction.valueOf(sortOrder.get());
        }

        if (sortColumnName.isPresent()) {
            sortColumn = sortColumnName.get();
        }

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Klassifikator2.xlsx");
            return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()),
                    header, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
            //return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public List<String> getTableNames() {
        return tableNames.getClassifierTables();
    }
}
