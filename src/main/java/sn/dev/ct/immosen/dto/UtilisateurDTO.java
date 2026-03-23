package sn.dev.ct.immosen.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import sn.dev.ct.immosen.entity.enums.Role;

import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Getter
@Setter @Builder
public class UtilisateurDTO {

    private Long id;
    private String nom;
    private String prenom;
    private String telephone;
    @NotBlank(message = "L'email est obligatoire")
    private String email;
    private String username;
    private Role role;
    private List<PhotoDTO> photos;
}
