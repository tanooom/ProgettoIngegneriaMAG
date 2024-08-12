package com.example.myapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GiocoController {

    @Autowired
    private GiocoService giocoService;

    @GetMapping("/storie")
    public List<Storia> getStorieDisponibili() {
        return giocoService.getStorieDisponibili();
    }

    @GetMapping("/gioca/{storiaId}")
    public Gioco caricaScenario(@PathVariable int storiaId) {
        return giocoService.caricaGioco(storiaId);
    }

    @PostMapping("/gioca/{storiaId}/scelta/{opzioneId}")
    public void faiScelta(@PathVariable int storiaId, @PathVariable int opzioneId) {
        giocoService.faiScelta(storiaId, opzioneId);
    }
}