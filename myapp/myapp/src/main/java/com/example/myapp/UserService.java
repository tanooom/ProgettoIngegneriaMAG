package com.example.myapp;

import org.mapdb.HTreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    private final HTreeMap<String, String> userMap;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserService(HTreeMap<String, String> userMap, PasswordEncoder passwordEncoder) {
        this.userMap = userMap;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(String username, String password, String nome, String cognome, String mail) {
        String encodedPassword = passwordEncoder.encode(password);
        // Salva i dati dell'utente come valore
        String userData = encodedPassword + ";" + nome + ";" + cognome + ";" + mail;
        // Usa lo username come chiave e userData come valore
        userMap.put(username, userData);
    }

    public String getEncodedPassword(String username) {
        String userData = userMap.get(username);
        if (userData != null) {
            return userData.split(";")[0]; // Estrae la password codificata
        }
        return null;
    }
    
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    // Metodo per ottenere tutte le informazioni dell'utente
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

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
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
            .roles("USER") // Modifica i ruoli in base alla tua logica
            .build();
    }
}