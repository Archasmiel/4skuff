package net.archasmiel.skufapi.web;

import java.util.Collections;
import java.util.Map;
import lombok.AllArgsConstructor;
import net.archasmiel.skufapi.service.JwtService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Deprecated
public class UserController {

    private final JwtService jwtService;

    @GetMapping("/token")
    public Map<String, String> token() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null && auth.getPrincipal() instanceof UserDetails details) {
            return Map.of(
                    "access_token", jwtService.generateToken(details),
                    "token_type", "bearer",
                    "expires_in", "" + jwtService.getJwtExpirationMs()
            );
        }
        return Collections.emptyMap();
    }

    @GetMapping("/me")
    public String me() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UserDetails details) {
            return details.getUsername();
        }
        return "Not authenticated";
    }

    @GetMapping("/user")
    public String user() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getPrincipal() instanceof UserDetails details) {
            System.out.println("Auth = " + auth);
            return Map.of(
                    "name", details.getUsername(),
                    "roles", details.getAuthorities()
            ).toString();
        }
        return "Not authenticated";
    }

    @GetMapping("/")
    public Map<String, Object> home() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UserDetails details) {
            return Map.of(
                    "message", "Welcome to the API",
                    "user", Map.of(
                            "name", details.getUsername(),
                            "roles", details.getAuthorities()
                    ),
                    "loginLink", "null"
            );
        }

        return Map.of(
                "message", "Welcome to the API - Please login",
                "loginLink", "/oauth2/authorization/google",
                "user", "null"
        );
    }

//    @PostMapping("/api/auth")
//    public void addUser(@RequestBody User user) {
//        userRepository.save(user);
//    }
//
//    @GetMapping("/api/auth")
//    public List<User> all() {
//        return userRepository.findAll();
//    }

}
