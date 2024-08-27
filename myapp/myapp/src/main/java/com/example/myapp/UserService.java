package com.example.myapp;

import org.mapdb.HTreeMap;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final HTreeMap<String, String> userMap;
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
}