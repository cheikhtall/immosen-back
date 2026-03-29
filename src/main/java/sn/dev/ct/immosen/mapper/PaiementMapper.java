package sn.dev.ct.immosen.mapper;


import sn.dev.ct.immosen.dto.PaiementDTO;
import sn.dev.ct.immosen.entity.Paiement;

public class PaiementMapper {

    public static PaiementDTO toDto(Paiement paiement) {
        if (paiement==null) return null;

        return PaiementDTO.builder()
                .id(paiement.getId())
                .datePaiement(paiement.getDatePaiement())
                .mensualiteId(paiement.getMensualite().getId())
                .modePaiement(paiement.getModePaiement())
                .montant(paiement.getMontant())
                .referencePaiement(paiement.getReferencePaiement())
                .build();
    }

    public static Paiement toEntity(PaiementDTO dto) {
        if (dto==null) return null;

        return Paiement.builder()
                .id(dto.getId())
                .datePaiement(dto.getDatePaiement())
                .montant(dto.getMontant())
                .referencePaiement(dto.getReferencePaiement())
                .modePaiement(dto.getModePaiement())
                .build();
    }
}
