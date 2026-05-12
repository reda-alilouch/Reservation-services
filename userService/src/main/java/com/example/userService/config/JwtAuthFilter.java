package com.example.userService.config;

import com.example.userService.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtre JWT — intercepte CHAQUE requête HTTP avant qu'elle atteigne le Controller.
 *
 * Fonctionnement :
 * 1. Requête reçue → ce filtre s'exécute en premier
 * 2. Lit le header "Authorization: Bearer <token>"
 * 3. Extrait et valide le token JWT
 * 4. Si valide → authentifie l'utilisateur dans Spring Security
 * 5. Passe la requête au Controller
 *
 * OncePerRequestFilter garantit que ce filtre s'exécute UNE seule fois par requête.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService        jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest  request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain         filterChain
    ) throws ServletException, IOException {

        // ── Étape 1 : Lire le header Authorization ────────────────────────
        // Exemple de header : "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
        final String authHeader = request.getHeader("Authorization");

        // Si pas de header ou ne commence pas par "Bearer " → passer au filtre suivant
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // ── Étape 2 : Extraire le token (enlever "Bearer ") ───────────────
        // "Bearer eyJhbGciOiJIUzI1NiJ9..." → "eyJhbGciOiJIUzI1NiJ9..."
        final String jwt   = authHeader.substring(7);

        // ── Étape 3 : Extraire l'email depuis le token ────────────────────
        final String email = jwtService.extractUsername(jwt);

        // ── Étape 4 : Authentifier si email valide et pas encore authentifié
        // SecurityContextHolder.getContext().getAuthentication() == null
        // signifie que l'utilisateur n'est pas encore authentifié pour cette requête
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Charger l'utilisateur depuis la base de données
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // Vérifier que le token est valide (signature + expiration)
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // Créer l'objet d'authentification Spring Security
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,        // l'utilisateur authentifié
                                null,               // credentials (null car JWT, pas de mdp)
                                userDetails.getAuthorities() // rôles : [ROLE_CLIENT, ROLE_ADMIN...]
                        );

                // Ajouter les détails de la requête HTTP
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // ── Étape 5 : Enregistrer l'authentification ──────────────
                // Spring Security sait maintenant QUI fait la requête
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // ── Étape 6 : Passer au filtre/controller suivant ─────────────────
        filterChain.doFilter(request, response);
    }
}
