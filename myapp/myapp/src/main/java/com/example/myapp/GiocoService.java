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
        // Controlla se esiste già un gioco attivo per questa storia
        Gioco gioco = giochiAttivi.get(storiaId);
        if (gioco == null) {
            // Recupera la storia dal controller
            Storia storia = storiaController.getStoriaById(storiaId);
            // Inizializza un nuovo gioco con la storia recuperata
            gioco = new Gioco(storia, user.getUsername());
            giochiAttivi.put(storiaId, gioco);
        }
        return gioco;
    }

    public void terminaGioco(int storiaId) {
        Gioco gioco = giochiAttivi.get(storiaId);
        if (gioco != null) {
            gioco.terminaGioco();
            // Rimuovi la partita dalle partite attive
            giochiAttivi.remove(storiaId);
        }
    }

    public Gioco getGioco(int storiaId) {
        return giochiAttivi.get(storiaId);
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

    /* TODO: serve questo metodo?
    private Gioco inizializzaNuovoGioco(int storiaId, String username, int lunghezza, String stato) {
        // TODO: modificare il caricamento della storia
        String titoloStoria = "Nome della Storia";
        int scenarioId = 1;
        String scenarioDescrizione = "Descrizione dello Scenario";

        // Inizializza un nuovo gioco
        Scenario scenario = new Scenario(scenarioId, scenarioDescrizione);
        Storia storia = new Storia(storiaId, titoloStoria, scenario, username, lunghezza, stato); // Recupera la storia dal database
        Gioco nuovoGioco = new Gioco(storia, username); // Passa anche username
        giochiAttivi.put(storiaId, nuovoGioco);
        return nuovoGioco;
    }
    */
}