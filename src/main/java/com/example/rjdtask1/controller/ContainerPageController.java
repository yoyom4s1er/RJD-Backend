package com.example.rjdtask1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/perepis")
public class ContainerPageController {

    @GetMapping("")
    public String getPage() {
        return "ContainerPerepis";
    }
}
