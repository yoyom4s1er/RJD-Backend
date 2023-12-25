package com.example.rjdtask1.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("api/page")
public class MainPageController {

    @GetMapping("")
    public String getAll() {
        return "index";
    }
}
