package sn.dev.ct.immosen.mapper;

import sn.dev.ct.immosen.dto.BienDTO;
import sn.dev.ct.immosen.dto.PhotoDTO;
import sn.dev.ct.immosen.dto.UtilisateurDTO;
import sn.dev.ct.immosen.entity.Bien;
import sn.dev.ct.immosen.entity.Photo;

import java.util.ArrayList;
import java.util.List;

public class BienMapper {

    public static BienDTO toDTO(Bien bien) {
        if(bien == null) return null;
        List<PhotoDTO> photoDTOs = new ArrayList<>();
        for (Photo photo : bien.getPhotos()) {
            PhotoDTO photoDTO = new PhotoDTO();
            photoDTO.setId(photo.getId());
            photoDTO.setNomFichier(photo.getNomFichier());
            photoDTO.setChemin(photo.getChemin());
            photoDTO.setEstPrincipale(photo.getEstPrincipale());
            photoDTOs.add(photoDTO);
        }
        UtilisateurDTO proprietaire = bien.getProprietaire() == null ?
                null : UtilisateurMapper.toDto(bien.getProprietaire());
        Long proprietaireId = bien.getProprietaire().getId() == null
                ? null : bien.getProprietaire().getId();

        return BienDTO.builder()
                .id(bien.getId())
                .prix(bien.getPrix())
                .name(bien.getName())
                .adresse(bien.getAdresse())
                .description(bien.getDescription())
                .nombrePieces(bien.getNombrePieces())
                .type(bien.getType())
                .statut(bien.getStatut())
                .reference(bien.getReference())
                .photos(photoDTOs)
                .proprietaireId(proprietaireId)
                .proprietaire(proprietaire)
                .build();

    }

    public static Bien toEntity(BienDTO bienDTO) {
        if(bienDTO == null) return null;

        Bien bien = new Bien();
        bien.setId(bienDTO.getId());
        bien.setAdresse(bienDTO.getAdresse());
        bien.setType(bienDTO.getType());
        bien.setNombrePieces(bienDTO.getNombrePieces());
        bien.setStatut(bienDTO.getStatut());
        bien.setSuperficie(bienDTO.getSuperficie());
        bien.setPrix(bienDTO.getPrix());
        bien.setReference(bienDTO.getReference());
        bien.setDescription(bienDTO.getDescription());

        List<Photo> photos = new ArrayList<>();
        if (bienDTO.getPhotos() != null) {
            for (PhotoDTO photoDTO : bienDTO.getPhotos()) {
                Photo photo = new Photo();
                photo.setId(photoDTO.getId());
                photo.setChemin(photoDTO.getChemin());
                photo.setEstPrincipale(photoDTO.getEstPrincipale());
                photo.setNomFichier(photoDTO.getNomFichier());
                photo.setBien(bien); // lien bidirectionnel
                photos.add(photo);
            }
        }
        bien.setPhotos(photos);
        return bien;
    }
}
