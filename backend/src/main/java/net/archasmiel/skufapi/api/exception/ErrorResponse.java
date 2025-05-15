package net.archasmiel.skufapi.api.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.archasmiel.skufapi.api.response.ApiResponse;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response for error")
public class ErrorResponse implements ApiResponse {

    @Schema(description = "Error", example = "GOOGLE_AUTH_FAILED")
    private String error;

    @Schema(description = "Message", example = "This error means...")
    private String message;
}
