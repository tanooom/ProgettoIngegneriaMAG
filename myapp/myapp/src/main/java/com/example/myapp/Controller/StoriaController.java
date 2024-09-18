package com.example.myapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.myapp.Model.Storia;
import com.example.myapp.Service.StoriaService;

@Controller
public class StoriaController {

    //private static final Logger logger = LoggerFactory.getLogger(StoriaController.class);

    @Autowired
    private StoriaService storiaService;

    // Aggiungiamo un metodo POST per salvare la storia
    @PostMapping("/api/storie")
    public ResponseEntity<Storia> salvaStoria(@RequestBody Storia storia) {
        try {
            // Salva la storia utilizzando il servizio
            Storia nuovaStoria = storiaService.creaStoria(storia);
            return ResponseEntity.ok(nuovaStoria);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Errore generico
        }
    }
    
    @GetMapping("/api/storie")
    public List<Storia> getStorie() {
        return storiaService.getAllStorie(); // Recupera tutte le storie
    }

    @GetMapping("/visualizzaStoria/{id}")
    public String visualizzaStoria(@PathVariable int id, Model model) {
        Storia storia = storiaService.getStoriaById(id);
        model.addAttribute("storia", storia);
        return "visualizzaStoria"; // Nome del template HTML senza estensione
    }
}