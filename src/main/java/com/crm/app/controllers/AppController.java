package com.crm.app.controllers;
import com.crm.app.models.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Base64;


@RestController
@CrossOrigin
@RequestMapping("/")
public class AppController {
    // Index
    @GetMapping("/")
    public String home() {
        return "index";
    }

}
