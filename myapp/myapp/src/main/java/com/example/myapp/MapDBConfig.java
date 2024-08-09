package com.example.myapp;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

@Configuration
public class MapDBConfig {

    private DB db;

    @Bean
    public DB mapDB() {
        // Configurazione del database MapDB su file
        db = DBMaker.fileDB("userdb.db").make();
        return db;
    }

    @PreDestroy
    public void closeDB() {
        if (db != null) {
            db.close();
        }
    }
}
