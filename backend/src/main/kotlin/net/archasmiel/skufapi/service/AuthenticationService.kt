package net.archasmiel.skufapi.service

import net.archasmiel.skufapi.api.exception.auth.AuthenticationException
import net.archasmiel.skufapi.api.exception.token.GoogleTokenException
import net.archasmiel.skufapi.api.exception.token.JwtTokenException
import net.archasmiel.skufapi.api.exception.user.GoogleUserExistException
import net.archasmiel.skufapi.api.exception.user.UserExistException
import net.archasmiel.skufapi.api.request.auth.GoogleAuthRequest
import net.archasmiel.skufapi.api.request.auth.LoginRequest
import net.archasmiel.skufapi.api.response.auth.JwtAuthResponse
import net.archasmiel.skufapi.util.GoogleTokenVerifier
import org.springframework.security.authentication.*
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val registrationService: RegistrationService,
    private val userService: UserService,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager,
    private val verifier: GoogleTokenVerifier
) {

    @Throws(
        GoogleUserExistException::class,
        AuthenticationException::class,
        UsernameNotFoundException::class,
        JwtTokenException::class
    )
    fun signIn(request: LoginRequest): JwtAuthResponse {
        if (request.username.isBlank() || request.password.isBlank()) {
            throw AuthenticationException("Username and password must not be empty")
        }

        if (userService.hasGoogleUserWithUsername(request.username)) {
            throw GoogleUserExistException(request.username, false)
        }

        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    request.username.trim(),
                    request.password
                )
            )
        } catch (e: BadCredentialsException) {
            throw AuthenticationException("Invalid username or password")
        } catch (e: DisabledException) {
            throw AuthenticationException("Account is disabled")
        } catch (e: LockedException) {
            throw AuthenticationException("Account is locked")
        } catch (e: UsernameNotFoundException) {
            throw UsernameNotFoundException("User not found: ${request.username}")
        } catch (e: Exception) {
            throw AuthenticationException("Authentication failed: ${e.message}")
        }

        return try {
            val user = userService.userDetailsService.loadUserByUsername(request.username)
            val jwt = jwtService.generateToken(user)
            JwtAuthResponse(jwt)
        } catch (e: Exception) {
            throw JwtTokenException("Token generation failed: ${e.message}")
        }
    }

    @Throws(
        GoogleTokenException::class,
        JwtTokenException::class,
        UserExistException::class
    )
    fun signIn(request: GoogleAuthRequest): JwtAuthResponse {
        val email = verifier.extractToken(request.token)
            .takeIf { it.payload?.email != null }
            ?.payload?.email
            ?: throw GoogleTokenException("Invalid token: missing email")

        return userService.findGoogleUserByEmail(email)
            ?.let { user ->
                val jwt = jwtService.generateToken(user)
                JwtAuthResponse(jwt)
            }
            ?: registrationService.signUp(email)
    }
}