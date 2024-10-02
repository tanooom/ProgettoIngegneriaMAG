package com.example.myapp.Service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.mapdb.DB;
import org.mapdb.HTreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.Model.Opzione;
import com.example.myapp.Model.Partita;
import com.example.myapp.Model.Scenario;
import com.example.myapp.Model.Storia;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

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
    private HTreeMap<Integer, Partita> matchMap;       // Mappa per le partite

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
        matchMap = db.hashMap("matchMap")
                     .keySerializer(org.mapdb.Serializer.INTEGER)
                     .valueSerializer(org.mapdb.Serializer.JAVA)
                     .createOrOpen();
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

    // Metodo per ottenere tutte le storie
    public Collection<Storia> getAllStories() {
        return storyMap.values(); // Ritorna tutte le storie come una collection
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
    public void saveMatch(Partita partita) {
        if (partita.getId() == 0) {
            throw new IllegalArgumentException("La partita deve avere un ID valido.");
        }
        matchMap.put(partita.getId(), partita);
        db.commit();
    }

    // Metodo per ottenere una partita per ID
    public Partita getMatchById(int id) {
        return matchMap.get(id);
    }

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

        // Abilita la formattazione "bello" (pretty printing)
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        
        // Crea una mappa per tutti i dati
        Map<String, Object> allData = new HashMap<>();
        allData.put("storyMap", new HashMap<>(storyMap));
        allData.put("userMap", new HashMap<>(userMap));
        allData.put("scenarioMap", new HashMap<>(scenarioMap));
        allData.put("inventoryMap", new HashMap<>(inventoryMap));
        allData.put("optionMap", new HashMap<>(optionMap));
        allData.put("matchMap", new HashMap<>(matchMap));

        // Scrivi i dati nel file JSON
        objectMapper.writeValue(new File(filePath), allData);
    }

    // Metodo per importare i dati da un file JSON
    public void importFromJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Leggi i dati dal file JSON come una mappa generale
        Map<String, Object> allData = objectMapper.readValue(new File(filePath), new TypeReference<Map<String, Object>>() {});

        // Usa TypeReference per deserializzare ogni singola mappa con il tipo corretto
        Map<Integer, Storia> importedStories = objectMapper.convertValue(allData.get("storyMap"), new TypeReference<Map<Integer, Storia>>() {});
        Map<String, String> importedUsers = objectMapper.convertValue(allData.get("userMap"), new TypeReference<Map<String, String>>() {});
        Map<Integer, Scenario> importedScenarios = objectMapper.convertValue(allData.get("scenarioMap"), new TypeReference<Map<Integer, Scenario>>() {});
        Map<String, String> importedInventory = objectMapper.convertValue(allData.get("inventoryMap"), new TypeReference<Map<String, String>>() {});
        Map<Integer, Opzione> importedOptions = objectMapper.convertValue(allData.get("optionMap"), new TypeReference<Map<Integer, Opzione>>() {});
        Map<Integer, Partita> importedMatches = objectMapper.convertValue(allData.get("matchMap"), new TypeReference<Map<Integer, Partita>>() {});

        // Ripopola le mappe con i dati importati
        storyMap.putAll(importedStories);
        userMap.putAll(importedUsers);
        scenarioMap.putAll(importedScenarios);
        inventoryMap.putAll(importedInventory);
        optionMap.putAll(importedOptions);
        matchMap.putAll(importedMatches);
        
        db.commit(); // Committa i dati al database
    }

    // Metodo per eliminare tutti gli utenti
    public void deleteAllUsers() {
        userMap.clear();  // Rimuove tutte le voci dalla mappa degli utenti
        db.commit();      // Commit delle modifiche per renderle persistenti
    }

    // Metodo per rimuovere un'opzione
    public void removeOption(int id) {
        if (optionMap.containsKey(id)) {
            optionMap.remove(id);
            db.commit(); // Salva le modifiche nel database
        } else {
            throw new IllegalArgumentException("Opzione con ID " + id + " non trovata.");
        }
    }

    // Metodo per rimuovere uno scenario
    public void removeScenario(int id) {
        if (scenarioMap.containsKey(id)) {
            scenarioMap.remove(id);
            db.commit(); // Salva le modifiche nel database
        } else {
            throw new IllegalArgumentException("Scenario con ID " + id + " non trovato.");
        }
    }

    public Map<Integer, Opzione> getAllOptions() {
        return optionMap;
    }

    public List<Opzione> getListAllOptions() { 
        return new ArrayList<>(optionMap.values()); // Restituisce le opzioni come lista
    }

    public Map<Integer, Scenario> getAllScenari() {
        return scenarioMap; 
    }

    public List<Scenario> getListAllScenari() { 
        return new ArrayList<>(scenarioMap.values()); // Restituisce gli scenari come lista
    }

    @PreDestroy
    public void cleanup(){
        db.close();
    }
}
