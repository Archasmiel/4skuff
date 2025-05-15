package net.archasmiel.skufapi.domain.repository;

import net.archasmiel.skufapi.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndIsGoogleUser(String username, boolean googleUser);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndIsGoogleUser(String email, boolean googleUser);

    boolean existsByUsernameAndIsGoogleUser(String username, boolean googleUser);

    boolean existsByEmailAndIsGoogleUser(String username, boolean isGoogleUser);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
