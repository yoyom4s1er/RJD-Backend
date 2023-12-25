package com.example.rjdtask1.controller;

import com.example.rjdtask1.model.Classifier1;
import com.example.rjdtask1.repository.Classifier1Repository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/classifier1")
public class Classifier1Controller {

    private Classifier1Repository classifier1Repository;

    @GetMapping("")
    public List<Classifier1> getAll() {
        return classifier1Repository.findAll();
    }
}
