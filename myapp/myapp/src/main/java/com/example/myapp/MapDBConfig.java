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
        db = DBMaker.fileDB("userdb.db")
                .checksumHeaderBypass()  // Bypass del controllo dell'intestazione
                .transactionEnable()     // Abilita le transazioni
                .make();
        return db;
    }

        @Bean
    public HTreeMap<String, String> userMap(DB db) {
        // Creazione della mappa HTreeMap per memorizzare le coppie chiave-valore
        return db.hashMap("userMap")
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
