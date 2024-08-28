package com.example.myapp;

import java.util.ArrayList;
import java.util.List;

public class Scenario {
    private final int id;
    private final String descrizione;
    private final List<Opzione> opzioni;

    // Costruttore
    public Scenario(int id, String descrizione) {
        this.id = id;
        this.descrizione = descrizione;
        this.opzioni = new ArrayList<>();
    }

    //da aggiungere scenari precedenti e scenari successivi e la storia a cui appartiene

    // Metodi per aggiungere opzioni
    public void aggiungiOpzione(Opzione opzione) {
        this.opzioni.add(opzione);
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
}