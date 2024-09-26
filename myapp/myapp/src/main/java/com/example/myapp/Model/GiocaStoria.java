package com.example.myapp.Model;

import java.util.Scanner;

public class GiocaStoria {
    private final Storia storia;

    public GiocaStoria(Storia storia) {
        this.storia = storia;
    }

    public void inizia() {
        Scenario scenarioCorrente = storia.getScenarioIniziale();
        
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
                    Opzione opzione = storia.getOpzioneById(opzioneId);
                    System.out.println(i + ". " + opzione.getDescrizione());
                    i++;
                }
                
                // Chiedi all'utente di fare una scelta
                System.out.print("Scegli un'opzione: ");
                int scelta = scanner.nextInt();
                
                // Ottieni l'opzione scelta
                Opzione opzioneScelta = storia.getOpzioneById(scenarioCorrente.getOpzioni().get(scelta - 1));
                
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
                scenarioCorrente = storia.getScenarioById(opzioneScelta.getScenarioSuccessivo());

                
                // Se lo scenario corrente è un finale, termina il gioco
                if (storia.getFinali().contains(scenarioCorrente)) {
                    System.out.println("Fine della storia.");
                    break;
                }
            }
        }
    }
}