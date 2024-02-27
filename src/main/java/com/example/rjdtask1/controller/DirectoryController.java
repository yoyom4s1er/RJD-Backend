package com.example.rjdtask1.controller;

import com.example.rjdtask1.model.Classifier1;
import com.example.rjdtask1.model.Directory1;
import com.example.rjdtask1.model.Directory2;
import com.example.rjdtask1.repository.Directory1Repository;
import com.example.rjdtask1.repository.Directory2Repository;
import com.example.rjdtask1.repository.TableNamesRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("api/directories")
public class DirectoryController {

    private Directory1Repository directoryRepository;
    private Directory2Repository directory2Repository;
    private TableNamesRepository tableNames;

    @GetMapping("/directory1")
    public List<Directory1> directory1GetAll() {
        return directoryRepository.findAll();
    }

    @PostMapping("/directory1")
    public ResponseEntity<Directory1> addDirectory1(@RequestBody Directory1 directory1) {
        directoryRepository.save(directory1);

        return ResponseEntity.ok(directory1);
    }

    @PutMapping("/directory1")
    public ResponseEntity updateDirectory1(@RequestBody Directory1 directory1) {
        directoryRepository.save(directory1);
        return ResponseEntity.ok(null);
    }
    @DeleteMapping("directory1/{id}")
    public ResponseEntity deleteDirectory1(@PathVariable long id) {
        directoryRepository.deleteById(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("directory1/excel")
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
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Spravochnik1.xlsx");
            return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()),
                    header, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
            //return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/directory2")
    public List<Directory2> directory2GetAll() {
        return directory2Repository.findAll();
    }

    @GetMapping("")
    public List<String> getTableNames() {
        return tableNames.getDirectoryTables();
    }
}
