package net.archasmiel.skufapi.repository

import net.archasmiel.skufapi.api.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
    fun findByUserName(username: String): Optional<User>

    fun findByUserNameAndGoogleUser(username: String, googleUser: Boolean): Optional<User>

    fun findByEmail(email: String): Optional<User>

    fun findByEmailAndGoogleUser(email: String, googleUser: Boolean): Optional<User>

    fun existsByUserNameAndGoogleUser(username: String, googleUser: Boolean): Boolean

    fun existsByEmailAndGoogleUser(email: String, googleUser: Boolean): Boolean

    fun existsByUserName(username: String): Boolean

    fun existsByEmail(email: String): Boolean
}