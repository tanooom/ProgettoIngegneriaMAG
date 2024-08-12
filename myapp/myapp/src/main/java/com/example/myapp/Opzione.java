package com.example.myapp;

public class Opzione {
    private final String descrizione;
    private final Scenario scenarioSuccessivo;
    private final boolean richiedeOggetto;
    private final String oggettoRichiesto;
    private final int id;

    // Costruttore
    public Opzione(String descrizione, Scenario scenarioSuccessivo, boolean richiedeOggetto, String oggettoRichiesto, int id) {
        this.descrizione = descrizione;
        this.scenarioSuccessivo = scenarioSuccessivo;
        this.richiedeOggetto = richiedeOggetto;
        this.oggettoRichiesto = oggettoRichiesto;
        this.id = id;
    }

    // Getter
    public String getDescrizione() {
        return descrizione;
    }

    public Scenario getScenarioSuccessivo() {
        return scenarioSuccessivo;
    }

    public boolean isRichiedeOggetto() {
        return richiedeOggetto;
    }

    public String getOggettoRichiesto() {
        return oggettoRichiesto;
    }

    public int getId() {
        return id;
    }
}

