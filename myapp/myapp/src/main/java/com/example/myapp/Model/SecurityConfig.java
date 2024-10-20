package com.example.myapp.Model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

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
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/login", "/register", "/user/register", "/error", "/css/**", "/js/**", "/images/", "/export").permitAll()

                //.requestMatchers( "/getAllPartite","/getPartiteByUsername", "/updateScenarioDescription", "/getScenariByTitle").permitAll()

                //Servono per i comandi da terminale
                .requestMatchers(/*"/deleteStoriaByTitle",*/ "/deleteUser", "/deleteAllUsers", "/deleteOption", "/deleteScenario", "/deleteStoria", 
                "/deleteAllStories", "/deleteAllScenari", "/deleteAllOptions", "/deleteAllPartite", "/deleteDatabase" /* "/deletePartita"*/).permitAll()

                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginPage("/login")
                .defaultSuccessUrl("/home", true)
                .failureUrl("/login?error")
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
            );
        return http.build();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/login?error=true");
    }
}