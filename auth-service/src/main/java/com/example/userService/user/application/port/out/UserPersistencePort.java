package com.example.userService.user.application.port.out;

import com.example.userService.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserPersistencePort {
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndIsActiveTrue(String email);
    boolean existsByEmail(String email);
    List<User> findAll();
    User save(User user);
}
