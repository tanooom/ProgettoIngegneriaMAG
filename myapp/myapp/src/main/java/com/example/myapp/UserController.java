package com.example.myapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";  // Questo restituirà il template register.html
    }

    @PostMapping("/user/register")
    public String registerUser(
            @RequestParam String username, 
            @RequestParam String password,
            @RequestParam String nome,
            @RequestParam String cognome,
            @RequestParam String mail,
            Model model) {

        userService.registerUser(username, password, nome, cognome, mail);
        model.addAttribute("username", username);
        return "registrationSuccess";
    }

    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }
}