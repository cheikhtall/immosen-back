package sn.dev.ct.immosen.mapper;


import sn.dev.ct.immosen.dto.PhotoDTO;
import sn.dev.ct.immosen.dto.UtilisateurDTO;
import sn.dev.ct.immosen.entity.Photo;
import sn.dev.ct.immosen.entity.Utilisateur;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurMapper {
    public static UtilisateurDTO toDto(Utilisateur utilisateur) {
        if(utilisateur == null) return null;
        List<PhotoDTO> photoDTOs = new ArrayList<>();
        if(utilisateur.getPhotos() != null) {
            for (Photo photo : utilisateur.getPhotos()) {
                PhotoDTO photoDTO = new PhotoDTO();
                photoDTO.setId(photo.getId());
                photoDTO.setNomFichier(photo.getNomFichier());
                photoDTO.setChemin(photo.getChemin());
                photoDTO.setEstPrincipale(photo.getEstPrincipale());
                photoDTOs.add(photoDTO);
            }
        }
        return UtilisateurDTO.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .email(utilisateur.getEmail())
                .telephone(utilisateur.getTelephone())
                .username(utilisateur.getUsername())
                .role(utilisateur.getRole())
                .photos(photoDTOs)
                .build();
    }

    public static Utilisateur toEntity(UtilisateurDTO dto) {
        if (dto == null) return null;

        Utilisateur user = new Utilisateur();
        user.setId(dto.getId());
        user.setNom(dto.getNom());
        user.setPrenom(dto.getPrenom());
        user.setEmail(dto.getEmail());
        user.setTelephone(dto.getTelephone());
        user.setUsername(dto.getUsername());
        user.setRole(dto.getRole());

        List<Photo> photos = new ArrayList<>();
        if (dto.getPhotos() != null) {
            for (PhotoDTO photoDTO : dto.getPhotos()) {
                Photo photo = new Photo();
                photo.setId(photoDTO.getId());
                photo.setChemin(photoDTO.getChemin());
                photo.setEstPrincipale(photoDTO.getEstPrincipale());
                photo.setNomFichier(photoDTO.getNomFichier());
                photo.setUtilisateur(user); // lien bidirectionnel
                photos.add(photo);
            }
        }
        user.setPhotos(photos);

        return user;
    }
}

// MDP ====> 2ZU*P5BX