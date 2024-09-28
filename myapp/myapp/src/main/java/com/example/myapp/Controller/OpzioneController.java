package com.example.myapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam; 

import com.example.myapp.Model.Opzione;
import com.example.myapp.Service.MapDBService;

@Controller
public class OpzioneController {

    @Autowired
    private MapDBService mapDBService;

    @GetMapping("/scriviStoria")
    public String scriviStoria() {
        return "scriviStoria";
    }

    @GetMapping("/scriviOpzione")
    public String scriviOpzione() {
        return "scriviOpzione"; 
    }

    @PostMapping("/putOption")
    public String putOption(@RequestParam int id, 
                            @RequestParam String description, 
                            //@RequestParam int scenarioSuccessivo,
                            @RequestParam boolean richiedeOggetto, 
                            @RequestParam String oggettoRichiesto, 
                            @RequestParam boolean richiedeIndovinello, 
                            @RequestParam String indovinello,
                            @RequestParam String rispostaCorrettaIndovinello,
                            @RequestParam boolean rilasciaOggetto, 
                            @RequestParam String oggettoRilasciato) {
        Opzione opzione = new Opzione(id, description, richiedeOggetto, oggettoRichiesto, richiedeIndovinello, indovinello, rispostaCorrettaIndovinello, rilasciaOggetto, oggettoRilasciato);
        mapDBService.saveOption(opzione);
        return "Opzione aggiunta con successo!";
    }

    @GetMapping("/getOption")
    public Opzione getOption(@RequestParam int id) {
        return mapDBService.getOptionById(id);
    }

    @DeleteMapping("/deleteOption")
    public ResponseEntity<String> deleteOption(@RequestParam int id) {
        try {
            mapDBService.removeOption(id);
            return ResponseEntity.ok("Opzione eliminata con successo!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore interno del server: " + e.getMessage());
        }
    }

    @PostMapping("/aggiungiOpzione")
    public String aggiungiOpzione(
        @RequestParam String descrizioneOpzione,
        @RequestParam(required = false) String indovinello,
        @RequestParam(required = false) String rispostaIndovinello,
        @RequestParam String richiedeIndovinello,
        @RequestParam String richiedeOggetto,
        @RequestParam(required = false) String oggettoRichiesto,
        @RequestParam String rilasciaOggetto,
        @RequestParam(required = false) String oggettoRilasciato) {

        // Converte i valori delle stringhe in boolean
        boolean richiedeIndovinelloBool = "si".equalsIgnoreCase(richiedeIndovinello);
        boolean richiedeOggettoBool = "si".equalsIgnoreCase(richiedeOggetto);
        boolean rilasciaOggettoBool = "si".equalsIgnoreCase(rilasciaOggetto);

        // Crea una nuova opzione con i dati dal form
        Opzione nuovaOpzione = new Opzione(
            1187,  // ID dell'opzione
            descrizioneOpzione, // Descrizione dell'opzione dal form
            richiedeOggettoBool, // richiede oggetto
            oggettoRichiesto != null && !oggettoRichiesto.isEmpty() ? oggettoRichiesto : null, // oggetto richiesto
            richiedeIndovinelloBool, // richiede indovinello
            indovinello != null && !indovinello.isEmpty() ? indovinello : null, // indovinello (se applicabile)
            rispostaIndovinello != null && !rispostaIndovinello.isEmpty() ? rispostaIndovinello : null, // risposta corretta indovinello (se applicabile)
            rilasciaOggettoBool, // rilascia oggetto
            oggettoRilasciato != null && !oggettoRilasciato.isEmpty() ? oggettoRilasciato : null // oggetto rilasciato
        );

        mapDBService.saveOption(nuovaOpzione);

        // Reindirizza alla pagina successiva o di conferma
        return "redirect:/scriviScenario";
}

}