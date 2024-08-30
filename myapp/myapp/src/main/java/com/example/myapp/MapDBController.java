package com.example.myapp;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mapdb")
public class MapDBController {

    @Autowired
    private MapDBService mapDBService;

    // Endpoint per gestire gli utenti
    @PostMapping("/putUser")
    public String putUser(@RequestParam String key, @RequestParam String value) {
        mapDBService.putUser(key, value);
        return "Utente aggiunto con successo!";
    }

    @GetMapping("/getUser")
    public String getUser(@RequestParam String key) {
        return mapDBService.getUser(key);
    }

    // Endpoint per gestire le storie
    /*@PostMapping("/putStory")
    public String putStory(@RequestParam int id, @RequestParam String title) {
        Storia storia = new Storia(id, title);
        mapDBService.saveStory(storia);
        return "Storia aggiunta con successo!";
    }*/

    @GetMapping("/getStory")
    public Storia getStory(@RequestParam int id) {
        return mapDBService.getStoryById(id);
    }

    // Endpoint per gestire gli scenari
    @PostMapping("/putScenario")
    public String putScenario(@RequestParam int id, @RequestParam String description) {
        Scenario scenario = new Scenario(id, description);
        mapDBService.saveScenario(scenario);
        return "Scenario aggiunto con successo!";
    }

    @GetMapping("/getScenario")
    public Scenario getScenario(@RequestParam int id) {
        return mapDBService.getScenarioById(id);
    }

    // Endpoint per gestire le opzioni
    /*@PostMapping("/putOption")
    public String putOption(@RequestParam int id, @RequestParam String description) {
        Opzione opzione = new Opzione(id, description);
        mapDBService.saveOption(opzione);
        return "Opzione aggiunta con successo!";
    }*/

    @GetMapping("/getOption")
    public Opzione getOption(@RequestParam int id) {
        return mapDBService.getOptionById(id);
    }

    // Endpoint per gestire le partite
    /*@PostMapping("/putMatch")
    public String putMatch(@RequestParam int id, @RequestParam String matchData) {
        Partita partita = new Partita(id, matchData);
        mapDBService.saveMatch(partita);
        return "Partita aggiunta con successo!";
    }

    @GetMapping("/getMatch")
    public Partita getMatch(@RequestParam int id) {
        return mapDBService.getMatchById(id);
    }*/

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

    // Endpoint per importare i dati da JSON
    @PostMapping("/import")
    public String importFromJson(@RequestParam String filePath) {
        try {
            mapDBService.importFromJson(filePath);
            return "Dati importati con successo da " + filePath;
        } catch (IOException e) {
            return "Errore durante l'importazione dei dati: " + e.getMessage();
        }
    }
}
