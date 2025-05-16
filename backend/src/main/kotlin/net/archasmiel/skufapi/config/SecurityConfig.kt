package net.archasmiel.skufapi.config

import lombok.RequiredArgsConstructor
import net.archasmiel.skufapi.security.SecurityArgumentResolver
import net.archasmiel.skufapi.service.UserService
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
class SecurityConfig(
    private val jwtAuthFilter: JwtAuthFilter,
    private val userService: UserService
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.cors {  }
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers(
                    "/api/auth/login",
                    "/api/auth/google",
                    "/api/auth/register",
                    "/api/auth/logout",
                    "/api/auth/me").permitAll()
                    .anyRequest().authenticated()
            }
            .sessionManagement{
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(
                authenticationProvider()
            )
            .addFilterBefore(
                jwtAuthFilter,
                UsernamePasswordAuthenticationFilter::class.java
            )

        return http.build()
    }

    @Bean
    fun corsConfigSource(): CorsConfigurationSource {
        val config = CorsConfiguration()
        config.allowedOrigins = listOf("http://localhost:3000")
        config.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        config.allowedHeaders = listOf("*")
        config.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        return source
    }

    @Bean
    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager {
        return authConfig.authenticationManager
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userService.userDetailsService)
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityArgumentResolver(): SecurityArgumentResolver {
        return SecurityArgumentResolver()
    }


}