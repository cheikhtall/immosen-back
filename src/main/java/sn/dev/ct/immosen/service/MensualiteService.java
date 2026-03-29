package sn.dev.ct.immosen.service;

import sn.dev.ct.immosen.dto.MensualiteDTO;
import sn.dev.ct.immosen.entity.Contrat;

import java.util.List;

public interface MensualiteService {
    void genererMensualite(Contrat contrat);
    void genererMensualiteV2(Contrat contrat);
    List<MensualiteDTO> getByContrat(Long contratId);
}
