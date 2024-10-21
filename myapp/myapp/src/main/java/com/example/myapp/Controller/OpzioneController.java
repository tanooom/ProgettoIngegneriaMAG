package com.example.myapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.myapp.Model.Opzione;
import com.example.myapp.Service.MapDBService;

@Controller
public class OpzioneController {

    @Autowired
    private MapDBService mapDBService;

    @GetMapping("/scriviOpzione")
    public String scriviOpzione() {
        return "scriviOpzione"; 
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
        @RequestParam(required = false) String oggettoRichiesto) {

        boolean richiedeIndovinelloBool = "si".equalsIgnoreCase(richiedeIndovinello);
        boolean richiedeOggettoBool = "si".equalsIgnoreCase(richiedeOggetto);

        int newId = mapDBService.getAllOptions().keySet().stream()
        .mapToInt(Integer::intValue)
        .max()
        .orElse(0) + 1;

        Opzione nuovaOpzione = new Opzione(
            newId, 
            descrizioneOpzione,
            richiedeOggettoBool, 
            oggettoRichiesto != null && !oggettoRichiesto.isEmpty() ? oggettoRichiesto : null,
            richiedeIndovinelloBool,
            indovinello != null && !indovinello.isEmpty() ? indovinello : null, 
            rispostaIndovinello != null && !rispostaIndovinello.isEmpty() ? rispostaIndovinello : null 
        );
        mapDBService.saveOption(nuovaOpzione);
        return "redirect:/scriviScenario";
    }

    @GetMapping("/scriviScenario")
    public String mostraScenarioForm(Model model){
        model.addAttribute("opzioni", mapDBService.getListAllOptions());
        model.addAttribute("scenari", mapDBService.getListAllScenari());
        return "scriviScenario";
    }

    @GetMapping("/getOpzioniByScenario/{idScenario}")
    @ResponseBody
    public List<Opzione> getOpzioniByScenario(@PathVariable int idScenario) {
        // Recupera le opzioni associate allo scenario precedente
        List<Opzione> opzioni = mapDBService.getOpzioniByScenario(idScenario);
        return opzioni;
    }

}