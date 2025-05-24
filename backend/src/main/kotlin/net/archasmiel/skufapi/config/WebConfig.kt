package net.archasmiel.skufapi.config

import lombok.RequiredArgsConstructor
import net.archasmiel.skufapi.config.security.JwtAuthFilter
import net.archasmiel.skufapi.config.security.SecurityArgumentResolver
import net.archasmiel.skufapi.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
class WebConfig(
    private val passwordEncoder: PasswordEncoder,
    private val jwtAuthFilter: JwtAuthFilter,
    private val userService: UserService,
    private val securityArgumentResolver: SecurityArgumentResolver
) : WebMvcConfigurer {

    private companion object {
        val ROUTE_WHITELIST = setOf(
            "/api/auth/login",
            "/api/auth/google",
            "/api/auth/register",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/webjars/**",
            )
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(securityArgumentResolver)
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf {
                it.disable()
            }
            .addFilterBefore(
                jwtAuthFilter,
                UsernamePasswordAuthenticationFilter::class.java
            )
            .authorizeHttpRequests {
                it.requestMatchers(
                    *ROUTE_WHITELIST.toTypedArray()
                ).permitAll()
                .anyRequest().authenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(
                authenticationProvider())

        return http.build()
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:3000")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true)
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userService.userDetailsService)
        authProvider.setPasswordEncoder(passwordEncoder)
        return authProvider
    }

}