package sn.dev.ct.immosen.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ContratDTO {

    private Long id;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    private Double montantMensuel;

    private Double caution;

    private String typeContrat;

    private String typeDureeContrat;

    private Boolean actif;

    private Long locataireId;

    private Long bienId;
}
