package com.example.myapp.Model;

import java.util.ArrayList;
import java.util.List;

public class Storia{
    private int id;
    private final String titolo;
    private final Scenario scenarioIniziale;
    private final List<Scenario> finali;
    private final List<Scenario> scenari;
    private final String username; // Username dello scrittore
    private final int lunghezza; // Numero di scenari
    private final String stato; // Stato della storia

    public Storia(int id, String titolo, Scenario scenarioIniziale, String username, int lunghezza, String stato) {
        this.titolo = titolo;
        this.scenarioIniziale = scenarioIniziale;
        this.finali = new ArrayList<>();
        this.scenari = new ArrayList<>();
        this.scenari.add(scenarioIniziale); // Aggiungi direttamente lo scenario iniziale alla lista
        this.username = username;
        this.lunghezza = lunghezza;
        this.stato = stato;
    }

    public Scenario getScenarioIniziale(){
        return scenarioIniziale;
    }

    public List<Scenario> getFinali(){
        return finali;
    }

    public void aggiungiScenario(Scenario scenario) {
        this.scenari.add(scenario);
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public Scenario getScenario(int scenarioId) {
        return scenari.get(scenarioId);
    }

    public void aggiungiFinale(Scenario finale) {
        this.finali.add(finale);
    }

    public String getUsername() {
        return username; 
    }

    public int getLunghezza() { 
        return lunghezza; 
    }

    public String getStato() { 
        return stato; 
    }

    public String getTitolo(){
        return titolo;
    }

}