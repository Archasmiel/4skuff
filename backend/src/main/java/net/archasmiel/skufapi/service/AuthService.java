package net.archasmiel.skufapi.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import java.io.IOException;
import java.security.GeneralSecurityException;
import lombok.RequiredArgsConstructor;
import net.archasmiel.skufapi.dto.auth.GoogleAuthRequest;
import net.archasmiel.skufapi.dto.auth.JwtAuthResponse;
import net.archasmiel.skufapi.dto.auth.SignInRequest;
import net.archasmiel.skufapi.dto.auth.SignUpRequest;
import net.archasmiel.skufapi.exception.GoogleTokenVerificationException;
import net.archasmiel.skufapi.model.Role;
import net.archasmiel.skufapi.model.User;
import net.archasmiel.skufapi.util.UUIDGenerator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserService userService;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final UUIDGenerator uuidGenerator;
  private final AuthenticationManager authenticationManager;
  private final GoogleIdTokenVerifier verifier;

  public JwtAuthResponse signUp(SignUpRequest request) {
    if (userService.hasUserWithUsername(request.getUsername())) {
      throw new RuntimeException("Username already exist");
    }
    if (userService.hasUserWithEmail(request.getEmail())) {
      throw new RuntimeException("Email already exist");
    }

    var user =
        User.builder()
            .username(request.getUsername())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.ROLE_USER)
            .isGoogleUser(false)
            .build();

    userService.create(user);

    var jwt = jwtService.generateToken(user);
    return new JwtAuthResponse(jwt);
  }

  public JwtAuthResponse signIn(SignInRequest request) {
    if (userService.hasGoogleUserWithUsername(request.getUsername())) {
      throw new RuntimeException("Google user already exist");
    }

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

    var user = userService.userDetailsService().loadUserByUsername(request.getUsername());

    var jwt = jwtService.generateToken(user);
    return new JwtAuthResponse(jwt);
  }

  /**
   * Verify Google IdToken and get {@link GoogleIdToken} from it.
   *
   * @param googleToken token as {@link String}
   * @return info about token as {@link GoogleIdToken}
   * @throws GoogleTokenVerificationException throws if null token or verification exceptions
   */
  public GoogleIdToken verifyGoogleIdToken(final String googleToken)
      throws GoogleTokenVerificationException {
    try {
      GoogleIdToken idToken = verifier.verify(googleToken);
      if (idToken == null) {
        throw new GoogleTokenVerificationException("Token wrong, malformed or expired");
      }
      return idToken;
    } catch (GeneralSecurityException e) {
      throw new GoogleTokenVerificationException("Server security problem");
    } catch (IOException e) {
      throw new GoogleTokenVerificationException("Server I/O exception");
    }
  }

  public JwtAuthResponse signIn(GoogleAuthRequest request) throws GoogleTokenVerificationException {
    String googleToken = request.getToken();
    GoogleIdToken idToken = verifyGoogleIdToken(googleToken);

    var user =
        userService
            .findGoogleUser(idToken)
            .orElseGet(
                () -> {
                  User newUser =
                      User.builder()
                          .username(uuidGenerator.usernameFromUUID())
                          .email(idToken.getPayload().getEmail())
                          .password(passwordEncoder.encode(uuidGenerator.numsFromUUID2x()))
                          .role(Role.ROLE_USER)
                          .isGoogleUser(true)
                          .build();
                  return userService.create(newUser);
                });

    var jwt = jwtService.generateToken(user);
    return new JwtAuthResponse(jwt);
  }
}
