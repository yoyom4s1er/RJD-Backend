package com.example.rjdtask1.controller;

import com.example.rjdtask1.model.Classifier1;
import com.example.rjdtask1.model.Classifier2;
import com.example.rjdtask1.repository.Classifier1Repository;
import com.example.rjdtask1.repository.Classifier2Repository;
import com.example.rjdtask1.repository.TableNamesRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/k2")
    public List<Classifier2> k2GetAll() {
        return classifier2Repository.findAll();
    }

    @GetMapping("")
    public List<String> getTableNames() {
        return tableNames.getClassifierTables();
    }
}
