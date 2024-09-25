package com.example.myapp.Model;

import java.util.ArrayList;
import java.util.List;

public class Scenario {
    private final int id;
    private final String nome;
    private final String descrizione;
    private final List<Opzione> opzioni; //TODO: cambia con id
    private final List<String> oggettiRaccoglibili;
    private final List<Scenario> entryScenari; //TODO: Scenario precedente DA FARE CON ID
    private final List<Scenario> exitScenari; //TODO: Scenario successivo DA FARE CON ID
    private Storia storia; //TODO: Storia a cui appartiene lo scenario DA FARE CON ID
    //private final boolean scenarioIniziale=false;
    //stessa cosa sopra per finali
    //TODO: valore boolean true o false per scenario iniziale o finale

    // Costruttore
    public Scenario(int id, String nome, String descrizione) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.opzioni = new ArrayList<>();
        this.oggettiRaccoglibili = new ArrayList<>();
        this.entryScenari = new ArrayList<>();
        this.exitScenari = new ArrayList<>();
    }

    // Metodi per aggiungere opzioni
    public void aggiungiOpzione(Opzione opzione) {
        this.opzioni.add(opzione);
    }

    public void aggiungiOggettoRaccoglibile(String oggetto) {
        this.oggettiRaccoglibili.add(oggetto);
    }

    // Aggiungi scenario precedente (EntryScenario)
    public void aggiungiEntryScenario(Scenario scenarioPrecedente) {
        if (!this.entryScenari.contains(scenarioPrecedente)) {
            this.entryScenari.add(scenarioPrecedente);
        }
    }

    // Aggiungi scenario successivo (ExitScenario)
    public void aggiungiExitScenario(Scenario scenarioSuccessivo) {
        if (!this.exitScenari.contains(scenarioSuccessivo)) {
            this.exitScenari.add(scenarioSuccessivo);
        }
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

    public Storia getStoria() {
        return storia;
    }

    public List<Scenario> getEntryScenari() {
        return entryScenari;
    }

    public List<Scenario> getExitScenari() {
        return exitScenari;
    }

    public Boolean setScenarioIniziale(){
        return true;
    }

    @Override
    public String toString() {
        return "Scenario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", opzioni=" + opzioni +
                ", oggettiRaccoglibili=" + oggettiRaccoglibili +
                ", EntryScenari=" + entryScenari +
                ", ExitScenari=" + exitScenari +
                '}';
    }
}