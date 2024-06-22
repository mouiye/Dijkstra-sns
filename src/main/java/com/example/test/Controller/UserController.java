package com.example.test.Controller;

import com.example.test.Model.SiteUser;
import com.example.test.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String loginId, @RequestParam String password) {

        if (userService.findByLoginId(loginId).isPresent()) {
            return "redirect:/login";
        }

        userService.register(loginId, password);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Principal principal) {

        if (principal != null) {
            return "redirect:/";
        }

        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String loginId, @RequestParam String password, Model model) {
        Optional<SiteUser> siteUser = userService.findByLoginId(loginId);
        if (siteUser.isPresent() && siteUser.get().getPassword().equals(password)) {
            model.addAttribute("user", siteUser);
            return "redirect:/profile";
        } else {
            model.addAttribute("error", "Invalid login ID or password");
            return "login";
        }
    }

}