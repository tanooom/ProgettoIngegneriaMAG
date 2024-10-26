package com.example.myapp.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Inventario implements Serializable{

    private final int id;
    private final List<String> oggetti;
    private final int idPartita;

    // Costruttore
    public Inventario(int id, int idPartita) {
        this.id = id;
        this.oggetti = new ArrayList<>();
        this.idPartita = idPartita;
    }

    // Getter
    public int getId() {
        return id;
    }

    public int getIdPartita() {
        return idPartita;
    }

    // Aggiunge un oggetto all'inventario
    public void aggiungiOggetto(String oggetto) {
        if (!oggetti.contains(oggetto)) {
            oggetti.add(oggetto);
        }
    }

    // Verifica se un oggetto è presente nell'inventario
    public boolean contieneOggetto(String oggetto) {
        return oggetti.contains(oggetto);
    }

    // Restituisce la lista di oggetti nell'inventario
    public List<String> getOggetti() {
        return new ArrayList<>(oggetti);
    }
}