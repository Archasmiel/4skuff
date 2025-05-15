package net.archasmiel.skufapi.api.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.archasmiel.skufapi.api.request.ApiRequest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Sign in request via Google")
public class GoogleAuthRequest implements ApiRequest {

    @Schema(description = "GoogleId token", example = "eyJhbGciOiJIUzUxMiJ9...")
    private String token;
}
