package com.example.myapp.Model;

import java.io.Serializable;

import com.example.myapp.Controller.InventarioController;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Partita implements Serializable {
    
    private final int id; 
    private final Storia storia; 
    private int idScenarioCorrente; 
    private final int inventarioId;
    private final String username;
    private String stato; 

    // Costruttore
    public Partita(int id, Storia storia, String username, int inventarioId, String stato) {
        this.id = id;
        this.storia = storia;
        this.idScenarioCorrente = storia.getIdScenarioIniziale();
        this.inventarioId = inventarioId; 
        this.username = username;
        this.stato = stato;
    }

    public boolean eseguiScelta(Opzione opzione, InventarioController inventarioController) {
        Inventario inventario = inventarioController.getInventarioById(this.inventarioId);
        if (opzione.isRichiedeOggetto() && !inventario.contieneOggetto(opzione.getOggettoRichiesto())) {
            return false;
        }
        return true;
    }

    public void aggiornaScenarioCorrente(int nuovoScenarioId) { 
        this.idScenarioCorrente = nuovoScenarioId;
    }

    public void raccogliOggetto(String oggetto, InventarioController inventarioManager) {
        Inventario inventario = inventarioManager.getInventarioById(this.inventarioId);
        inventario.aggiungiOggetto(oggetto);
    }

    // Getter
    public int getIdScenarioCorrente() {
        return idScenarioCorrente;
    }

    public int getInventarioId() {
        return inventarioId;
    }

    public Storia getStoria() {
        return this.storia;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public String getStato() {
        return stato;
    }

    // Setter
    public void setIdScenarioCorrente(int idScenarioCorrente) {
        this.idScenarioCorrente = idScenarioCorrente;
    } 

    @JsonIgnore
    public void iniziaPartita(){
        this.stato = "In corso";
    }

    @JsonIgnore
    public void terminaPartita() {
        this.stato = "Terminata"; 
    }
    
    @JsonIgnore
    public boolean isInCorso() {
        return "In corso".equals(this.stato);
    }  
}