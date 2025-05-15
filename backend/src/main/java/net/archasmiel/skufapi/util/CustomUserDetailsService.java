package net.archasmiel.skufapi.util;

import lombok.RequiredArgsConstructor;
import net.archasmiel.skufapi.model.User;
import net.archasmiel.skufapi.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository
        .findByUsername(username)
        .map(UserDetails.class::cast)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
