package net.archasmiel.skufapi.service;

import lombok.RequiredArgsConstructor;
import net.archasmiel.skufapi.api.request.auth.RegisterRequest;
import net.archasmiel.skufapi.api.response.auth.JwtAuthResponse;
import net.archasmiel.skufapi.domain.enums.Role;
import net.archasmiel.skufapi.domain.model.User;
import net.archasmiel.skufapi.util.UUIDGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UUIDGenerator uuidGenerator;

    public JwtAuthResponse signUp(RegisterRequest request) {
        if (userService.hasUserWithUsername(request.getUsername())) {
            throw new RuntimeException("Username already exist");
        }
        if (userService.hasUserWithEmail(request.getEmail())) {
            throw new RuntimeException("Email already exist");
        }

        var user = User.builder()
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

    public JwtAuthResponse signUp(String googleMail) {
        // Already suspecting that user doesn't exist
        String password = uuidGenerator.password();
        password = passwordEncoder.encode(password);

        User user = User.builder()
                .username(uuidGenerator.username())
                .email(googleMail)
                .password(password)
                .role(Role.ROLE_USER)
                .isGoogleUser(true)
                .build();

        userService.create(user);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthResponse(jwt);
    }

}
