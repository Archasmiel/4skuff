package net.archasmiel.skufapi.service

import net.archasmiel.skufapi.api.request.auth.RegisterRequest
import net.archasmiel.skufapi.api.response.auth.JwtAuthResponse
import net.archasmiel.skufapi.domain.enums.Role
import net.archasmiel.skufapi.domain.model.User
import net.archasmiel.skufapi.exception.user.UserExistsException
import net.archasmiel.skufapi.util.UUIDGenerator
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class RegistrationService(
    private val passwordEncoder: PasswordEncoder,
    private val uuidGenerator: UUIDGenerator,
    private val userService: UserService,
    private val jwtService: JwtService
) {

    fun signUp(request: RegisterRequest): JwtAuthResponse {
        when {
            userService.hasUserWithUsername(request.username) ->
                throw UserExistsException(request.username, false)
            userService.hasUserWithEmail(request.email) ->
                throw UserExistsException(request.email, true)
        }

        val password = passwordEncoder.encode(request.password)
        val user = User(
            null,
            request.username,
            request.email,
            password,
            Role.ROLE_USER,
            false
        )

        userService.create(user)

        val token = jwtService.generateToken(user)
        return JwtAuthResponse(token)
    }

    fun signUp(googleEmail: String): JwtAuthResponse {
        when {
            userService.hasUserWithEmail(googleEmail) ->
                throw UserExistsException(googleEmail, true)
        }

        val password = passwordEncoder.encode(uuidGenerator.password())

        val user = User(
            null,
            uuidGenerator.username(),
            googleEmail,
            password,
            Role.ROLE_USER,
            true
        )

        userService.create(user)

        val token = jwtService.generateToken(user)
        return JwtAuthResponse(token)
    }
}