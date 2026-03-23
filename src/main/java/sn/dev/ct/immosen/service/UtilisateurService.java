package sn.dev.ct.immosen.service;

import sn.dev.ct.immosen.dto.UtilisateurDTO;

import java.util.List;

public interface UtilisateurService {

    UtilisateurDTO create(UtilisateurDTO dto);
    UtilisateurDTO getById(Long id);
    List<UtilisateurDTO> getAll();
    UtilisateurDTO update(Long id, UtilisateurDTO dto);
    void delete(Long id);
    UtilisateurDTO activateOrDesactivateUtilisateur(Long id);
}
