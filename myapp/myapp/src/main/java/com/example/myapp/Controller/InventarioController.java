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
        System.out.println(inventari);
    }

    public Inventario getInventarioById(int id) {
        return inventari.get(id);
    }

    public Inventario creaInventario(int partitaId) {
        System.out.println("CREA INVENTARIO DI INVENTARIO CONTROLLER 1");
        Inventario nuovoInventario = new Inventario(nextId++, partitaId);
        System.out.println("INVENTARIO DI INVENTARIO CONTROLLER 2: " + nuovoInventario);
        aggiungiInventario(nuovoInventario);
               
        // Logica per salvare su MapDB usando MapDBService
        mapDBService.saveInventory(nuovoInventario); //QUI MI DA ERRORE
        System.out.println("INVENTARIO SALVATO SU INVENTARIO CONTROLLER");
        return nuovoInventario;
    }
}