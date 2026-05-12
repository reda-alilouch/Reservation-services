package com.example.userService.repository;

import com.example.userService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    // ─────────────────────────────────────────────────────────────────────
    // Méthodes de recherche — Spring génère le SQL automatiquement
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Chercher un utilisateur par son email.
     * SQL généré : SELECT * FROM users WHERE email = ?
     *
     * @param email l'adresse email à rechercher
     * @return Optional<User> — contient l'utilisateur s'il existe, vide sinon
     */
    Optional<User> findByEmail(String email);

    /**
     * Vérifier si un email est déjà utilisé.
     * SQL généré : SELECT COUNT(*) > 0 FROM users WHERE email = ?
     * Utilisé dans l'inscription pour éviter les doublons.
     *
     * @param email l'adresse email à vérifier
     * @return true si l'email existe déjà, false sinon
     */
    boolean existsByEmail(String email);

    /**
     * Récupérer tous les utilisateurs actifs.
     * SQL généré : SELECT * FROM users WHERE is_active = true
     *
     * @return liste des utilisateurs actifs
     */
    List<User> findByIsActiveTrue();

    /**
     * Récupérer tous les utilisateurs par rôle.
     * SQL généré : SELECT * FROM users WHERE role = ?
     *
     * @param role le rôle à filtrer (CLIENT, PRESTATAIRE, ADMIN)
     * @return liste des utilisateurs avec ce rôle
     */
    List<User> findByRole(com.example.userService.enums.Role role);

    /**
     * Chercher un utilisateur actif par son email.
     * SQL généré : SELECT * FROM users WHERE email = ? AND is_active = true
     * Utilisé à la connexion pour bloquer les comptes désactivés.
     *
     * @param email l'adresse email
     * @return Optional<User>
     */
    Optional<User> findByEmailAndIsActiveTrue(String email);

    // ─────────────────────────────────────────────────────────────────────
    // Requêtes personnalisées avec @Query (JPQL)
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Rechercher des utilisateurs par nom ou prénom (insensible à la casse).
     * Utilisé par l'admin pour rechercher un utilisateur dans le tableau de bord.
     *
     * @param keyword le mot-clé de recherche
     * @return liste des utilisateurs correspondants
     */
    @Query("SELECT u FROM User u WHERE " +
            "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.lastName)  LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.email)     LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<User> searchByKeyword(@Param("keyword") String keyword);

    /**
     * Compter les utilisateurs par rôle.
     * Utilisé dans le tableau de bord admin pour les statistiques.
     *
     * @param role le rôle à compter
     * @return nombre d'utilisateurs avec ce rôle
     */
    @Query("SELECT COUNT(u) FROM User u WHERE u.role = :role")
    long countByRole(@Param("role") com.example.userService.enums.Role role);
}
