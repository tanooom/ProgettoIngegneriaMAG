package com.example.myapp.Model;

public class Main {
    public static void main(String[] args) {
        // Instanzia la storia
        Storia storia1 = creaStoria();

        // Inizia il gioco
        GiocaStoria gioco = new GiocaStoria(storia1);
        gioco.inizia();
    }

    public static Storia creaStoria() {
        // Creiamo gli scenari
        Scenario scenario1 = new Scenario(1, "C'era una volta...");
        Scenario scenario2 = new Scenario(2, "Sei arrivato alla porta, ma è chiusa.");
        Scenario scenario3 = new Scenario(3, "Indovinello: di che colore è il cavallo bianco di Napoleone?");
        Scenario scenario4 = new Scenario(4, "Hai aperto la porta e proseguito.");
    
        // Definizione delle opzioni per lo scenario 1
        Opzione opzioneA = new Opzione("Vai alla porta", scenario2, false, null, 1, false, null, null);
        Opzione opzioneB = new Opzione("Rispondi all'indovinello", scenario3, false, null, 2, false, null, null);
        scenario1.aggiungiOpzione(opzioneA);
        scenario1.aggiungiOpzione(opzioneB);
    
        // Definizione dell'opzione per lo scenario 2 (richiede la chiave)
        Opzione apriPorta = new Opzione("Apri la porta", scenario4, true, "chiave", 3, false, null, null);
        scenario2.aggiungiOpzione(apriPorta);
    
        // Definizione delle opzioni per lo scenario 3 (indovinello)
        Opzione rispostaBianca = new Opzione("Bianco", scenario4, false, null, 4, true, "di che colore è il cavallo bianco di Napoleone?", "bianco");
        Opzione rispostaNera = new Opzione("Nero", scenario4, false, null, 5, true, "di che colore è il cavallo bianco di Napoleone?", "bianco");
        scenario3.aggiungiOpzione(rispostaBianca);
        scenario3.aggiungiOpzione(rispostaNera);
    
        // Creazione della storia
        Storia storia1 = new Storia(1, "Storia 1", scenario1, "autore", 4, "in corso");
        storia1.aggiungiScenario(scenario2);
        storia1.aggiungiScenario(scenario3);
        storia1.aggiungiScenario(scenario4);
        storia1.aggiungiFinale(scenario4);
    
        return storia1;
    }
    
}