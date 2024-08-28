package com.example.myapp;

import java.util.ArrayList;
import java.util.List;

public class Gioco {
    
    private final Storia storia;
    private Scenario scenarioCorrente;
    private final List<String> inventario;

    // Costruttore
    public Gioco(Storia storia) {
        this.storia = storia;
        this.scenarioCorrente = storia.getScenarioIniziale();
        this.inventario = new ArrayList<>();
    }

    // Metodo per fare una scelta
    public boolean faiScelta(Opzione opzione) {
        if (opzione.isRichiedeOggetto() && !inventario.contains(opzione.getOggettoRichiesto())) {
            return false; // L'utente non ha l'oggetto richiesto
        }
        this.scenarioCorrente = opzione.getScenarioSuccessivo();
        return true;
    }

    // Metodo per raccogliere un oggetto
    public void raccogliOggetto(String oggetto) {
        this.inventario.add(oggetto);
    }

    // Getter
    public Scenario getScenarioCorrente() {
        return scenarioCorrente;
    }

    public List<String> getInventario() {
        return inventario;
    }

    public Storia getStoria() {
        return this.storia;
    }
}