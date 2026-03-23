package sn.dev.ct.immosen.mapper;

import org.mapstruct.Mapper;
import sn.dev.ct.immosen.dto.PaiementDTO;
import sn.dev.ct.immosen.entity.Paiement;

@Mapper(componentModel = "spring")
public interface PaiementMapper {
    PaiementDTO toDTO(Paiement paiement);
    Paiement toEntity(PaiementDTO paiementDTO);
}
