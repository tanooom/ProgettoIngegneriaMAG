package com.example.myapp.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.myapp.Model.Utente;
import com.example.myapp.Service.UserService;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    // Mostra il modulo di registrazione
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new Utente());
        return "register";
    }
    
    @GetMapping("/registrationSuccess")
    public String registrationSuccess() {
        return "registrationSuccess"; // Nome del file registrationSuccess.html
    }

    @PostMapping("/user/register")
    public String registerUser(
            @RequestParam String username, 
            @RequestParam String password,
            @RequestParam String nome,
            @RequestParam String cognome,
            @RequestParam String mail,
            Model model) {

        // Controlla se l'username esiste già nel database
        if (userService.findByUsername(username) != null) {
            model.addAttribute("errorMessage", "L'username è già in uso. Scegli un altro username.");
            return "register"; // Torna alla pagina di registrazione con l'errore
        }

        try {
            // Crea un nuovo oggetto Utente con la password in chiaro
            Utente user = new Utente(username, password, nome, cognome, mail);

            // Registra l'utente usando il metodo register nel servizio
            userService.register(user);

            // Passa i dati per la visualizzazione nella pagina di successo
            model.addAttribute("nome", nome);
            model.addAttribute("cognome", cognome);
            model.addAttribute("username", username);

            return "registrationSuccess";  // Usa redirect per reindirizzare

        } catch (Exception e) {
            logger.error("Si è verificato un errore durante la registrazione", e);
            model.addAttribute("errorMessage", "Si è verificato un errore durante la registrazione: " + e.getMessage());
            return "register"; // Torna alla pagina di registrazione con l'errore
        }
    }

    // Classe di eccezione personalizzata per la gestione delle risorse non trovate
    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }
}