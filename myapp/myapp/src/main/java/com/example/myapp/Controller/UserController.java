package com.example.myapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.myapp.Model.Utente;
//import com.example.myapp.Service.AuthService;
import com.example.myapp.Service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    //@Autowired
    //private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder; // Aggiungi questa linea

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

        // Codifica la password
        String encodedPassword = passwordEncoder.encode(password);

        // Crea un nuovo oggetto Utente con la password codificata
        Utente user = new Utente(username, encodedPassword, nome, cognome, mail);

        try {
            // Registra l'utente usando il metodo register
            userService.register(user);

            // Autentica automaticamente l'utente dopo la registrazione
            //authService.autoLogin(username, password);

            // Passa i dati per la visualizzazione nella pagina di successo
            model.addAttribute("nome", nome);
            model.addAttribute("cognome", cognome);
            model.addAttribute("username", username);

            // Reindirizza a registrationSuccess.html
            return "redirect:/registrationSuccess";  // Usa redirect per reindirizzare

        } catch (Exception e) {
            logger.error("Si è verificato un errore durante la registrazione", e);
            model.addAttribute("errorMessage", "Si è verificato un errore durante la registrazione: " + e.getMessage());
            return "redirect:/login";
        }
    }

    // Classe di eccezione personalizzata per la gestione delle risorse non trovate
    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }
}
