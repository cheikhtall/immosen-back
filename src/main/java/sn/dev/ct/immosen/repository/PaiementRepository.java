package sn.dev.ct.immosen.repository;

import sn.dev.ct.immosen.entity.Mensualite;
import sn.dev.ct.immosen.entity.Paiement;

import java.util.List;

public interface PaiementRepository extends BaseRepository<Paiement> {
    List<Paiement> findByMensualite(Mensualite mensualite);
    List<Paiement> findByMensualiteId(Long mensualiteId);
    List<Paiement> findByMensualiteIdIn(List<Long> mensualiteIds);

}
