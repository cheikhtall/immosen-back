package sn.dev.ct.immosen.dto;

import lombok.*;

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

    private String modePaiement;

    private String referenceTransaction;

    private Long mensualiteId;

}
