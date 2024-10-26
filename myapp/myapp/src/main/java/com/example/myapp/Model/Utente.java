package com.example.myapp.Model;

public class Utente {

    private String username;
    private String password;
    private String nome;
    private String cognome;
    private String mail;

    public Utente() {}

    // Costruttore
    public Utente(String username, String password, String nome, String cognome, String mail) {
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.mail = mail;
    }

    // Getter
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getCognome() {
        return cognome;
    }

    public String getMail() {
        return mail;
    }

    // Setter
    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}