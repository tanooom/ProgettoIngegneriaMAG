package com.example.myapp.Controller;

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
    private final MapDBController mapDBController;

    @Autowired
    private final InventarioController inventarioController;

    // Costruttore
    public PartitaController(PartitaService partitaService, StoriaService storiaService, UserService userService, MapDBController mapDBController, InventarioController inventarioController) {
        this.partitaService = partitaService;
        this.storiaService = storiaService;
        this.userService = userService;
        this.mapDBController = mapDBController;
        this.inventarioController = inventarioController;
    }

    @GetMapping("/storie-disponibili")
    public List<Storia> getStorieDisponibili() {
        return partitaService.getStorieDisponibili();
    }

    // Implementa la funzione caricaScenario() di giocaStoria
    @GetMapping("/gioca/{storiaId}/{partitaId}/{scenarioCorrenteId}/start")
    public Map<String, Object> giocaStoria(@PathVariable int storiaId, @PathVariable int partitaId, @PathVariable Integer scenarioCorrenteId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Utente user = userService.getUser(username);
        
        // Carica la partita esistente o ne crea una nuova
        Partita partita = partitaService.caricaPartita(storiaId, user); //Creo l'oggetto partita
        
        //TODO: non dovrebbe servire!
        if (partita == null) {
            partita = partitaService.creaNuovaPartita(storiaId, username);
        }
        
        // Recupera l'ID dello scenario corrente
        if (scenarioCorrenteId == null) {
            scenarioCorrenteId = partita.getIdScenarioCorrente();
        }
       
        // Ottiene lo scenario corrente
        Scenario scenarioCorrente = mapDBController.getScenarioById(scenarioCorrenteId);
        if (scenarioCorrente == null) {
            throw new RuntimeException("Scenario non trovato per ID: " + scenarioCorrenteId);
        }

        // Ottiene l'oggetto rilasciato dallo scenario
        String oggettoRilasciato = scenarioCorrente.getOggettoRaccoglibile();

        // Recupera l'inventario della partita
        Inventario inventario = inventarioController.getInventarioById(partita.getInventarioId());

        // Aggiunge l'oggetto all'inventario
        if (oggettoRilasciato != null && !oggettoRilasciato.isEmpty()) {
            inventario.aggiungiOggetto(oggettoRilasciato);
        }

        boolean isUltimoScenario = partitaService.isUltimoScenario(scenarioCorrente.getId());
        System.out.println("Is ultimo scenario? " + isUltimoScenario);

        Map<String, Object> response = new HashMap<>();
        response.put("titolo", partita.getStoria().getTitolo());
        response.put("scenarioCorrente", scenarioCorrente);
        response.put("inventario", inventario.getOggetti());
        response.put("oggettoRilasciato", oggettoRilasciato);
        response.put("isUltimoScenario", isUltimoScenario);
        return response;
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
            System.out.println("Errore durante il recupero dell'opzione: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Implementa la funzione eseguiScelta() di giocaStoria
    @PostMapping("/gioca/{partitaId}/{scenarioCorrenteId}/scelta/{opzioneId}")
    public ResponseEntity<Map<String, Object>> eseguiScelta(@PathVariable int partitaId, @PathVariable int scenarioCorrenteId, @PathVariable int opzioneId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            Utente user = userService.getUser(username);

            // Aggiorna lo scenario corrente passando a quello dell'opzione
            partitaService.eseguiScelta(partitaId, scenarioCorrenteId, opzioneId, user, inventarioController);

            // Carica il nuovo scenario corrente
            Partita partita = partitaService.getPartita(partitaId);
            Scenario nuovoScenario = mapDBController.getScenarioById(partita.getIdScenarioCorrente());

            // Ritorna il nuovo scenario aggiornato
            response.put("nuovoScenarioId", nuovoScenario.getId());

            boolean isUltimoScenario = partitaService.isUltimoScenario(nuovoScenario.getId());
            System.out.println("Is ultimo scenario? " + isUltimoScenario);
            response.put("isUltimoScenario", isUltimoScenario);
            
            if (isUltimoScenario) { // Se è l'ultimo scenario, termina la partita
                partitaService.terminaPartita(partitaId);
                response.put("finePartita", true);
            }

            /*boolean isUltimoScenario = partitaService.isUltimoScenario(scenarioCorrenteId);
            if (isUltimoScenario) { // Se è l'ultimo scenario, termina la partita
                partitaService.terminaPartita(partitaId);
                //response.put("partitaConclusa", true);
                //System.out.println("Termina partita!");
            } else { // Aggiorna lo scenario corrente passando a quello dell'opzione
                partitaService.eseguiScelta(partitaId, scenarioCorrenteId, opzioneId, user, inventarioController);

                // Carica il nuovo scenario corrente
                Partita partita = partitaService.getPartita(partitaId);
                Scenario nuovoScenario = mapDBController.getScenarioById(partita.getIdScenarioCorrente());

                // Ritorna il nuovo scenario aggiornato
                response.put("nuovoScenarioId", nuovoScenario.getId());
                //response.put("partitaConclusa", false);
                System.out.println("Aggiorna nuovo scenario");
            } */
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Errore nel processo della scelta: " + e.getMessage());
            response.put("errore", "Errore durante il processo della scelta: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);  // Ritorna la mappa
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


    //TODO: Serve questo metodo?
    @GetMapping("/gioca/{partitaId}/scenario")
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

    //Controlla che l'oggetto richiesto sia nell'inventario
    @GetMapping("/gioca/{partitaId}/{scenarioCorrenteId}/controllaOggetto/{oggettoRichiesto}")
    public Map<String, Boolean> controllaOggetto(@PathVariable int partitaId, @PathVariable int scenarioCorrenteId, @PathVariable String oggettoRichiesto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Utente user = userService.getUser(username);
    
        // Carica la partita dell'utente
        Partita partita = partitaService.caricaPartita(partitaId, user);
    
        // Ottieni l'inventario associato alla partita
        Inventario inventario = inventarioController.getInventarioById(partita.getInventarioId());
        // Controlla se l'inventario contiene l'oggetto richiesto
        boolean possiedeOggetto = inventario.contieneOggetto(oggettoRichiesto);

        // Risposta con il risultato
        Map<String, Boolean> response = new HashMap<>();
        response.put("possiedeOggetto", possiedeOggetto);
        return response;
    }

    @PostMapping("/creaPartita/{storiaId}")
    public ResponseEntity<Partita> creaPartita(@PathVariable int storiaId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        // Creiamo una nuova partita
        Partita nuovaPartita = partitaService.creaNuovaPartita(storiaId, username);
        
        // Ritorna la nuova partita creata
        return ResponseEntity.status(HttpStatus.CREATED).body(nuovaPartita);
    }
}