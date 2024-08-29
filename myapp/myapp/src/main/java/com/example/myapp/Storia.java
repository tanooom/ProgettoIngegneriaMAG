package com.example.myapp;

public class Storia {
    private final int id;
    private final String titolo;
    private final Scenario scenarioIniziale; //Scenario di partenza
    private final String username; // Username dello scrittore
    private final int lunghezza; // Numero di scenari
    private final String stato; // Stato della storia

    // Costruttore
    public Storia(int id, String titolo, Scenario scenarioIniziale, String username, int lunghezza, String stato) {
        this.id = id;
        this.titolo = titolo;
        this.scenarioIniziale = scenarioIniziale;
        this.username = username;
        this.lunghezza = lunghezza;
        this.stato = stato;
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

    public String getUsername() {
        return username; 
    }

    public int getLunghezza() { 
        return lunghezza; 
    }

    public String getStato() { 
        return stato; 
    }
}