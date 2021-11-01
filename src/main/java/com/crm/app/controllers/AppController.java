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

    @GetMapping("/login")
    public boolean isLogin(@RequestBody User user) {
        return
                user.getUsername().equals("user") && user.getPassword().equals("password");
    }

    @GetMapping("/users")
    public Principal user(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization")
                .substring("Basic".length()).trim();
        return () ->  new String(Base64.getDecoder()
                .decode(authToken)).split(":")[0];
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/error/access-denied";
    }

}
