package com.example.myapp.Service;

import org.mapdb.HTreeMap;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.myapp.Model.UserRepository;
import com.example.myapp.Model.Utente;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService implements UserDetailsService {

    private final HTreeMap<String, String> userMap;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    // Aggiungi un contatore per gestire manualmente l'incremento degli ID
    private final AtomicLong userIdCounter = new AtomicLong(1); // Partenza da 1

    public UserService(HTreeMap<String, String> userMap, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userMap = userMap;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
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

    // Restituisci i dati dell'utente in formato JSON
    public Utente getUserData(String username) {
        return getUser(username);
    }

    // Metodo per registrare un utente in MapDB
    public Utente register(Utente user) {
        // Controlla se l'utente esiste in MapDB
        if (userMap.containsKey(user.getUsername())) {
            throw new IllegalArgumentException("L'utente con questo username esiste già in MapDB.");
        }
    
        // Controlla se l'utente esiste nel database relazionale
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("L'utente con questo username esiste già nel database.");
        }
    
        // Imposta manualmente l'ID incrementale
        Long newId = userIdCounter.getAndIncrement();
        user.setId(newId);

        // Codifica la password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    
        try {
            // Salva l'utente in MapDB
           userMap.put(user.getUsername(), String.format("%d;%s;%s;%s;%s",
                    user.getId(), user.getPassword(), user.getNome(), user.getCognome(), user.getMail()));
    
             // Salva l'utente nel database relazionale
            return userRepository.save(user);
        } catch (Exception e) {
            // Gestisci eventuali eccezioni
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

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

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

    public void save(Utente user) {
        userRepository.save(user); // Salva l'utente nel database
    }
}