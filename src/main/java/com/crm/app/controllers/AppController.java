package com.crm.app.controllers;
import org.springframework.web.bind.annotation.*;




@RestController
@CrossOrigin
@RequestMapping("/")
public class AppController {
    // Index
    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/error/access-denied";
    }

}
