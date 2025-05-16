package net.archasmiel.skufapi.api.response.auth

import io.swagger.v3.oas.annotations.media.Schema
import net.archasmiel.skufapi.api.response.ApiResponse

@Schema(description = "Response with user details")
data class MeResponse(
    @field:Schema(description = "Username", example = "john_doe")
    val username: String,

    @field:Schema(description = "User roles", example = "ROLE_USER,ROLE_ADMIN")
    val roles: String
) : ApiResponse