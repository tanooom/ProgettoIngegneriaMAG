package com.example.myapp.Service;

import org.mapdb.DB;
import org.mapdb.HTreeMap;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.myapp.Model.Utente;

@Service
public class UserService implements UserDetailsService {

    private final HTreeMap<String, String> userMap;
    private final PasswordEncoder passwordEncoder;
    private final DB db;  

    // Modifica il costruttore per accettare il DB
    public UserService(HTreeMap<String, String> userMap, PasswordEncoder passwordEncoder, DB db) {
        this.userMap = userMap;
        this.passwordEncoder = passwordEncoder;
        this.db = db;  // Inizializza la variabile db
    }

    // Metodo per ottenere tutte le informazioni dell'utente da MapDB
    public Utente getUser(String username) {
        String userData = userMap.get(username);
        if (userData != null) {
            String[] data = userData.split(";");
            return new Utente(username, data[0], data[1], data[2], data[3]); // username, password, nome, cognome, mail
        }
        return null;
    }

    // Metodo per registrare un utente in MapDB
    public Utente register(Utente user) {

        // Codifica la password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            // Salva l'utente in MapDB
            userMap.put(user.getUsername(), String.format("%s;%s;%s;%s",
                     user.getPassword(), user.getNome(), user.getCognome(), user.getMail()));

            // Commit delle modifiche per renderle persistenti
            db.commit();

            return user; // Ritorna l'utente registrato con successo
        } catch (Exception e) {
            // Gestisci eventuali eccezioni e fai logging
            throw new RuntimeException("Errore durante la registrazione dell'utente", e);
        }
    }

    public String getEncodedPassword(String username) {
        Utente user = getUser(username);
        if (user != null) {
            return user.getPassword();
        }
        return null;
    }

    /*public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }*/

    public Utente findByUsername(String username) {
        return getUser(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utente user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}