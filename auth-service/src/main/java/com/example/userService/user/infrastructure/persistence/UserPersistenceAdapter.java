package com.example.userService.user.infrastructure.persistence;

import com.example.userService.entity.User;
import com.example.userService.user.application.port.out.UserPersistencePort;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class UserPersistenceAdapter implements UserPersistencePort {
    private final JpaUserRepository repository;
    public UserPersistenceAdapter(JpaUserRepository repository) { this.repository = repository; }
    public Optional<User> findById(UUID id) { return repository.findById(id); }
    public Optional<User> findByEmail(String email) { return repository.findByEmail(email); }
    public Optional<User> findByEmailAndIsActiveTrue(String email) { return repository.findByEmailAndIsActiveTrue(email); }
    public boolean existsByEmail(String email) { return repository.existsByEmail(email); }
    public List<User> findAll() { return repository.findAll(); }
    public User save(User user) { return repository.save(user); }
}
