package com.example.myapp.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.myapp.Controller.InventarioController;
import com.example.myapp.Controller.MapDBController;
import com.example.myapp.Model.Opzione;
import com.example.myapp.Model.Partita;
import com.example.myapp.Model.Scenario;
import com.example.myapp.Model.Storia;
import com.example.myapp.Model.Utente;

@Service
public class PartitaService {

    private final List<Partita> partiteAttive;
    private final StoriaService storiaService;
    private final MapDBController mapDBController;
    private final InventarioController inventarioController;
    

    // Costruttore
    public PartitaService(StoriaService storiaService, List<Partita> partiteAttive, MapDBController mapDBController, InventarioController inventarioController) {
        this.storiaService = storiaService;
        this.partiteAttive = partiteAttive;
        this.mapDBController = mapDBController;
        this.inventarioController = inventarioController;
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

    public void faiScelta(int storiaId, int scenarioId, int opzioneId, Utente user, InventarioController inventarioController) {
        Partita partita = partiteAttive.get(storiaId);
        if (partita != null) {
            Opzione opzione = storiaService.getOpzioneById(storiaId, partita.getIdScenarioCorrente(), opzioneId);
            if (opzione == null) {
                throw new RuntimeException("Opzione non trovata per l'ID: " + opzioneId);
            }
            //Aggiorna lo scenario corrente
            partita.faiScelta(opzione, inventarioController);

        } else {
            throw new RuntimeException("Partita non trovata per l'ID: " + storiaId);
        }
    }

    public void salvaPartita(int storiaId, int scenarioCorreteId, Utente user) {
        Partita partita = partiteAttive.get(storiaId);
        if (partita != null) {
            //TODO: Logica per salvare la partita sul database o sistema di salvataggio
            // Aggiorna lo stato della partita e richiamo salvaInventario
        }
    }

    public boolean isUltimoScenario(int scenarioId) {
        Scenario scenario = mapDBController.getScenarioById(scenarioId);
        return scenario.isScenarioFinale();
    }

    // Metodo per inizializzare una nuova partita
    public Partita inizializzaNuovaPartita(int storiaId, String username) {
        Storia storia = storiaService.getStoriaById(storiaId); // Supponendo che tu abbia un metodo per ottenere la storia
        int inventarioId = inventarioController.creaInventario(); // Crea un nuovo inventario
        Partita nuovaPartita = new Partita(storiaId, storia, username, inventarioId);
        partiteAttive.add(nuovaPartita); // Aggiungi la nuova partita alla lista delle partite attive
        return nuovaPartita;
    }
}