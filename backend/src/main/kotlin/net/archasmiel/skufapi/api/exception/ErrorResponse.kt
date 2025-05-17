package net.archasmiel.skufapi.api.exception

import io.swagger.v3.oas.annotations.media.Schema
import net.archasmiel.skufapi.api.response.ApiResponse

@Schema(description = "Response for error")
data class ErrorResponse(
    @field:Schema(description = "Error code", example = "GOOGLE_AUTH_FAILED")
    val code: String,

    @field:Schema(description = "Message string", example = "This error means...")
    val message: String
) : ApiResponse