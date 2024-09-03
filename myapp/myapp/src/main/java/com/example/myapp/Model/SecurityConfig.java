package com.example.myapp.Model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.myapp.Service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                // TODO: da togliere i commenti e da eliminare quello temporaneo dopo aver sistemato login e registrazione
                //.requestMatchers("/login", "/register", "/user/register", "/error", "/css/**", "/js/**", "/images/**", "/registrationSuccess").permitAll()
                //.anyRequest().authenticated()
                .anyRequest().permitAll() // TEMPORANEO -> Permette l'accesso a tutte le richieste senza autenticazione

            )
            .formLogin(login -> login
                .loginPage("/login")
                .defaultSuccessUrl("/home", true)
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
            );

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, UserService userService) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
            http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
            .userDetailsService(userService) // Usa userService qui
            .passwordEncoder(passwordEncoder());
        
        return authenticationManagerBuilder.build(); // Restituisci direttamente l'oggetto AuthenticationManager
    }
}