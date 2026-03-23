package sn.dev.ct.immosen.repository;

import sn.dev.ct.immosen.entity.Utilisateur;

import java.util.Optional;

public interface UtilisateurRepository extends BaseRepository<Utilisateur>{
    Optional<Utilisateur> findByEmail(String email);
    Optional<Utilisateur> findByTelephone(String telephone);
    boolean existsByEmail(String email);
    Optional<Utilisateur> findByUsername(String username);
}
