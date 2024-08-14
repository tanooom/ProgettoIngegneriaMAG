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

    public void registerUser(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        userMap.put(username, encodedPassword);
    }

    public String getEncodedPassword(String username) {
        return userMap.get(username);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}


