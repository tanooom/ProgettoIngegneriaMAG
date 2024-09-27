package com.example.myapp.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.myapp.Model.Opzione;
import com.example.myapp.Model.Scenario;
import com.example.myapp.Model.Storia;

@Service
public class StoriaService {

    // TODO: da popolare con le storie dal database
    private final List<Storia> tutteLeStorie = new ArrayList<>();

    public Storia creaStoria(Storia storia) {
        // Logica per assegnare un ID unico alla storia e salvarla nel database (es. MapDB)
        storia.setId(tutteLeStorie.size() + 1);
        tutteLeStorie.add(storia);
        
        // Eventuale logica per salvare su MapDB usando MapDBService
        // mapDBService.saveStory(storia); 

        return storia;
    }
    
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

    // Recupera storie dal database
    public Storia getStoriaById(int storiaId) {
        // TODO: Logica per recuperare una storia dal database o da una lista
        return tutteLeStorie.stream()
            .filter(storia -> storia.getId() == storiaId) // Assicurati di avere un metodo getId()
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Storia non trovata per l'ID: " + storiaId));
    }

    public Scenario getScenarioById(int storiaId, int scenarioId) {
        Storia storia = getStoriaById(storiaId);
        return storia.getIdScenari().stream()
            .filter(scenarioIdFromList -> scenarioIdFromList == scenarioId) // Cambiato per confrontare con Integer
            .map(scenarioIdFromList -> {
                return new Scenario(scenarioIdFromList, "NomeScenario", "DescrizioneScenario"); // Sostituisci con il tuo metodo di recupero
            })
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Scenario non trovato per l'ID: " + scenarioId));
    }
    
    public Opzione getOpzioneById(int storiaId, int scenarioId, int opzioneId) {
        Scenario scenario = getScenarioById(storiaId, scenarioId);
        return scenario.getOpzioni().stream()
            .filter(opzioneIdFromList -> opzioneIdFromList == opzioneId) // Cambiato per confrontare con Integer
            .map(opzioneIdFromList -> {
                return new Opzione(opzioneIdFromList, "DescrizioneOpzione", false, null, false, null, null, false, null); // Sostituisci con il tuo metodo di recupero
            })
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Opzione non trovata per l'ID: " + opzioneId));
    }

    // Ottiene una lista di tutte le storie
    public List<Storia> getAllStorie() {
        return new ArrayList<>(tutteLeStorie); // Ritorna la lista completa di storie
    }
}