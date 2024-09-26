package com.example.myapp.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.myapp.Controller.InventarioController;
import com.example.myapp.Model.Opzione;
import com.example.myapp.Model.Partita;
import com.example.myapp.Model.Storia;
import com.example.myapp.Model.Utente;

@Service
public class PartitaService {

    private final Map<Integer, Partita> giochiAttivi = new HashMap<>();
    private final StoriaService storiaService;

    // Costruttore
    public PartitaService(StoriaService storiaService) {
        this.storiaService = storiaService;
    }

    public List<Storia> getStorieDisponibili() {
        // Recupera le storie disponibili tramite il controller
        return storiaService.getStorieDisponibili(null, null, null, null);
    }

    public Partita caricaPartita(int storiaId, Utente user) {
        // Controlla se esiste già una partita attiva per questa storia
        Partita partita = giochiAttivi.get(storiaId);
        if (partita == null) {
            // Recupera la storia dal controller
            Storia storia = storiaService.getStoriaById(storiaId);
            //TODO: fixare id
            int id = 0;
            int inventarioId = 0;
            // Inizializza un nuovo partita con la storia recuperata
            partita = new Partita(id, storia, user.getUsername(), inventarioId);
            giochiAttivi.put(storiaId, partita);
        }
        return partita;
    }

    public void terminaPartita(int storiaId) {
        Partita partita = giochiAttivi.get(storiaId);
        if (partita != null) {
            partita.terminaGioco();
            // Rimuovi la partita dalle partite attive
            giochiAttivi.remove(storiaId);
        }
    }

    public Partita getGioco(int storiaId) {
        return giochiAttivi.get(storiaId);
    }

    public void faiScelta(int storiaId, int opzioneId, Utente user, InventarioController inventarioController) {
        Partita partita = giochiAttivi.get(storiaId);
        if (partita != null) {
            Opzione opzione = storiaService.getOpzioneById(storiaId, partita.getScenarioCorrente(), opzioneId);
            if (opzione == null) {
                throw new RuntimeException("Opzione non trovata per l'ID: " + opzioneId);
            }            
            partita.faiScelta(opzione, inventarioController);
        } else {
            throw new RuntimeException("Gioco non trovato per l'ID: " + storiaId);
        }
    }
    
    /*private Gioco inizializzaNuovoGioco(int storiaId, String username, int lunghezza, String stato) {
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