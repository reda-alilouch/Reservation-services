package com.example.userService.user.infrastructure.web;

import com.example.userService.user.application.dto.request.ChangePasswordRequest;
import com.example.userService.user.application.dto.request.UpdateProfileRequest;
import com.example.userService.user.application.dto.response.UserDTO;
import com.example.userService.user.application.port.in.UserUseCase;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserUseCase userService;

  // ─────────────────────────────────────────────────────────────────────
  // ENDPOINTS UTILISATEUR CONNECTÉ
  // ─────────────────────────────────────────────────────────────────────

  /**
   * GET /users/me Retourne le profil de l'utilisateur connecté. Le cookie access_token est lu par
   * JwtAuthFilter → email extrait → UserService.findByEmail() appelé.
   */
  @GetMapping("/me")
  public ResponseEntity<UserDTO> getMe(HttpServletRequest request) {
    String email = extractEmailFromCookie(request);
    return ResponseEntity.ok(userService.findByEmail(email));
  }

  /**
   * PUT /users/me Modifier son propre profil (prénom, nom).
   *
   * <p>Corps JSON : { "firstName": "Mohammed", "lastName": "Alaoui" }
   */
  @PutMapping("/me")
  public ResponseEntity<UserDTO> updateMe(
      HttpServletRequest request, @Valid @RequestBody UpdateProfileRequest body) {

    String email = extractEmailFromCookie(request);
    UserDTO current = userService.findByEmail(email);
    UserDTO updated = userService.updateProfile(current.getId(), body);
    return ResponseEntity.ok(updated);
  }

  /**
   * PUT /users/me/password Changer son propre mot de passe.
   *
   * <p>Corps JSON : { "oldPassword": "ancienMdp", "newPassword": "nouveauMdp", "confirmPassword":
   * "nouveauMdp" }
   */
  @PutMapping("/me/password")
  public ResponseEntity<Void> changePassword(
      HttpServletRequest request, @Valid @RequestBody ChangePasswordRequest body) {

    String email = extractEmailFromCookie(request);
    UserDTO current = userService.findByEmail(email);
    userService.changePassword(current.getId(), body);
    return ResponseEntity.noContent().build();
  }

  // ─────────────────────────────────────────────────────────────────────
  // ENDPOINTS ADMIN SEULEMENT
  // ─────────────────────────────────────────────────────────────────────

  /** GET /users Liste tous les utilisateurs — ADMIN uniquement. */
  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<UserDTO>> getAllUsers() {
    return ResponseEntity.ok(userService.findAll());
  }

  /** GET /users/{id} Récupérer un utilisateur par son ID — ADMIN uniquement. */
  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
    return ResponseEntity.ok(userService.findById(id));
  }

  /**
   * PUT /users/{id}/status?active=false Activer ou désactiver un compte — ADMIN uniquement.
   *
   * <p>Exemple : PUT /users/550e8400-.../status?active=false
   */
  @PutMapping("/{id}/status")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> updateUserStatus(
      @PathVariable UUID id, @RequestParam boolean active) {

    userService.updateUserStatus(id, active);
    return ResponseEntity.noContent().build();
  }

  // ─────────────────────────────────────────────────────────────────────
  // Helper — extraire l'email depuis le cookie via Spring Security
  // ─────────────────────────────────────────────────────────────────────

  /**
   * Lit le cookie access_token depuis la requête HTTP, puis extrait l'email (username) stocké dans
   * le JWT.
   *
   * <p>JwtAuthFilter a déjà validé le token avant d'arriver ici — on peut donc faire confiance au
   * contenu du cookie.
   */
  private String extractEmailFromCookie(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null) {
      throw new IllegalStateException("Aucun cookie trouvé — veuillez vous connecter");
    }
    Optional<Cookie> tokenCookie =
        Arrays.stream(cookies).filter(c -> "access_token".equals(c.getName())).findFirst();

    if (tokenCookie.isEmpty()) {
      throw new IllegalStateException("Cookie access_token manquant — veuillez vous connecter");
    }

    // JwtService extrait l'email depuis le token
    return request.getUserPrincipal().getName();
  }
}
