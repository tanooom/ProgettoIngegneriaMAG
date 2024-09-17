package com.example.myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.example.myapp"}) // Specifica il package
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	// COMANDO: curl "http://localhost:8080/mapdb/export?filePath=backup.json"
}