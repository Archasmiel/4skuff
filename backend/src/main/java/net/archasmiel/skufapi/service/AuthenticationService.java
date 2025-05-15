package net.archasmiel.skufapi.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import lombok.RequiredArgsConstructor;
import net.archasmiel.skufapi.api.request.auth.GoogleAuthRequest;
import net.archasmiel.skufapi.api.request.auth.LoginRequest;
import net.archasmiel.skufapi.api.response.auth.JwtAuthResponse;
import net.archasmiel.skufapi.exception.GoogleTokenException;
import net.archasmiel.skufapi.util.GoogleTokenVerifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RegistrationService regService;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final GoogleTokenVerifier verifier;

    public JwtAuthResponse signIn(LoginRequest request) throws RuntimeException {
        if (userService.hasGoogleUserWithUsername(request.getUsername())) {
            throw new RuntimeException("Google user already exist");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()));

        var user = userService.userDetailsService().loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthResponse(jwt);
    }

    public JwtAuthResponse signIn(GoogleAuthRequest request) throws GoogleTokenException {
        String googleToken = request.getToken();
        GoogleIdToken idToken = verifier.getToken(googleToken);

        var user = userService.findGoogleUser(idToken);
        if (user.isEmpty()) {
            return regService.signUp(idToken.getPayload().getEmail());
        }

        var jwt = jwtService.generateToken(user.get());
        return new JwtAuthResponse(jwt);
    }

}
