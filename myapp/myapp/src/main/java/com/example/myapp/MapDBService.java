package com.example.myapp;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.mapdb.DB;
import org.mapdb.HTreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MapDBService {

    @Autowired
    private DB db;

    private HTreeMap<Integer, Storia> storieMap; // Mappa per le storie
    private HTreeMap<String, String> userMap; // Mappa per gli utenti

    @PostConstruct
    public void init() {
        // Inizializzazione della mappa delle storie
        storieMap = db.hashMap("storieMap")
                      .keySerializer(org.mapdb.Serializer.INTEGER)
                      .valueSerializer(org.mapdb.Serializer.JAVA)
                      .createOrOpen();

        // Inizializzazione della mappa degli utenti
        userMap = db.hashMap("userMap")
                    .keySerializer(org.mapdb.Serializer.STRING)
                    .valueSerializer(org.mapdb.Serializer.STRING)
                    .createOrOpen();
    }

    // Metodo per salvare una storia
    public void salvaStoria(Storia storia) {
        if (storia.getId() == 0) {
            throw new IllegalArgumentException("La storia deve avere un ID valido.");
        }
        storieMap.put(storia.getId(), storia);
        db.commit(); // Commit dei cambiamenti al database
    }

    // Metodo per ottenere una storia per ID
    public Storia getStoriaById(int id) {
        return storieMap.get(id);
    }

    // Metodo per esportare i dati in un file JSON
    public void exportToJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Esporta la mappa delle storie
        Map<Integer, Storia> storieData = new HashMap<>(storieMap);
        objectMapper.writeValue(new File(filePath), storieData);
    }

    // Metodo per importare i dati da un file JSON
    public void importFromJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Specifica il tipo con TypeReference
        Map<Integer, Storia> importedData = objectMapper.readValue(new File(filePath), new TypeReference<Map<Integer, Storia>>() {});
        storieMap.putAll(importedData); // Importiamo i dati nella mappa
        db.commit(); // Committiamo i dati al database
    }

    // Metodo per gestire gli utenti
    public void putUser(String key, String value) {
        userMap.put(key, value);
    }

    public String getUser(String key) {
        return userMap.get(key);
    }

    @PreDestroy
    public void cleanup() {
        db.close();
    }

}