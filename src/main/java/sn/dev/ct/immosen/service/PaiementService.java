package sn.dev.ct.immosen.service;

import sn.dev.ct.immosen.dto.PaiementDTO;

import java.util.List;

public interface PaiementService {
    PaiementDTO create(PaiementDTO paiementDTO);
    List<PaiementDTO> getPaiementsByContrat(Long contratId);
    PaiementDTO payerMensualite(Long paiementId);
}
