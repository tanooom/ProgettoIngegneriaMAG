package com.example.myapp.Controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myapp.Model.Inventario;
import com.example.myapp.Model.Opzione;
import com.example.myapp.Model.Partita;
import com.example.myapp.Model.Scenario;
import com.example.myapp.Model.Storia;
import com.example.myapp.Model.Utente;
import com.example.myapp.Service.MapDBService;
import com.example.myapp.Service.PartitaService;
import com.example.myapp.Service.StoriaService;
import com.example.myapp.Service.UserService;

@RestController
@RequestMapping("/api")
public class PartitaController {

    @Autowired
    private final PartitaService partitaService;
    
    @Autowired
    private final StoriaService storiaService;

    @Autowired
    private final UserService userService;

    @Autowired
    private final MapDBService mapDBService;

    @Autowired
    private final MapDBController mapDBController;

    @Autowired
    private final InventarioController inventarioController;

    // Costruttore
    public PartitaController(PartitaService partitaService, StoriaService storiaService, UserService userService, MapDBService mapDBService, MapDBController mapDBController, InventarioController inventarioController) {
        this.partitaService = partitaService;
        this.storiaService = storiaService;
        this.userService = userService;
        this.mapDBService = mapDBService;
        this.mapDBController = mapDBController;
        this.inventarioController = inventarioController;
    }

    @GetMapping("/storie-disponibili")
    public List<Storia> getStorieDisponibili() {
        return partitaService.getStorieDisponibili();
    }

    // Implementa la funzione giocaStoria() di visualizzaStoria
    @PostMapping("/creaPartita/{storiaId}")
    public ResponseEntity<Partita> creaPartita(@PathVariable int storiaId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        Partita nuovaPartita = partitaService.creaNuovaPartita(storiaId, username);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(nuovaPartita);
    }

    // Implementa la funzione caricaScenario() di giocaStoria
    @GetMapping("/gioca/{storiaId}/{partitaId}/{scenarioCorrenteId}/start")
    public Map<String, Object> giocaStoria(@PathVariable int storiaId, @PathVariable int partitaId, @PathVariable Integer scenarioCorrenteId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Utente user = userService.getUser(username);
        
        Partita partita = partitaService.caricaPartita(partitaId, user); 

        if (partita == null) { 
            partita = partitaService.creaNuovaPartita(storiaId, username);
        }
        if (scenarioCorrenteId == null) {
            scenarioCorrenteId = partita.getIdScenarioCorrente();
        }
        Scenario scenarioCorrente = mapDBController.getScenarioById(scenarioCorrenteId);
        if (scenarioCorrente == null) {
            throw new RuntimeException("Scenario non trovato per ID: " + scenarioCorrenteId);
        }

        String oggettoRilasciato = scenarioCorrente.getOggettoRaccoglibile();
        Inventario inventario = inventarioController.getInventarioById(partita.getInventarioId());

        if (oggettoRilasciato != null && !oggettoRilasciato.isEmpty()) {
            inventario.aggiungiOggetto(oggettoRilasciato);
        }

        boolean isUltimoScenario = partitaService.isUltimoScenario(scenarioCorrente.getId());

        Map<String, Object> response = new HashMap<>();
        response.put("titolo", partita.getStoria().getTitolo());
        response.put("scenarioCorrente", scenarioCorrente);
        response.put("inventario", inventario.getOggetti());
        response.put("oggettoRilasciato", oggettoRilasciato);
        response.put("isUltimoScenario", isUltimoScenario);
        return response;
    }

    // Implementa la funzione releaseObject() di giocaStoria
    @PostMapping("/gioca/{partitaId}/aggiungiOggetto/{oggetto}")
    public ResponseEntity<Map<String, String>> aggiungiOggettoAlInventario(@PathVariable int partitaId, @PathVariable String oggetto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Utente user = userService.getUser(username);
        
        Partita partita = partitaService.caricaPartita(partitaId, user);
        Inventario inventario = inventarioController.getInventarioById(partita.getInventarioId());
        
        if (inventario != null) {
            inventario.aggiungiOggetto(oggetto);
            mapDBService.putInventoryItem(inventario.getId(), inventario);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Oggetto aggiunto all'inventario");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Inventario non trovato"));
        }
    }

    // Implementa la funzione getOpzione() di giocaStoria
    @GetMapping("/gioca/{partitaId}/{scenarioCorrenteId}/{opzioneId}")
    public ResponseEntity<Opzione> getOpzioneById(@PathVariable int partitaId, @PathVariable int scenarioCorrenteId, @PathVariable int opzioneId) {
        try {
            Opzione opzione = storiaService.getOpzioneById(scenarioCorrenteId, opzioneId);
            
            if (opzione != null) {
                return ResponseEntity.ok(opzione);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Implementa la funzione faiScelta() di giocaStoria
    @GetMapping("/gioca/{partitaId}/{scenarioCorrenteId}/controllaOggetto/{oggettoRichiesto}")
    public Map<String, Boolean> controllaOggetto(@PathVariable int partitaId, @PathVariable int scenarioCorrenteId, @PathVariable String oggettoRichiesto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Utente user = userService.getUser(username);
    
        Partita partita = partitaService.caricaPartita(partitaId, user);
        if (partita == null) {
            throw new IllegalArgumentException("Partita non trovata per l'utente: " + user.getUsername() + " e partitaId: " + partitaId);
        }
    
        Inventario inventario = inventarioController.getInventarioById(partita.getInventarioId());
        boolean possiedeOggetto = inventario.contieneOggetto(oggettoRichiesto);

        Map<String, Boolean> response = new HashMap<>();
        response.put("possiedeOggetto", possiedeOggetto);
        return response;
    }

    // Implementa la funzione eseguiScelta() di giocaStoria
    @PostMapping("/gioca/{partitaId}/{scenarioCorrenteId}/scelta/{opzioneId}")
    public ResponseEntity<Map<String, Object>> eseguiScelta(@PathVariable int partitaId, @PathVariable int scenarioCorrenteId, @PathVariable int opzioneId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            Utente user = userService.getUser(username);

            partitaService.eseguiScelta(partitaId, scenarioCorrenteId, opzioneId, user, inventarioController);

            Partita partita = partitaService.getPartita(partitaId);
            Scenario nuovoScenario = mapDBController.getScenarioById(partita.getIdScenarioCorrente());
            int nuovoScenarioId = nuovoScenario.getId();
            
            response.put("nuovoScenarioId", nuovoScenarioId);

            boolean isUltimoScenario = partitaService.isUltimoScenario(nuovoScenario.getId());
            response.put("isUltimoScenario", isUltimoScenario);
            
            if (isUltimoScenario) { 
                partitaService.terminaPartita(partitaId);
                response.put("finePartita", true);
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("errore", "Errore durante il processo della scelta: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    //Implementa la funzione salva ed esci -> si salva lo scenario in cui si è arrivati
    @PostMapping("/gioca/{partitaId}/{scenarioCorrenteId}/salva")
    public void salvaPartita(@PathVariable int partitaId, @PathVariable int scenarioCorrenteId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Utente user = userService.getUser(username);
        
        partitaService.salvaPartita(partitaId, scenarioCorrenteId, user);
    }
}