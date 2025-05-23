package net.archasmiel.skufapi.api.response.auth

import io.swagger.v3.oas.annotations.media.Schema
import net.archasmiel.skufapi.api.response.ApiResponse

@Schema(description = "Authentication response with JWT token")
data class JwtAuthResponse(
    @field:Schema(description = "Jwt token", example = "eyJhbGciOiJIUzUxMiJ9.ayJHgU...")
    val jwtToken: String
) : ApiResponse