package com.example.myapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StoriaController {

    @Autowired
    private StoriaService storiaService;

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