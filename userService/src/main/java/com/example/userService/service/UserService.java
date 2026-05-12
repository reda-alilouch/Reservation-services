package com.example.userService.service;

import com.example.userService.dto.request.ChangePasswordRequest;
import com.example.userService.dto.request.LoginRequest;
import com.example.userService.dto.request.RegisterRequest;
import com.example.userService.dto.request.UpdateProfileRequest;
import com.example.userService.dto.response.AuthResponse;
import com.example.userService.dto.response.UserDTO;
import com.example.userService.entity.User;
import com.example.userService.enums.Role;
import com.example.userService.exception.EmailAlreadyExistsException;
import com.example.userService.exception.InvalidTokenException;
import com.example.userService.exception.UserNotFoundException;
import com.example.userService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service principal du User Service.
 * Contient toute la logique métier :
 * - Inscription et connexion
 * - Gestion des profils
 * - Changement de mot de passe
 * - Administration des comptes
 *
 * Implémente UserDetailsService pour Spring Security
 * (permet de charger un utilisateur par son email)
 */

@Service
@RequiredArgsConstructor  // Lombok génère le constructeur avec tous les champs final
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    // ── Inscription ───────────────────────────────────────────────────────

    @Transactional
    public AuthResponse register(RegisterRequest request) {

        // 1. Email déjà utilisé ?
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "L'email " + request.getEmail() + " est déjà utilisé"
            );
        }

        // 2. Construire l'utilisateur
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // BCrypt
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
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 2. Charger l'utilisateur
        User user = userRepository.findByEmailAndIsActiveTrue(request.getEmail())
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

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable"));

        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new InvalidTokenException("Refresh token expiré ou invalide");
        }

        String newAccessToken = jwtService.generateToken(user);
        return buildAuthResponse(user, newAccessToken, refreshToken);
    }

    // ── Profil ────────────────────────────────────────────────────────────

    public UserDTO findById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        "Utilisateur avec l'id " + id + " introuvable"
                ));
        return mapToDTO(user);
    }

    public UserDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(
                        "Utilisateur avec l'email " + email + " introuvable"
                ));
        return mapToDTO(user);
    }

    @Transactional
    public UserDTO updateProfile(UUID id, UpdateProfileRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable"));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        return mapToDTO(userRepository.save(user));
    }

    @Transactional
    public void changePassword(UUID id, ChangePasswordRequest request) {
        User user = userRepository.findById(id)
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
        return userRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateUserStatus(UUID id, boolean active) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable"));

        user.setIsActive(active);
        userRepository.save(user);
    }

    // ── UserDetailsService (Spring Security) ─────────────────────────────

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Utilisateur avec l'email " + email + " introuvable"
                ));
    }

    // ── Helpers privés ────────────────────────────────────────────────────

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
                .isActive(user.getIsActive())
                .createdAt(user.getCreatedAt())
                .build();
    }
}