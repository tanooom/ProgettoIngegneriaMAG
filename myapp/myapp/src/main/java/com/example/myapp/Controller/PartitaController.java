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

import com.example.myapp.Model.Inventario;
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

    @Autowired
    private final InventarioController inventarioController;

    // Costruttore
    public PartitaController(PartitaService partitaService, UserService userService, MapDBController mapDBController, InventarioController inventarioController) {
        this.partitaService = partitaService;
        this.userService = userService;
        this.mapDBController = mapDBController;
        this.inventarioController = inventarioController;
    }

    @GetMapping("/storie-disponibili")
    public List<Storia> getStorieDisponibili() {
        return partitaService.getStorieDisponibili();
    }

    @GetMapping("/api/gioca/{storiaId}/start")
    public Map<String, Object> giocaStoria(@PathVariable int storiaId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Utente user = userService.getUser(username);
        
        Partita partita = partitaService.caricaPartita(storiaId, user);
        if (partita == null) {
            throw new RuntimeException("Partita non trovata per l'utente: " + username);
        }
        
        Scenario scenarioCorrente = mapDBController.getScenarioById(partita.getIdScenarioCorrente());
        if (scenarioCorrente == null) {
            throw new RuntimeException("Scenario non trovato per ID: " + partita.getIdScenarioCorrente());
        }
        
        Inventario inventario = inventarioController.getInventarioById(partita.getInventarioId());
        
        Map<String, Object> response = new HashMap<>();
        response.put("titolo", partita.getStoria().getTitolo());
        response.put("scenarioCorrente", scenarioCorrente);
        response.put("inventario", inventario.getOggetti());
        response.put("partitaConclusa", !partita.isInCorso());

        return response;
    }

    @PostMapping("/gioca/{storiaId}/scelta/{opzioneId}")
    public void faiScelta(@PathVariable int storiaId, @PathVariable int opzioneId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Utente user = userService.getUser(username);

        boolean isUltimoScenario = partitaService.isUltimoScenario(opzioneId);
        
        if (isUltimoScenario) {
            partitaService.terminaPartita(storiaId);
        } else {
            partitaService.faiScelta(storiaId, opzioneId, user, inventarioController);
        }
    }

    @PostMapping("/gioca/{storiaId}/salva")
    public void salvaPartita(@PathVariable int storiaId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Utente user = userService.getUser(username);
        
        partitaService.salvaPartita(storiaId, user);
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

    @GetMapping("/gioca/{storiaId}/controllaOggetto/{oggetto}")
    public Map<String, Boolean> controllaOggetto(@PathVariable int storiaId, @PathVariable String oggetto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Utente user = userService.getUser(username);
    
        // Carica la partita dell'utente
        Partita partita = partitaService.caricaPartita(storiaId, user);
    
        // Ottieni l'inventario associato alla partita
        Inventario inventario = inventarioController.getInventarioById(partita.getInventarioId());
    
        // Controlla se l'inventario contiene l'oggetto richiesto
        boolean possiedeOggetto = inventario.contieneOggetto(oggetto);
    
        // Risposta con il risultato
        Map<String, Boolean> response = new HashMap<>();
        response.put("possiedeOggetto", possiedeOggetto);
        return response;
    }
}