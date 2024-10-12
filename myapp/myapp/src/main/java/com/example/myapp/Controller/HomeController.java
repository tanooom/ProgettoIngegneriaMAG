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

    @GetMapping("/home")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Utente user = userService.getUser(username);
        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("nome", user.getNome());
            model.addAttribute("cognome", user.getCognome());
        }
        return "home";
    }

    @GetMapping("/profilo")
    public String profilo(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Utente user = userService.getUser(username);
        if (user != null) {
            model.addAttribute("user", user); 
        }
        return "profilo";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        SecurityContextHolder.clearContext();
        return "redirect:/login?logout";
    }

    @GetMapping("/visualizzaStoria")
    public String visualizzaStoria() {
        return "visualizzaStoria";
    }

    @GetMapping("/giocaStoria")
    public String giocaStoria() {
        return "giocaStoria";
    }

    /*@GetMapping("/leMieStorie")
    public String leMieStorie() {
        return "leMieStorie";
    }*/
}