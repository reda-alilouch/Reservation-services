package com.example.userService.config;

import com.example.userService.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
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
import java.util.Arrays;
import java.util.Optional;
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

    private final JwtService         jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest  request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain         filterChain
    ) throws ServletException, IOException {

        // ── Étape 1 : Lire le cookie "access_token" ──────────────────────
        String jwt = extractTokenFromCookie(request);

        // Pas de cookie → passer au filtre suivant (endpoint public ou non authentifié)
        if (jwt == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // ── Étape 2 : Extraire l'email depuis le token JWT ────────────────
        String email;
        try {
            email = jwtService.extractUsername(jwt);
        } catch (Exception e) {
            // Token malformé ou signature invalide → continuer sans authentifier
            filterChain.doFilter(request, response);
            return;
        }

        // ── Étape 3 : Authentifier si pas encore fait ─────────────────────
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Charger l'utilisateur depuis la base
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // Vérifier validité du token (signature + expiration)
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // Créer l'objet d'authentification Spring Security
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities() // rôles : ADMIN, CLIENT...
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // ── Étape 4 : Enregistrer dans le SecurityContext ──────────
                // Spring sait maintenant QUI fait cette requête
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // ── Étape 5 : Passer au controller ───────────────────────────────
        filterChain.doFilter(request, response);
    }

    // ─────────────────────────────────────────────────────────────────────
    // Helper — extraire le token depuis les cookies
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Cherche le cookie "access_token" dans la requête HTTP.
     *
     * @param request la requête HTTP entrante
     * @return la valeur du token JWT, ou null si absent
     */
    private String extractTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        // Aucun cookie dans la requête
        if (cookies == null) {
            return null;
        }

        // Chercher le cookie nommé "access_token"
        Optional<Cookie> tokenCookie = Arrays.stream(cookies)
                .filter(cookie -> "access_token".equals(cookie.getName()))
                .findFirst();

        return tokenCookie.map(Cookie::getValue).orElse(null);
    }
}
