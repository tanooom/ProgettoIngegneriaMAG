package com.example.myapp;

public class Opzione {
    private final String descrizione;
    private final Scenario scenarioSuccessivo;
    private final boolean richiedeOggetto;
    private final String oggettoRichiesto;
    private final int id;
    private final boolean richiedeIndovinello;
    private final String indovinello;

    // Costruttore
    public Opzione(String descrizione, Scenario scenarioSuccessivo, boolean richiedeOggetto, String oggettoRichiesto, int id, boolean richiedeIndovinello, String indovinello) {
        this.descrizione = descrizione;
        this.scenarioSuccessivo = scenarioSuccessivo;
        this.richiedeOggetto = richiedeOggetto;
        this.oggettoRichiesto = oggettoRichiesto;
        this.id = id;
        this.indovinello = indovinello;
        this.richiedeIndovinello = richiedeIndovinello;
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

    public boolean isRichiedeIndovinello() {
        return richiedeIndovinello;
    }

    public String getIndovinello() {
        return indovinello;
    }
}