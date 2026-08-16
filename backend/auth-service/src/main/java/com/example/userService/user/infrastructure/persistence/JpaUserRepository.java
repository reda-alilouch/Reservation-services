package com.example.userService.user.infrastructure.persistence;

import com.example.userService.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface JpaUserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByEmail(String email);

  Optional<User> findByEmailAndIsActiveTrue(String email);

  boolean existsByEmail(String email);
}
