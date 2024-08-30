package com.example.myapp;

import java.util.Scanner;

public class GiocaStoria {
    private final Storia storia;
    private final Scanner scanner;

    public GiocaStoria(Storia storia) {
        this.storia = storia;
        this.scanner = new Scanner(System.in);
    }

    public void inizia() {
        Scenario scenarioAttuale = storia.getScenarioIniziale();
        while (true) {
            System.out.println("\n" + scenarioAttuale.getDescrizione());
            System.out.println("Scegli un'opzione:");

            for (Opzione opzione : scenarioAttuale.getOpzioni()) {
                System.out.println(opzione.getId() + ": " + opzione.getDescrizione());
            }

            System.out.print("Inserisci il numero dell'opzione: ");
            int scelta = scanner.nextInt();

            // Controllo se l'opzione scelta esiste
            if (scelta >= 1 && scelta <= scenarioAttuale.getOpzioni().size()) {
                scenarioAttuale = scenarioAttuale.getOpzioni().get(scelta - 1).getScenarioSuccessivo();
            } else {
                System.out.println("Opzione non valida. Riprova.");
            }

            // Condizione per terminare il gioco se si raggiunge un finale
            if (storia.getFinali().contains(scenarioAttuale)) {
                System.out.println("\nHai raggiunto un finale: " + scenarioAttuale.getDescrizione());
                break;
            }
        }
    }
}