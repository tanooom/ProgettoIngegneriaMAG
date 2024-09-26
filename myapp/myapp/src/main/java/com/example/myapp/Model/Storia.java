package com.example.myapp.Model;

import java.util.ArrayList;
import java.util.List;

public class Storia{
    private int id;
    private final String titolo;
    private final String username; // Username dello scrittore
    private final int lunghezza; // Numero di scenari
    private final String stato; // Stato della storia
    private int scenarioIniziale;
    private final List<Integer> scenari; // Lista ID scenari

    public Storia(int id, String titolo, String username, int lunghezza, String stato) {
        this.id = id;
        this.titolo = titolo;
        this.username = username;
        this.lunghezza = lunghezza;
        this.stato = stato;
        this.scenari = new ArrayList<>();
    }

    public void aggiungiScenario(int scenarioId) {
        this.scenari.add(scenarioId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public List<Integer> getScenari() {
        return scenari;
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

     // Imposta l'ID dello scenario iniziale
     public void setScenarioIniziale(int scenario) {
        if (scenari.contains(scenario)) {
            this.scenarioIniziale = scenario;
        } else {
            throw new RuntimeException("Scenario non trovato nella lista.");
        }
    }

    // Restituisce l'ID dello scenario iniziale
    public int getScenarioIniziale() {
        return this.scenarioIniziale;
    }

    public int getScenarioId(int scenarioId) {
        if (scenari.contains(scenarioId)) {
            return scenarioId; // Restituisce semplicemente l'ID
        } else {
            throw new RuntimeException("Scenario non trovato per l'ID: " + scenarioId);
        }
    }

    public int getOpzioneId(int opzioneId) {
        // TODO: controllare se l'opzione è valida
        return opzioneId;
    }    

    @Override
    public String toString() {
        return "Storia{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                ", username='" + username + '\'' +
                ", lunghezza=" + lunghezza +
                ", stato='" + stato + '\'' +
                ", scenari=" + scenari.toString() +
                '}';
    }
}