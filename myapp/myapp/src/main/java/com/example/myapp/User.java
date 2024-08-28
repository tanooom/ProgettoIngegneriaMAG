package com.example.myapp;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Aggiunta della chiave primaria

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
    
    private String nome;
    private String cognome;
    private String mail;

    // Costruttori, getter e setter
    public User() {}

    public User(String username, String password, String nome, String cognome, String mail) {
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.mail = mail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    @Override
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Restituisce un'autorità predefinita; puoi modificarlo in base alle tue esigenze
        return Collections.emptyList(); // Modifica qui se hai ruoli specifici
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Modifica se hai logica per l'espirazione dell'account
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Modifica se hai logica per il blocco dell'account
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Modifica se hai logica per l'espirazione delle credenziali
    }

    @Override
    public boolean isEnabled() {
        return true; // Modifica se hai logica per la disabilitazione dell'account
    }
}