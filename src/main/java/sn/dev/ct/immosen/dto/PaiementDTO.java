package sn.dev.ct.immosen.dto;

import lombok.*;
import sn.dev.ct.immosen.entity.enums.ModePaiement;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaiementDTO {
    private Long id;

    private LocalDateTime datePaiement;

    private Double montant;

    private ModePaiement modePaiement;

    private String referencePaiement;

    private Long mensualiteId;

}
