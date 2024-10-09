package com.example.myapp.Model;

import java.io.Serializable;

public class Opzione implements Serializable{
    //private static final long serialVersionUID = 1L; // Cambia il valore se necessario

    private final int id;
    private final String descrizione;
    //private final int idScenarioSuccessivo;
    private final boolean richiedeOggetto;
    private final String oggettoRichiesto;
    private final boolean richiedeIndovinello;
    private final String indovinello;
    private final String rispostaCorrettaIndovinello;

    public Opzione(int id, String descrizione, boolean richiedeOggetto, String oggettoRichiesto, boolean richiedeIndovinello, String indovinello, String rispostaCorrettaIndovinello) {
        this.id = id;
        this.descrizione = descrizione;
        //this.idScenarioSuccessivo = idScenarioSuccessivo;
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

    /*public int getIdScenarioSuccessivo() {
        return idScenarioSuccessivo;
    }*/

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