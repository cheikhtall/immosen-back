package sn.dev.ct.immosen.mapper;

import org.mapstruct.Mapper;
import sn.dev.ct.immosen.dto.BienDTO;
import sn.dev.ct.immosen.entity.Bien;

@Mapper(componentModel = "spring")
public interface BienMapper {
    BienDTO toDTO(Bien bien);
    Bien toEntity(BienDTO bienDTO);
}
