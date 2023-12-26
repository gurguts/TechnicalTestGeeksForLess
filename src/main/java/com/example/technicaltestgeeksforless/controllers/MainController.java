package com.example.technicaltestgeeksforless.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Main Controller that serves to redirect to HTML pages.
 */
@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping
    public String getMainPage() {
        return "index";
    }

    @GetMapping("/list")
    public String getListPage() {
        return "list";
    }
}
