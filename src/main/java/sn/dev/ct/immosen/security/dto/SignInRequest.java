package sn.dev.ct.immosen.security.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {
    @NotBlank(message = "The username or email cannot be empty")
    private String username;
    @NotBlank(message = "The password cannot be empty")
    private String password;
}
