package com.example.myapp.Model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.myapp.Service.MapDBService;

public class ScriviScenario {
    
    @Autowired
    private MapDBService mapDBService;

    private final Map<Integer, Storia> storie = new HashMap<>();

    public void aggiungiOpzione(int storiaId, int scenarioId, int opzioneId) {
        Storia storia = storie.get(storiaId);
        if (storia != null) {
            int scenarioIdRecuperato = storia.getScenarioId(scenarioId);
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
            int scenarioRecuperatoId = storia.getScenarioId(scenarioId);
            Scenario scenario = mapDBService.getScenarioById(scenarioRecuperatoId);
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

    public Map<Integer, Storia> getStorie() {
        return storie;
    }
}