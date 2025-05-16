package net.archasmiel.skufapi.service

import net.archasmiel.skufapi.api.request.auth.GoogleAuthRequest
import net.archasmiel.skufapi.api.request.auth.LoginRequest
import net.archasmiel.skufapi.api.response.auth.JwtAuthResponse
import net.archasmiel.skufapi.exception.user.GoogleUserExistsException
import net.archasmiel.skufapi.util.GoogleTokenVerifier
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val registrationService: RegistrationService,
    private val userService: UserService,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager,
    private val verifier: GoogleTokenVerifier
) {

    fun signIn(request: LoginRequest): JwtAuthResponse {
        if (userService.hasGoogleUserWithUsername(request.username)) {
            throw GoogleUserExistsException(request.username, false)
        }

        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.username,
                request.password
            )
        )

        val user = userService.userDetailsService.loadUserByUsername(request.username)
        val jwt = jwtService.generateToken(user)
        return JwtAuthResponse(jwt)
    }

    fun signIn(request: GoogleAuthRequest): JwtAuthResponse {
        val email = verifier.extractToken(request.token).payload.email
        val user = userService.findGoogleUser(email)
            ?: return registrationService.signUp(email)

        val jwt = jwtService.generateToken(user)
        return JwtAuthResponse(jwt)
    }
}