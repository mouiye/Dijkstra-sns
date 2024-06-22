package com.example.test.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class MainController {

    @GetMapping("/")
    public String register(Principal principal) {

        if (principal != null) {
            return "redirect:/posts";
        } else {
            return "redirect:/login";
        }
    }
}
