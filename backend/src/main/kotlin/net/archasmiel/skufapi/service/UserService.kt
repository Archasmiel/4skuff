package net.archasmiel.skufapi.service

import net.archasmiel.skufapi.domain.model.User
import net.archasmiel.skufapi.domain.repository.UserRepository
import net.archasmiel.skufapi.exception.user.EmailNotFoundException
import net.archasmiel.skufapi.exception.user.UserExistsException
import net.archasmiel.skufapi.exception.user.UsernameNotFoundException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserService(
    private val repository: UserRepository
) {

    val userDetailsService: UserDetailsService
        get() = UserDetailsService { this.getByUsername(it) }

    fun save(user: User): User = repository.save(user)

    fun create(user: User): User {
        when {
            repository.existsByUserName(user.userName) ->
                throw UserExistsException(user.userName, false)

            repository.existsByEmail(user.email) ->
                throw UserExistsException(user.email, true)
        }
        return save(user)
    }

    fun hasGoogleUserWithUsername(username: String): Boolean =
        repository.existsByUserNameAndGoogleUser(username, true)

    fun hasGoogleUserWithEmail(email: String): Boolean =
        repository.existsByEmailAndGoogleUser(email, true)

    fun hasUserWithUsername(username: String): Boolean =
        repository.existsByUserName(username)

    fun hasUserWithEmail(email: String): Boolean =
        repository.existsByEmail(email)

    fun findGoogleUser(email: String): User? =
        repository.findByEmailAndGoogleUser(email, true)
            .orElseGet { null }

    fun getByUsername(username: String): User =
        repository.findByUserName(username)
            .orElseThrow { UsernameNotFoundException(username) }

    fun getByEmail(email: String): User =
        repository.findByEmail(email)
            .orElseThrow { EmailNotFoundException(email) }
}