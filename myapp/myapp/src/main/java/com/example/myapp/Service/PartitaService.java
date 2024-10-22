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
        //TODO: va bene? serve?
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
        return partiteAttive.stream()
                            .filter(partita -> partita.getId() == partitaId)
                            .findFirst()
                            .orElse(null); // Restituisce null se non trovata
    }

    public void faiScelta(int partitaId, int scenarioCorrenteId, int opzioneId, Utente user, InventarioController inventarioController) {
        System.out.println("FAI SCELTA 1");
        System.out.println("PARTITA: " + partitaId + ", con SCENARIO: " + scenarioCorrenteId + ", con Opzione: " + opzioneId + ", con PARTITE ATTIVE: " + partiteAttive);
        System.out.println("FAI SCELTA 2: "+ getPartita(partitaId));
        Partita partita = getPartita(partitaId);
        System.out.println("PARTITA NUOVA DI FAI SCELTA 3: " + partita);
        if (partita != null) {
            Opzione opzione = storiaService.getOpzioneById(partita.getIdScenarioCorrente(), opzioneId);
            if (opzione == null) {
                throw new RuntimeException("Opzione non trovata per l'ID: " + opzioneId);
            }
            //Aggiorna lo scenario corrente
            System.out.println("FAI SCELTA 4");
            partita.faiScelta(opzione, inventarioController);

        } else {
            throw new RuntimeException("Partita non trovata per l'ID: " + partitaId);
        }
    }

    public void salvaPartita(int partitaId, int scenarioCorrenteId, Utente user) {
        // Recupera la partita attiva con l'ID fornito
        Partita partita = partiteAttive.stream()
                .filter(p -> p.getId() == partitaId && p.getUsername().equals(user.getUsername()))
                .findFirst()
                .orElse(null);
    
        if (partita != null) {
            partita.setIdScenarioCorrente(scenarioCorrenteId);
    
            // Recupera lo scenario corrente
            Scenario scenarioCorrente = mapDBController.getScenarioById(scenarioCorrenteId);
    
            if (scenarioCorrente != null) {
                
                if (scenarioCorrente.getIdExitScenari().isEmpty()) {
                    partita.terminaPartita();
                }
    
                // Ottieni l'inventario associato alla partita
                Inventario inventario = inventarioController.getInventarioById(partita.getInventarioId());
    
                // Crea o aggiorna un oggetto Inventario con il nuovo oggetto "chiave"
                inventario.aggiungiOggetto("chiave");
    
                // Inserisci l'oggetto inventario aggiornato nel MapDB
                mapDBService.putInventoryItem(inventario.getId(), inventario);
    
                mapDBService.saveMatch(partita);
                mapDBService.saveInventory(inventario);
    
            } else {
                throw new RuntimeException("Scenario non trovato per l'ID: " + scenarioCorrenteId);
            }
        } else {
            throw new RuntimeException("Partita non trovata per l'ID: " + partitaId);
        }
    }

    public boolean isUltimoScenario(int scenarioId) {
        Scenario scenario = mapDBController.getScenarioById(scenarioId);
        return scenario.isScenarioFinale();
    }

    // Metodo per CREARE una nuova partita
    public Partita creaNuovaPartita(int storiaId, String username) {
        System.out.println("USERNAME DI PARTITA SERVICE 1: " + username);
        Storia storia = storiaService.getStoriaById(storiaId); // Supponendo che tu abbia un metodo per ottenere la storia
        int partitaId = partiteAttive.size()+1;
        System.out.println("PARTITA ID DI PARTITA SERVICE 2: " + partitaId);
        Inventario inventario = inventarioController.creaInventario(partitaId); // Crea un nuovo inventario -> QUI MI DA ERRORE
        int inventarioId = inventario.getId();
        System.out.println("NUOVO INVENTARIO DI PARTITA SERVICE 2: " + inventarioId);
        Partita nuovaPartita = new Partita(partitaId, storia, username, inventarioId, "In corso");
        System.out.println("NUOVA PARTITA DI PARTITA SERVICE 3: " + nuovaPartita);
        
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