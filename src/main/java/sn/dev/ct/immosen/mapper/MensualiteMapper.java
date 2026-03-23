package sn.dev.ct.immosen.mapper;

import org.mapstruct.Mapper;
import sn.dev.ct.immosen.dto.MensualiteDTO;
import sn.dev.ct.immosen.entity.Mensualite;

@Mapper(componentModel = "spring")
public interface MensualiteMapper {
    MensualiteDTO toDto(Mensualite mensualite);
    Mensualite toEntity(MensualiteDTO mensualiteDTO);
}
