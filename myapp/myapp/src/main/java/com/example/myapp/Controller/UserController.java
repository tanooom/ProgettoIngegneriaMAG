package com.example.myapp.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.myapp.Model.Utente;
import com.example.myapp.Service.MapDBService;
import com.example.myapp.Service.UserService;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MapDBService mapDBService;
    
    @GetMapping("/getUser")
    public String getUser(@RequestParam String key) {
        return mapDBService.getUser(key);
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new Utente());
        return "register";
    }
    
    @GetMapping("/registrationSuccess")
    public String registrationSuccess() {
        return "registrationSuccess"; 
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
        if (userService.getUser(username) != null) {
            model.addAttribute("errorMessage", "L'username è già in uso. Scegli un altro username.");
            return "register";
        }
        try {
            Utente user = new Utente(username, password, nome, cognome, mail);
            userService.register(user);
            model.addAttribute("nome", nome);
            model.addAttribute("cognome", cognome);
            model.addAttribute("username", username);
            return "registrationSuccess"; 
        } catch (Exception e) {
            logger.error("Si è verificato un errore durante la registrazione", e);
            model.addAttribute("errorMessage", "Si è verificato un errore durante la registrazione: " + e.getMessage());
            return "register";
        }
    }

    // Endpoint per eliminare un utente
    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam String username) {
        try {
            userService.deleteUser(username);
            return ResponseEntity.ok("Utente eliminato con successo!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: " + e.getMessage());
        }
    }
}