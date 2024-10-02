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
	// COMANDO: curl "http://localhost:8080/export?filePath=backup.json"

	//TODO: alla fine sistema tutti i @media dei css
	//TODO: alla fine togli tutti i console log e i system.out.println() di debug
	//TODO: dopo che riusciamo a salvare una storia togli Storia_example.html e StoriaExampleController.java

	// PER RIMUOVERE UN UTENTE: 
	//Invoke-RestMethod -Method Delete -Uri "http://localhost:8080/deleteUser?username=andre2"

	// PER RIMUOVERE TUTTI GLI UTENTI:
	//Invoke-RestMethod -Method Delete -Uri "http://localhost:8080/deleteAllUsers"

	// PER RIMUOVERE UN'OPZIONE
	//Invoke-RestMethod -Method Delete -Uri "http://localhost:8080/deleteOption?id=124"

	// PER RIMUOVERE UNO SCENARIO
	//Invoke-RestMethod -Method Delete -Uri "http://localhost:8080/deleteScenario?id=124"

}
