package com.example.myapp.Model;

public class Utente {

    private String username;
    private String password;
    private String nome;
    private String cognome;
    private String mail;

    public Utente() {}

    public Utente(String username, String password, String nome, String cognome, String mail) {
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}