package com.example.myapp.Model;

public class Partita {
    private final int id;
    private final Storia storia;
    private Scenario scenarioCorrente; //TODO: cambia con id
    private final Inventario inventario; //TODO: metere id inventario
    private final String username; // Aggiunta per tenere traccia dell'utente
    private boolean inCorso; // Stato della partita (in corso o terminata)

    // Costruttore
    public Partita(int id, Storia storia, String username) {
        this.id = id;
        this.storia = storia;
        this.scenarioCorrente = storia.getScenarioIniziale();
        this.inventario = new Inventario(); // Inizializza un inventario vuoto
        this.username = username;
        this.inCorso = true; // La partita inizia in corso
    }

    // Metodo per fare una scelta
    public boolean faiScelta(Opzione opzione) {
        if (opzione.isRichiedeOggetto() && !inventario.contieneOggetto(opzione.getOggettoRichiesto())) {
            return false; // L'utente non ha l'oggetto richiesto
        }
        this.scenarioCorrente = opzione.getScenarioSuccessivo();
        return true;
    }

    // Metodo per raccogliere un oggetto
    public void raccogliOggetto(String oggetto) {
        this.inventario.aggiungiOggetto(oggetto);
    }

    // Getter
    public Scenario getScenarioCorrente() {
        return scenarioCorrente;
    }

    public Inventario getInventario() {
        return inventario;
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

    public void terminaGioco() {
        this.inCorso = false; // Metodo per terminare la partita
    }
}