package com.cs319group3.backend.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultContoller {
    @GetMapping("/")
    public String home() {
        return "Osuruk kemiksiz kakadır yeğen";
    }
}

