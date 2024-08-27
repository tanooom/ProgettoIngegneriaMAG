package com.example.myapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistrationController {

    @GetMapping("/registrationSuccess")
    public String registrationSuccess() {
        return "registrationSuccess"; // Questa è la vista (es. registrationSuccess.html)
    }
}

