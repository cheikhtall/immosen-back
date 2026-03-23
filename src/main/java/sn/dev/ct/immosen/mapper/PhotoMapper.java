package sn.dev.ct.immosen.mapper;

import org.mapstruct.Mapper;
import sn.dev.ct.immosen.dto.PhotoDTO;
import sn.dev.ct.immosen.entity.Photo;

@Mapper(componentModel = "spring")
public interface PhotoMapper {
    PhotoDTO toDTO(Photo photo);
    Photo toEntity(PhotoDTO photoDTO);
}
