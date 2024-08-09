package com.example.myapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mapdb")
public class MapDBController {

    @Autowired
    private MapDBService mapDBService;

    @PostMapping("/put")
    public String put(@RequestParam String key, @RequestParam String value) {
        mapDBService.put(key, value);
        return "Value added successfully!";
    }

    @GetMapping("/get")
    public String get(@RequestParam String key) {
        return mapDBService.get(key);
    }
}
