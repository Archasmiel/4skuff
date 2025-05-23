package net.archasmiel.skufapi.api.request.auth

import io.swagger.v3.oas.annotations.media.Schema
import net.archasmiel.skufapi.api.request.ApiRequest

@Schema(description = "Login request via Google")
data class GoogleAuthRequest(
    @field:Schema(description = "Google id token", example = "eyJhbGciOiJIUzUxMiJ9...")
    val token: String
) : ApiRequest