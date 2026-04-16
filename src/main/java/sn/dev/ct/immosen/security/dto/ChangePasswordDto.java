package sn.dev.ct.immosen.security.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class ChangePasswordDto {
    @NotBlank(message = "Le mot de passe actuel est obligatoire")
    private String oldPassword;
    @NotBlank(message = "Le nouveau mot de passe est obligatoire")
    private String newPassword;
    @NotBlank(message = "La confirmation du nouveau mot de passe est obligatoire")
    private String confirmPassword;
}
