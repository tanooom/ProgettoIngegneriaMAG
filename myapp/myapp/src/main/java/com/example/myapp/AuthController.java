package com.example.myapp;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, 
                           @RequestParam String nome, @RequestParam String cognome, 
                           @RequestParam String mail) {
        userService.registerUser(username, password, nome, cognome, mail);
        
        // Autentica automaticamente l'utente dopo la registrazione
        autoLogin(username, password);

        return "redirect:/registrationSuccess";
    }

    // Metodo per autenticare automaticamente l'utente
    private void autoLogin(String username, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        String encodedPassword = userService.getEncodedPassword(username);
        if (encodedPassword != null && userService.checkPassword(password, encodedPassword)) {
            return "redirect:/home";
        }
        return "redirect:/login?error";
    }
}