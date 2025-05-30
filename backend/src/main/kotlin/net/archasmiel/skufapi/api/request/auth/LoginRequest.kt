package net.archasmiel.skufapi.api.request.auth

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import net.archasmiel.skufapi.api.request.ApiRequest

@Schema(description = "Login request via username/password")
data class LoginRequest(
    @field:Schema(description = "Username", example = "johndoe@example.com")
    @field:Size(min = 5, max = 50, message = "Username/email must be 5-50 characters long")
    @field:NotBlank(message = "Username can't be empty")
    val username: String,

    @field:Schema(description = "Password", example = "my_1secret1_password")
    @field:Size(min = 8, max = 255, message = "Password must be 8-255 characters long")
    @field:NotBlank(message = "Password can't be empty")
    val password: String
) : ApiRequest