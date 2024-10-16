package com.example.myapp.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.myapp.Controller.InventarioController;
import com.example.myapp.Controller.MapDBController;
import com.example.myapp.Model.Inventario;
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
    private final MapDBService mapDBService;
    

    // Costruttore
    public PartitaService(StoriaService storiaService, List<Partita> partiteAttive, MapDBController mapDBController, InventarioController inventarioController, MapDBService mapDBService) {
        this.storiaService = storiaService;
        this.partiteAttive = partiteAttive;
        this.mapDBController = mapDBController;
        this.inventarioController = inventarioController;
        this.mapDBService = mapDBService;
    }

    public List<Storia> getStorieDisponibili() {
        // Recupera le storie disponibili tramite il controller
        //TODO: va bene?
        return storiaService.getStorieDisponibili(null, null, null, null);
    }

    public Partita caricaPartita(int partitaId, Utente user) {
        // Trova la partita attiva dell'utente per la storia specificata
        return partiteAttive.stream()
                .filter(partita -> partita.getStoria().getId() == partitaId && partita.getUsername().equals(user.getUsername()))
                .findFirst()
                .orElse(null); // Restituisce null se non trovata
    }

    public void terminaPartita(int partitaId) {
        Partita partita = partiteAttive.get(partitaId);
        if (partita != null) {
            partita.terminaPartita();
            // Rimuovi la partita dalle partite attive
            partiteAttive.remove(partitaId);
        }
    }

    public Partita getPartita(int partitaId) {
        return partiteAttive.get(partitaId);
    }

    public void faiScelta(int partitaId, int scenarioCorrenteId, int opzioneId, Utente user, InventarioController inventarioController) {
        System.out.println("INVECE QUI?");
        System.out.println("PARTITA: " + partitaId + ", con SCENARIO: " + scenarioCorrenteId + ", con Opzione: " + opzioneId);
        Partita partita = partiteAttive.get(partitaId);
        if (partita != null) {
            Opzione opzione = storiaService.getOpzioneById(partita.getIdScenarioCorrente(), opzioneId);
            if (opzione == null) {
                throw new RuntimeException("Opzione non trovata per l'ID: " + opzioneId);
            }
            //Aggiorna lo scenario corrente
            partita.faiScelta(opzione, inventarioController);

        } else {
            throw new RuntimeException("Partita non trovata per l'ID: " + partitaId);
        }
    }

    //aggiorna su db quando clicco salva ed esci
    public void salvaPartita(int partitaId, int scenarioCorreteId, Utente user) {
        Partita partita = partiteAttive.get(partitaId);
        if (partita != null) {
            //TODO: Logica per salvare la partita sul database o sistema di salvataggio
            // Aggiorna lo stato della partita e richiamo salvaInventario
        }
    }

    public boolean isUltimoScenario(int scenarioId) {
        Scenario scenario = mapDBController.getScenarioById(scenarioId);
        return scenario.isScenarioFinale();
    }

    // Metodo per CREARE una nuova partita
    public Partita creaNuovaPartita(int storiaId, String username) {
        Storia storia = storiaService.getStoriaById(storiaId); // Supponendo che tu abbia un metodo per ottenere la storia
        Inventario inventario = inventarioController.creaInventario(partiteAttive.size()+1); // Crea un nuovo inventario
        int inventarioId = inventario.getId();
        System.out.println("ID INVENTARIO CREATO: " + inventarioId);
        Partita nuovaPartita = new Partita(partiteAttive.size()+1, storia, username, inventarioId, "In corso");

        // Logica per salvare su MapDB usando MapDBService
        mapDBService.saveMatch(nuovaPartita); 

        partiteAttive.add(nuovaPartita); // Aggiungi la nuova partita alla lista delle partite attive
        return nuovaPartita;
    }

    public List<Partita> getPartiteByUsername(String username) {
        return mapDBService.getListAllPartite()
                           .stream()
                           .filter(partita -> partita.getUsername().equalsIgnoreCase(username)) // Ignora il case
                           .collect(Collectors.toList());
    }
    
    
    
}