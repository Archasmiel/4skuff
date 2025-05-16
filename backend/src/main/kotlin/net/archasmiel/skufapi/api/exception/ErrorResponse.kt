package net.archasmiel.skufapi.api.exception

import io.swagger.v3.oas.annotations.media.Schema
import net.archasmiel.skufapi.api.response.ApiResponse

@Schema(description = "Response for error")
data class ErrorResponse(
    @field:Schema(description = "Error", example = "GOOGLE_AUTH_FAILED")
    val error: String,

    @field:Schema(description = "Message", example = "This error means...")
    val message: String?
) : ApiResponse