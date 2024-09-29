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
            Storia nuovaStoria = new Storia(
                id,
                titolo,
                username,
                lunghezza,
                stato
            );

            // Aggiunta di scenari fittizi
            nuovaStoria.aggiungiScenario(101); // ID di un scenario fittizio
            nuovaStoria.aggiungiScenario(102);
            nuovaStoria.aggiungiScenario(103);
            nuovaStoria.setIdScenarioIniziale(101); // Impostazione dello scenario iniziale

            //Storia nuovaStoria = storiaService.creaStoria(storia);

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

    @GetMapping("/api/storieFiltrate?")
    public ResponseEntity<List<Storia>> getStorieFiltrate(  @RequestParam String searchTerm,
                                                            @RequestParam String username,
                                                            @RequestParam Integer lunghezza, 
                                                            @RequestParam String stato) {
        List<Storia> storie = storiaService.getAllStorie(); // Cambia 'Storie' in 'Storia'
        List<Storia> storieResult = new ArrayList<>();
        System.out.println("GET ALL STORIE: " + storie);
        if (storie == null || storie.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(storie);
        }
        //spazi, lowercase, replace per uguaglianze

        if(searchTerm != null && !searchTerm.isEmpty()){
            for(Storia storia : storie){
                if(storia.getTitolo().contains(searchTerm)){
                    storieResult.add(storia);
                }
            }
        }

        if(username != null && !username.isEmpty()){
            for(Storia storia : storie){
                if(storia.getUsername().equals(username)){
                    storieResult.add(storia);
                }
            }
        }

        if(lunghezza != null && lunghezza != 0){
            for(Storia storia : storie){
                if(storia.getLunghezza() == lunghezza){
                    storieResult.add(storia);
                }
            }
        }

        if(stato != null && !stato.isEmpty()){
            for(Storia storia : storie){
                if(storia.getStato().equals(stato)){
                    storieResult.add(storia);
                }
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