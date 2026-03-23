package sn.dev.ct.immosen.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PhotoDTO {
    private Long id;

    private String nomFichier;

    private String chemin;

    private String type;

    private Boolean estPrincipale;
}
