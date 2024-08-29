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
    private AuthService authService;

    // Mostra il modulo di registrazione
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new Utente());
        return "register";
    }

    // Gestisce la registrazione dell'utente
    @PostMapping("/user/register")
    public String registerUser(
            @RequestParam String username, 
            @RequestParam String password,
            @RequestParam String nome,
            @RequestParam String cognome,
            @RequestParam String mail,
            Model model) {

        // Crea un nuovo oggetto User
        Utente user = new Utente(username, password, nome, cognome, mail);

        try {
            // Registra l'utente usando il metodo register
            userService.register(user);

            // Autentica automaticamente l'utente dopo la registrazione
            authService.autoLogin(username, password);

            // Passa i dati per la visualizzazione nella pagina di successo
            model.addAttribute("nome", nome);
            model.addAttribute("cognome", cognome);
            model.addAttribute("username", username);

            // Reindirizza a registrationSuccess.html
            return "registrationSuccess";

        } catch (Exception e) {
            // Gestisce eventuali eccezioni durante la registrazione
            model.addAttribute("errorMessage", "Si è verificato un errore durante la registrazione: " + e.getMessage());
            return "register"; // Torna al modulo di registrazione con un messaggio di errore
        }
    }

    @GetMapping("/registrationSuccess")
    public String registrationSuccess() {
        return "registrationSuccess"; // Nome del file registrationSuccess.html
    }

    // Classe di eccezione personalizzata per la gestione delle risorse non trovate
    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }
}