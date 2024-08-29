package com.example.myapp;

import java.util.ArrayList;
import java.util.List;

public class Storia {
    private int id;
    private final String titolo;
    private final Scenario scenarioIniziale;
    private final List<Scenario> finali;
    private final List<Scenario> scenari;

    // Costruttore
    public Storia(String titolo, Scenario scenarioIniziale) {
        this.titolo = titolo;
        this.scenarioIniziale = scenarioIniziale;
        this.finali = new ArrayList<>();
        this.scenari = new ArrayList<>();
        aggiungiScenario(scenarioIniziale);
    }

    // Getter
    public String getTitolo() {
        return titolo;
    }

    public Scenario getScenarioIniziale() {
        return scenarioIniziale;
    }

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
    }
}