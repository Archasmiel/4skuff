package net.archasmiel.skufapi.api.response.auth;

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
@Schema(description = "Response with Jwt token")
public class JwtAuthResponse implements ApiResponse {

    @Schema(description = "Jwt token", example = "eyJhbGciOiJIUzUxMiJ9...")
    private String jwtToken;
}
