package com.example.myapp.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.myapp.Model.Storia;
import com.example.myapp.Service.MapDBService;
import com.example.myapp.Service.StoriaService;

@Controller
public class StoriaController {

    //private static final Logger logger = LoggerFactory.getLogger(StoriaController.class);

    @Autowired
    private final StoriaService storiaService;

    @Autowired
    private final MapDBService mapDBService;

    public StoriaController(StoriaService storiaService, MapDBService mapDBService) {
        this.storiaService = storiaService;
        this.mapDBService = mapDBService;
    }

    // Aggiungiamo un metodo POST per salvare la storia
    @PostMapping("/api/storie")
    public ResponseEntity<Storia> salvaStoria(  @RequestParam(required = false, defaultValue = "0") int id,
                                                @RequestParam String titolo,
                                                @RequestParam String username,
                                                @RequestParam int lunghezza,
                                                @RequestParam String stato) {
        System.out.println("Salvataggio storia iniziato...");
        try {
            // Creazione di una nuova storia fittizia TEMPORANEA
            Storia storia = new Storia(
                id,
                titolo,
                username,
                lunghezza,
                stato
            );

            Storia nuovaStoria = storiaService.creaStoria(storia);

            // Salva la storia nel database DA TENERE:
            mapDBService.saveStory(nuovaStoria);
            
            System.out.println("QUESTA E' LA NUOVA STORIA:" +  nuovaStoria.toString());
            return ResponseEntity.ok(nuovaStoria);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Errore generico
        }
    }

    @GetMapping("/api/storie")
    public ResponseEntity<List<Storia>> getStorie() {
        List<Storia> storie = storiaService.getAllStorie(); // Cambia 'Storie' in 'Storia'
        System.out.println("GET ALL STORIE: " + storie);
        if (storie == null || storie.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(storie);
        }
        return ResponseEntity.ok(storie);
    }

    @GetMapping("/api/storieFiltrate")
    public ResponseEntity<List<Storia>> getStorieFiltrate(  @RequestParam(required = false) String searchTerm,
                                                            @RequestParam(required = false) String username,
                                                            @RequestParam(required = false) String lunghezza, 
                                                            @RequestParam(required = false) String stato) {
    
        List<Storia> storie = storiaService.getAllStorie();
        List<Storia> storieResult = new ArrayList<>();
    
        if (storie == null || storie.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(storie);
        }
    
        // Normalizzazione dei parametri di input
        if (searchTerm != null) searchTerm = searchTerm.trim().toLowerCase();
        if (username != null) username = username.trim().toLowerCase();
        if (stato != null) stato = stato.trim().toLowerCase();
    
        // Verifica delle storie in base ai filtri
        for (Storia storia : storie) {
            boolean matches = true;  // Flag per tenere traccia dei filtri soddisfatti
    
            // Filtro per searchTerm (titolo)
            if (searchTerm != null && !storia.getTitolo().trim().toLowerCase().contains(searchTerm)) {
                matches = false;
            }
    
            // Filtro per username
            if (username != null && !storia.getUsername().trim().toLowerCase().equals(username)) {
                matches = false;
            }
    
            // Filtro per lunghezza (range 0-5, 5-10, 10+)
            if (lunghezza != null) {
                int numScenari = storia.getLunghezza();  // Assumo che lunghezza sia il numero di scenari
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
    
            // Filtro per stato
            if (stato != null && !storia.getStato().trim().toLowerCase().equals(stato)) {
                matches = false;
            }
    
            // Se la storia soddisfa tutti i filtri, viene aggiunta alla lista dei risultati
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
        return "visualizzaStoria"; // Nome del template HTML senza estensione
    }

    @GetMapping("/api/usernames")
    public ResponseEntity<List<String>> getUsernames() {
        System.out.println("111");
        List<String> usernames = storiaService.getAllUsernames();
        System.out.println("GET ALL USERNAME: " + usernames);
        
        if (usernames == null || usernames.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(usernames);
        }
        return ResponseEntity.ok(usernames);
    }  
    
    @GetMapping("/api/storie/{storiaId}/scenario-iniziale/descrizione")
    public ResponseEntity<String> getDescrizioneScenarioIniziale(@PathVariable int storiaId) {
        try {
            String descrizione = storiaService.getDescrizioneScenarioIniziale(storiaId);
            System.out.println("DESCRIZIONE: " + descrizione);
            return ResponseEntity.ok(descrizione);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}