package com.example.myapp.Model;

public class Opzione {
    private final int id;
    private final String descrizione;
    private final int scenarioSuccessivo;
    private final boolean richiedeOggetto;
    private final String oggettoRichiesto;
    private final boolean richiedeIndovinello;
    private final String indovinello;
    private final String rispostaCorrettaIndovinello;

    // Costruttore
    public Opzione(int id, String descrizione, int scenarioSuccessivo, boolean richiedeOggetto, String oggettoRichiesto, boolean richiedeIndovinello, String indovinello, String rispostaCorrettaIndovinello) {
        this.id = id;
        this.descrizione = descrizione;
        this.scenarioSuccessivo = scenarioSuccessivo;
        this.richiedeOggetto = richiedeOggetto;
        this.oggettoRichiesto = oggettoRichiesto;
        this.indovinello = indovinello;
        this.richiedeIndovinello = richiedeIndovinello;
        this.rispostaCorrettaIndovinello = rispostaCorrettaIndovinello; // Inizializzazione della risposta corretta
    }

    // Getter
    public String getDescrizione() {
        return descrizione;
    }

    public int getScenarioSuccessivo() {
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

    public String getRispostaCorrettaIndovinello() {
        return rispostaCorrettaIndovinello;
    }
}