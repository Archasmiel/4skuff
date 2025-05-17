package net.archasmiel.skufapi.service

import net.archasmiel.skufapi.api.request.auth.RegisterRequest
import net.archasmiel.skufapi.api.response.auth.JwtAuthResponse
import net.archasmiel.skufapi.domain.model.User
import net.archasmiel.skufapi.exception.token.JwtTokenException
import net.archasmiel.skufapi.exception.user.UserExistException
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

    @Throws(JwtTokenException::class, UserExistException::class)
    fun signUp(request: RegisterRequest): JwtAuthResponse {
        when {
            userService.hasUserWithUsername(request.username) ->
                throw UserExistException(request.username, false)

            userService.hasUserWithEmail(request.email) ->
                throw UserExistException(request.email, true)
        }

        val user = User.fromRegisterUser(
            request, passwordEncoder)

        userService.create(user)

        val token = jwtService.generateToken(user)
        return JwtAuthResponse(token)
    }

    @Throws(JwtTokenException::class, UserExistException::class)
    fun signUp(googleEmail: String): JwtAuthResponse {
        val user = User.fromGoogleEmail(
            googleEmail, passwordEncoder, uuidGenerator)

        userService.create(user)

        val token = jwtService.generateToken(user)
        return JwtAuthResponse(token)
    }
}