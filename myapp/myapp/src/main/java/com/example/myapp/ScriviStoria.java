package com.example.myapp;


//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


public class ScriviStoria {
    
    private MapDBService mapDBService;

    private final Map<Integer, Storia> storie = new HashMap<>();
    private int nextStoriaId = 1;

    public Storia creaStoria(int id, String titolo, Scenario scenarioIniziale, String username, String stato, int lunghezza){
        Storia storia = new Storia(id, titolo, scenarioIniziale, username,lunghezza, stato);
        storia.setId(nextStoriaId++);
        storie.put(storia.getId(), storia);
        salvaStoria(storia);
        return storia;
    }

    public void aggiungiScenario(int storiaId, Scenario scenario){
        Storia storia = storie.get(storiaId);
        if (storia != null) {
            storia.aggiungiScenario(scenario);
            salvaStoria(storia);
        } else {
            throw new RuntimeException("Storia non trovata con ID: " + storiaId);
        }
    }

    public void aggiungiOpzione(int storiaId, int scenarioId, Opzione opzione) {
        Storia storia = storie.get(storiaId);
        if (storia != null) {
            Scenario scenario = storia.getScenario(scenarioId);
            if (scenario != null) {
                scenario.aggiungiOpzione(opzione);
                salvaStoria(storia);
            } else {
                throw new RuntimeException("Scenario non trovato con ID: " + scenarioId);
            }
        } else {
            throw new RuntimeException("Storia non trovata con ID: " + storiaId);
        }
    }


    public void aggiungiFinale(int storiaId, Scenario finale) {
        Storia storia = storie.get(storiaId);
        if (storia != null) {
            storia.aggiungiFinale(finale);
            salvaStoria(storia);
        } else {
            throw new RuntimeException("Storia non trovata con ID: " + storiaId);
        }
    }

    // Aggiungi un oggetto raccoglibile a uno scenario
    public void aggiungiOggettoRaccoglibile(int storiaId, int scenarioId, String oggetto) {
        Storia storia = storie.get(storiaId);
        if (storia != null) {
            Scenario scenario = storia.getScenario(scenarioId);
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
