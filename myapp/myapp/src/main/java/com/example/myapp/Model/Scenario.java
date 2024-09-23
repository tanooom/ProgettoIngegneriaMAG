package com.example.myapp.Model;

import java.util.ArrayList;
import java.util.List;

public class Scenario {
    private final int id;
    private final String nome;
    private final String descrizione;
    private final List<Opzione> opzioni;
    private final List<String> oggettiRaccoglibili;
    private final List<Scenario> EntryScenari;
    private final List<Scenario> ExitScenari;

    // Costruttore
    public Scenario(int id, String nome, String descrizione) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.opzioni = new ArrayList<>();
        this.oggettiRaccoglibili = new ArrayList<>();
        this.EntryScenari = new ArrayList<>();
        this.ExitScenari = new ArrayList<>();
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

    public String getNome() {
        return nome;
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

    @Override
    public String toString() {
        return "Scenario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", opzioni=" + opzioni +
                ", oggettiRaccoglibili=" + oggettiRaccoglibili +
                ", EntryScenari=" + EntryScenari +
                ", ExitScenari=" + ExitScenari +
                '}';
    }
}