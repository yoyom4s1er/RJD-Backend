package com.example.rjdtask1.controller;

import com.example.rjdtask1.model.Directory1;
import com.example.rjdtask1.model.Directory2;
import com.example.rjdtask1.repository.Directory1Repository;
import com.example.rjdtask1.repository.Directory2Repository;
import com.example.rjdtask1.repository.TableNamesRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/directory2")
    public List<Directory2> directory2GetAll() {
        return directory2Repository.findAll();
    }

    @GetMapping("")
    public List<String> getTableNames() {
        return tableNames.getDirectoryTables();
    }
}
