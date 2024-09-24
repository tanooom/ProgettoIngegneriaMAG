package com.example.myapp.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myapp.Model.Partita;
import com.example.myapp.Model.Storia;
import com.example.myapp.Model.Utente;
import com.example.myapp.Service.GiocoService;
import com.example.myapp.Service.UserService;

@RestController
@RequestMapping("/api")
public class GiocoController {

    @Autowired
    private GiocoService giocoService;

    @Autowired
    private UserService userService;

    @GetMapping("/storie-disponibili")
    public List<Storia> getStorieDisponibili() {
        return giocoService.getStorieDisponibili();
    }

    @GetMapping("/gioca/{storiaId}")
    public Map<String, Object> caricaScenario(@PathVariable int storiaId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Utente user = userService.getUser(username);
        Partita gioco = giocoService.caricaGioco(storiaId, user);
        Map<String, Object> response = new HashMap<>();
        response.put("titolo", gioco.getStoria().getTitolo());
        response.put("scenarioCorrente", gioco.getScenarioCorrente());
        response.put("inventario", gioco.getInventario());
        return response;
    }

    @PostMapping("/gioca/{storiaId}/scelta/{opzioneId}")
    public void faiScelta(@PathVariable int storiaId, @PathVariable int opzioneId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Utente user = userService.getUser(username);
        // Usa l'utente per caricare scenari personalizzati o eseguire logiche specifiche
        giocoService.faiScelta(storiaId, opzioneId, user);
    }
}