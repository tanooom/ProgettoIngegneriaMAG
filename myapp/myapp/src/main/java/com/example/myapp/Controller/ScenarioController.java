package com.example.myapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
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
        @RequestParam List<Integer> idOpzioni,
        @RequestParam(defaultValue = "no") String rilasciaOggetto,
        @RequestParam String oggettoRilasciato,
        @RequestParam(defaultValue = "") List<Integer> idEntryScenari
    ) {
        int newId = mapDBService.getAllScenari().keySet().stream()
            .mapToInt(Integer::intValue)
            .max()
            .orElse(0) + 1;

        // Verifica se l'oggetto rilasciato deve essere null
        if ("no".equals(rilasciaOggetto) || (oggettoRilasciato == null || oggettoRilasciato.trim().isEmpty())) {
            oggettoRilasciato = null;
        }

        List<Integer> idExitScenari = Arrays.asList(1, 2, 3); // Questa logica potrebbe cambiare in base alla tua implementazione

        // Creare un nuovo scenario temporaneo per calcolare le proprietà
        Scenario nuovoScenarioTemp = new Scenario(
            newId, 
            nomeScenario, 
            descrizioneScenario,
            idOpzioni,
            oggettoRilasciato,
            idEntryScenari.isEmpty() ? Collections.emptyList() : idEntryScenari,
            idExitScenari,
            false, 
            false
        );

        // Determinare i valori di scenarioIniziale e scenarioFinale
        boolean scenarioIniziale = nuovoScenarioTemp.isScenarioIniziale();
        boolean scenarioFinale = nuovoScenarioTemp.isScenarioFinale();

        // Ora creare il nuovo scenario definitivo
        Scenario nuovoScenario = new Scenario(
            newId, 
            nomeScenario, 
            descrizioneScenario,
            idOpzioni,
            oggettoRilasciato,
            idEntryScenari.isEmpty() ? Collections.emptyList() : idEntryScenari,
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
