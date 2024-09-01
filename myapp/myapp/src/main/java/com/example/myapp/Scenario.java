package com.example.myapp;

import java.util.ArrayList;
import java.util.List;

public class Scenario {
    private final int id;
    private final String descrizione;
    private final List<Opzione> opzioni;
    private final List<String> oggettiRaccoglibili;

    // Costruttore
    public Scenario(int id, String descrizione) {
        this.id = id;
        this.descrizione = descrizione;
        this.opzioni = new ArrayList<>();
        this.oggettiRaccoglibili = new ArrayList<>();
    }

    // TODO: da aggiungere scenari precedenti e scenari successivi e la storia a cui appartiene

    // Metodi per aggiungere opzioni
    public void aggiungiOpzione(Opzione opzione) {
        this.opzioni.add(opzione);
    }

    public void aggiungiOggettoRaccoglibile(String oggetto) {
        this.oggettiRaccoglibili.add(oggetto);
    }

    // Getter
    public int getId() {
        return id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public List<Opzione> getOpzioni() {
        return opzioni;
    }
    
    public List<String> getOggettiRaccoglibili() {
        return oggettiRaccoglibili;
    }
}