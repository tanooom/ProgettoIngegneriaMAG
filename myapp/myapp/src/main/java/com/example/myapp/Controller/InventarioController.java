package com.example.myapp.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;

import com.example.myapp.Model.Inventario;  // Aggiungi questa importazione

@Controller
public class InventarioController {
    private final Map<Integer, Inventario> inventari;
    private int nextId = 1; // Per generare un nuovo ID unico

    public InventarioController() {
        this.inventari = new HashMap<>();
    }

    public void aggiungiInventario(Inventario inventario) {
        inventari.put(inventario.getId(), inventario);
    }

    public Inventario getInventarioById(int id) {
        return inventari.get(id); // Ritorna l'inventario associato all'ID
    }

    public int creaInventario() {
        Inventario nuovoInventario = new Inventario(nextId++, 0); // Imposta idPartita a 0 inizialmente
        aggiungiInventario(nuovoInventario);
        return nuovoInventario.getId();
    }
}
