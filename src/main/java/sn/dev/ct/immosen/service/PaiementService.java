package sn.dev.ct.immosen.service;

import sn.dev.ct.immosen.dto.PaiementDTO;

import java.util.List;

public interface PaiementService {
    PaiementDTO create(PaiementDTO paiementDTO);
    List<PaiementDTO> getPaiementsByContrat(Long contratId);
    void payerMensualite(Long mensualiteId, Double montant, String modePaiement);
    void payerPlusieursMensualites(Long contratId, Double montant, String modePaiement);
    void annulerPaiement(Long paiementId);
}
