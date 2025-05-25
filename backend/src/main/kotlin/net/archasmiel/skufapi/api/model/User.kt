package net.archasmiel.skufapi.api.model

import jakarta.persistence.*
import net.archasmiel.skufapi.api.enums.Role
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.Instant

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "username", unique = true, nullable = false, length = 50)
    val userName: String,

    @Column(name = "email", unique = true, nullable = false, length = 100)
    val email: String,

    @Column(name = "password", nullable = false)
    var passWord: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    val role: Role,

    @Column(name = "is_google", nullable = false)
    val googleUser: Boolean = false,

    @Column(name = "enabled", nullable = false)
    val enabled: Boolean = true,

    @Version
    val version: Long? = null,

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    val createdAt: Instant? = null,

    @LastModifiedDate
    @Column(name = "updated_at")
    val updatedAt: Instant? = null
) : UserDetails {

    // UserDetails implementation
    override fun getAuthorities() = setOf(SimpleGrantedAuthority(role.name))
    override fun getPassword() = passWord
    override fun getUsername() = userName
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = enabled

    // Business methods
    fun activate() = copy(enabled = true)
    fun deactivate() = copy(enabled = false)
    fun changeRole(newRole: Role) = copy(role = newRole)
}