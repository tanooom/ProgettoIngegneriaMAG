package com.example.myapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GiocoController {

    @Autowired
    private GiocoService giocoService;

    @Autowired
    private UserService userService;

    @GetMapping("/storie")
    public List<Storia> getStorieDisponibili() {
        return giocoService.getStorieDisponibili();
    }

    @GetMapping("/gioca/{storiaId}")
    public Gioco caricaScenario(@PathVariable int storiaId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.getUser(username);
        
        // Usa l'utente per caricare scenari personalizzati o eseguire logiche specifiche
        return giocoService.caricaGioco(storiaId, user);
    }

    @PostMapping("/gioca/{storiaId}/scelta/{opzioneId}")
    public void faiScelta(@PathVariable int storiaId, @PathVariable int opzioneId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.getUser(username);
        
        // Usa l'utente per caricare scenari personalizzati o eseguire logiche specifiche
        giocoService.faiScelta(storiaId, opzioneId, user);
    }
}