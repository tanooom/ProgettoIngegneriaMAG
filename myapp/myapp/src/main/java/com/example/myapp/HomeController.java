package com.example.myapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/accesso")
    public String accesso() {
        return "accesso"; 
    }

    @GetMapping("/home") //qui potrebbe volerci solo "/"
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.getUser(username);
        model.addAttribute("user", user);  // Passa l'utente alla vista
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