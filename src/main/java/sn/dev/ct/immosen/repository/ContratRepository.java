package sn.dev.ct.immosen.repository;

import sn.dev.ct.immosen.entity.Bien;
import sn.dev.ct.immosen.entity.Contrat;
import sn.dev.ct.immosen.entity.Utilisateur;
import sn.dev.ct.immosen.entity.enums.TypeContrat;
import sn.dev.ct.immosen.entity.enums.TypeDureeContrat;

import java.util.List;

public interface ContratRepository extends BaseRepository<Contrat>{
    List<Contrat> findContratByLocataire(Utilisateur locataire);
    List<Contrat> findByBien(Bien bien);
    boolean existsByBienIdAndActifTrue(Long bienId);
    List<Contrat> findByTypeContratAndTypeDureeContratAndActifTrue(TypeContrat typeContrat, TypeDureeContrat typeDureeContrat);
}

