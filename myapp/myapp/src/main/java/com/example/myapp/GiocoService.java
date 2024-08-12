package com.example.myapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class GiocoService {

    private final Map<Integer, Gioco> giochiAttivi = new HashMap<>();

    public List<Storia> getStorieDisponibili() {
        // Restituisce un elenco di storie disponibili
        return new ArrayList<>(); // Restituisci una nuova lista di storie, qui come esempio vuoto
    }

    public Gioco caricaGioco(int storiaId) {
        return giochiAttivi.getOrDefault(storiaId, inizializzaNuovoGioco(storiaId));
    }

    public void faiScelta(int storiaId, int opzioneId) {
        Gioco gioco = giochiAttivi.get(storiaId);
        if (gioco != null) {
            Opzione opzione = gioco.getScenarioCorrente().getOpzioni().stream()
                .filter(o -> o.getId() == opzioneId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Opzione non trovata"));
            gioco.faiScelta(opzione);
        } else {
            throw new RuntimeException("Gioco non trovato per l'ID: " + storiaId);
        }
    }

    private Gioco inizializzaNuovoGioco(int storiaId) {
        // Carica la storia e inizializza un nuovo gioco
        String titoloStoria = "Nome della Storia"; //da modificare
        int scenarioId = 1; //da modificare
        String scenarioDescrizione = "Descrizione dello Scenario"; //da modificare

        Scenario scenario = new Scenario(scenarioId, scenarioDescrizione);
        Storia storia = new Storia(titoloStoria, scenario); // Recupera la storia dal database
        Gioco nuovoGioco = new Gioco(storia);
        giochiAttivi.put(storiaId, nuovoGioco);
        return nuovoGioco;
    }
}