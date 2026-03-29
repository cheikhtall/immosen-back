package sn.dev.ct.immosen.mapper;


import sn.dev.ct.immosen.dto.PhotoDTO;
import sn.dev.ct.immosen.entity.Photo;

public class PhotoMapper {

    public static PhotoDTO toDto(Photo photo) {
        if(photo == null) return null;
        PhotoDTO photoDTO = new PhotoDTO();
        photoDTO.setId(photo.getId());
        photoDTO.setEstPrincipale(photo.getEstPrincipale());
        photoDTO.setChemin(photo.getChemin());
        photoDTO.setNomFichier(photo.getNomFichier());
        photoDTO.setType(photo.getType());

        return photoDTO;
    }

    public static Photo toEntity(PhotoDTO photoDTO) {
        if(photoDTO == null) return null;
        Photo photo = new Photo();
        photo.setId(photoDTO.getId());
        photo.setEstPrincipale(photoDTO.getEstPrincipale());
        photo.setChemin(photoDTO.getChemin());
        photo.setNomFichier(photoDTO.getNomFichier());
        photo.setType(photoDTO.getType());

        return photo;
    }
}
