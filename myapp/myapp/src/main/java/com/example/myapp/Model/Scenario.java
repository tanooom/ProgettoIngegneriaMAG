package com.example.myapp.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Scenario implements Serializable{
    private static final long serialVersionUID = 1308912331969162728L;

    private final int id;
    private final String nome;
    private String descrizione;
    private final List<Integer> idOpzioni;
    private final String oggettoRaccoglibile;
    private final Integer idScenarioPrecedente;
    private final Integer idOpzionePrecedente;
    private List<Integer> idExitScenari; 
    private final boolean scenarioIniziale;
    private final boolean scenarioFinale; 

    // Costruttore
    public Scenario(int id, String nome, String descrizione, List<Integer> idOpzioni, String oggettoRaccoglibile, 
    Integer idScenarioPrecedente, Integer idOpzionePrecedente, List<Integer> idExitScenari, boolean scenarioIniziale, boolean scenarioFinale) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.idOpzioni = idOpzioni != null ? idOpzioni : new ArrayList<>();
        this.oggettoRaccoglibile = oggettoRaccoglibile;
        this.idScenarioPrecedente = idScenarioPrecedente;
        this.idOpzionePrecedente = idOpzionePrecedente;
        this.idExitScenari = idExitScenari;
        this.scenarioIniziale = scenarioIniziale;
        this.scenarioFinale = scenarioFinale;
    }

    public void aggiungiOpzione(int opzioneId) {
        this.idOpzioni.add(opzioneId);
    }

    public void aggiungiIdExitScenario(int idScenarioSuccessivo) {
        if (!this.idExitScenari.contains(idScenarioSuccessivo)) {
            this.idExitScenari.add(idScenarioSuccessivo);
        }
    }

    public Scenario trovaProssimoScenario(List<Scenario> tuttiGliScenari, int idOpzione) {
        for (Scenario scenario : tuttiGliScenari) {
            if (scenario.getIdOpzionePrecedente() != null && scenario.getIdOpzionePrecedente() == idOpzione) {
                return scenario;
            }
        }
        return null;
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

    public List<Integer> getIdOpzioni() {
        return idOpzioni;
    }
    
    public String getOggettoRaccoglibile() {
        return oggettoRaccoglibile;
    }

    public Integer getIdScenarioPrecedente() {
        return idScenarioPrecedente;
    }

    public Integer getIdOpzionePrecedente() {
        return idOpzionePrecedente;
    }

    public List<Integer> getIdExitScenari() {
        return idExitScenari;
    }

    public boolean getScenarioFinale() {
        return scenarioFinale;
    }

    // Setter
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    
    public void setIdExitScenari(List<Integer> idExitScenari) {
        this.idExitScenari = idExitScenari;
    }

    public boolean isScenarioIniziale() {
        return this.scenarioIniziale;
    }

    public boolean isScenarioFinale() {
        return this.idExitScenari.isEmpty();
    }
}