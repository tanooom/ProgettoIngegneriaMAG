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

    // Mappe per i vari elementi
    private HTreeMap<Integer, Storia> storyMap;        // Mappa per le storie
    private HTreeMap<String, String> userMap;          // Mappa per gli utenti
    private HTreeMap<Integer, Scenario> scenarioMap;   // Mappa per gli scenari
    private HTreeMap<String, String> inventoryMap;     // Mappa per l'inventario
    private HTreeMap<Integer, Opzione> optionMap;      // Mappa per le opzioni
    //private HTreeMap<Integer, Partita> matchMap;       // Mappa per le partite

    @PostConstruct
    @SuppressWarnings("unchecked")
    public void init() {
        // Inizializzazione della mappa delle storie
        storyMap = db.hashMap("storyMap")
                      .keySerializer(org.mapdb.Serializer.INTEGER)
                      .valueSerializer(org.mapdb.Serializer.JAVA)
                      .createOrOpen();

        // Inizializzazione della mappa degli utenti
        userMap = db.hashMap("userMap")
                    .keySerializer(org.mapdb.Serializer.STRING)
                    .valueSerializer(org.mapdb.Serializer.STRING)
                    .createOrOpen();

        // Inizializzazione della mappa degli scenari
        scenarioMap = db.hashMap("scenarioMap")
                        .keySerializer(org.mapdb.Serializer.INTEGER)
                        .valueSerializer(org.mapdb.Serializer.JAVA)
                        .createOrOpen();

        // Inizializzazione della mappa dell'inventario
        inventoryMap = db.hashMap("inventoryMap")
                         .keySerializer(org.mapdb.Serializer.STRING)
                         .valueSerializer(org.mapdb.Serializer.STRING)
                         .createOrOpen();

        // Inizializzazione della mappa delle opzioni
        optionMap = db.hashMap("optionMap")
                      .keySerializer(org.mapdb.Serializer.INTEGER)
                      .valueSerializer(org.mapdb.Serializer.JAVA)
                      .createOrOpen();

        // Inizializzazione della mappa delle partite
        /*matchMap = db.hashMap("matchMap")
                     .keySerializer(org.mapdb.Serializer.INTEGER)
                     .valueSerializer(org.mapdb.Serializer.JAVA)
                     .createOrOpen();*/
    }

    // Metodo per salvare una storia
    public void saveStory(Storia storia) {
        if (storia.getId() == 0) {
            throw new IllegalArgumentException("La storia deve avere un ID valido.");
        }
        storyMap.put(storia.getId(), storia);
        db.commit(); // Commit dei cambiamenti al database
    }

    // Metodo per ottenere una storia per ID
    public Storia getStoryById(int id) {
        return storyMap.get(id);
    }

    // Metodo per salvare uno scenario
    public void saveScenario(Scenario scenario) {
        if (scenario.getId() == 0) {
            throw new IllegalArgumentException("Lo scenario deve avere un ID valido.");
        }
        scenarioMap.put(scenario.getId(), scenario);
        db.commit();
    }

    // Metodo per ottenere uno scenario per ID
    public Scenario getScenarioById(int id) {
        return scenarioMap.get(id);
    }

    // Metodo per salvare un'opzione
    public void saveOption(Opzione opzione) {
        if (opzione.getId() == 0) {
            throw new IllegalArgumentException("L'opzione deve avere un ID valido.");
        }
        optionMap.put(opzione.getId(), opzione);
        db.commit();
    }

    // Metodo per ottenere un'opzione per ID
    public Opzione getOptionById(int id) {
        return optionMap.get(id);
    }

    // Metodo per salvare una partita
    /*public void saveMatch(Partita partita) {
        if (partita.getId() == 0) {
            throw new IllegalArgumentException("La partita deve avere un ID valido.");
        }
        matchMap.put(partita.getId(), partita);
        db.commit();
    }

    // Metodo per ottenere una partita per ID
    public Partita getMatchById(int id) {
        return matchMap.get(id);
    }*/

    // Metodo per gestire l'inventario
    public void putInventoryItem(String key, String value) {
        inventoryMap.put(key, value);
    }

    public String getInventoryItem(String key) {
        return inventoryMap.get(key);
    }

    // Metodo per gestire gli utenti
    public void putUser(String key, String value) {
        userMap.put(key, value);
    }

    public String getUser(String key) {
        return userMap.get(key);
    }

    // Metodo per esportare i dati in un file JSON
    public void exportToJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Esporta la mappa delle storie
        Map<Integer, Storia> storieData = new HashMap<>(storyMap);
        objectMapper.writeValue(new File(filePath), storieData);
    }

    // Metodo per importare i dati da un file JSON
    public void importFromJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Specifica il tipo con TypeReference
        Map<Integer, Storia> importedData = objectMapper.readValue(new File(filePath), new TypeReference<Map<Integer, Storia>>() {});
        storyMap.putAll(importedData); // Importiamo i dati nella mappa
        db.commit(); // Committiamo i dati al database
    }

    @PreDestroy
    public void cleanup() {
        db.close();
    }
}
