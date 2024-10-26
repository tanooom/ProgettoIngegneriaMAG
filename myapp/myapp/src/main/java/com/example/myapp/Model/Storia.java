package com.example.myapp.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Storia implements Serializable{
    private static final long serialVersionUID = 1L;

    private int id;
    private final String titolo;
    private final String username; 
    private final int lunghezza;
    private int idScenarioIniziale;
    private final List<Integer> idScenari;

    // Costruttore
    public Storia(int id, String titolo, String username, int lunghezza, 
    int idScenarioIniziale, List<Integer> idScenari) {
        this.id = id;
        this.titolo = titolo;
        this.username = username;
        this.lunghezza = lunghezza;
        this.idScenarioIniziale = idScenarioIniziale;
        this.idScenari = idScenari != null ? idScenari : new ArrayList<>();
    }

    public void aggiungiScenario(int scenarioId) {
        this.idScenari.add(scenarioId);
    }

    // Getter
    public int getId() {
        return id;
    }

    public List<Integer> getIdScenari() {
        return idScenari;
    }

    public String getUsername() {
        return username; 
    }

    public int getLunghezza() { 
        return lunghezza; 
    }

    public String getTitolo(){
        return titolo;
    }

    public int getIdScenarioIniziale() {
        return this.idScenarioIniziale;
    }

    public int getScenarioId(int scenarioId) {
        if (idScenari.contains(scenarioId)) {
            return scenarioId;
        } else {
            throw new RuntimeException("Scenario non trovato per l'ID: " + scenarioId);
        }
    }

    public int getOpzioneId(int opzioneId) {
        return opzioneId;
    }

    @JsonIgnore
    public List<Integer> getScenari() {
        return new ArrayList<>(idScenari);
    }
    
    // Setter
    public void setId(int id){
        this.id = id;
    }

    public void setIdScenarioIniziale(int idScenario) {
        if (idScenari.contains(idScenario)) {
            this.idScenarioIniziale = idScenario;
        } else {
            throw new RuntimeException("Scenario non trovato nella lista.");
        }
    }
}