package net.archasmiel.skufapi.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import java.security.InvalidParameterException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import net.archasmiel.skufapi.model.Role;
import net.archasmiel.skufapi.model.User;
import net.archasmiel.skufapi.repo.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repository;

  public User save(User user) {
    return repository.save(user);
  }

  public User create(User user) {
    if (repository.existsByUsername(user.getUsername())) {
      throw new RuntimeException("Has user with such username");
    }

    if (repository.existsByEmail(user.getEmail())) {
      throw new RuntimeException("Has user with such email");
    }

    return save(user);
  }

  public boolean hasGoogleUserWithUsername(String username) {
    return repository.existsByUsernameAndIsGoogleUser(username, true);
  }

  public boolean hasGoogleUserWithEmail(String email) {
    return repository.existsByEmailAndIsGoogleUser(email, true);
  }

  public boolean hasUserWithUsername(String username) {
    return repository.existsByUsername(username);
  }

  public boolean hasUserWithEmail(String email) {
    return repository.existsByEmail(email);
  }

  public Optional<User> findGoogleUser(GoogleIdToken token) {
    return repository.findByEmailAndIsGoogleUser(token.getPayload().getEmail(), true);
  }

  public User getByUsername(String username) {
    return repository
        .findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
  }

  public User getByEmail(String email) {
    return repository
        .findByEmail(email)
        .orElseThrow(() -> new InvalidParameterException("Email not found"));
  }

  public UserDetailsService userDetailsService() {
    return this::getByUsername;
  }

  public User getCurrentUser() {
    var username = SecurityContextHolder.getContext().getAuthentication().getName();
    return getByUsername(username);
  }

  @Deprecated
  public void getAdmin() {
    var user = getCurrentUser();
    user.setRole(Role.ROLE_ADMIN);
    save(user);
  }
}
