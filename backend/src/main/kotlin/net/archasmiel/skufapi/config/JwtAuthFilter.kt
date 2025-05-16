package net.archasmiel.skufapi.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import net.archasmiel.skufapi.service.JwtService
import net.archasmiel.skufapi.service.UserService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
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
        private val AUTH_WHITELIST = listOf(
            "/api/auth/login",
            "/api/auth/google",
            "/api/auth/register"
        )
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (request.servletPath in AUTH_WHITELIST) {
            filterChain.doFilter(request, response)
            return
        }

        handleStandardJwt(request, response, filterChain)
    }

    private fun handleStandardJwt(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader(HEADER_NAME)
        if (authHeader.isNullOrBlank() || !authHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response)
            return
        }

        val jwt = authHeader.substring(BEARER_PREFIX.length)
        try {
            val username = jwtService.extractUsername(jwt)
            if (username != null && SecurityContextHolder.getContext().authentication == null) {
                val userDetails = userService.userDetailsService
                    .loadUserByUsername(username)

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    setAuthentication(userDetails, request)
                }
                filterChain.doFilter(request, response)
            }
        } catch (e: Exception) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT")
        }

    }

    private fun setAuthentication(userDetails: UserDetails, request: HttpServletRequest) {
        val authToken = UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.authorities
        ).apply {
            details = WebAuthenticationDetailsSource().buildDetails(request)
        }
        SecurityContextHolder.getContext().authentication = authToken
    }

}