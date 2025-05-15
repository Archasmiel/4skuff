package net.archasmiel.skufapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.archasmiel.skufapi.api.exception.ErrorResponse;
import net.archasmiel.skufapi.api.response.ApiResponse;
import net.archasmiel.skufapi.api.request.auth.GoogleAuthRequest;
import net.archasmiel.skufapi.api.response.auth.MeResponse;
import net.archasmiel.skufapi.api.request.auth.LoginRequest;
import net.archasmiel.skufapi.api.request.auth.RegisterRequest;
import net.archasmiel.skufapi.security.SecurityContext;
import net.archasmiel.skufapi.service.AuthenticationService;
import net.archasmiel.skufapi.service.RegistrationService;
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

    private final AuthenticationService authService;
    private final RegistrationService regService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> signup(@RequestBody RegisterRequest request) {
        try {
            ApiResponse response = regService.signUp(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse response = new ErrorResponse("SIGNUP_FAILED", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        try {
            ApiResponse response = authService.signIn(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse response = new ErrorResponse("AUTH_FAILED", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/google")
    public ResponseEntity<ApiResponse> loginGoogle(@RequestBody GoogleAuthRequest request) {
        try {
            ApiResponse response = authService.signIn(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse response = new ErrorResponse("AUTH_FAILED", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getMe(@SecurityContext Authentication auth) {
        try {
            if (auth == null) {
                throw new RuntimeException("No authentication");
            }
            if (auth.getPrincipal() != null && auth.getPrincipal() instanceof UserDetails details) {
                ApiResponse response =
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
    public ResponseEntity<ApiResponse> logout(@SecurityContext Authentication auth) {
        try {
            if (auth == null) {
                throw new RuntimeException("No authentication");
            }
            if (auth.getPrincipal() != null && auth.getPrincipal() instanceof UserDetails details) {
                ApiResponse response =
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
