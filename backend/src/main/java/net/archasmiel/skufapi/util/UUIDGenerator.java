package net.archasmiel.skufapi.util;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import net.archasmiel.skufapi.repo.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UUIDGenerator {

  private final UserRepository userRepository;
  private static final int MAX_ATTEMPTS = 100;

  public String usernameFromUUID() {
    return "user_" + UUID.randomUUID().toString().substring(0, 8);
  }

  public String numsFromUUID1x() {
    return UUID.randomUUID().toString().substring(0, 8);
  }

  public String numsFromUUID2x() {
    return numsFromUUID1x() + numsFromUUID1x();
  }

  public String numsFromUUID4x() {
    return numsFromUUID2x() + numsFromUUID2x();
  }

  public String generateUniqueUsername(String email, String fullName) {
    String baseUsername = createBaseUsername(fullName);

    // First try the base username
    if (!userRepository.existsByUsername(baseUsername)) {
      return baseUsername;
    }

    // Then try email prefix
    String emailPrefix = extractEmailPrefix(email);
    if (!userRepository.existsByUsername(emailPrefix)) {
      return emailPrefix;
    }

    // Then try combinations with incrementing numbers
    for (int i = 1; i <= MAX_ATTEMPTS; i++) {
      String candidate = baseUsername + i;
      if (!userRepository.existsByUsername(candidate)) {
        return candidate;
      }
    }

    // Fallback to UUID if all else fails
    return "user_" + UUID.randomUUID().toString().substring(0, 8);
  }

  private String createBaseUsername(String fullName) {
    return Arrays.stream(fullName.split(" "))
        .map(String::toLowerCase)
        .map(s -> s.replaceAll("[^a-z0-9]", ""))
        .filter(s -> !s.isEmpty())
        .collect(Collectors.joining("_"));
  }

  private String extractEmailPrefix(String email) {
    return email.substring(0, email.indexOf('@')).replaceAll("[^a-zA-Z0-9]", "_").toLowerCase();
  }
}
