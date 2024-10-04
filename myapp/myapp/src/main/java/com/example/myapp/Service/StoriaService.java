package com.example.myapp.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.Model.Opzione;
import com.example.myapp.Model.Scenario;
import com.example.myapp.Model.Storia;

@Service
public class StoriaService {

    private final List<Storia> tutteLeStorie = new ArrayList<>();

    @Autowired
    private MapDBService mapDBService; // Assicurati di iniettare il servizio MapDB

    @PostConstruct
    public void init() {
        // Recupera tutte le storie da MapDB e popola tutteLeStorie
        tutteLeStorie.addAll(mapDBService.getAllStories());
    }

    public Storia creaStoria(Storia storia) {
        // Logica per assegnare un ID unico alla storia e salvarla nel database (es. MapDB)
        storia.setId(tutteLeStorie.size() + 1);
        tutteLeStorie.add(storia);
        
        // Eventuale logica per salvare su MapDB usando MapDBService
        mapDBService.saveStory(storia); 
        return storia;
    }
    
    public List<Storia> getStorieDisponibili(String searchTerm, String username, String lunghezza, String stato) {
        return tutteLeStorie.stream()
            .filter(storia -> (searchTerm == null || storia.getTitolo().toLowerCase().contains(searchTerm.toLowerCase())))
            .filter(storia -> (username == null || username.isEmpty() || storia.getUsername().equalsIgnoreCase(username)))
            .filter(storia -> (lunghezza == null || matchesLunghezza(storia, lunghezza)))
            .filter(storia -> (stato == null || stato.isEmpty() || storia.getStato().equalsIgnoreCase(stato)))
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
        return tutteLeStorie.stream()
            .filter(storia -> storia.getId() == storiaId) // Assicurati di avere un metodo getId()
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Storia non trovata per l'ID: " + storiaId));
    }
    
    public Opzione getOpzioneById(int storiaId, int scenarioId, int opzioneId) {
        Scenario scenario = mapDBService.getScenarioById(scenarioId);
        return scenario.getOpzioni().stream()
            .filter(opzioneIdFromList -> opzioneIdFromList == opzioneId) // Cambiato per confrontare con Integer
            .map(opzioneIdFromList -> {
                return new Opzione(opzioneIdFromList, "DescrizioneOpzione", false, null, false, null, null); // Sostituisci con il tuo metodo di recupero
            })
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Opzione non trovata per l'ID: " + opzioneId));
    }

    // Ottiene una lista di tutte le storie
    public List<Storia> getAllStorie() {
        System.out.println("TUTTE LE STORIE: " + tutteLeStorie.size());
        return new ArrayList<>(tutteLeStorie); // Ritorna la lista completa di storie
    }

    public List<String> getAllUsernames() {
        return tutteLeStorie.stream()
            .map(Storia::getUsername)
            .distinct()
            .collect(Collectors.toList());
    }    

    public String getDescrizioneScenarioIniziale(int storiaId) {
        Storia storia = getStoriaById(storiaId);
        System.out.println("STORIA: " + storia);
        int idScenarioIniziale = storia.getIdScenarioIniziale();
        System.out.println("ID SCENARIO INIZIALE: " + idScenarioIniziale);
        Scenario scenarioIniziale = mapDBService.getScenarioById(idScenarioIniziale);
        System.out.println("SCENARIO INIZIALE: " + scenarioIniziale.getDescrizione());
        return scenarioIniziale.getDescrizione();
    }
}