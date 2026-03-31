package sn.dev.ct.immosen.service.impl;

import org.springframework.stereotype.Service;
import sn.dev.ct.immosen.dto.ContratDTO;
import sn.dev.ct.immosen.entity.Bien;
import sn.dev.ct.immosen.entity.Contrat;
import sn.dev.ct.immosen.entity.Mensualite;
import sn.dev.ct.immosen.entity.Utilisateur;
import sn.dev.ct.immosen.entity.enums.StatutBien;
import sn.dev.ct.immosen.entity.enums.TypeContrat;
import sn.dev.ct.immosen.entity.enums.TypeDureeContrat;
import sn.dev.ct.immosen.exception.ResourceNotFoundException;
import sn.dev.ct.immosen.mapper.ContratMapper;
import sn.dev.ct.immosen.repository.BienRepository;
import sn.dev.ct.immosen.repository.ContratRepository;
import sn.dev.ct.immosen.repository.MensualiteRepository;
import sn.dev.ct.immosen.repository.UtilisateurRepository;
import sn.dev.ct.immosen.service.ContratService;
import sn.dev.ct.immosen.service.MensualiteService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContratServiceImpl implements ContratService {
    private final ContratRepository contratRepository;
    private final BienRepository bienRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final MensualiteService mensualiteService;
    private final MensualiteRepository mensualiteRepository;

    public ContratServiceImpl(ContratRepository contratRepository, BienRepository bienRepository,
                              UtilisateurRepository utilisateurRepository, MensualiteService mensualiteService,
                              MensualiteRepository mensualiteRepository) {
        this.contratRepository = contratRepository;
        this.bienRepository = bienRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.mensualiteService = mensualiteService;
        this.mensualiteRepository = mensualiteRepository;
    }
    @Override
    public ContratDTO create(ContratDTO contratDTO) {
        Bien bien = bienRepository.findById(contratDTO.getBienId()).orElseThrow(
                () -> new ResourceNotFoundException("Bien", contratDTO.getBienId())
        );
        if (contratRepository.existsByBienIdAndActifTrue(contratDTO.getBienId())) {
            throw new RuntimeException("Ce bien est déjà sous contrat actif");
        }

        Utilisateur locataire = utilisateurRepository.findById(contratDTO.getLocataireId()).orElseThrow(
                () -> new ResourceNotFoundException("Utilisateur", contratDTO.getLocataireId())
        );
        TypeContrat typeContrat = TypeContrat.valueOf(contratDTO.getTypeContrat());
        TypeDureeContrat typeDureeContrat;
        if(typeContrat == TypeContrat.LOCATION){
            if(contratDTO.getTypeDureeContrat() == null){
                throw new RuntimeException("Type de durée est obligatoire pour une location");
            }
            typeDureeContrat = TypeDureeContrat.valueOf(contratDTO.getTypeDureeContrat());
            if(typeDureeContrat == TypeDureeContrat.DETERMINE && contratDTO.getDateFin() == null){
                throw new RuntimeException("Date de fin est obligatoire");
            }
        }else if (typeContrat == TypeContrat.VENTE){
            contratDTO.setDateFin(null);
        }
        Contrat contrat = ContratMapper.toEntity(contratDTO);
        contrat.setBien(bien);
        contrat.setLocataire(locataire);
        Contrat savedContrat = contratRepository.save(contrat);
        mensualiteService.genererMensualite(savedContrat);
        return ContratMapper.toDTO(savedContrat);
    }

    @Override
    public List<ContratDTO> getAllContrats() {
        return contratRepository.findAll().stream().map(ContratMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public ContratDTO getContratById(Long id) {
        return contratRepository.findById(id).map(ContratMapper::toDTO)
                .orElseThrow(
                () -> new ResourceNotFoundException("Contrat",id)
        );
    }

    @Override
    public void deleteContrat(Long id) {
        Contrat contrat = contratRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Contrat",id)
        );
        contratRepository.delete(contrat);
    }

    @Override
    public void resilier(Long id) {
        Contrat contrat = contratRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Contrat",id)
        );
        contrat.setActif(false);
        contrat.getBien().setStatut(StatutBien.DISPONIBLE);
        bienRepository.save(contrat.getBien());
        contratRepository.save(contrat);
    }

    @Override
    public void resilierContrat(Long contratId) {

        Contrat contrat = contratRepository.findById(contratId)
                .orElseThrow(() -> new RuntimeException("Contrat non trouvé"));

        if (!contrat.isActif()) {
            throw new RuntimeException("Contrat déjà résilié");
        }

        contrat.setActif(false);

        LocalDate today = LocalDate.now();

        // 🔥 Supprimer mensualités futures NON PAYÉES
        List<Mensualite> aSupprimer = contrat.getMensualites().stream()
                .filter(m ->
                        m.getDateEcheance().isAfter(today)
                                && m.getMontantPaye() == 0
                )
                .toList();

        mensualiteRepository.deleteAll(aSupprimer);

        contratRepository.save(contrat);
    }
}
