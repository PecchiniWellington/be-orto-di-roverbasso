package com.ortoroverbasso.ortorovebasso.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Benvenuto! L'app è avviata correttamente 🚀";
    }
}
