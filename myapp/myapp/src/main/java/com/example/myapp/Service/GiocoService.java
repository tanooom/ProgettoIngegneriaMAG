package com.example.myapp.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.myapp.Model.Opzione;
import com.example.myapp.Model.Partita;
import com.example.myapp.Model.Storia;
import com.example.myapp.Model.Utente;

@Service
public class GiocoService {

    private final Map<Integer, Partita> giochiAttivi = new HashMap<>();
    private final StoriaService storiaService;

    // Costruttore
    public GiocoService(StoriaService storiaService) {
        this.storiaService = storiaService;
    }

    public List<Storia> getStorieDisponibili() {
        // Recupera le storie disponibili tramite il controller
        return storiaService.getStorieDisponibili(null, null, null, null);
    }

    public Partita caricaGioco(int storiaId, Utente user) {
        // Controlla se esiste già una partita attiva per questa storia
        Partita gioco = giochiAttivi.get(storiaId);
        if (gioco == null) {
            // Recupera la storia dal controller
            Storia storia = storiaService.getStoriaById(storiaId);
            //TODO: fixare id
            int id = 0;
            // Inizializza un nuovo partita con la storia recuperata
            gioco = new Partita(id, storia, user.getUsername());
            giochiAttivi.put(storiaId, gioco);
        }
        return gioco;
    }

    public void terminaGioco(int storiaId) {
        Partita gioco = giochiAttivi.get(storiaId);
        if (gioco != null) {
            gioco.terminaGioco();
            // Rimuovi la partita dalle partite attive
            giochiAttivi.remove(storiaId);
        }
    }

    public Partita getGioco(int storiaId) {
        return giochiAttivi.get(storiaId);
    }

    public void faiScelta(int storiaId, int opzioneId, Utente user) {
        Partita gioco = giochiAttivi.get(storiaId);
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
    }*/
}