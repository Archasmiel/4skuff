package net.archasmiel.skufapi.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.archasmiel.skufapi.dto.IResponse;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response with Jwt token")
public class MeResponse implements IResponse {

  @Schema(description = "Jwt token", example = "eyJhbGciOiJIUzUxMiJ9...")
  private String username;

  @Schema(description = "Jwt token", example = "eyJhbGciOiJIUzUxMiJ9...")
  private String roles;
}
