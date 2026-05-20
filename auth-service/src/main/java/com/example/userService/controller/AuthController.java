package com.example.userService.controller;

import com.example.userService.dto.request.LoginRequest;
import com.example.userService.dto.request.RegisterRequest;
import com.example.userService.dto.response.AuthResponse;
import com.example.userService.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private static final String ACCESS_TOKEN_COOKIE  = "access_token";
    private static final String REFRESH_TOKEN_COOKIE = "refresh_token";

    // Durées de vie des cookies (en secondes)
    private static final int ACCESS_TOKEN_MAX_AGE  = 24 * 60 * 60;      // 24h
    private static final int REFRESH_TOKEN_MAX_AGE = 7 * 24 * 60 * 60;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request,
            HttpServletResponse response) {

        AuthResponse authResponse = userService.register(request);

        addTokenCookies(response, authResponse.getAccessToken(), authResponse.getRefreshToken());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(maskTokens(authResponse));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response) {

        AuthResponse authResponse = userService.login(request);
        addTokenCookies(response, authResponse.getAccessToken(), authResponse.getRefreshToken());

        return ResponseEntity.ok(maskTokens(authResponse));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(
            @CookieValue(name = "refresh_token", required = false) String refreshToken,
            HttpServletResponse response) {

        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        AuthResponse authResponse = userService.refreshToken(refreshToken);

        // Renouveler uniquement le cookie access_token
        addAccessTokenCookie(response, authResponse.getAccessToken());

        return ResponseEntity.ok(maskTokens(authResponse));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        clearTokenCookies(response);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check")
    public ResponseEntity<String> check(
            @CookieValue(name = "access_token", required = false) String accessToken) {

        if (accessToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Aucun cookie access_token trouvé");
        }
        return ResponseEntity.ok("Cookie access_token présent ✓");
    }

    private void addTokenCookies(HttpServletResponse response,
                                      String accessToken,
                                      String refreshToken) {
        addAccessTokenCookie(response, accessToken);
        addRefreshTokenCookie(response, refreshToken);
    }

    private void addAccessTokenCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(ACCESS_TOKEN_COOKIE, token);
        cookie.setHttpOnly(true);       // non accessible par JavaScript
        cookie.setSecure(false);        // mettre true en production (HTTPS)
        cookie.setPath("/");            // disponible sur tous les endpoints
        cookie.setMaxAge(ACCESS_TOKEN_MAX_AGE);
        response.addCookie(cookie);
    }

    private void addRefreshTokenCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);        // mettre true en production
        cookie.setPath("/auth/refresh");// accessible uniquement depuis /auth/refresh
        cookie.setMaxAge(REFRESH_TOKEN_MAX_AGE);
        response.addCookie(cookie);
    }

    private void clearTokenCookies(HttpServletResponse response) {
        Cookie accessCookie = new Cookie(ACCESS_TOKEN_COOKIE, "");
        accessCookie.setMaxAge(0);
        accessCookie.setPath("/");
        response.addCookie(accessCookie);

        Cookie refreshCookie = new Cookie(REFRESH_TOKEN_COOKIE, "");
        refreshCookie.setMaxAge(0);
        refreshCookie.setPath("/auth/refresh");
        response.addCookie(refreshCookie);
    }

    private AuthResponse maskTokens(AuthResponse original) {
        return AuthResponse.builder()
                .accessToken("[stocké dans cookie HttpOnly]")
                .refreshToken("[stocké dans cookie HttpOnly]")
                .tokenType(original.getTokenType())
                .userId(original.getUserId())
                .email(original.getEmail())
                .role(original.getRole())
                .build();
    }
}