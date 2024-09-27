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

    private final Map<Integer, Partita> partiteAttive = new HashMap<>();
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
        Partita partita = partiteAttive.get(storiaId);
        if (partita == null) {
            // Recupera la storia dal controller
            Storia storia = storiaService.getStoriaById(storiaId);
            //TODO: fixare id
            int id = 0;
            int inventarioId = 0;
            // Inizializza una nuova partita con la storia recuperata
            partita = new Partita(id, storia, user.getUsername(), inventarioId);
            partiteAttive.put(storiaId, partita);
        }
        return partita;
    }

    public void terminaPartita(int storiaId) {
        Partita partita = partiteAttive.get(storiaId);
        if (partita != null) {
            partita.terminaPartita();
            // Rimuovi la partita dalle partite attive
            partiteAttive.remove(storiaId);
        }
    }

    public Partita getPartita(int storiaId) {
        return partiteAttive.get(storiaId);
    }

    public void faiScelta(int storiaId, int opzioneId, Utente user, InventarioController inventarioController) {
        Partita partita = partiteAttive.get(storiaId);
        if (partita != null) {
            Opzione opzione = storiaService.getOpzioneById(storiaId, partita.getIdScenarioCorrente(), opzioneId);
            if (opzione == null) {
                throw new RuntimeException("Opzione non trovata per l'ID: " + opzioneId);
            }            
            partita.faiScelta(opzione, inventarioController);
        } else {
            throw new RuntimeException("Partita non trovata per l'ID: " + storiaId);
        }
    }
    
    /*private Partita inizializzaNuovaPartita(int storiaId, String username, int lunghezza, String stato) {
        // TODO: modificare il caricamento della storia
        String titoloStoria = "Nome della Storia";
        int scenarioId = 1;
        String scenarioDescrizione = "Descrizione dello Scenario";

        // Inizializza una nuova partita
        Scenario scenario = new Scenario(scenarioId, scenarioDescrizione);
        Storia storia = new Storia(storiaId, titoloStoria, scenario, username, lunghezza, stato); // Recupera la storia dal database
        Partita nuovaPartita = new Partita(storia, username); // Passa anche username
        partiteAttivi.put(storiaId, nuovaPartita);
        return nuovaPartita;
    }*/
}