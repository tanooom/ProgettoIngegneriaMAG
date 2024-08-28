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

    @Autowired
    private AuthService authService; // Aggiungi AuthService

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

        // Crea un nuovo oggetto User
        User user = new User(username, password, nome, cognome, mail);

        // Registra l'utente usando il metodo register
        userService.register(user);

        // Autentica automaticamente l'utente dopo la registrazione
        authService.autoLogin(username, password); // Usa AuthService

        // Passa i dati per la visualizzazione nella pagina di successo
        model.addAttribute("nome", nome);
        model.addAttribute("cognome", cognome);
        model.addAttribute("username", username);
        return "registrationSuccess"; // Reindirizza a registrationSuccess.html
    }

    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }
}