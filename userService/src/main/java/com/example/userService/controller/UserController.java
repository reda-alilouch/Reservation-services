package com.example.userService.controller;

import com.example.userService.dto.request.ChangePasswordRequest;
import com.example.userService.dto.request.UpdateProfileRequest;
import com.example.userService.dto.response.UserDTO;
import com.example.userService.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMe(@AuthenticationPrincipal UserDetails userDetails) {
        UserDTO user = userService.findByEmail(userDetails.getUsername());
        return ResponseEntity.ok(user);
    }

    @PutMapping ("/me")
    public ResponseEntity<UserDTO> updateMe(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody UpdateProfileRequest request) {

        UserDTO current = userService.findByEmail(userDetails.getUsername());
        UserDTO update = userService.updateProfile(current.getId(),request);
        return ResponseEntity.ok(update);
    }

    @PutMapping ("/me/password")
    public ResponseEntity<UserDTO> updateMe(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody ChangePasswordRequest request) {

        UserDTO current = userService.findByEmail(userDetails.getUsername());
        userService.changePassword(current.getId(),request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> updateUserStatus(
            @PathVariable UUID id,
            @RequestParam boolean active) {
        userService.updateUserStatus(id, active);
        return ResponseEntity.noContent().build();
    }
}
