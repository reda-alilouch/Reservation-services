package com.example.userService.user.application.service;

import com.example.userService.entity.User;
import com.example.userService.enums.Role;
import com.example.userService.shared.exception.EmailAlreadyExistsException;
import com.example.userService.shared.exception.InvalidTokenException;
import com.example.userService.shared.exception.UserNotFoundException;
import com.example.userService.user.application.dto.request.ChangePasswordRequest;
import com.example.userService.user.application.dto.request.LoginRequest;
import com.example.userService.user.application.dto.request.RegisterRequest;
import com.example.userService.user.application.dto.request.UpdateProfileRequest;
import com.example.userService.user.application.dto.response.AuthResponse;
import com.example.userService.user.application.dto.response.UserDTO;
import com.example.userService.user.application.port.in.AuthUseCase;
import com.example.userService.user.application.port.in.UserUseCase;
import com.example.userService.user.application.port.out.UserPersistencePort;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements AuthUseCase, UserUseCase {

  private final UserPersistencePort userRepository;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  // ── Inscription ───────────────────────────────────────────────────────

  @Transactional
  public AuthResponse register(RegisterRequest request) {

    // 1. Email déjà utilisé ?
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new EmailAlreadyExistsException("L'email " + request.getEmail() + " est déjà utilisé");
    }

    // 2. Construire l'utilisateur
    User user =
        User.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(request.getRole() != null ? request.getRole() : Role.CLIENT)
            .isActive(true)
            .build();

    // 3. Sauvegarder en base
    User savedUser = userRepository.save(user);

    // 4. Générer les tokens JWT
    String accessToken = jwtService.generateToken(savedUser);
    String refreshToken = jwtService.generateRefreshToken(savedUser);

    return buildAuthResponse(savedUser, accessToken, refreshToken);
  }

  // ── Connexion ─────────────────────────────────────────────────────────

  public AuthResponse login(LoginRequest request) {

    // 1. Spring Security vérifie email + mot de passe
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

    // 2. Charger l'utilisateur
    User user =
        userRepository
            .findByEmailAndIsActiveTrue(request.getEmail())
            .orElseThrow(() -> new UserNotFoundException("Compte désactivé ou introuvable"));

    // 3. Générer les tokens
    String accessToken = jwtService.generateToken(user);
    String refreshToken = jwtService.generateRefreshToken(user);

    return buildAuthResponse(user, accessToken, refreshToken);
  }

  // ── Refresh Token ─────────────────────────────────────────────────────

  public AuthResponse refreshToken(String refreshToken) {

    String email = jwtService.extractUsername(refreshToken);

    if (email == null) {
      throw new InvalidTokenException("Refresh token invalide");
    }

    User user =
        userRepository
            .findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable"));

    if (!jwtService.isTokenValid(refreshToken, user)) {
      throw new InvalidTokenException("Refresh token expiré ou invalide");
    }

    String newAccessToken = jwtService.generateToken(user);
    return buildAuthResponse(user, newAccessToken, refreshToken);
  }

  // ── Profil ────────────────────────────────────────────────────────────

  public UserDTO findById(UUID id) {
    User user =
        userRepository
            .findById(id)
            .orElseThrow(
                () -> new UserNotFoundException("Utilisateur avec l'id " + id + " introuvable"));
    return mapToDTO(user);
  }

  public UserDTO findByEmail(String email) {
    User user =
        userRepository
            .findByEmail(email)
            .orElseThrow(
                () ->
                    new UserNotFoundException(
                        "Utilisateur avec l'email " + email + " introuvable"));
    return mapToDTO(user);
  }

  @Transactional
  public UserDTO updateProfile(UUID id, UpdateProfileRequest request) {
    User user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable"));

    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());

    return mapToDTO(userRepository.save(user));
  }

  @Transactional
  public void changePassword(UUID id, ChangePasswordRequest request) {
    User user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable"));

    // Vérifier l'ancien mot de passe
    if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
      throw new IllegalArgumentException("Ancien mot de passe incorrect");
    }

    // Vérifier la confirmation
    if (!request.getNewPassword().equals(request.getConfirmPassword())) {
      throw new IllegalArgumentException("Les mots de passe ne correspondent pas");
    }

    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    userRepository.save(user);
  }

  // ── Administration ────────────────────────────────────────────────────

  public List<UserDTO> findAll() {
    return userRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
  }

  @Transactional
  public void updateUserStatus(UUID id, boolean active) {
    User user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable"));

    user.setActive(active);
    userRepository.save(user);
  }

  private AuthResponse buildAuthResponse(User user, String accessToken, String refreshToken) {
    return AuthResponse.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .tokenType("Bearer")
        .userId(user.getId())
        .email(user.getEmail())
        .role(user.getRole())
        .build();
  }

  private UserDTO mapToDTO(User user) {
    return UserDTO.builder()
        .id(user.getId())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .email(user.getEmail())
        .role(user.getRole())
        .isActive(user.isActive())
        .createdAt(user.getCreatedAt())
        .build();
  }
}
