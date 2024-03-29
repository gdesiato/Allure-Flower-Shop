package com.giuseppe.allureshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping
    public String homePage(){
        return "home";
    }

    // NAVIGATION BAR

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/explore")
    public String explore() {
        return "explore";
    }
}
