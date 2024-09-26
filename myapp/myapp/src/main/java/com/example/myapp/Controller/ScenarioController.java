package com.example.myapp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScenarioController {

    @GetMapping("/scriviScenario")
    public String mostraScriviScenario() {
        return "scriviScenario"; // Nome del file HTML da restituire, senza estensione
    }
}

