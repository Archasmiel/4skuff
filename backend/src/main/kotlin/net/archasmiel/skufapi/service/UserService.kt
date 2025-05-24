package net.archasmiel.skufapi.service

import net.archasmiel.skufapi.api.enums.Role
import net.archasmiel.skufapi.api.exception.user.ResourceNotFoundException
import net.archasmiel.skufapi.api.exception.user.UserExistException
import net.archasmiel.skufapi.api.model.User
import net.archasmiel.skufapi.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
@Transactional
class UserService(
    private val repository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    val userDetailsService: UserDetailsService
        get() = UserDetailsService { username ->
            getByUsername(username).let { user ->
                org.springframework.security.core.userdetails.User(
                    user.userName,
                    user.password,
                    user.enabled,
                    true,  // accountNonExpired
                    true,  // credentialsNonExpired
                    true,  // accountNonLocked
                    user.authorities
                )
            }
        }

    // ========== CRUD Operations ==========
    fun save(user: User): User {
        return repository.save(user.apply {
            passWord = when {
                googleUser -> ""
                password.isBlank() -> password
                else -> passwordEncoder.encode(password)
            }
        })
    }

    @Throws(UserExistException::class)
    fun create(user: User): User {
        validateUserCreation(user)
        return save(
            User(
                userName = user.userName,
                email = user.email,
                passWord = user.password,
                role = user.role,
                googleUser = user.googleUser,
                createdAt = user.createdAt ?: Instant.now(),
                updatedAt = Instant.now(),
                version = user.version?.plus(1) ?: 1
            )
        )
    }

    @Throws(ResourceNotFoundException::class)
    fun remove(username: String) {
        if (!repository.existsByUserName(username)) {
            throw ResourceNotFoundException("username", username)
        }
        repository.deleteByUserName(username)
    }

    @Throws(ResourceNotFoundException::class)
    fun update(user: User): User {
        val existing = repository.findById(
            user.id ?: throw ResourceNotFoundException("id", "null")
        )
            .orElseThrow { ResourceNotFoundException("id", user.id.toString()) }

        return save(
            existing.copy(
                userName = user.userName.takeIf { it.isNotBlank() } ?: existing.userName,
            email = user.email.takeIf { it.isValidEmail() } ?: existing.email,
            role = user.role,
            googleUser = user.googleUser,
            enabled = user.enabled,
            // Only update password if new one provided and not Google user
            passWord = passwordValidator(user, existing)
        ))
    }

    // ========== Enhanced Query Operations ==========
    fun getAll(pageable: Pageable = Pageable.unpaged()): Page<User> =
        repository.findAll(pageable)

    fun getAll(start: String): MutableList<User> =
        repository.findAllByUserNameContaining(start)

    fun getAll(): List<User> = repository.findAll()

    fun getById(id: Long): User = repository.findById(id)
        .orElseThrow { ResourceNotFoundException("id", id.toString()) }

    fun search(
        query: String,
        pageable: Pageable,
        searchType: SearchType = SearchType.USERNAME
    ): Page<User> = when (searchType) {
        SearchType.USERNAME -> repository.findByUserNameContainingIgnoreCase(query, pageable)
        SearchType.EMAIL -> repository.findByEmailContainingIgnoreCase(query, pageable)
    }

    enum class SearchType { USERNAME, EMAIL }

    // ========== Auth Methods ==========
    fun getByUsername(username: String): User =
        repository.findByUserName(username)
            .orElseThrow { ResourceNotFoundException("username", username) }

    fun getByEmail(email: String): User =
        repository.findByEmail(email)
            .orElseThrow { ResourceNotFoundException("email", email) }

    fun existsByUsername(username: String): Boolean =
        repository.existsByUserName(username)

    fun existsByEmail(email: String): Boolean =
        repository.existsByEmail(email)

    fun existsByEmailAndGoogleUser(email: String): Boolean =
        repository.existsByEmailAndGoogleUser(email, true)

    fun existsByUsernameAndGoogleUser(username: String): Boolean =
        repository.existsByUserNameAndGoogleUser(username, true)

    fun findGoogleUserByEmail(email: String): User? =
        repository.findByEmailAndGoogleUser(email, true)
            .orElseGet { null }

    // ========== Business Logic ==========
    fun changePassword(username: String, oldPassword: String, newPassword: String) {
        val user = getByUsername(username).takeUnless { it.googleUser }
            ?: throw IllegalStateException("Google users cannot change password")

        if (!passwordEncoder.matches(oldPassword, user.password)) {
            throw SecurityException("Invalid current password")
        }

        save(user.copy(passWord = newPassword))
    }

    fun toggleUserStatus(username: String, active: Boolean): User {
        return update(getByUsername(username).copy(enabled = active))
    }

    fun promoteToAdmin(username: String): User {
        return update(getByUsername(username).copy(role = Role.ADMIN))
    }

    // ========== Validation ==========
    private fun validateUserCreation(user: User) {
        require(user.userName.isNotBlank()) { "Username cannot be blank" }
        require(user.email.isValidEmail()) { "Invalid email format" }

        when {
            repository.existsByUserName(user.userName) ->
                throw UserExistException(user.userName, false)

            repository.existsByEmail(user.email) ->
                throw UserExistException(user.email, true)
        }
    }

    private fun String.isValidEmail(): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        return Regex(emailRegex).matches(this)
    }

    private fun passwordValidator(newUser: User, oldUser: User): String =
        if (!newUser.googleUser && newUser.password.isNotBlank()) newUser.password else oldUser.password

}