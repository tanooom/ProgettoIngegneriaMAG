package com.example.myapp.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.myapp.Model.Inventario;
import com.example.myapp.Model.Partita;
import com.example.myapp.Model.Scenario;
import com.example.myapp.Model.Storia;
import com.example.myapp.Service.MapDBService;

@RestController
public class MapDBController {

    @Autowired
    private MapDBService mapDBService;

    @PostMapping("/saveStoria")
    public String saveStoria(@RequestBody Storia storia) {
        mapDBService.saveStory(storia);
        return "Storia salvata con successo!";
    }

    @GetMapping("/getStory")
    public Storia getStory(@RequestParam int id) {
        return mapDBService.getStoryById(id);
    }

    @GetMapping("/getScenario")
    public Scenario getScenarioById(@RequestParam int id) {
        return mapDBService.getScenarioById(id);
    }

    @PostMapping("/saveInventario")
    public String saveStoria(@RequestBody Inventario inventario) {
        mapDBService.saveInventory(inventario);
        return "Inventario salvato con successo!";
    }

    @PostMapping("/putMatch")
    public String putMatch( @RequestParam int id, 
                            @RequestParam Storia storia,  
                            @RequestParam String username, 
                            @RequestParam int inventarioId,
                            @RequestParam String stato) {
        Partita partita = new Partita(id, storia, username, inventarioId, stato);
        mapDBService.saveMatch(partita);
        return "Partita aggiunta con successo!";
    }

    @GetMapping("/getMatch")
    public Partita getMatch(@RequestParam int id) {
        return mapDBService.getMatchById(id);
    }

    @PostMapping("/putInventoryItem")
    public String putInventoryItem(@RequestParam Integer key, @RequestParam Inventario value) {
        mapDBService.putInventoryItem(key, value);
        return "Oggetto dell'inventario aggiunto con successo!";
    }

    @GetMapping("/getInventoryItem")
    public Inventario getInventoryItem(@RequestParam Integer key) {
        return mapDBService.getInventoryItem(key);
    }
    
    // Endpoint per esportare i dati in JSON
    @GetMapping("/export")
    public String exportToJson(@RequestParam String filePath) {
        try {
            mapDBService.exportToJson(filePath);
            return "Dati esportati con successo in " + filePath;
        } catch (IOException e) {
            return "Errore durante l'esportazione dei dati: " + e.getMessage();
        }
    }

    // Endpoint per eliminare tutti gli utenti
    @DeleteMapping("/deleteAllUsers")
    public String deleteAllUsers() {
        try {
            mapDBService.deleteAllUsers();
            return "Tutti gli utenti sono stati rimossi con successo!";
        } catch (Exception e) {
            return "Errore durante la rimozione degli utenti: " + e.getMessage();
        }
    }

    // Endpoint per eliminare tutte le storie
    @DeleteMapping("/deleteAllStories")
    public String deleteAllStories() {
        try {
            mapDBService.deleteAllStories();
            return "Tutte le storie sono state rimosse con successo!";
        } catch (Exception e) {
            return "Errore durante la rimozione delle storie: " + e.getMessage();
        }
    }

    // Endpoint per eliminare tutti gli scenari
    @DeleteMapping("/deleteAllScenari")
    public String deleteAllScenari() {
        try {
            mapDBService.deleteAllScenari();
            return "Tutti gli scenari sono stati rimossi con successo!";
        } catch (Exception e) {
            return "Errore durante la rimozione degli scenari: " + e.getMessage();
        }
    }

    // Endpoint per eliminare tutte le opzioni
    @DeleteMapping("/deleteAllOptions")
    public String deleteAllOptions() {
        try {
            mapDBService.deleteAllOptions();
            return "Tutte le opzioni sono state rimosse con successo!";
        } catch (Exception e) {
            return "Errore durante la rimozione delle opzioni: " + e.getMessage();
        }
    }

    // Endpoint per eliminare tutte le partite
    @DeleteMapping("/deleteAllPartite")
    public String deleteAllPartite() {
        try {
            mapDBService.deleteAllPartite();
            return "Tutte le partite sono state rimosse con successo!";
        } catch (Exception e) {
            return "Errore durante la rimozione delle partite: " + e.getMessage();
        }
    }

    // Endpoint per eliminare tutto il database
    @DeleteMapping("/deleteDatabase")
    public String deleteDatabase() {
        try {
            mapDBService.deleteDatabase();
            return "Il database è stato cancellato";
        } catch (Exception e) {
            return "Errore durante la cancellazione del database: " + e.getMessage();
        }
    }
}