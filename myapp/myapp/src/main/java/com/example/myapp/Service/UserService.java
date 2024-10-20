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

    public UserService(HTreeMap<String, String> userMap, PasswordEncoder passwordEncoder, DB db) {
        this.userMap = userMap;
        this.passwordEncoder = passwordEncoder;
        this.db = db; 
    }

    // Metodo per ottenere tutte le informazioni dell'utente
    public Utente getUser(String username) {
        String userData = userMap.get(username);
        if (userData != null) {
            String[] data = userData.split(";");
            return new Utente(username, data[0], data[1], data[2], data[3]); // username, password, nome, cognome, mail
        }
        return null;
    }

    public Utente getUserByEmail(String email) {
        for (String username : userMap.keySet()) {
            String userData = userMap.get(username);
            String[] data = userData.split(";");
                return new Utente(username, data[0], data[1], data[2], email); // username, password, nome, cognome, mail
        }
        return null;
    }
    
    // Metodo per registrare un utente
    public Utente register(Utente user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userMap.put(user.getUsername(), String.format("%s;%s;%s;%s",
                     user.getPassword(), user.getNome(), user.getCognome(), user.getMail()));
            db.commit();
            return user; 
        } catch (Exception e) {
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utente user = getUser(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }

    // Metodo per eliminare un utente per username
    public void deleteUser(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("L'username non può essere nullo o vuoto.");
        }
        if (userMap.containsKey(username)) {
            userMap.remove(username);
            db.commit();
        } else {
            throw new IllegalArgumentException("L'utente con username " + username + " non esiste.");
        }
    }
}