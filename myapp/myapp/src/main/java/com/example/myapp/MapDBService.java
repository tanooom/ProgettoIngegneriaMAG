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

    private HTreeMap<String, String> map;

    @PostConstruct
    public void init() {
        map = db.hashMap("userMap")
                 .keySerializer(org.mapdb.Serializer.STRING)
                 .valueSerializer(org.mapdb.Serializer.STRING)
                 .createOrOpen();
    }

    public void put(String key, String value) {
        map.put(key, value);
    }

    public String get(String key) {
        return map.get(key);
    }

    // Metodo per esportare i dati in un file JSON
    public void exportToJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> mapData = new HashMap<>(map); // Copiamo i dati dalla mappa
        objectMapper.writeValue(new File(filePath), mapData);
    }

    // Metodo per importare i dati da un file JSON
    public void importFromJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        // Specifica il tipo con TypeReference
        Map<String, String> importedData = objectMapper.readValue(new File(filePath), new TypeReference<Map<String, String>>() {});
        map.putAll(importedData); // Importiamo i dati nella mappa
        db.commit(); // Committiamo i dati al database
    }

    @PreDestroy
    public void cleanup() {
        db.close();
    }
}