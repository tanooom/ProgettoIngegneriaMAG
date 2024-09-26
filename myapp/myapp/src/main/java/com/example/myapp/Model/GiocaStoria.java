package com.example.myapp.Model;

import java.util.Scanner;

import com.example.myapp.Controller.MapDBController;

public class GiocaStoria {
    private final Storia storia;
    private final MapDBController mapDBController;

    public GiocaStoria(Storia storia, MapDBController mapDBController) {
        this.storia = storia;
        this.mapDBController = mapDBController;
    }

    public void inizia() {
        int scenarioInizialeId = storia.getScenarioIniziale();
        Scenario scenarioCorrente = mapDBController.getScenarioById(scenarioInizialeId);
        
        try (Scanner scanner = new Scanner(System.in)) {
            while (scenarioCorrente != null) {
                // Stampa la descrizione dello scenario
                System.out.println(scenarioCorrente.getDescrizione());
                
                // Se ci sono oggetti raccoglibili, mostrali
                if (!scenarioCorrente.getOggettiRaccoglibili().isEmpty()) {
                    System.out.println("Oggetti disponibili:");
                    for (String oggetto : scenarioCorrente.getOggettiRaccoglibili()) {
                        System.out.println("- " + oggetto);
                    }
                }
                
                // Mostra le opzioni disponibili
                int i = 1;
                for (Integer opzioneId : scenarioCorrente.getOpzioni()) {
                    Opzione opzione = mapDBController.getOption(opzioneId);
                    System.out.println(i + ". " + opzione.getDescrizione());
                    i++;
                }
                
                // Chiedi all'utente di fare una scelta
                System.out.print("Scegli un'opzione: ");
                int scelta = scanner.nextInt();
                
                // Ottieni l'opzione scelta
                Opzione opzioneScelta = mapDBController.getOption(scenarioCorrente.getOpzioni().get(scelta - 1));

                // Verifica se l'opzione richiede un oggetto
                if (opzioneScelta.isRichiedeOggetto()) {
                    System.out.print("Per continuare hai bisogno di: " + opzioneScelta.getOggettoRichiesto() + ". Ce l'hai? (s/n): ");
                    String risposta = scanner.next();
                    if (!risposta.equalsIgnoreCase("s")) {
                        System.out.println("Non puoi procedere senza l'oggetto richiesto.");
                        continue; // Rimane nello stesso scenario
                    }
                }
                
                // Verifica se l'opzione richiede di rispondere a un indovinello
                if (opzioneScelta.isRichiedeIndovinello()) {
                    System.out.print("Rispondi all'indovinello: " + opzioneScelta.getIndovinello() + ": ");
                    String rispostaIndovinello = scanner.next();
                    
                    // Confronta la risposta dell'utente con quella corretta
                    if (!rispostaIndovinello.equalsIgnoreCase(opzioneScelta.getRispostaCorrettaIndovinello())) {
                        System.out.println("Risposta errata! Riprova.");
                        continue; // Rimane nello stesso scenario
                    }
                }
                
                // Passa allo scenario successivo
                scenarioCorrente = mapDBController.getScenarioById(opzioneScelta.getScenarioSuccessivo());

                // Verifica se lo scenario corrente è un finale e termina la partita
                if (scenarioCorrente != null && scenarioCorrente.isScenarioFinale()) {
                    System.out.println("Fine della storia! Hai raggiunto un finale.");
                    break;
                }
            }
        }
    }
}