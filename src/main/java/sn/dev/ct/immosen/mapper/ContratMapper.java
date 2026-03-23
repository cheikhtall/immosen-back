package sn.dev.ct.immosen.mapper;

import org.mapstruct.Mapper;
import sn.dev.ct.immosen.dto.ContratDTO;
import sn.dev.ct.immosen.entity.Contrat;

@Mapper(componentModel = "spring")
public interface ContratMapper {
    ContratDTO toDto(Contrat contrat);
    Contrat toEntity(ContratDTO contratDTO);
}
