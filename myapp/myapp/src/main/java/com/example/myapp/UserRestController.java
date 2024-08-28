package com.example.myapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/users/{username}")
    public User getUser(@PathVariable String username) {
        User user = userService.getUser(username);
        if (user == null) {
            throw new ResourceNotFoundException("User not found"); // Crea una classe di eccezione per gestire questo caso
        }
        return user;
    }
}