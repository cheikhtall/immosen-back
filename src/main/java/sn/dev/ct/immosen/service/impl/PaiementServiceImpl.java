package sn.dev.ct.immosen.service.impl;

import org.springframework.stereotype.Service;
import sn.dev.ct.immosen.dto.PaiementDTO;
import sn.dev.ct.immosen.entity.Mensualite;
import sn.dev.ct.immosen.entity.Paiement;
import sn.dev.ct.immosen.entity.enums.ModePaiement;
import sn.dev.ct.immosen.entity.enums.StatutMensualite;
import sn.dev.ct.immosen.exception.ResourceNotFoundException;
import sn.dev.ct.immosen.mapper.PaiementMapper;
import sn.dev.ct.immosen.repository.MensualiteRepository;
import sn.dev.ct.immosen.repository.PaiementRepository;
import sn.dev.ct.immosen.service.PaiementService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaiementServiceImpl implements PaiementService {

    private final PaiementRepository paiementRepository;
    private final MensualiteRepository mensualiteRepository;

    public PaiementServiceImpl(PaiementRepository paiementRepository, MensualiteRepository mensualiteRepository) {
        this.paiementRepository = paiementRepository;
        this.mensualiteRepository = mensualiteRepository;
    }

    @Override
    public PaiementDTO create(PaiementDTO paiementDTO) {
        Paiement paiement = PaiementMapper.toEntity(paiementDTO);
        Mensualite mensualite = mensualiteRepository.findById(paiementDTO.getMensualiteId()).orElseThrow(
                () -> new ResourceNotFoundException("Mensualite", paiementDTO.getMensualiteId())
        );
        paiement.setMensualite(mensualite);
        Paiement saved = paiementRepository.save(paiement);
        return PaiementMapper.toDto(saved);
    }

    @Override
    public List<PaiementDTO> getPaiementsByContrat(Long contratId) {
        List<Mensualite> mensualites = mensualiteRepository.findByContratId(contratId);

        List<Long> mensualiteIds = mensualites.stream()
                .map(Mensualite::getId)
                .collect(Collectors.toList());

        return paiementRepository.findByMensualiteIdIn(mensualiteIds)
                .stream()
                .map(PaiementMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public void payerMensualite(Long mensualiteId, Double montant, String modePaiement) {
        Mensualite mensualite = mensualiteRepository.findById(mensualiteId).orElseThrow(
                () -> new ResourceNotFoundException("Mensualité", mensualiteId));
        Paiement paiement = Paiement.builder()
                .montant(montant)
                .datePaiement(LocalDateTime.now())
                .modePaiement(ModePaiement.valueOf(modePaiement))
                .mensualite(mensualite)
                .build();
        paiementRepository.save(paiement);

        double totalPaye = mensualite.getMontantPaye() + montant;
        mensualite.setMontantPaye(totalPaye);

        if (mensualite.getMontantPaye() == 0) {
            mensualite.setStatut(StatutMensualite.EN_ATTENTE);
        } else if (mensualite.getMontantPaye() < mensualite.getMontantDu()) {
            mensualite.setStatut(StatutMensualite.PARTIELLEMENT_PAYEE);
        } else {
            mensualite.setStatut(StatutMensualite.PAYEE);
        }
        mensualiteRepository.save(mensualite);
    }

    @Override
    public void payerPlusieursMensualites(Long contratId, Double montant, String modePaiement) {
        List<Mensualite> mensualites = mensualiteRepository.findByContratIdOrderByAnneeAscMoisAsc(contratId);
        double reste = montant;
        for (Mensualite mensualite : mensualites) {
            if(reste  <= 0) break;
            double montantRestant = mensualite.getMontantDu() - mensualite.getMontantPaye();
            if(montantRestant <= 0) continue;
            double montantAffecte = Math.min(reste, montantRestant);

            Paiement paiement = Paiement.builder()
                    .montant(montantAffecte)
                    .datePaiement(LocalDateTime.now())
                    .modePaiement(ModePaiement.valueOf(modePaiement))
                    .mensualite(mensualite)
                    .build();

            paiementRepository.save(paiement);
            mensualite.setMontantPaye(mensualite.getMontantPaye() + montantAffecte);
            if (mensualite.getMontantPaye() == 0) {
                mensualite.setStatut(StatutMensualite.EN_ATTENTE);
            } else if (mensualite.getMontantPaye() < mensualite.getMontantDu()) {
                mensualite.setStatut(StatutMensualite.PARTIELLEMENT_PAYEE);
            } else {
                mensualite.setStatut(StatutMensualite.PAYEE);
            }
            mensualiteRepository.save(mensualite);
            reste -= montantAffecte;
        }

        if(reste > 0){
            throw new RuntimeException("Montant supérieur à la dette");
        }
    }

    @Override
    public void annulerPaiement(Long paiementId) {
        Paiement paiement = paiementRepository.findById(paiementId).orElseThrow(
                () -> new ResourceNotFoundException("Paiement", paiementId));

        Mensualite mensualite = paiement.getMensualite();
        mensualite.setMontantPaye(mensualite.getMontantPaye() - paiement.getMontant());
        paiementRepository.delete(paiement);

        if (mensualite.getMontantPaye() == 0) {
            mensualite.setStatut(StatutMensualite.EN_ATTENTE);
        } else if (mensualite.getMontantPaye() < mensualite.getMontantDu()) {
            mensualite.setStatut(StatutMensualite.PARTIELLEMENT_PAYEE);
        } else {
            mensualite.setStatut(StatutMensualite.PAYEE);
        }

        mensualiteRepository.save(mensualite);
    }
}
