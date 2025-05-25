package net.archasmiel.skufapi.api.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Response for error")
data class ErrorResponse(
    @field:Schema(description = "Error code", example = "GOOGLE_AUTH_FAILED")
    val code: String,

    @field:Schema(description = "Message, representing user data", example = "Auth failed due to...")
    val message: String
) : ApiResponse