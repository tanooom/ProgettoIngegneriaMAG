package com.example.myapp.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myapp.Model.Partita;
import com.example.myapp.Model.Scenario;
import com.example.myapp.Model.Storia;
import com.example.myapp.Model.Utente;
import com.example.myapp.Service.PartitaService;
import com.example.myapp.Service.StoriaService;
import com.example.myapp.Service.UserService;

@RestController
@RequestMapping("/api")
public class PartitaController {

    @Autowired
    private final PartitaService partitaService;

    @Autowired
    private final StoriaService storiaService;

    @Autowired
    private final UserService userService;

    @Autowired
    private final MapDBController mapDBController;

    // Costruttore
    public PartitaController(PartitaService partitaService, StoriaService storiaService, UserService userService, MapDBController mapDBController) {
        this.partitaService = partitaService;
        this.storiaService = storiaService;
        this.userService = userService;
        this.mapDBController = mapDBController;
    }

    @GetMapping("/storie-disponibili")
    public List<Storia> getStorieDisponibili() {
        return partitaService.getStorieDisponibili();
    }

    @GetMapping("/gioca/{storiaId}/start")
    public String giocaStoria(@PathVariable int storiaId, Model model) {
        System.out.printf("STORIA ID:", storiaId);
        Storia storia = storiaService.getStoriaById(storiaId);
        model.addAttribute("storia", storia);
        Scenario scenarioCorrente = mapDBController.getScenarioById(storia.getIdScenarioIniziale());
        model.addAttribute("scenario", scenarioCorrente);
        return "giocaStoria"; 
    }

    @GetMapping("/gioca/{storiaId}/scenario")
    public Map<String, Object> caricaScenario(@PathVariable int storiaId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Utente user = userService.getUser(username);
        Partita partita = partitaService.caricaPartita(storiaId, user);
        Map<String, Object> response = new HashMap<>();
        response.put("titolo", partita.getStoria().getTitolo());
        response.put("scenarioCorrente", partita.getIdScenarioCorrente());
        response.put("inventario", partita.getInventarioId());
        return response;
    }

    @PostMapping("/gioca/{storiaId}/scelta/{opzioneId}")
    public void faiScelta(@PathVariable int storiaId, @PathVariable int opzioneId, InventarioController inventarioController) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Utente user = userService.getUser(username);
        // Usa l'utente per caricare scenari personalizzati o eseguire logiche specifiche
        partitaService.faiScelta(storiaId, opzioneId, user, inventarioController);
    }
}
