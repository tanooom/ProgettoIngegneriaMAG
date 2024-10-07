package com.example.myapp.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Scenario implements Serializable{
    private static final long serialVersionUID = 1308912331969162728L;

    private final int id;
    private final String nome;
    private final String descrizione;
    private final List<Integer> idOpzioni;
    private final String oggettoRaccoglibile;
    private final Integer idScenarioPrecedente; // Scenario precedente
    private final List<Integer> idExitScenari; // Scenario successivo
    private final boolean scenarioIniziale; // Se true è lo scenario iniziale
    private final boolean scenarioFinale; // Se true è uno scenario finale

    public Scenario(int id, String nome, String descrizione, List<Integer> idOpzioni, String oggettoRaccoglibile, 
    Integer idScenarioPrecedente, List<Integer> idExitScenari, boolean scenarioIniziale, boolean scenarioFinale) {

    this.id = id;
    this.nome = nome;
    this.descrizione = descrizione;
    this.idOpzioni = idOpzioni != null ? idOpzioni : new ArrayList<>();
    this.oggettoRaccoglibile = oggettoRaccoglibile;
    this.idScenarioPrecedente = idScenarioPrecedente;
    this.idExitScenari = idExitScenari != null ? idExitScenari : new ArrayList<>();
    this.scenarioIniziale = scenarioIniziale;
    this.scenarioFinale = scenarioFinale;
    }

    // Metodi per aggiungere opzioni
    public void aggiungiOpzione(int opzioneId) {
        this.idOpzioni.add(opzioneId);
    }

    /*public void aggiungiOggettoRaccoglibile(String oggetto) {
        this.oggettiRaccoglibili.add(oggetto);
    }*/

    //COMMENTATO, NON SO SE QUESTO METODO SERVE
    /*// Aggiungi scenario precedente (EntryScenario)
    public void aggiungiIdEntryScenario(int idScenarioPrecedente) {
        if (!this.idEntryScenari.contains(idScenarioPrecedente)) {
            this.idEntryScenari.add(idScenarioPrecedente);
        }
    }*/

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
        return idOpzioni;
    }
    
    public String getOggettoRaccoglibile() {
        return oggettoRaccoglibile;
    }

    public Integer getIdScenarioPrecedente() {
        return idScenarioPrecedente;
    }

    public List<Integer> getIdExitScenari() {
        return idExitScenari;
    }

    // Metodo per controllare se lo scenario è iniziale
    public boolean isScenarioIniziale() {
        return this.scenarioIniziale;
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
                ", idOpzioni=" + idOpzioni +
                ", oggettoRaccoglibile=" + oggettoRaccoglibile +
                ", idScenarioPrecedente=" + idScenarioPrecedente +
                ", ExitScenari=" + idExitScenari +
                ", scenarioIniziale=" + scenarioIniziale +
                ", scenarioFinale=" + scenarioFinale +
             '}';
    }
}
