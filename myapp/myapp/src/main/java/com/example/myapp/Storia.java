package com.example.myapp;

import java.util.ArrayList;
import java.util.List;

public class Storia {
<<<<<<< HEAD
    private int id;
    private final String titolo;
    private final Scenario scenarioIniziale;
    private final List<Scenario> finali;
    private final List<Scenario> scenari;
=======
    private final int id;
    private final String titolo;
    private final Scenario scenarioIniziale;
    private final String username; // Username dello scrittore
    private final int lunghezza; // Numero di scenari
    private final String stato; // Stato della storia
>>>>>>> 1a20db10f3e9b51100b03dc6370368aa4eac9efa

    // Costruttore
    public Storia(int id, String titolo, Scenario scenarioIniziale, String username, int lunghezza, String stato) {
        this.id = id;
        this.titolo = titolo;
        this.scenarioIniziale = scenarioIniziale;
<<<<<<< HEAD
        this.finali = new ArrayList<>();
        this.scenari = new ArrayList<>();
        aggiungiScenario(scenarioIniziale);
=======
        this.username = username;
        this.lunghezza = lunghezza;
        this.stato = stato;
>>>>>>> 1a20db10f3e9b51100b03dc6370368aa4eac9efa
    }

    // Getter
    public int getId() {
        return id;
    }
    public String getTitolo() {
        return titolo;
    }

    public Scenario getScenarioIniziale() {
        return scenarioIniziale;
    }

<<<<<<< HEAD
    public List<Scenario> getFinali(){
        return finali;
    }

    public void aggiungiScenario(Scenario scenario) {
        this.scenari.add(scenario);
    }

    

    // Getter e Setter per ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Scenario getScenario(int scenarioId) {
        return scenari.get(scenarioId);
    }

    public void aggiungiFinale(Scenario finale) {
        this.finali.add(finale);
=======
    public String getUsername() {
        return username; 
    }

    public int getLunghezza() { 
        return lunghezza; 
    }

    public String getStato() { 
        return stato; 
>>>>>>> 1a20db10f3e9b51100b03dc6370368aa4eac9efa
    }
}