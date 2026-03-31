package sn.dev.ct.immosen.repository;

import org.springframework.data.jpa.repository.Query;
import sn.dev.ct.immosen.entity.Contrat;
import sn.dev.ct.immosen.entity.Mensualite;
import sn.dev.ct.immosen.entity.enums.StatutMensualite;

import java.util.List;

public interface MensualiteRepository extends BaseRepository<Mensualite> {
    List<Mensualite> findByContrat(Contrat contrat);
    List<Mensualite> findByStatut(StatutMensualite statut);
    List<Mensualite> findByAnneeAndMois(Integer annee, Integer mois);
    List<Mensualite> findByContratId(Long contractId);
    boolean existsByContratIdAndMoisAndAnnee(Long id, int mois, int annee);
    List<Mensualite> findByContratIdOrderByAnneeAscMoisAsc(Long contratId);
    @Query("""
        SELECT m FROM Mensualite m
        WHERE m.dateEcheance < CURRENT_DATE
        AND m.statut <> 'PAYEE'
    """)
    List<Mensualite> findMensualitesEnRetard();
}
