package sn.dev.ct.immosen.dto;

import lombok.*;
import sn.dev.ct.immosen.entity.enums.StatutMensualite;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MensualiteDTO {
    private Long id;

    private Integer mois;

    private Integer annee;

    private Double montantDu;

    private Double montantPaye;

    private StatutMensualite statut;

    private LocalDate dateEcheance;

    private Double penalite;

    private Long contratId;
}
