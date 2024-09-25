package com.example.myapp.Model;

import java.util.ArrayList;
import java.util.List;

public class Scenario {
    private final int id;
    private final String nome;
    private final String descrizione;
    private final List<Integer> opzioni;
    private final List<String> oggettiRaccoglibili;
    private final List<Integer> entryScenari; // Scenario precedente
    private final List<Integer> exitScenari; // Scenario successivo
    private int storia; // Storia a cui appartiene lo scenario
    private final boolean scenarioIniziale = false; // Se true è lo scenario iniziale
    private final boolean scenarioFinale = false; // Se true è uno scenario finale

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
    public void aggiungiOpzione(int opzione) {
        this.opzioni.add(opzione);
    }

    public void aggiungiOggettoRaccoglibile(String oggetto) {
        this.oggettiRaccoglibili.add(oggetto);
    }

    // Aggiungi scenario precedente (EntryScenario)
    public void aggiungiEntryScenario(int scenarioPrecedente) {
        if (!this.entryScenari.contains(scenarioPrecedente)) {
            this.entryScenari.add(scenarioPrecedente);
        }
    }

    // Aggiungi scenario successivo (ExitScenario)
    public void aggiungiExitScenario(int scenarioSuccessivo) {
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

    public List<Integer> getOpzioni() {
        return opzioni;
    }
    
    public List<String> getOggettiRaccoglibili() {
        return oggettiRaccoglibili;
    }

    public int getStoria() {
        return storia;
    }

    public List<Integer> getEntryScenari() {
        return entryScenari;
    }

    public List<Integer> getExitScenari() {
        return exitScenari;
    }

    public void setStoria(int storia) {
        this.storia = storia;
    }

    // Metodo per controllare se lo scenario è iniziale
    public boolean isScenarioIniziale() {
        // Uno scenario è considerato iniziale se non ha scenari precedenti
        return this.entryScenari.isEmpty();
    }

    // Metodo per controllare se lo scenario è finale
    public boolean isScenarioFinale() {
        // Uno scenario è considerato finale se non ha scenari successivi
        return this.exitScenari.isEmpty();
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
                ", storiaId=" + storia +
                ", scenarioIniziale=" + scenarioIniziale +
                ", scenarioFinale=" + scenarioFinale +
                '}';
    }
}