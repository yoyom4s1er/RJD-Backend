package com.example.rjdtask1.controller;

import com.example.rjdtask1.model.Directory2;
import com.example.rjdtask1.repository.Directory2Repository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/directory2")
public class Directory2Controller {

    private Directory2Repository directoryRepository;

    @GetMapping("")
    public List<Directory2> getAll() {
        return directoryRepository.findAll();
    }
}
