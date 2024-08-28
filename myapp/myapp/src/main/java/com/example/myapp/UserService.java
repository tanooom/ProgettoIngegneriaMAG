package com.example.myapp;

import org.mapdb.HTreeMap;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final HTreeMap<String, String> userMap;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(HTreeMap<String, String> userMap, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userMap = userMap;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    // Metodo per ottenere tutte le informazioni dell'utente da MapDB
    public User getUser(String username) {
        String userData = userMap.get(username);
        if (userData != null) {
            String[] data = userData.split(";");
            return new User(username, data[0], data[1], data[2], data[3]); // username, password, nome, cognome, mail
        }
        return null;
    }

    // Restituisci i dati dell'utente in formato JSON
    public User getUserData(String username) {
        return getUser(username);
    }

    // Metodo per registrare un utente in MapDB
    public User register(User user) {
        // Codifica della password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Salva utente in MapDB
        userMap.put(user.getUsername(), String.format("%s;%s;%s;%s",
                user.getPassword(), user.getNome(), user.getCognome(), user.getMail()));

        // Salva utente in JPA (opzionale, se vuoi persistenza nel database relazionale)
        return userRepository.save(user);
    }

    public String getEncodedPassword(String username) {
        User user = getUser(username);
        if (user != null) {
            return user.getPassword();
        }
        return null;
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public User findByUsername(String username) {
        return getUser(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
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