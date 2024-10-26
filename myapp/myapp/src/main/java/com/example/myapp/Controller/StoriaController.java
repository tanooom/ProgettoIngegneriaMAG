package com.example.myapp.Controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.myapp.Model.Scenario;
import com.example.myapp.Model.Storia;
import com.example.myapp.Service.MapDBService;
import com.example.myapp.Service.StoriaService;

@Controller
public class StoriaController {

    @Autowired
    private final StoriaService storiaService;

    @Autowired
    private final MapDBService mapDBService;

    public StoriaController(StoriaService storiaService, MapDBService mapDBService) {
        this.storiaService = storiaService;
        this.mapDBService = mapDBService;
    }

    @PostMapping("/aggiungiStoria")
    public String aggiungiStoria(
        @RequestParam String titoloStoria,
        @RequestParam int idScenarioIniziale
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        int newId = mapDBService.getAllStorie().keySet().stream()
            .mapToInt(Integer::intValue)
            .max()
            .orElse(0) + 1;

        List<Integer> idScenari = calcolaScenariCollegati(idScenarioIniziale);
        int lunghezza = idScenari.size();

        Storia nuovaStoria = new Storia(
            newId, 
            titoloStoria,
            username, 
            lunghezza,
            idScenarioIniziale,
            idScenari
        );
        mapDBService.saveStory(nuovaStoria);
        return "redirect:/scriviStoria";
    }

    //Calcola iterativamente tutti gli scenari collegati a partire da uno scenario iniziale
    private List<Integer> calcolaScenariCollegati(int idScenarioIniziale) {
        List<Integer> scenariCollegati = new ArrayList<>();
        Queue<Integer> codaScenari = new LinkedList<>();
        codaScenari.add(idScenarioIniziale);

        while (!codaScenari.isEmpty()) {
            int idCorrente = codaScenari.poll();
            scenariCollegati.add(idCorrente);

            Scenario scenarioCorrente = mapDBService.getScenarioById(idCorrente);
            if (scenarioCorrente != null && scenarioCorrente.getIdExitScenari() != null) {
                for (Integer idExit : scenarioCorrente.getIdExitScenari()) {
                    if (!scenariCollegati.contains(idExit)) {
                        codaScenari.add(idExit);
                    }
                }
            }
        }
        return scenariCollegati;
    }

    @GetMapping("/api/storie")
    public ResponseEntity<List<Storia>> getStorie() {
        List<Storia> storie = storiaService.getAllStorie();
        if (storie == null || storie.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(storie);
        }
        return ResponseEntity.ok(storie);
    }

    @GetMapping("/api/storieFiltrate")
    public ResponseEntity<List<Storia>> getStorieFiltrate(  @RequestParam(required = false) String searchTerm,
                                                            @RequestParam(required = false) String username,
                                                            @RequestParam(required = false) String lunghezza) {
    
        List<Storia> storie = storiaService.getAllStorie();
        List<Storia> storieResult = new ArrayList<>();
    
        if (storie == null || storie.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(storie);
        }
    
        if (searchTerm != null) searchTerm = searchTerm.trim().toLowerCase();
        if (username != null) username = username.trim().toLowerCase();
    
        for (Storia storia : storie) {
            boolean matches = true; 
    
            if (searchTerm != null && !storia.getTitolo().trim().toLowerCase().contains(searchTerm)) {
                matches = false;
            }
            if (username != null && !storia.getUsername().trim().toLowerCase().equals(username)) {
                matches = false;
            }
            if (lunghezza != null) {
                int numScenari = storia.getLunghezza();
                switch (lunghezza) {
                    case "range0-5" -> {
                        if (numScenari < 0 || numScenari > 5) { matches = false; }
                    }
                    case "range5-10" -> {
                        if (numScenari < 5 || numScenari > 10) { matches = false; }
                    }
                    case "range10+" -> {
                        if (numScenari <= 10) { matches = false; }
                    }
                    default -> matches = false;
                }
            }
            if (matches) {
                storieResult.add(storia);
            }
        }
        return ResponseEntity.ok(storieResult);
    }

    @GetMapping("/visualizzaStoria/{id}")
    public String visualizzaStoria(@PathVariable int id, Model model) {
        Storia storia = storiaService.getStoriaById(id);
        model.addAttribute("storia", storia);
        return "visualizzaStoria";
    }

    @GetMapping("/api/usernames")
    public ResponseEntity<List<String>> getUsernames() {
        List<String> usernames = storiaService.getAllUsernames();
        
        if (usernames == null || usernames.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(usernames);
        }
        return ResponseEntity.ok(usernames);
    }  
    
    @GetMapping("/api/storie/{storiaId}/scenario-iniziale/descrizione")
    public ResponseEntity<String> getDescrizioneScenarioIniziale(@PathVariable int storiaId) {
        try {
            String descrizione = storiaService.getDescrizioneScenarioIniziale(storiaId);
            return ResponseEntity.ok(descrizione);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteStoria")
    public ResponseEntity<String> deleteStory(@RequestParam int id) {
        try {
            mapDBService.removeStoria(id);
            return ResponseEntity.ok("Storia eliminata con successo!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore interno del server: " + e.getMessage());
        }
    }

    @GetMapping("/scriviStoria")
    public String scriviStoria(Model model) {
        model.addAttribute("scenari", mapDBService.getListAllScenari());
        return "scriviStoria";
    }

    @GetMapping("/leMieStorie")
    public String leMieStorie(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // Recupera tutte le storie dell'utente loggato
        List<Storia> storie = storiaService.getStorieByUsername(username);

        model.addAttribute("storie", storie);
        model.addAttribute("hasStories", !storie.isEmpty());

        return "leMieStorie";
    }

    @DeleteMapping("/deleteStoriaByTitle")
    public ResponseEntity<String> deleteStoria(@RequestParam String title) {
        try {
            mapDBService.removeStoriaByTitle(title);
            return ResponseEntity.ok("Storia eliminata con successo.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}