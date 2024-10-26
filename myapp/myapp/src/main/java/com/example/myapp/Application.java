package com.example.myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.example.myapp"}) 
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/*
	PER AGGIORGNARE IL JSON DEL DATABASE: 
		curl "http://localhost:8080/export?filePath=backup.json"
	PER CANCELLARE TUTTO IL DATABASE:
		Invoke-RestMethod -Method Delete -Uri "http://localhost:8080/deleteDatabase"
 
	PER RIMUOVERE UN UTENTE: 
		Invoke-RestMethod -Method Delete -Uri "http://localhost:8080/deleteUser?username=andre2"
	PER RIMUOVERE TUTTI GLI UTENTI:
		Invoke-RestMethod -Method Delete -Uri "http://localhost:8080/deleteAllUsers"

	PER RIMUOVERE UN'OPZIONE:
		Invoke-RestMethod -Method Delete -Uri "http://localhost:8080/deleteOption?id=124"
	PER RIMUOVERE TUTTE LE OPZIONI:
		Invoke-RestMethod -Method Delete -Uri "http://localhost:8080/deleteAllOptions"

	PER RIMUOVERE UNO SCENARIO:
		Invoke-RestMethod -Method Delete -Uri "http://localhost:8080/deleteScenario?id=124"
	PER RIMUOVERE TUTTI GLI SCENARI:
		Invoke-RestMethod -Method Delete -Uri "http://localhost:8080/deleteAllScenari"

	PER RIMUOVERE UNA STORIA:
		Invoke-RestMethod -Method Delete -Uri "http://localhost:8080/deleteStoria?id=124"
	PER RIMUOVERE TUTTE LE STORIE:
		Invoke-RestMethod -Method Delete -Uri "http://localhost:8080/deleteAllStories"

	PER RIMUOVERE TUTTE LE PARTITE (RIMUOVERE ANCHE TUTTI GLI INVENTARI):
		Invoke-RestMethod -Method Delete -Uri "http://localhost:8080/deleteAllPartite"

	PER RIMUOVERE TUTTI GLI INVENTARI:
		Invoke-RestMethod -Method Delete -Uri "http://localhost:8080/deleteAllInventari"
	*/
}