package sn.dev.ct.immosen.dto;

import lombok.*;
import sn.dev.ct.immosen.entity.enums.StatutBien;
import sn.dev.ct.immosen.entity.enums.TypeBien;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BienDTO {
    private Long id;

    private String name;

    private String reference;

    private TypeBien type;

    private String adresse;

    private Double superficie;

    private Integer nombrePieces;

    private Double prix;

    private StatutBien statut;

    private String description;

    private Long proprietaireId;

    private UtilisateurDTO proprietaire;

    private List<String> photos;
}
