package com.example.myapp;

import org.mapdb.DB;
import org.mapdb.HTreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class MapDBService {

    @Autowired
    private DB db;

    private HTreeMap<String, String> map;

    @PostConstruct
    public void init() {
        map = db.hashMap("myMap")
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

    @PreDestroy
    public void cleanup() {
        db.close();
    }
}
