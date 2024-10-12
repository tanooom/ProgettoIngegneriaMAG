package com.example.myapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.myapp.Model.Scenario;
import com.example.myapp.Service.MapDBService;

@Controller
public class ScenarioController {

    @Autowired
    private MapDBService mapDBService;

    @PostMapping("/aggiungiScenario")
    public String aggiungiScenario(
        @RequestParam String nomeScenario,
        @RequestParam String descrizioneScenario,
        @RequestParam(required = false) List<Integer> idOpzioni,
        @RequestParam(defaultValue = "no") String rilasciaOggetto,
        @RequestParam String oggettoRilasciato,
        @RequestParam(required = false) Integer idScenarioPrecedente
    ) {
        int newId = mapDBService.getAllScenari().keySet().stream()
            .mapToInt(Integer::intValue)
            .max()
            .orElse(0) + 1;

        if ("no".equals(rilasciaOggetto) || (oggettoRilasciato == null || oggettoRilasciato.trim().isEmpty())) {
            oggettoRilasciato = null;
        }

        if (idOpzioni == null) {
            idOpzioni = Collections.emptyList();
        }

        if (idScenarioPrecedente != null && idScenarioPrecedente == 0) {
            idScenarioPrecedente = null;
        }

        List<Integer> idExitScenari = Collections.emptyList();

        // aggiorna l'idExitScenari dello scenario precedente
        if (idScenarioPrecedente != null) {
            Scenario scenarioPrecedente = mapDBService.getScenarioById(idScenarioPrecedente);
            if (scenarioPrecedente != null) {
                List<Integer> idExitScenariPrecedenti = scenarioPrecedente.getIdExitScenari();
                if (idExitScenariPrecedenti == null) {
                    idExitScenariPrecedenti = new ArrayList<>();
                } else {
                    idExitScenariPrecedenti = new ArrayList<>(idExitScenariPrecedenti);
                }
                idExitScenariPrecedenti.add(newId);
                scenarioPrecedente.setIdExitScenari(idExitScenariPrecedenti);
                mapDBService.saveScenario(scenarioPrecedente);
            }
        }

        // Crea un nuovo scenario temporaneo
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

        // Crea il nuovo scenario definitivo
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

    @GetMapping("/getScenariByTitle")
    public ResponseEntity<List<Scenario>> getScenariByTitle(@RequestParam String title) {
        List<Scenario> scenari = mapDBService.getScenariByTitle(title);
        if (scenari == null || scenari.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(scenari); 
    }

}