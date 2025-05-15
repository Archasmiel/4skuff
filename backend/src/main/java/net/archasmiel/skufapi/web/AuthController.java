package net.archasmiel.skufapi.web;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.archasmiel.skufapi.dto.ErrorResponse;
import net.archasmiel.skufapi.dto.IResponse;
import net.archasmiel.skufapi.dto.auth.GoogleAuthRequest;
import net.archasmiel.skufapi.dto.auth.MeResponse;
import net.archasmiel.skufapi.dto.auth.SignInRequest;
import net.archasmiel.skufapi.dto.auth.SignUpRequest;
import net.archasmiel.skufapi.security.SkufSecurity;
import net.archasmiel.skufapi.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<IResponse> signup(@RequestBody SignUpRequest request) {
    try {
      IResponse response = authService.signUp(request);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      ErrorResponse response = new ErrorResponse("SIGNUP_FAILED", e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
  }

  @PostMapping("/login")
  public ResponseEntity<IResponse> login(@RequestBody SignInRequest request) {
    try {
      IResponse response = authService.signIn(request);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      ErrorResponse response = new ErrorResponse("AUTH_FAILED", e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
  }

  @PostMapping("/google")
  public ResponseEntity<IResponse> loginGoogle(@RequestBody GoogleAuthRequest request) {
    try {
      IResponse response = authService.signIn(request);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      ErrorResponse response = new ErrorResponse("AUTH_FAILED", e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
  }

  @GetMapping("/me")
  public ResponseEntity<IResponse> getMe(@SkufSecurity Authentication auth) {
    try {
      if (auth == null) {
        throw new RuntimeException("No authentication");
      }
      if (auth.getPrincipal() != null && auth.getPrincipal() instanceof UserDetails details) {
        IResponse response =
            new MeResponse(details.getUsername(), details.getAuthorities().toString());
        return ResponseEntity.ok(response);
      }
      throw new RuntimeException("Null or empty user principal");
    } catch (Exception e) {
      ErrorResponse response = new ErrorResponse("REQUEST_FAILED", e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
  }

  @Deprecated
  @GetMapping("/logout")
  public ResponseEntity<IResponse> logout(@SkufSecurity Authentication auth) {
    try {
      if (auth == null) {
        throw new RuntimeException("No authentication");
      }
      if (auth.getPrincipal() != null && auth.getPrincipal() instanceof UserDetails details) {
        IResponse response =
            new MeResponse(details.getUsername(), "Successfully logged out");
        return ResponseEntity.ok(response);
      }
      throw new RuntimeException("Null or empty user principal");
    } catch (Exception e) {
      ErrorResponse response = new ErrorResponse("REQUEST_FAILED", e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
  }
}
