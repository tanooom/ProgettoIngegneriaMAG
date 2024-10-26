package com.example.myapp.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.myapp.Model.Inventario;
import com.example.myapp.Service.MapDBService;

@Controller
public class InventarioController {
    private final Map<Integer, Inventario> inventari;
    private int nextId = 1;

    @Autowired
    private final MapDBService mapDBService;

    public InventarioController(MapDBService mapDBService) {
        this.inventari = new HashMap<>();
        this.mapDBService = mapDBService;
    }

    public void aggiungiInventario(Inventario inventario) {
        inventari.put(inventario.getId(), inventario);
    }

    public Inventario getInventarioById(int id) {
        return inventari.get(id);
    }

    // Logica per creare un inventario
    public Inventario creaInventario(int partitaId) {
        Inventario nuovoInventario = new Inventario(nextId++, partitaId);
        aggiungiInventario(nuovoInventario);
               
        mapDBService.saveInventory(nuovoInventario);
        return nuovoInventario;
    }
}