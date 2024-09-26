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

    // Metodo per ottenere uno scenario specifico per indice
    public int getScenario(int scenarioId) {
        if (scenarioId < 0 || scenarioId >= scenari.size()) {
            throw new IndexOutOfBoundsException("Scenario ID non valido.");
        }
        return scenari.get(scenarioId); // Ritorna l'ID dello scenario
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

    /**public List<Scenario> getFinali() {
        List<Scenario> finali = new ArrayList<>();
        for (int scenarioId : scenari) {
            Scenario scenario = getScenarioById(scenarioId); // usa il metodo sopra
            if (scenario.isScenarioFinale()) {
                finali.add(scenario);
            }
        }
        return finali;
    }**/
    

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