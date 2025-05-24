package net.archasmiel.skufapi.config.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import net.archasmiel.skufapi.api.exception.token.JwtTokenException
import net.archasmiel.skufapi.service.JwtService
import net.archasmiel.skufapi.service.UserService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
@RequiredArgsConstructor
class JwtAuthFilter(
    private val jwtService: JwtService,
    private val userService: UserService
): OncePerRequestFilter() {

    companion object {
        const val BEARER_PREFIX: String = "Bearer "
        const val HEADER_NAME: String = "Authorization"
        private val ROUTE_WHITELIST = setOf(
            "/api/auth/login",
            "/api/auth/google",
            "/api/auth/register",
            "swagger", "api-docs",
        )
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val authHeader = request.getHeader(HEADER_NAME)
                ?: throw JwtTokenException("Missing authorization header")

            if (!authHeader.startsWith(BEARER_PREFIX)) {
                throw JwtTokenException("Invalid authorization format")
            }

            val jwt =  authHeader.substring(BEARER_PREFIX.length).takeIf { it.isNotBlank() }
                ?: throw JwtTokenException("Empty token")

            val username = jwtService.extractUsername(jwt)

            val userDetails = try {
                userService.userDetailsService.loadUserByUsername(username)
            } catch (e: UsernameNotFoundException) {
                throw JwtTokenException("Username not found: $username")
            }

            if (!jwtService.isTokenValid(jwt, userDetails)) {
                throw JwtTokenException("Invalid or expired token")
            }

            setAuthentication(userDetails, request)
            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.message)
        }
    }

    private fun setAuthentication(userDetails: UserDetails, request: HttpServletRequest) {
        val authentication = UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.authorities
        ).apply {
            details = WebAuthenticationDetailsSource().buildDetails(request)
        }

        SecurityContextHolder.createEmptyContext().apply {
            this.authentication = authentication
            SecurityContextHolder.setContext(this)
        }
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return ROUTE_WHITELIST.any { request.servletPath.contains(it) }
    }

}