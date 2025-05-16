package net.archasmiel.skufapi.domain.model

import jakarta.persistence.*
import net.archasmiel.skufapi.domain.enums.Role
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,

    @Column(name = "username", unique = true, nullable = false)
    val userName: String,

    @Column(name = "email", unique = true, nullable = false)
    val email: String,

    @Column(name = "password", nullable = false)
    private val passWord: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    val role: Role,

    @Column(name = "is_google", nullable = false)
    val googleUser: Boolean = false

) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority(role.name))
    }

    override fun getPassword(): String = passWord

    override fun getUsername(): String = userName

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}