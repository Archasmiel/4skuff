package net.archasmiel.skufapi.controller

import net.archasmiel.skufapi.api.request.auth.GoogleAuthRequest
import net.archasmiel.skufapi.api.request.auth.LoginRequest
import net.archasmiel.skufapi.api.request.auth.RegisterRequest
import net.archasmiel.skufapi.api.response.ApiResponse
import net.archasmiel.skufapi.api.response.auth.MeResponse
import net.archasmiel.skufapi.exception.auth.AuthenticationException
import net.archasmiel.skufapi.exception.token.GoogleTokenException
import net.archasmiel.skufapi.exception.token.JwtTokenException
import net.archasmiel.skufapi.exception.user.GoogleUserExistException
import net.archasmiel.skufapi.exception.user.UserExistException
import net.archasmiel.skufapi.security.SecurityContext
import net.archasmiel.skufapi.service.AuthenticationService
import net.archasmiel.skufapi.service.RegistrationService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthenticationService,
    private val registerService: RegistrationService
) {

    @Throws(
        JwtTokenException::class,
        UserExistException::class)
    @PostMapping("/register")
    fun signup(@RequestBody request: RegisterRequest): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(
            registerService.signUp(request)
        )
    }

    @Throws(
        AuthenticationException::class,
        UsernameNotFoundException::class,
        JwtTokenException::class,
        GoogleUserExistException::class)
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(
            authService.signIn(request)
        )
    }

    @Throws(
        UserExistException::class,
        JwtTokenException::class,
        GoogleTokenException::class)
    @PostMapping("/google")
    fun loginGoogle(@RequestBody request: GoogleAuthRequest): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(
            authService.signIn(request)
        )
    }

    @GetMapping("/me")
    fun getMe(@SecurityContext user: UserDetails): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(
            MeResponse(user.username, "Authorities: ${user.authorities}")
        )
    }

    @GetMapping("/logout")
    fun logout(@SecurityContext user: UserDetails): ResponseEntity<ApiResponse> {
        return ResponseEntity.ok(
            MeResponse(user.username, "Successfully logged out")
        )
    }
}