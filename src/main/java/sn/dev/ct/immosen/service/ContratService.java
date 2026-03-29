package sn.dev.ct.immosen.service;

import sn.dev.ct.immosen.dto.ContratDTO;

import java.util.List;

public interface ContratService {
    ContratDTO create (ContratDTO contratDTO);
    List<ContratDTO> getAllContrats();
    ContratDTO getContratById(Long id);
    void deleteContrat(Long id);
    void resilier(Long id);
    void resilierContrat(Long contratId);
}
