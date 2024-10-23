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
        return null; // Se non trova lo scenario successivo, restituisce null.
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public List<Integer> getOpzioni() {
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

    public void setIdExitScenari(List<Integer> idExitScenari) {
        this.idExitScenari = idExitScenari;
    }

    public boolean isScenarioIniziale() {
        return this.scenarioIniziale;
    }

    public boolean isScenarioFinale() {
        return this.idExitScenari.isEmpty();
    }

    @Override
    public String toString() {
        return "Scenario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", idOpzioni=" + idOpzioni +
                ", oggettoRaccoglibile=" + oggettoRaccoglibile +
                ", idScenarioPrecedente=" + idScenarioPrecedente +
                ", idOpzionePrecedente=" + idOpzionePrecedente +
                ", ExitScenari=" + idExitScenari +
                ", scenarioIniziale=" + scenarioIniziale +
                ", scenarioFinale=" + scenarioFinale +
            '}';
    };
}