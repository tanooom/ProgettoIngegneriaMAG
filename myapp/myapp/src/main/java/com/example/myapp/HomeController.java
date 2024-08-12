package com.example.myapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/accesso")
    public String accesso() {
        return "accesso"; 
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Benvenuto nella Home Page!");
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/visualizzaStoria")
    public String visualizzaStoria() {
        return "visualizzaStoria";
    }

    @GetMapping("/giocaStoria")
    public String giocaStoria() {
        return "giocaStoria";
    }
}
