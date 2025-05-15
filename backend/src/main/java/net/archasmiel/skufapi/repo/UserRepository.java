package net.archasmiel.skufapi.repo;

import java.util.Optional;
import net.archasmiel.skufapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

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
