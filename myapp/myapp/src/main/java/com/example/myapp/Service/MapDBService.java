package com.example.myapp.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.mapdb.DB;
import org.mapdb.HTreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.Model.Inventario;
import com.example.myapp.Model.Opzione;
import com.example.myapp.Model.Partita;
import com.example.myapp.Model.Scenario;
import com.example.myapp.Model.Storia;
import com.example.myapp.Model.Utente;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Service
public class MapDBService {

    @Autowired
    private DB db;

    private HTreeMap<Integer, Storia> storyMap;      
    private HTreeMap<String, String> userMap;         
    private HTreeMap<Integer, Scenario> scenarioMap;   
    private HTreeMap<Integer, Inventario> inventoryMap;    
    private HTreeMap<Integer, Opzione> optionMap;     
    private HTreeMap<Integer, Partita> matchMap;    

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
                         .keySerializer(org.mapdb.Serializer.INTEGER)
                         .valueSerializer(org.mapdb.Serializer.JAVA)
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
        db.commit(); 
    }

    // Metodo per ottenere una storia per ID
    public Storia getStoryById(int id) {
        return storyMap.get(id);
    }

    public Map<Integer, Storia> getAllStorie() {
        return storyMap; 
    }

    public Map<Integer, Partita> getAllPartite() {
        return matchMap; 
    }

    // Metodo per rimuovere una storia
    public void removeStoria(int id) {
        if (storyMap.containsKey(id)) {
            storyMap.remove(id);
            db.commit(); 
        } else {
            throw new IllegalArgumentException("Storia con ID " + id + " non trovata.");
        }
    }

    // Metodo per ottenere tutte le storie
    public Collection<Storia> getAllStories() {
        return storyMap.values();
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

    // Metodo per salvare un inventario
    public void saveInventory(Inventario inventario) {
        if (inventario.getId() == 0) {
            throw new IllegalArgumentException("L'inventario deve avere un ID valido.");
        }
        inventoryMap.put(inventario.getId(), inventario); 
        db.commit();
    }

    // Metodi per ottenere un inventario per ID
    public Inventario getInventoryById(int id) {
        return inventoryMap.get(id);
    }

    public void putInventoryItem(Integer key, Inventario value) {
        inventoryMap.put(key, value);
    }

    public Inventario getInventoryItem(Integer key) {
        return inventoryMap.get(key);
    }

    public void putUser(String key, String value) {
        userMap.put(key, value);
    }

    public String getUser(String key) {
        return userMap.get(key);
    }

    public void exportToJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        Map<String, Object> allData = new HashMap<>();
        allData.put("storyMap", new HashMap<>(storyMap));
        
        // Conversione della userMap
        Map<String, Utente> userMapExport = new HashMap<>();
        for (Map.Entry<String, String> entry : userMap.entrySet()) {
            String[] userData = entry.getValue().split(";");
            Utente utente = new Utente(entry.getKey(), userData[0], userData[1], userData[2], userData[3]);
            userMapExport.put(entry.getKey(), utente);
        }
        
        allData.put("userMap", userMapExport);
        allData.put("scenarioMap", new HashMap<>(scenarioMap));
        allData.put("inventoryMap", new HashMap<>(inventoryMap));
        allData.put("optionMap", new HashMap<>(optionMap));
        allData.put("matchMap", new HashMap<>(matchMap));

        objectMapper.writeValue(new File(filePath), allData);
    }

    // Metodo per eliminare tutti gli utenti
    public void deleteAllUsers() {
        userMap.clear(); 
        db.commit();     
    }

    // Metodo per rimuovere un'opzione
    public void removeOption(int id) {
        if (optionMap.containsKey(id)) {
            optionMap.remove(id);
            db.commit();
        } else {
            throw new IllegalArgumentException("Opzione con ID " + id + " non trovata.");
        }
    }

    // Metodo per rimuovere uno scenario
    public void removeScenario(int id) {
        if (scenarioMap.containsKey(id)) {
            scenarioMap.remove(id);
            db.commit();
        } else {
            throw new IllegalArgumentException("Scenario con ID " + id + " non trovato.");
        }
    }

    public void removeMatch(int id) {
        if (matchMap.containsKey(id)) {
            matchMap.remove(id);
            db.commit();
        } else {
            throw new IllegalArgumentException("Partita con ID " + id + " non trovata.");
        }
    }

    public void removeInventory(int id) {
        if (inventoryMap.containsKey(id)) {
            inventoryMap.remove(id);
            db.commit();
        } else {
            throw new IllegalArgumentException("Inventario con ID " + id + " non trovato.");
        }
    }

    public Map<Integer, Opzione> getAllOptions() {
        return optionMap;
    }

    public List<Opzione> getListAllOptions() { 
        return new ArrayList<>(optionMap.values()); 
    }

    public Map<Integer, Scenario> getAllScenari() {
        return scenarioMap; 
    }

    public List<Scenario> getListAllScenari() { 
        return new ArrayList<>(scenarioMap.values());
    }

    public List<Partita> getListAllPartite() { 
        return new ArrayList<>(matchMap.values()); 
    }

    public List<Inventario> getListAllInventari() { 
        return new ArrayList<>(inventoryMap.values()); 
    }

    public void deleteAllStories() {
        storyMap.clear();
        db.commit(); 
    }

    public void deleteAllScenari() {
        scenarioMap.clear();
        db.commit(); 
    }

    public void deleteAllOptions() {
        optionMap.clear();
        db.commit(); 
    }

    public void deleteAllPartite() {
        matchMap.clear();
        inventoryMap.clear();
        db.commit(); 
    }

    public void deleteAllInventari() {
        inventoryMap.clear();
        db.commit(); 
    }

    public void deleteDatabase() {
        storyMap.clear();
        userMap.clear();
        scenarioMap.clear();
        inventoryMap.clear();
        optionMap.clear();
        matchMap.clear();
        db.commit(); 
    }

    // Metodo per rimuovere una storia in base al titolo
    public void removeStoriaByTitle(String title) {
        Storia storiaDaRimuovere = null;
        for (Storia storia : storyMap.values()) {
            if (storia.getTitolo().equals(title)) { 
                storiaDaRimuovere = storia;
                break;
            }
        }
        if (storiaDaRimuovere != null) {
            storyMap.remove(storiaDaRimuovere.getId());
            db.commit();
        } else {
            throw new IllegalArgumentException("Storia con titolo '" + title + "' non trovata.");
        }
    }

    @PreDestroy
    public void cleanup(){
        db.close();
    }

    public List<Scenario> getScenariByTitle(String title) {
        Storia storia = null;
        for (Storia s : storyMap.values()) {
            if (s.getTitolo().equals(title)) { 
                storia = s;
                break;
            }
        }
        if (storia != null) {
            List<Scenario> scenari = new ArrayList<>();
            for (Integer scenarioId : storia.getIdScenari()) { 
                Scenario scenario = scenarioMap.get(scenarioId); 
                if (scenario != null) {
                    scenari.add(scenario);
                } else {
                    throw new IllegalArgumentException("Scenario con ID '" + scenarioId + "' non trovato.");
                }
            }
            return scenari;
        } else {
            throw new IllegalArgumentException("Storia con titolo '" + title + "' non trovata.");
        }
    }

    public List<Opzione> getOpzioniByScenario(int scenarioId) {
        Scenario scenario = scenarioMap.get(scenarioId);
        if (scenario == null) {
            throw new IllegalArgumentException("Scenario con ID " + scenarioId + " non trovato.");
        }
        
        List<Opzione> opzioni = new ArrayList<>();
        for (Integer opzioneId : scenario.getIdOpzioni()) { 
            Opzione opzione = optionMap.get(opzioneId);
            if (opzione != null) {
                opzioni.add(opzione);
            } else {
                throw new IllegalArgumentException("Opzione con ID " + opzioneId + " non trovata.");
            }
        }
        return opzioni;
    }
    
}