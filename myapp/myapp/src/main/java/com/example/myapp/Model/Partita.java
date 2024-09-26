package com.example.myapp.Model;

import com.example.myapp.Controller.InventarioController;

public class Partita {
    private final int id;
    private final Storia storia;
    private int scenarioCorrente;
    private final int inventarioId;
    private final String username; // Aggiunta per tenere traccia dell'utente
    private boolean inCorso; // Stato della partita (in corso o terminata)

    // Costruttore
    public Partita(int id, Storia storia, String username, int inventarioId) {
        this.id = id;
        this.storia = storia;
        this.scenarioCorrente = storia.getScenarioIniziale();
        this.inventarioId = inventarioId; // Inizializza un inventario vuoto
        this.username = username;
        this.inCorso = true; // La partita inizia in corso
    }

    // Metodo per fare una scelta
    public boolean faiScelta(Opzione opzione, InventarioController inventarioController) {
        // Usa l'ID per recuperare l'inventario
        Inventario inventario = inventarioController.getInventarioById(this.inventarioId);
        
        if (opzione.isRichiedeOggetto() && !inventario.contieneOggetto(opzione.getOggettoRichiesto())) {
            return false; // L'utente non ha l'oggetto richiesto
        }
        this.scenarioCorrente = opzione.getScenarioSuccessivo();
        return true;
    }

    // Metodo per raccogliere un oggetto
    public void raccogliOggetto(String oggetto, InventarioController inventarioManager) {
        Inventario inventario = inventarioManager.getInventarioById(this.inventarioId);
        inventario.aggiungiOggetto(oggetto); // Aggiungi l'oggetto all'inventario
    }

    // Getter
    public int getScenarioCorrente() {
        return scenarioCorrente;
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

    public boolean isInCorso() {
        return inCorso; // Getter per ottenere lo stato della partita
    }

    public void terminaPartita() {
        this.inCorso = false; // Metodo per terminare la partita
    }
}