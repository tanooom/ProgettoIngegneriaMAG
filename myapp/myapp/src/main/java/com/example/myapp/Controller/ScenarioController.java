package com.example.myapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Collections;

import com.example.myapp.Model.Scenario;
import com.example.myapp.Service.MapDBService;

import java.util.List;


@Controller
public class ScenarioController {

    @Autowired
    private MapDBService mapDBService;

    /*@GetMapping("/scriviScenario")
    public String mostraScriviScenario() {
        return "scriviScenario";
    }*/

    @PostMapping("/aggiungiScenario")
    public String aggiungiScenario(
        @RequestParam String nomeScenario,
        @RequestParam String descrizioneScenario,
        @RequestParam(required = false) List<Integer> idOpzioni, // parametro opzionale
        @RequestParam(defaultValue = "no") String rilasciaOggetto,
        @RequestParam String oggettoRilasciato,
        @RequestParam(required = false) Integer idScenarioPrecedente
        //@RequestParam(required = false) List<Integer> idExitScenari
    ) {
        int newId = mapDBService.getAllScenari().keySet().stream()
            .mapToInt(Integer::intValue)
            .max()
            .orElse(0) + 1;

        // Verifica se l'oggetto rilasciato deve essere null
        if ("no".equals(rilasciaOggetto) || (oggettoRilasciato == null || oggettoRilasciato.trim().isEmpty())) {
            oggettoRilasciato = null;
        }

        // Se idOpzioni è null, assegnare una lista vuota
        if (idOpzioni == null) {
            idOpzioni = Collections.emptyList();
        }

        // Se idScenarioPrecedente è 0, impostalo a null
        if (idScenarioPrecedente != null && idScenarioPrecedente == 0) {
            idScenarioPrecedente = null;
        }

        List<Integer> idExitScenari = Collections.emptyList();

        // Se idScenarioPrecedente non è null, aggiorna l'idExitScenari dello scenario precedente
        if (idScenarioPrecedente != null) {
            Scenario scenarioPrecedente = mapDBService.getScenarioById(idScenarioPrecedente);
            if (scenarioPrecedente != null) {
                // Recupera l'attuale lista di idExitScenari
                List<Integer> idExitScenariPrecedenti = scenarioPrecedente.getIdExitScenari();
                
                // Se la lista è null, inizializzala come vuota, oppure crea una lista modificabile
                if (idExitScenariPrecedenti == null) {
                    idExitScenariPrecedenti = new ArrayList<>();
                } else {
                    // Crea una nuova lista modificabile se l'attuale non lo è
                    idExitScenariPrecedenti = new ArrayList<>(idExitScenariPrecedenti);
                }

                // Aggiungi l'id del nuovo scenario a idExitScenari dello scenario precedente
                idExitScenariPrecedenti.add(newId);

                // Aggiorna lo scenario precedente con la nuova lista di idExitScenari
                scenarioPrecedente.setIdExitScenari(idExitScenariPrecedenti);

                // Salva le modifiche allo scenario precedente
                mapDBService.saveScenario(scenarioPrecedente);
            }
        }

        // Creare un nuovo scenario temporaneo per calcolare le proprietà
        Scenario nuovoScenarioTemp = new Scenario(
            newId, 
            nomeScenario, 
            descrizioneScenario,
            idOpzioni,
            oggettoRilasciato,
            idScenarioPrecedente,
            idExitScenari,            
            false, 
            false
        );

        boolean scenarioIniziale = (idScenarioPrecedente == null);
        boolean scenarioFinale = nuovoScenarioTemp.isScenarioFinale();

        // Ora creare il nuovo scenario definitivo
        Scenario nuovoScenario = new Scenario(
            newId, 
            nomeScenario, 
            descrizioneScenario,
            idOpzioni,
            oggettoRilasciato,
            idScenarioPrecedente,
            idExitScenari,
            scenarioIniziale,
            scenarioFinale
        );

        mapDBService.saveScenario(nuovoScenario);

        return "redirect:/scriviStoria";
}

    @DeleteMapping("/deleteScenario")
    public ResponseEntity<String> deleteScenario(@RequestParam int id) {
        try {
            mapDBService.removeScenario(id);
            return ResponseEntity.ok("Scenario eliminato con successo!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore interno del server: " + e.getMessage());
        }
    }

}
