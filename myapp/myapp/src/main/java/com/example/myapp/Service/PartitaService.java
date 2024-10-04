package com.example.myapp.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.myapp.Controller.InventarioController;
import com.example.myapp.Model.Opzione;
import com.example.myapp.Model.Partita;
import com.example.myapp.Model.Storia;
import com.example.myapp.Model.Utente;

@Service
public class PartitaService {

    private final List<Partita> partiteAttive;
    private final StoriaService storiaService;
    

    // Costruttore
    public PartitaService(StoriaService storiaService, List<Partita> partiteAttive) {
        this.storiaService = storiaService;
        this.partiteAttive = partiteAttive;
    }

    public List<Storia> getStorieDisponibili() {
        // Recupera le storie disponibili tramite il controller
        return storiaService.getStorieDisponibili(null, null, null, null);
    }

    public Partita caricaPartita(int storiaId, Utente user) {
        // Trova la partita attiva dell'utente per la storia specificata
        return partiteAttive.stream()
                .filter(partita -> partita.getStoria().getId() == storiaId && partita.getUsername().equals(user.getUsername()))
                .findFirst()
                .orElse(null); // Restituisce null se non trovata
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

    public void salvaPartita(int storiaId, Utente user) {
        Partita partita = partiteAttive.get(storiaId);
        if (partita != null) {
            // Logica per salvare la partita sul database o sistema di salvataggio
            // Ad esempio, aggiorna lo stato della partita e salva l'inventario
        }
    }

    public boolean isUltimoScenario(int opzioneId) {
        // Puoi implementare questa logica come desideri.
        // Potrebbe essere necessario passare l'ID dello scenario successivo o altro.
        return false; // Placeholder, dovresti implementare la logica corretta
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