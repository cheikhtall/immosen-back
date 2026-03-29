package sn.dev.ct.immosen.dto;

import lombok.*;
import sn.dev.ct.immosen.entity.enums.TypePhoto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PhotoDTO {
    private Long id;

    private String nomFichier;

    private String chemin;

    private TypePhoto type;

    private Boolean estPrincipale;
}
