package net.archasmiel.skufapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response for error")
public class ErrorResponse implements IResponse {

  @Schema(description = "Error", example = "GOOGLE_AUTH_FAILED")
  private String error;

  @Schema(description = "Message", example = "This error means...")
  private String message;
}
