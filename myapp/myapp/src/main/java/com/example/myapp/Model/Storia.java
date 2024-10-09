package com.example.myapp.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Storia implements Serializable{
    private static final long serialVersionUID = 1L;

    private int id;
    private final String titolo;
    private final String username; // Username dello scrittore
    private final int lunghezza; // Numero di scenari
    private int idScenarioIniziale;
    private final List<Integer> idScenari; // Lista ID scenari

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

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
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

     // Imposta l'ID dello scenario iniziale
     public void setIdScenarioIniziale(int idScenario) {
        if (idScenari.contains(idScenario)) {
            this.idScenarioIniziale = idScenario;
        } else {
            throw new RuntimeException("Scenario non trovato nella lista.");
        }
    }

    // Restituisce l'ID dello scenario iniziale
    public int getIdScenarioIniziale() {
        return this.idScenarioIniziale;
    }

    public int getScenarioId(int scenarioId) {
        if (idScenari.contains(scenarioId)) {
            return scenarioId; // Restituisce semplicemente l'ID
        } else {
            throw new RuntimeException("Scenario non trovato per l'ID: " + scenarioId);
        }
    }

    public int getOpzioneId(int opzioneId) {
        return opzioneId;
    }    

    @Override
    public String toString() {
        return "Storia{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                ", username='" + username + '\'' +
                ", lunghezza=" + lunghezza +
                ", idScenari=" + idScenari.toString() +
                '}';
    }
}
