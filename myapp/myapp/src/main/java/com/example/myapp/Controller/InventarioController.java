package com.example.myapp.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;

import com.example.myapp.Model.Inventario;  // Aggiungi questa importazione

@Controller
public class InventarioController {
    private final Map<Integer, Inventario> inventari;

    public InventarioController() {
        this.inventari = new HashMap<>();
    }

    public void aggiungiInventario(Inventario inventario) {
        inventari.put(inventario.getId(), inventario);
    }

    public Inventario getInventarioById(int id) {
        return inventari.get(id); // Ritorna l'inventario associato all'ID
    }
}
