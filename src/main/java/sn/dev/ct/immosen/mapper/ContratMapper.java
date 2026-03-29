package sn.dev.ct.immosen.mapper;

import sn.dev.ct.immosen.dto.ContratDTO;
import sn.dev.ct.immosen.entity.Contrat;
import sn.dev.ct.immosen.entity.enums.TypeContrat;
import sn.dev.ct.immosen.entity.enums.TypeDureeContrat;

public class ContratMapper {

    public static ContratDTO toDTO(Contrat contrat) {
        if(contrat == null) return null;
        return ContratDTO.builder()
                .id(contrat.getId())
                .actif(contrat.isActif())
                .typeContrat(
                        contrat.getTypeContrat() != null
                                ? contrat.getTypeContrat().name()
                                : null
                )
                .typeDureeContrat(
                        contrat.getTypeDureeContrat() != null
                                ? contrat.getTypeDureeContrat().name()
                                : null
                )
                .bienId(
                        contrat.getBien() != null
                                ? contrat.getBien().getId()
                                : null
                )
                .locataireId(
                        contrat.getLocataire() != null
                                ? contrat.getLocataire().getId()
                                : null
                )
                .dateDebut(contrat.getDateDebut())
                .dateFin(contrat.getDateFin())
                .montantMensuel(contrat.getMontantMensuel())
                .caution(contrat.getCaution())
                .build();
    }
    public static Contrat toEntity(ContratDTO dto) {
        if(dto == null) return null;
        return   Contrat.builder()
                .dateDebut(dto.getDateDebut())
                .dateFin(dto.getDateFin())
                .montantMensuel(dto.getMontantMensuel())
                .caution(dto.getCaution())
                .typeContrat(
                        dto.getTypeContrat() != null
                                ? TypeContrat.valueOf(dto.getTypeContrat())
                                : null
                )
                .typeDureeContrat(
                        dto.getTypeDureeContrat() != null
                                ? TypeDureeContrat.valueOf(dto.getTypeDureeContrat())
                                : null
                )
                .actif(dto.getActif() != null ? dto.getActif() : true)
                .build();
    }

    public static void updateEntity(Contrat contrat, ContratDTO dto) {

        contrat.setDateDebut(dto.getDateDebut());
        contrat.setDateFin(dto.getDateFin());
        contrat.setMontantMensuel(dto.getMontantMensuel());
        contrat.setCaution(dto.getCaution());

        if (dto.getTypeContrat() != null) {
            contrat.setTypeContrat(TypeContrat.valueOf(dto.getTypeContrat()));
        }

        if (dto.getTypeDureeContrat() != null) {
            contrat.setTypeDureeContrat(TypeDureeContrat.valueOf(dto.getTypeDureeContrat()));
        }

        if (dto.getActif() != null) {
            contrat.setActif(dto.getActif());
        }
    }
}
