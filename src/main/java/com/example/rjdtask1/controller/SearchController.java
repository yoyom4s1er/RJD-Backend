package com.example.rjdtask1.controller;

import com.example.rjdtask1.repository.SearchRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/search")
public class SearchController {

    private SearchRepository searchRepository;
    @GetMapping("/tableNames")
    public List<String> searchResponse(@RequestParam String query) {

        return searchRepository.searchTableNames(query);
    }
}
