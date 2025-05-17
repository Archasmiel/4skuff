package net.archasmiel.skufapi.api.response.auth

import io.swagger.v3.oas.annotations.media.Schema
import net.archasmiel.skufapi.api.response.ApiResponse

@Schema(description = "Response with Jwt token")
data class JwtAuthResponse(
    @field:Schema(description = "Jwt token", example = "eyJhbGciOiJIUzUxMiJ9...")
    val jwtToken: String
) : ApiResponse