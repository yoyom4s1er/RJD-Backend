package com.example.rjdtask1.controller;

import com.example.rjdtask1.model.Classifier1;
import com.example.rjdtask1.model.Classifier2;
import com.example.rjdtask1.repository.Classifier1Repository;
import com.example.rjdtask1.repository.Classifier2Repository;
import com.example.rjdtask1.repository.TableNames;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/classifiers")
public class ClassifierController {

    private Classifier1Repository classifier1Repository;
    private Classifier2Repository classifier2Repository;
    private TableNames tableNames;

    @GetMapping("/k1")
    public List<Classifier1> k1GetAll() {
        return classifier1Repository.findAll();
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
