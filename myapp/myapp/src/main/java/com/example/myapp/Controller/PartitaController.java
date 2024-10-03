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
import com.example.myapp.Model.Scenario;
import com.example.myapp.Model.Storia;
import com.example.myapp.Model.Utente;
import com.example.myapp.Service.PartitaService;
import com.example.myapp.Service.UserService;

@RestController
@RequestMapping("/api")
public class PartitaController {

    @Autowired
    private final PartitaService partitaService;

    @Autowired
    private final UserService userService;

    @Autowired
    private final MapDBController mapDBController;

    // Costruttore
    public PartitaController(PartitaService partitaService, UserService userService, MapDBController mapDBController) {
        this.partitaService = partitaService;
        this.userService = userService;
        this.mapDBController = mapDBController;
    }

    @GetMapping("/storie-disponibili")
    public List<Storia> getStorieDisponibili() {
        return partitaService.getStorieDisponibili();
    }

    @GetMapping("/gioca/{storiaId}/start")
    public Map<String, Object> giocaStoria(@PathVariable int storiaId) {
        // Ottieni l'utente autenticato
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Utente user = userService.getUser(username);
        
        // Carica la partita per l'utente e la storia selezionata
        Partita partita = partitaService.caricaPartita(storiaId, user);
        
        // Ottieni lo scenario corrente
        Scenario scenarioCorrente = mapDBController.getScenarioById(partita.getIdScenarioCorrente());
        
        // Costruisci la risposta con tutte le informazioni necessarie
        Map<String, Object> response = new HashMap<>();
        response.put("titolo", partita.getStoria().getTitolo());
        response.put("scenarioCorrente", scenarioCorrente);
        response.put("inventario", partita.getInventarioId()); // Associa l'inventario dell'utente
        
        return response;
    }

    @GetMapping("/gioca/{storiaId}/scenario")
    public Map<String, Object> caricaScenario(@PathVariable int storiaId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Utente user = userService.getUser(username);
        Partita partita = partitaService.caricaPartita(storiaId, user);
        Map<String, Object> response = new HashMap<>();
        response.put("titolo", partita.getStoria().getTitolo());
        response.put("scenarioCorrente", partita.getIdScenarioCorrente());
        response.put("inventario", partita.getInventarioId());
        return response;
    }

    @PostMapping("/gioca/{storiaId}/scelta/{opzioneId}")
    public void faiScelta(@PathVariable int storiaId, @PathVariable int opzioneId, InventarioController inventarioController) {
        // Ottieni l'utente autenticato
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Utente user = userService.getUser(username);
        // Usa l'utente per caricare scenari personalizzati o eseguire logiche specifiche
        partitaService.faiScelta(storiaId, opzioneId, user, inventarioController);
    }
}