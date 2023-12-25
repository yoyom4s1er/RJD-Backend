package com.example.rjdtask1.controller;

import com.example.rjdtask1.model.Directory1;
import com.example.rjdtask1.repository.Directory1Repository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/directory1")
public class Directory1Controller {

    private Directory1Repository directoryRepository;

    @GetMapping("")
    public List<Directory1> getAll() {
        return directoryRepository.findAll();
    }
}
