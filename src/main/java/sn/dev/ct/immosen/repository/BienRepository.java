package sn.dev.ct.immosen.repository;

import sn.dev.ct.immosen.entity.Bien;
import sn.dev.ct.immosen.entity.enums.StatutBien;

import java.util.List;

public interface BienRepository extends BaseRepository<Bien>{
    List<Bien> findByName(String name);
    List<Bien> findByStatut(StatutBien statut);
    List<Bien> findByAdresseContainingIgnoreCase(String adresse);
}
