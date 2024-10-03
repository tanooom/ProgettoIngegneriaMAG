package com.example.myapp.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.myapp.Model.Partita;
import com.example.myapp.Model.Scenario;
import com.example.myapp.Model.Storia;
import com.example.myapp.Service.MapDBService;

@RestController
//@RequestMapping("/mapdb")
public class MapDBController {

    @Autowired
    private MapDBService mapDBService;

    @PostMapping("/saveStoria")
    public String saveStoria(@RequestBody Storia storia) {
        mapDBService.saveStory(storia);
        return "Storia salvata con successo!";
    }

    /*public String putStory( @RequestParam int id, 
                            @RequestParam String title, 
                            @RequestParam String username, 
                            @RequestParam int lunghezza, 
                            @RequestParam String stato, 
                            @RequestParam int scenarioinizialeId,
                            @RequestParam List<Integer> scenariId) {
        Storia storia = new Storia(id, title, username, lunghezza, stato, scenarioinizialeId, scenariId);
        storia.aggiungiScenario(scenarioinizialeId);
        mapDBService.saveStory(storia);
        return "Storia aggiunta con successo!";
    }*/

    @GetMapping("/getStory")
    public Storia getStory(@RequestParam int id) {
        return mapDBService.getStoryById(id);
    }

    // Endpoint per gestire gli scenari
    @PostMapping("/putScenario")
    public String putScenario(@RequestParam int id, 
                            @RequestParam String nome, 
                            @RequestParam String description,
                            @RequestParam(required = false) List<Integer> idOpzioni,
                            @RequestParam(required = false) String oggettoRaccoglibile,
                            @RequestParam(required = false) List<Integer> idEntryScenari,
                            @RequestParam(required = false) List<Integer> idExitScenari,
                            @RequestParam boolean scenarioIniziale,
                            @RequestParam boolean scenarioFinale) {
        
        Scenario scenario = new Scenario(id, nome, description, 
                                        idOpzioni != null ? idOpzioni : new ArrayList<>(),
                                        oggettoRaccoglibile,
                                        idEntryScenari != null ? idEntryScenari : new ArrayList<>(),
                                        idExitScenari != null ? idExitScenari : new ArrayList<>(),
                                        scenarioIniziale, 
                                        scenarioFinale);
        mapDBService.saveScenario(scenario);
        return "Scenario aggiunto con successo!";
    }


    @GetMapping("/getScenario")
    public Scenario getScenarioById(@RequestParam int id) {
        return mapDBService.getScenarioById(id);
    }

    /*// Endpoint per gestire le opzioni
    @PostMapping("/putOption")
    public String putOption(@RequestParam int id, 
                            @RequestParam String description, 
                            @RequestParam int scenarioSuccessivo,
                            @RequestParam boolean richiedeOggetto, 
                            @RequestParam String oggettoRichiesto, 
                            @RequestParam boolean richiedeIndovinello, 
                            @RequestParam String indovinello,
                            @RequestParam String rispostaCorrettaIndovinello) {
        Opzione opzione = new Opzione(id, description, scenarioSuccessivo, richiedeOggetto, oggettoRichiesto, richiedeIndovinello, indovinello, rispostaCorrettaIndovinello);
        mapDBService.saveOption(opzione);
        return "Opzione aggiunta con successo!";
    }

    @GetMapping("/getOption")
    public Opzione getOption(@RequestParam int id) {
        return mapDBService.getOptionById(id);
    }*/

    // Endpoint per gestire le partite
    @PostMapping("/putMatch")
    public String putMatch( @RequestParam int id, 
                            @RequestParam Storia storia,  
                            @RequestParam String username, 
                            @RequestParam int inventarioId) {
        Partita partita = new Partita(id, storia, username, inventarioId);
        mapDBService.saveMatch(partita);
        return "Partita aggiunta con successo!";
    }

    @GetMapping("/getMatch")
    public Partita getMatch(@RequestParam int id) {
        return mapDBService.getMatchById(id);
    }

    // Endpoint per gestire l'inventario
    @PostMapping("/putInventoryItem")
    public String putInventoryItem(@RequestParam String key, @RequestParam String value) {
        mapDBService.putInventoryItem(key, value);
        return "Oggetto dell'inventario aggiunto con successo!";
    }

    @GetMapping("/getInventoryItem")
    public String getInventoryItem(@RequestParam String key) {
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

    /*// Endpoint per importare i dati da JSON
    @PostMapping("/import")
    public String importFromJson(@RequestParam String filePath) {
        try {
            mapDBService.importFromJson(filePath);
            return "Dati importati con successo da " + filePath;
        } catch (IOException e) {
            return "Errore durante l'importazione dei dati: " + e.getMessage();
        }
    }*/

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
}
