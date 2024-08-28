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

    @PostMapping("/put")
    public String put(@RequestParam String key, @RequestParam String value) {
        mapDBService.put(key, value);
        return "Value added successfully!";
    }

    @GetMapping("/get")
    public String get(@RequestParam String key) {
        return mapDBService.get(key);
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