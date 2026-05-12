package com.example.userService.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity         // active @PreAuthorize dans les controllers
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter         jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    /**
     * Bean SecurityFilterChain :
     * C'est ici qu'on configure toutes les règles de sécurité HTTP.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // ── 1. Désactiver CSRF ────────────────────────────────────────
                // CSRF n'est pas nécessaire avec JWT (pas de cookies de session)
                .csrf(AbstractHttpConfigurer::disable)

                // ── 2. Règles d'autorisation par endpoint ─────────────────────
                .authorizeHttpRequests(auth -> auth

                        // Endpoints PUBLICS — accessibles sans token
                        .requestMatchers(
                                "/auth/register",       // inscription
                                "/auth/login",          // connexion
                                "/auth/refresh",        // rafraîchir le token
                                "/swagger-ui/**",       // documentation Swagger
                                "/swagger-ui.html",
                                "/api-docs/**",
                                "/actuator/health"      // health check
                        ).permitAll()

                        // Endpoints réservés à l'ADMIN uniquement
                        .requestMatchers(
                                "/users",               // liste tous les utilisateurs
                                "/users/*/status"       // activer/désactiver un compte
                        ).hasAuthority("ADMIN")

                        // Tous les autres endpoints nécessitent un token valide
                        .anyRequest().authenticated()
                )

                // ── 3. Politique de session STATELESS ─────────────────────────
                // Pas de session HTTP côté serveur.
                // Chaque requête doit porter son token JWT.
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // ── 4. Provider d'authentification ────────────────────────────
                // Utilise DaoAuthenticationProvider (UserDetailsService + BCrypt)
                .authenticationProvider(authenticationProvider)

                // ── 5. Ajouter le filtre JWT ──────────────────────────────────
                // JwtAuthFilter s'exécute AVANT le filtre d'authentification standard
                // Ordre : JwtAuthFilter → UsernamePasswordAuthenticationFilter → Controller
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}