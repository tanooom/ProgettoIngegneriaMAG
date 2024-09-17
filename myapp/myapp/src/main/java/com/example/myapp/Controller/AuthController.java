/*package com.example.myapp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.myapp.Model.Utente;
import com.example.myapp.Service.AuthService;
import com.example.myapp.Service.UserService;

@Controller
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, 
                           @RequestParam String nome, @RequestParam String cognome, 
                           @RequestParam String mail) {
        
        // Crea un nuovo oggetto User
        Utente user = new Utente(username, password, nome, cognome, mail);
        
        // Registra l'utente usando il metodo register
        userService.register(user);

        // Autentica automaticamente l'utente dopo la registrazione
        authService.autoLogin(username, password);

        return "redirect:/registrationSuccess";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        String encodedPassword = userService.getEncodedPassword(username);
        if (encodedPassword != null && userService.checkPassword(password, encodedPassword)) {
            authService.autoLogin(username, password);
            return "redirect:/home";
        }
        System.out.println("Login failed for user: " + username);
        return "redirect:/login?error";
    }
}*/