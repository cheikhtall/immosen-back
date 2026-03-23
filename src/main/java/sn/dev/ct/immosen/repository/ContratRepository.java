package sn.dev.ct.immosen.repository;

import sn.dev.ct.immosen.entity.Bien;
import sn.dev.ct.immosen.entity.Contrat;
import sn.dev.ct.immosen.entity.Utilisateur;

import java.util.List;

public interface ContratRepository extends BaseRepository<Contrat>{
    List<Contrat> findContratByLocataire(Utilisateur locataire);
    List<Contrat> findByBien(Bien bien);
    List<Contrat> findByActifTrue();
}

