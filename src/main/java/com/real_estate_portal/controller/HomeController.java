package com.real_estate_portal.controller;

// src/main/java/com/realestate/portal/controller/HomeController.java

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/listings";
    }
}
