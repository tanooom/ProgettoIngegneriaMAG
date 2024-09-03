package com.example.myapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.myapp.Model.Utente;
import com.example.myapp.Service.UserService;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/home") // TODO: Qui potrebbe volerci solo "/"
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Utente user = userService.getUser(username);
        model.addAttribute("user", user);  // Passa l'utente alla vista
        return "home"; // Restituisce home.html
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        SecurityContextHolder.clearContext(); // Pulisce il contesto di sicurezza
        return "redirect:/login?logout"; // Reindirizza al login
    }

    @GetMapping("/visualizzaStoria")
    public String visualizzaStoria() {
        return "visualizzaStoria";
    }

    @GetMapping("/scriviStoria")
    public String scriviStoria() {
        return "scriviStoria";
    }
}