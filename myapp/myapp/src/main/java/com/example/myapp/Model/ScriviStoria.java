package com.example.myapp.Model;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.myapp.Service.MapDBService;

public class ScriviStoria {
    
    @Autowired
    private MapDBService mapDBService;

    private final Map<Integer, Storia> storie = new HashMap<>();
    private int nextStoriaId = 1;

    public Storia creaStoria(int id, String titolo, String username, String stato, int lunghezza){
        Storia storia = new Storia(id, titolo, username, lunghezza, stato);
        storia.setId(nextStoriaId++);
        storie.put(storia.getId(), storia);
        salvaStoria(storia);
        return storia;
    }

    public void aggiungiScenario(int storiaId, int scenarioId){
        Storia storia = storie.get(storiaId);
        if (storia != null) {
            storia.aggiungiScenario(scenarioId);
            salvaStoria(storia);
        } else {
            throw new RuntimeException("Storia non trovata con ID: " + storiaId);
        }
    }

    public void aggiungiOpzione(int storiaId, int scenarioId, int opzioneId) {
        Storia storia = storie.get(storiaId);
        if (storia != null) {
            int scenarioIdRecuperato = storia.getScenario(scenarioId);
            Scenario scenario = mapDBService.getScenarioById(scenarioIdRecuperato);
            if (scenario != null) {
                scenario.aggiungiOpzione(opzioneId);
                salvaStoria(storia);
            } else {
                throw new RuntimeException("Scenario non trovato con ID: " + scenarioId);
            }
        } else {
            throw new RuntimeException("Storia non trovata con ID: " + storiaId);
        }
    }

    // Aggiungi un oggetto raccoglibile a uno scenario
    public void aggiungiOggettoRaccoglibile(int storiaId, int scenarioId, String oggetto) {
        Storia storia = storie.get(storiaId);
        if (storia != null) {
            int scenarioIdRecuperato = storia.getScenario(scenarioId);
            Scenario scenario = mapDBService.getScenarioById(scenarioIdRecuperato);
            if (scenario != null) {
                scenario.aggiungiOggettoRaccoglibile(oggetto);
                salvaStoria(storia);
            } else {
                throw new RuntimeException("Scenario non trovato con ID: " + scenarioId);
            }
        } else {
            throw new RuntimeException("Storia non trovata con ID: " + storiaId);
        }
    }

    private void salvaStoria(Storia storia) {
        mapDBService.saveStory(storia);
    }

    public Storia getStoria(int storiaId) {
        return storie.get(storiaId);
    }

    public void eliminaStoria(int storiaId) {
        storie.remove(storiaId);
    }
}