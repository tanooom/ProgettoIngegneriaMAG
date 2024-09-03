package com.example.myapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.myapp.Model.ResourceNotFoundException;
import com.example.myapp.Model.Utente;
import com.example.myapp.Service.UserService;

@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/users/{username}")
    public Utente getUser(@PathVariable String username) {
        Utente user = userService.getUser(username);
        if (user == null) {
            throw new ResourceNotFoundException("User not found"); // Crea una classe di eccezione per gestire questo caso
        }
        return user;
    }
}