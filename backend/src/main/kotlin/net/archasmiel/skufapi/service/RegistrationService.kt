package net.archasmiel.skufapi.service

import net.archasmiel.skufapi.api.enums.Role
import net.archasmiel.skufapi.api.exception.token.JwtTokenException
import net.archasmiel.skufapi.api.exception.user.UserExistException
import net.archasmiel.skufapi.api.model.User
import net.archasmiel.skufapi.api.request.auth.RegisterRequest
import net.archasmiel.skufapi.api.response.auth.JwtAuthResponse
import net.archasmiel.skufapi.util.UUIDGenerator
import org.springframework.stereotype.Service

@Service
class RegistrationService(
    private val uuidGenerator: UUIDGenerator,
    private val userService: UserService,
    private val jwtService: JwtService
) {

    @Throws(JwtTokenException::class, UserExistException::class)
    fun signUp(request: RegisterRequest): JwtAuthResponse {
        when {
            userService.existsByUsername(request.username) ->
                throw UserExistException(request.username, false)

            userService.existsByEmail(request.email) ->
                throw UserExistException(request.email, true)
        }

        val user = userService.create(User(
            userName = request.username,
            email = request.email,
            passWord = uuidGenerator.password(),
            role = Role.USER,
            googleUser = false
        ))

        val token = jwtService.generateToken(user)
        return JwtAuthResponse(token)
    }

    @Throws(JwtTokenException::class, UserExistException::class)
    fun signUp(googleEmail: String): JwtAuthResponse {
        val user = userService.create(User(
            userName = uuidGenerator.username(),
            email = googleEmail,
            passWord = "",
            role = Role.USER,
            googleUser = true
        ))

        val token = jwtService.generateToken(user)
        return JwtAuthResponse(token)
    }
}