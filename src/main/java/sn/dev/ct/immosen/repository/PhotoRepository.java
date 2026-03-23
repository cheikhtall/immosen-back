package sn.dev.ct.immosen.repository;

import sn.dev.ct.immosen.entity.Photo;

import java.util.List;

public interface PhotoRepository extends BaseRepository<Photo> {
    List<Photo> findByBienId(Long bienId);
    List<Photo> findByUtilisateurId(Long utilisateurId);
}
