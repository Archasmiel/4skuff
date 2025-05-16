package net.archasmiel.skufapi.controller

import net.archasmiel.skufapi.api.exception.ErrorResponse
import net.archasmiel.skufapi.api.request.auth.GoogleAuthRequest
import net.archasmiel.skufapi.api.request.auth.LoginRequest
import net.archasmiel.skufapi.api.request.auth.RegisterRequest
import net.archasmiel.skufapi.api.response.ApiResponse
import net.archasmiel.skufapi.api.response.auth.MeResponse
import net.archasmiel.skufapi.exception.token.BearerTokenException
import net.archasmiel.skufapi.security.SecurityContext
import net.archasmiel.skufapi.service.AuthenticationService
import net.archasmiel.skufapi.service.RegistrationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthenticationService,
    private val registerService: RegistrationService
) {

    @PostMapping("/register")
    fun signup(@RequestBody request: RegisterRequest): ResponseEntity<ApiResponse> {
        return try {
            ResponseEntity.ok(registerService.signUp(request))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ErrorResponse("SIGNUP_FAILED", e.message)
            )
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<ApiResponse> {
        return try {
            ResponseEntity.ok(authService.signIn(request))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ErrorResponse("AUTH_FAILED", e.message)
            )
        }
    }

    @PostMapping("/google")
    fun loginGoogle(@RequestBody request: GoogleAuthRequest): ResponseEntity<ApiResponse> {
        return try {
            ResponseEntity.ok(authService.signIn(request))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ErrorResponse("GOOGLE_AUTH_FAILED", e.message)
            )
        }
    }

    @GetMapping("/me")
    fun getMe(@SecurityContext auth: Authentication?): ResponseEntity<ApiResponse> {
        return try {
            val details = auth?.principal as? UserDetails
                ?: throw BearerTokenException("Invalid user principal")

            ResponseEntity.ok(
                MeResponse(details.username, details.authorities.toString())
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ErrorResponse("REQUEST_FAILED", e.message)
            )
        }
    }

    @GetMapping("/logout")
    fun logout(@SecurityContext auth: Authentication?): ResponseEntity<ApiResponse> {
        return try {
            val details = auth?.principal as? UserDetails
                ?: throw BearerTokenException("Invalid user principal")

            // TODO("Needs revoking in JwtService")
            ResponseEntity.ok(
                MeResponse(details.username, "Successfully logged out")
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ErrorResponse("LOGOUT_FAILED", e.message)
            )
        }
    }
}