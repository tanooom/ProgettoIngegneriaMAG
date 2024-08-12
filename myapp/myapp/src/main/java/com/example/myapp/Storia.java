package com.example.myapp;

public class Storia {
    private final String titolo;
    private final Scenario scenarioIniziale;

    // Costruttore
    public Storia(String titolo, Scenario scenarioIniziale) {
        this.titolo = titolo;
        this.scenarioIniziale = scenarioIniziale;
    }

    // Getter
    public String getTitolo() {
        return titolo;
    }

    public Scenario getScenarioIniziale() {
        return scenarioIniziale;
    }
}

