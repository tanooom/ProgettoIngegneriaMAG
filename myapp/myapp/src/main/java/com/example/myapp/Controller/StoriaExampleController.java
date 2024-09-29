package com.example.myapp.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.myapp.Model.Scenario;
import com.example.myapp.Model.Storia;
import com.example.myapp.Service.MapDBService;

@Controller
public class StoriaExampleController {

    @Autowired
    private final MapDBService mapDBService;

    public StoriaExampleController(MapDBService mapDBService) {
        this.mapDBService = mapDBService;
    }

    // Funzione per visualizzare la pagina HTML con la storia fittizia
    @GetMapping("/storia_example")
    public String creaStoriaExample(Model model) {
        // Crea una storia fittizia con valori predefiniti
        Storia nuovaStoria = new Storia(
                1,
                "La grande avventura",
                "Sempronio",
                3,
                "Non iniziato"
        );

        // Crea scenari fittizi
        Scenario primoScenario = new Scenario(101, "Scenario 1", "C'era una volta...");
        Scenario secondoScenario = new Scenario(102, "Scenario 2", "Gira a destra");
        Scenario terzoScenario = new Scenario(103, "Scenario 3", "Gira a sinistra");

        // Aggiungi gli ID degli scenari alla storia
        nuovaStoria.aggiungiScenario(primoScenario.getId());
        nuovaStoria.aggiungiScenario(secondoScenario.getId());
        nuovaStoria.aggiungiScenario(terzoScenario.getId());
        nuovaStoria.setIdScenarioIniziale(primoScenario.getId());

        // Salva la storia e gli scenari nel database
        mapDBService.saveStory(nuovaStoria);
        mapDBService.saveScenario(primoScenario);
        mapDBService.saveScenario(secondoScenario);
        mapDBService.saveScenario(terzoScenario);

        // Prepara i dati per il template HTML
        model.addAttribute("storia", nuovaStoria);
        return "storia_example"; // Riferimento alla pagina HTML storia_example.html
    }

    // Funzione per restituire i dati della storia fittizia in formato JSON
    @GetMapping("/api/storia_example")
    public ResponseEntity<Map<String, Object>> getStoriaExample() {
        // Crea una storia fittizia con valori predefiniti
        Storia nuovaStoria = new Storia(
                1, 
                "La grande avventura",
                "Sempronio",
                3,
                "Non iniziato"
        );

        // Crea scenari fittizi
        Scenario primoScenario = new Scenario(101, "Scenario 1", "C'era una volta...");
        Scenario secondoScenario = new Scenario(102, "Scenario 2", "Gira a destra");
        Scenario terzoScenario = new Scenario(103, "Scenario 3", "Gira a sinistra");

        // Aggiungi gli scenari alla storia
        nuovaStoria.aggiungiScenario(primoScenario.getId());
        nuovaStoria.aggiungiScenario(secondoScenario.getId());
        nuovaStoria.aggiungiScenario(terzoScenario.getId());
        nuovaStoria.setIdScenarioIniziale(primoScenario.getId());

        // Creiamo una struttura dati per passare la storia e gli scenari insieme
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("id", nuovaStoria.getId());
        responseData.put("titolo", nuovaStoria.getTitolo());
        responseData.put("username", nuovaStoria.getUsername());
        responseData.put("lunghezza", nuovaStoria.getLunghezza());
        responseData.put("stato", nuovaStoria.getStato());
        
        // Creiamo una lista di scenari (dati completi, non solo gli ID)
        List<Map<String, String>> scenariData = new ArrayList<>();
        scenariData.add(createScenarioMap(primoScenario));
        scenariData.add(createScenarioMap(secondoScenario));
        scenariData.add(createScenarioMap(terzoScenario));

        responseData.put("scenari", scenariData);

        return ResponseEntity.ok(responseData);
    }

    // Metodo di utilità per convertire uno scenario in una mappa
    private Map<String, String> createScenarioMap(Scenario scenario) {
        Map<String, String> scenarioMap = new HashMap<>();
        scenarioMap.put("id", String.valueOf(scenario.getId()));
        scenarioMap.put("nome", scenario.getNome());
        scenarioMap.put("descrizione", scenario.getDescrizione());
        return scenarioMap;
    }
}