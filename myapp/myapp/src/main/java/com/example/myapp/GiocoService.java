package com.example.myapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class GiocoService {

    private final Map<Integer, Gioco> giochiAttivi = new HashMap<>();
    private final StoriaController storiaController;

    // Costruttore
    public GiocoService(StoriaController storiaController) {
        this.storiaController = storiaController;
    }

    public List<Storia> getStorieDisponibili() {
        // Recupera le storie disponibili tramite il controller
        return storiaController.getStorieDisponibili(null, null, null, null);
    }

    public Gioco caricaGioco(int storiaId, Utente user) {
        // Recupera la storia in base allo storiaId
        Storia storia = storiaController.getStoriaById(storiaId); // Implementa questo metodo per recuperare la storia
    
        // Restituisci il gioco attivo o inizializza un nuovo gioco con i dettagli della storia recuperata
        return giochiAttivi.getOrDefault(storiaId, 
            inizializzaNuovoGioco(storiaId, storia.getUsername(), storia.getLunghezza(), storia.getStato()));
    }

    public void faiScelta(int storiaId, int opzioneId, Utente user) {
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

    private Gioco inizializzaNuovoGioco(int storiaId, String username, int lunghezza, String stato) {
        // TODO: modificare il caricamento della storia
        String titoloStoria = "Nome della Storia";
        int scenarioId = 1;
        String scenarioDescrizione = "Descrizione dello Scenario";

        // Inizializza un nuovo gioco
        Scenario scenario = new Scenario(scenarioId, scenarioDescrizione);
        Storia storia = new Storia(storiaId, titoloStoria, scenario, username, lunghezza, stato); // Recupera la storia dal database
        Gioco nuovoGioco = new Gioco(storia);
        giochiAttivi.put(storiaId, nuovoGioco);
        return nuovoGioco;
    }
}