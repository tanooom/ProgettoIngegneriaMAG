package com.example.myapp;

import org.mapdb.DB;
import org.mapdb.HTreeMap;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final HTreeMap<String, String> userMap;
    private final PasswordEncoder passwordEncoder;

    public UserService(DB mapDb, PasswordEncoder passwordEncoder) {
        this.userMap = mapDb.hashMap("users", org.mapdb.Serializer.STRING, org.mapdb.Serializer.STRING).createOrOpen();
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(String username, String encodedPassword) {
        if (userMap.containsKey(username)) {
            throw new RuntimeException("User already exists");
        }
        userMap.put(username, encodedPassword);
    }

    public boolean authenticateUser(String username, String password) {
        String encodedPassword = userMap.get(username);
        return encodedPassword != null && passwordEncoder.matches(password, encodedPassword);
    }
}
