package com.example.myapp;

import javax.annotation.PreDestroy;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapDBConfig {

    private DB db;

    @Bean
    public DB mapDB() {
        // Configurazione del database MapDB su file
        db = DBMaker.fileDB("generaldb.db")  // Nome del database
                .checksumHeaderBypass()      // Bypass del controllo dell'intestazione
                .transactionEnable()         // Abilita le transazioni
                .make();
        return db;
    }

    @Bean
    public HTreeMap<String, String> userMap(DB db) {
        // Creazione della mappa HTreeMap per gli Utenti
        return db.hashMap("userMap")
                .keySerializer(org.mapdb.Serializer.STRING)
                .valueSerializer(org.mapdb.Serializer.STRING)
                .createOrOpen();
    }

    @Bean
    public HTreeMap<String, String> storyMap(DB db) {
        // Creazione della mappa HTreeMap per le Storie
        return db.hashMap("storyMap")
                .keySerializer(org.mapdb.Serializer.STRING)
                .valueSerializer(org.mapdb.Serializer.STRING)
                .createOrOpen();
    }

    @Bean
    public HTreeMap<String, String> scenarioMap(DB db) {
        // Creazione della mappa HTreeMap per gli Scenari
        return db.hashMap("scenarioMap")
                .keySerializer(org.mapdb.Serializer.STRING)
                .valueSerializer(org.mapdb.Serializer.STRING)
                .createOrOpen();
    }

    @Bean
    public HTreeMap<String, String> inventoryMap(DB db) {
        // Creazione della mappa HTreeMap per l'Inventario
        return db.hashMap("inventoryMap")
                .keySerializer(org.mapdb.Serializer.STRING)
                .valueSerializer(org.mapdb.Serializer.STRING)
                .createOrOpen();
    }

    @Bean
    public HTreeMap<String, String> optionMap(DB db) {
        // Creazione della mappa HTreeMap per le Opzioni (azioni possibili in uno scenario)
        return db.hashMap("optionMap")
                .keySerializer(org.mapdb.Serializer.STRING)
                .valueSerializer(org.mapdb.Serializer.STRING)
                .createOrOpen();
    }

    @Bean
    public HTreeMap<String, String> matchMap(DB db) {
        // Creazione della mappa HTreeMap per le Partite (unione tra storia e utenti)
        return db.hashMap("matchMap")
                .keySerializer(org.mapdb.Serializer.STRING)
                .valueSerializer(org.mapdb.Serializer.STRING)
                .createOrOpen();
    }

    @PreDestroy
    public void closeDB() {
        if (db != null) {
            try {
                db.close();
                System.out.println("Database chiuso con successo.");
            } catch (Exception e) {
                System.err.println("Errore durante la chiusura del database: " + e.getMessage());
            }
        }
    }
}
