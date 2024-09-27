package com.example.myapp.Model;

import java.util.ArrayList;
import java.util.List;

public class Scenario {
    private final int id;
    private final String nome;
    private final String descrizione;
    private final List<Integer> opzioni;
    private final List<String> oggettiRaccoglibili;
    private final List<Integer> idEntryScenari; // Scenario precedente
    private final List<Integer> idExitScenari; // Scenario successivo
    private int idStoria; // Storia a cui appartiene lo scenario
    private final boolean scenarioIniziale = false; // Se true è lo scenario iniziale
    private final boolean scenarioFinale = false; // Se true è uno scenario finale

    // Costruttore
    public Scenario(int id, String nome, String descrizione) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.opzioni = new ArrayList<>();
        this.oggettiRaccoglibili = new ArrayList<>();
        this.idEntryScenari = new ArrayList<>();
        this.idExitScenari = new ArrayList<>();
    }

    // Metodi per aggiungere opzioni
    public void aggiungiOpzione(int opzioneId) {
        this.opzioni.add(opzioneId);
    }

    public void aggiungiOggettoRaccoglibile(String oggetto) {
        this.oggettiRaccoglibili.add(oggetto);
    }

    // Aggiungi scenario precedente (EntryScenario)
    public void aggiungiIdEntryScenario(int idScenarioPrecedente) {
        if (!this.idEntryScenari.contains(idScenarioPrecedente)) {
            this.idEntryScenari.add(idScenarioPrecedente);
        }
    }

    // Aggiungi scenario successivo (ExitScenario)
    public void aggiungiIdExitScenario(int idScenarioSuccessivo) {
        if (!this.idExitScenari.contains(idScenarioSuccessivo)) {
            this.idExitScenari.add(idScenarioSuccessivo);
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

    public int getIdStoria() {
        return idStoria;
    }

    public List<Integer> getIdEntryScenari() {
        return idEntryScenari;
    }

    public List<Integer> getIdExitScenari() {
        return idExitScenari;
    }

    public void setIdStoria(int storia) {
        this.idStoria = storia;
    }

    // Metodo per controllare se lo scenario è iniziale
    public boolean isScenarioIniziale() {
        // Uno scenario è considerato iniziale se non ha scenari precedenti
        return this.idEntryScenari.isEmpty();
    }

    // Metodo per controllare se lo scenario è finale
    public boolean isScenarioFinale() {
        // Uno scenario è considerato finale se non ha scenari successivi
        return this.idExitScenari.isEmpty();
    }

    @Override
    public String toString() {
        return "Scenario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", opzioni=" + opzioni +
                ", oggettiRaccoglibili=" + oggettiRaccoglibili +
                ", EntryScenari=" + idEntryScenari +
                ", ExitScenari=" + idExitScenari +
                ", storiaId=" + idStoria +
                ", scenarioIniziale=" + scenarioIniziale +
                ", scenarioFinale=" + scenarioFinale +
                '}';
    }
}