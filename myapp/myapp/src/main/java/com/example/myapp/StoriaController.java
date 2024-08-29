package com.example.myapp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class StoriaController {

    // TODO: da popolare con le storie dal database
    private final List<Storia> tutteLeStorie = new ArrayList<>(); 
    
    public List<Storia> getStorieDisponibili(String searchTerm, String username, String lunghezza, String stato) {
        return tutteLeStorie.stream()
            .filter(storia -> (searchTerm == null || storia.getTitolo().toLowerCase().contains(searchTerm.toLowerCase())))
            .filter(storia -> (username == null || storia.getUsername().equals(username)))
            .filter(storia -> (lunghezza == null || matchesLunghezza(storia, lunghezza)))
            .filter(storia -> (stato == null || storia.getStato().equals(stato)))
            .collect(Collectors.toList());
    }

    private boolean matchesLunghezza(Storia storia, String lunghezza) {
        int numScenari = storia.getLunghezza();
        return switch (lunghezza) {
            case "0-5" -> numScenari >= 0 && numScenari <= 5;
            case "5-10" -> numScenari > 5 && numScenari <= 10;
            case "10+" -> numScenari > 10;
            default -> true;
        };
    }

    // Aggiungi questo metodo per recuperare storie dal database
    public Storia getStoriaById(int storiaId) {
        // Logica per recuperare una storia dal database o da una lista
        return tutteLeStorie.stream()
            .filter(storia -> storia.getId() == storiaId) // Assicurati di avere un metodo getId()
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Storia non trovata per l'ID: " + storiaId));
    }
}