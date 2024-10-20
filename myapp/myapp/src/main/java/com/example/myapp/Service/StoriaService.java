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
    private MapDBService mapDBService; 

    @PostConstruct
    public void init() {
        tutteLeStorie.addAll(mapDBService.getAllStories());
    }

    public Storia creaStoria(Storia storia) {

        storia.setId(tutteLeStorie.size() + 1);
        tutteLeStorie.add(storia);
        
        mapDBService.saveStory(storia); 
        return storia;
    }
    
    public List<Storia> getStorieDisponibili(String searchTerm, String username, String lunghezza, String stato) {
        return tutteLeStorie.stream()
            .filter(storia -> (searchTerm == null || storia.getTitolo().toLowerCase().contains(searchTerm.toLowerCase())))
            .filter(storia -> (username == null || username.isEmpty() || storia.getUsername().equalsIgnoreCase(username)))
            .filter(storia -> (lunghezza == null || matchesLunghezza(storia, lunghezza)))
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
            .filter(storia -> storia.getId() == storiaId)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Storia non trovata per l'ID: " + storiaId));
    }
    
    public Opzione getOpzioneById(int scenarioId, int opzioneId) {
        Scenario scenario = mapDBService.getScenarioById(scenarioId);

        if (scenario == null) {
            throw new RuntimeException("Scenario non trovato per l'ID: " + scenarioId);
        }
        if (!scenario.getOpzioni().contains(opzioneId)) {
            throw new RuntimeException("Opzione non trovata per l'ID: " + opzioneId + " nello scenario ID: " + scenarioId);
        }
        Opzione opzioneCompleta = mapDBService.getOptionById(opzioneId);
        if (opzioneCompleta == null) {
            throw new RuntimeException("Opzione non trovata per l'ID: " + opzioneId);
        }
        return opzioneCompleta;
    }

    // Ottiene una lista di tutte le storie
    public List<Storia> getAllStorie() {
        return new ArrayList<>(tutteLeStorie); 
    }

    public List<String> getAllUsernames() {
        return tutteLeStorie.stream()
            .map(Storia::getUsername)
            .distinct()
            .collect(Collectors.toList());
    }    

    public String getDescrizioneScenarioIniziale(int storiaId) {
        Storia storia = getStoriaById(storiaId);
        int idScenarioIniziale = storia.getIdScenarioIniziale();
        Scenario scenarioIniziale = mapDBService.getScenarioById(idScenarioIniziale);
        return scenarioIniziale.getDescrizione();
    }

    public List<Storia> getStorieByUsername(String username) {
        return mapDBService.getAllStorie().values().stream()
            .filter(storia -> storia.getUsername().equals(username))
            .collect(Collectors.toList());
    }
}