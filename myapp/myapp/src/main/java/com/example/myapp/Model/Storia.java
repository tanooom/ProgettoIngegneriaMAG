package com.example.myapp.Model;

import java.util.ArrayList;
import java.util.List;

public class Storia{
    private int id;
    private final String titolo;
    private final String username; // Username dello scrittore
    private final int lunghezza; // Numero di scenari
    private final String stato; // Stato della storia
    private final List<Integer> scenari; // Lista ID scenari

    public Storia(int id, String titolo, String username, int lunghezza, String stato) {
        this.titolo = titolo;
        this.username = username;
        this.lunghezza = lunghezza;
        this.stato = stato;
        this.scenari = new ArrayList<>();
    }

    public void aggiungiScenario(int scenario) {
        this.scenari.add(scenario);
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    // Metodo per ottenere uno scenario specifico per indice
    public int getScenario(int scenarioId) {
        return scenari.get(scenarioId);
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