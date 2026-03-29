package sn.dev.ct.immosen.mapper;

import sn.dev.ct.immosen.dto.MensualiteDTO;
import sn.dev.ct.immosen.entity.Mensualite;

public class MensualiteMapper {

    public static MensualiteDTO toDto(Mensualite entity) {
        if (entity == null) return null;
        return MensualiteDTO.builder()
                .id(entity.getId())
                .annee(entity.getAnnee())
                .mois(entity.getMois())
                .montantDu(entity.getMontantDu())
                .montantPaye(entity.getMontantPaye())
                .statut(entity.getStatut())
                .dateEcheance(entity.getDateEcheance())
                .penalite(entity.getPenalite())
                .contratId(entity.getId())
                .build();
    }

    public static Mensualite toEntity(MensualiteDTO dto) {
        if (dto == null) return null;

        return Mensualite.builder()
                .id(dto.getId())
                .annee(dto.getAnnee())
                .mois(dto.getMois())
                .montantDu(dto.getMontantDu())
                .montantPaye(dto.getMontantPaye())
                .statut(dto.getStatut())
                .dateEcheance(dto.getDateEcheance())
                .penalite(dto.getPenalite())
                .build();
    }
}
