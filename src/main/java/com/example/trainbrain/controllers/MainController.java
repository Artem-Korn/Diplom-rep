package com.example.trainbrain.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String greeting() {
        return "home";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}