package net.archasmiel.skufapi.repository

import net.archasmiel.skufapi.api.enums.Role
import net.archasmiel.skufapi.api.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    fun findByUserName(username: String): Optional<User>
    fun findByEmail(email: String): Optional<User>
    fun existsByUserName(username: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun existsByEmailAndGoogleUser(email: String, googleUser: Boolean): Boolean
    fun existsByUserNameAndGoogleUser(email: String, googleUser: Boolean): Boolean
    fun deleteByUserName(username: String)
    fun findByEmailAndGoogleUser(email: String, googleUser: Boolean): Optional<User>
    fun findByUserNameContainingIgnoreCase(query: String, pageable: Pageable): Page<User>
    fun findByEmailContainingIgnoreCase(query: String, pageable: Pageable): Page<User>
    fun countByEnabled(enabled: Boolean): Long
    fun countByRole(role: Role): Long
    fun findByCreatedAtAfter(date: Instant): List<User>
    fun findAllByUserNameContaining(username: String): MutableList<User>
}