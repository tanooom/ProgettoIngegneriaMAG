package com.example.myapp.Model;

import java.util.ArrayList;
import java.util.List;

public class Inventario {

    private final int id;
    private final List<String> oggetti;
    private final int idPartita;

    // Costruttore: inizializza un inventario vuoto
    public Inventario(int id, int idPartita) {
        this.id = id;
        this.oggetti = new ArrayList<>();
        this.idPartita = idPartita;
    }

    public int getId() {
        return id;
    }

    public int getIdPartita() {
        return idPartita;
    }

    // Aggiunge un oggetto all'inventario
    public void aggiungiOggetto(String oggetto) {
        //TODO: controllare, da salvare sul db
        if (!oggetti.contains(oggetto)) {
            oggetti.add(oggetto);
            System.out.println("Oggetto '" + oggetto + "' aggiunto all'inventario.");
        } else {
            System.out.println("Oggetto '" + oggetto + "' è già presente nell'inventario.");
        }
    }

    // Rimuove un oggetto dall'inventario -> DA IMPLEMENTARE???
    public boolean rimuoviOggetto(String oggetto) {
        //TODO: controllare, da salvare sul db
        if (oggetti.contains(oggetto)) {
            oggetti.remove(oggetto);
            System.out.println("Oggetto '" + oggetto + "' rimosso dall'inventario.");
            return true;
        } else {
            System.out.println("Oggetto '" + oggetto + "' non è presente nell'inventario.");
            return false;
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

    // Metodo per stampare l'inventario
    public void stampaInventario() {
        if (oggetti.isEmpty()) {
            System.out.println("Inventario vuoto.");
        } else {
            System.out.println("Inventario attuale:");
            for (String oggetto : oggetti) {
                System.out.println("- " + oggetto);
            }
        }
    }

    // Metodo per svuotare l'inventario
    public void svuotaInventario() {
        oggetti.clear();
        System.out.println("L'inventario è stato svuotato.");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Inventario ID: ").append(id)
          .append(", ID Partita: ").append(idPartita)
          .append(", Oggetti: [");

        if (oggetti.isEmpty()) {
            sb.append("Nessun oggetto presente");
        } else {
            for (int i = 0; i < oggetti.size(); i++) {
                sb.append(oggetti.get(i));
                if (i < oggetti.size() - 1) {
                    sb.append(", "); // Aggiunge una virgola tra gli oggetti
                }
            }
        }

        sb.append("]");
        return sb.toString();
    }
}