package net.archasmiel.skufapi.api.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import net.archasmiel.skufapi.api.request.ApiRequest;

@Data
@Schema(description = "Sign in request via standard credentials")
public class LoginRequest implements ApiRequest {

    @Schema(description = "Username or Email", example = "johndoe or john@example.com")
    @Size(min = 5, max = 50, message = "Username/email must be 5-50 characters long")
    @NotBlank(message = "Username/email can't be empty")
    private String username;

    @Schema(description = "Password", example = "my_1secret1_password")
    @Size(min = 8, max = 255, message = "Password must be 8-255 characters long")
    @NotBlank(message = "Password can't be empty")
    private String password;
}
