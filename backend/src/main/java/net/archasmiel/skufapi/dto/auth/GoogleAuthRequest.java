package net.archasmiel.skufapi.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.archasmiel.skufapi.dto.IRequest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Sign in request via Google")
public class GoogleAuthRequest implements IRequest {

  @Schema(description = "GoogleId token", example = "eyJhbGciOiJIUzUxMiJ9...")
  private String token;
}
