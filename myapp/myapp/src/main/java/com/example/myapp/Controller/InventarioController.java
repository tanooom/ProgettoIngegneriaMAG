package com.example.myapp.Controller;

import java.util.HashMap;
import java.util.Map;

import com.example.myapp.Model.Inventario;

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