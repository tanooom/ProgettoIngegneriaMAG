package com.example.myapp.Model;

import java.io.Serializable;

import com.example.myapp.Controller.InventarioController;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Partita implements Serializable {
    
    private final int id; //autoincrement
    private final Storia storia; //me lo prende da storiaId
    private int idScenarioCorrente; //uguale 
    private final int inventarioId; //me lo crea dentro api/.../start
    private final String username; // prende l'username dell'utente che sta giocando
    private String stato; // Stato della partita (Non inziata, In corso o Terminata)

    // Costruttore
    public Partita(int id, Storia storia, String username, int inventarioId, String stato) {
        this.id = id;
        this.storia = storia;
        this.idScenarioCorrente = storia.getIdScenarioIniziale();
        this.inventarioId = inventarioId; // Inizializza un inventario vuoto
        this.username = username;
        this.stato = stato;
    }

    // Metodo per fare una scelta
    public boolean eseguiScelta(Opzione opzione, InventarioController inventarioController) {
        // Usa l'ID per recuperare l'inventario
        Inventario inventario = inventarioController.getInventarioById(this.inventarioId);
    
        // Verifica se l'opzione richiede un oggetto e se l'utente lo possiede
        if (opzione.isRichiedeOggetto() && !inventario.contieneOggetto(opzione.getOggettoRichiesto())) {
            return false; // L'utente non ha l'oggetto richiesto, non può procedere
        }
        // Se tutti i requisiti sono soddisfatti, ritorna true
        return true;
    }

    //TODO: è lo stesso di setIdScenarioCorrente?
    public void aggiornaScenarioCorrente(int nuovoScenarioId) { 
        this.idScenarioCorrente = nuovoScenarioId;
    }      

    // Metodo per raccogliere un oggetto
    public void raccogliOggetto(String oggetto, InventarioController inventarioManager) {
        Inventario inventario = inventarioManager.getInventarioById(this.inventarioId);
        inventario.aggiungiOggetto(oggetto); // Aggiungi l'oggetto all'inventario
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
        return username; // Getter per ottenere l'username
    }

    public int getId() {
        return id;
    }

    public String getStato() {
        return stato;
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

    @Override
    public String toString() {
        return "Partita{" +
                "ID=" + id +
                ", Storia='" + storia.getTitolo() + '\'' +
                ", ID Scenario Corrente=" + idScenarioCorrente +
                ", Inventario ID=" + inventarioId +
                ", Username='" + username + '\'' +
                ", Stato='" + stato + '\'' +
                '}';
    }

    public void setIdScenarioCorrente(int idScenarioCorrente) {
        this.idScenarioCorrente = idScenarioCorrente;
    }   
    
}