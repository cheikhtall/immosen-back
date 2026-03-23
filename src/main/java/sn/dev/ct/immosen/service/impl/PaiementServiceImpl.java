package sn.dev.ct.immosen.service.impl;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import sn.dev.ct.immosen.dto.PaiementDTO;
import sn.dev.ct.immosen.entity.Mensualite;
import sn.dev.ct.immosen.entity.Paiement;
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
    private final PaiementMapper mapper = Mappers.getMapper(PaiementMapper.class);
    private final MensualiteRepository mensualiteRepository;

    public PaiementServiceImpl(PaiementRepository paiementRepository, MensualiteRepository mensualiteRepository) {
        this.paiementRepository = paiementRepository;
        this.mensualiteRepository = mensualiteRepository;
    }

    @Override
    public PaiementDTO create(PaiementDTO paiementDTO) {
        Paiement paiement = mapper.toEntity(paiementDTO);
        Paiement saved = paiementRepository.save(paiement);
        return mapper.toDTO(saved);
    }

    @Override
    public List<PaiementDTO> getPaiementsByContrat(Long contratId) {
        List<Mensualite> mensualites = mensualiteRepository.findByContratId(contratId);

        List<Long> mensualiteIds = mensualites.stream()
                .map(Mensualite::getId)
                .collect(Collectors.toList());

        return paiementRepository.findByMensualiteIdIn(mensualiteIds)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PaiementDTO payerMensualite(Long paiementId) {
        Paiement paiement = paiementRepository.findById(paiementId).orElseThrow(
                () -> new ResourceNotFoundException("Paiement", paiementId));

        paiement.getMensualite().setStatut(
                paiement.getMontant() < paiement.getMensualite().getMontantDu()
                        ? StatutMensualite.PARTIELLEMENT_PAYEE
                        : StatutMensualite.PAYEE
        );
        paiement.setDatePaiement(LocalDateTime.now());
        Paiement updated = paiementRepository.save(paiement);
        return mapper.toDTO(updated);
    }
}
