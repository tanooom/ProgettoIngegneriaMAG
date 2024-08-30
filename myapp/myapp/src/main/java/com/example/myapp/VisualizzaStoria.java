package com.example.myapp;

public class VisualizzaStoria {
    private final Storia storia;

    public VisualizzaStoria(Storia storia) {
        this.storia = storia;
    }

    public void mostraDettagli() {
        System.out.println("Titolo: " + storia.getTitolo());
        System.out.println("Inizio: " + storia.getScenarioIniziale().getDescrizione());
        System.out.println("Finali disponibili:");
        for (Scenario finale : storia.getFinali()) {
            System.out.println("- " + finale.getDescrizione());
        }
    }
}