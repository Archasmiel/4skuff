package net.archasmiel.skufapi.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import net.archasmiel.skufapi.dto.IRequest;

@Data
@Schema(description = "Sign up request")
public class SignUpRequest implements IRequest {

  @Schema(description = "Username", example = "johndoe")
  @Size(min = 5, max = 50, message = "Username can be only 5-50 characters long")
  @NotBlank(message = "Username can't be empty")
  private String username;

  @Schema(description = "Email", example = "jondoe@gmail.com")
  @Size(min = 5, max = 255, message = "Email must be 5-255 characters long")
  @NotBlank(message = "Email can't be empty")
  @Email(message = "Email must follow rule user@example.com")
  private String email;

  @Schema(description = "Password", example = "my_1secret1_password")
  @Size(max = 255, message = "Password limit is 255 characters")
  private String password;
}
