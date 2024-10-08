package com.example.myapp.Model;

import com.example.myapp.Controller.InventarioController;

public class Partita {
    private final int id; //autoincrement
    private final Storia storia; //me lo prende da storiaId
    private final int idScenarioCorrente; //uguale 
    private final int inventarioId; //me lo crea dentro api/.../start
    private final String username; // prende l'username dell'utente che sta giocando
    private boolean inCorso; // Stato della partita (Non inziata, In corso o Terminata)
    //TODO: togliere lo stato da storia, modificare inCorso, chiamarlo "stato", deve essere String

    // Costruttore
    public Partita(int id, Storia storia, String username, int inventarioId) {
        this.id = id;
        this.storia = storia;
        this.idScenarioCorrente = storia.getIdScenarioIniziale();
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
        //this.idScenarioCorrente = opzione.getIdScenarioSuccessivo();
        //Opzione non ha piu idScenarioSuccessivo
        return true;
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

    public boolean isInCorso() {
        return inCorso; // Getter per ottenere lo stato della partita
    }

    public void terminaPartita() {
        this.inCorso = false; // Metodo per terminare la partita
    }
}