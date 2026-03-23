package sn.dev.ct.immosen.repository;

import sn.dev.ct.immosen.entity.Contrat;
import sn.dev.ct.immosen.entity.Mensualite;
import sn.dev.ct.immosen.entity.enums.StatutMensualite;

import java.util.List;

public interface MensualiteRepository extends BaseRepository<Mensualite> {
    List<Mensualite> findByContrat(Contrat contrat);
    List<Mensualite> findByStatut(StatutMensualite statut);
    List<Mensualite> findByAnneeAndMois(Integer annee, Integer mois);
    List<Mensualite> findByContratId(Long contractId);
}
