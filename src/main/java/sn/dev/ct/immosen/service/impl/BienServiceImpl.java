package sn.dev.ct.immosen.service.impl;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import sn.dev.ct.immosen.dto.BienDTO;
import sn.dev.ct.immosen.entity.Bien;
import sn.dev.ct.immosen.entity.Utilisateur;
import sn.dev.ct.immosen.exception.BadRequestException;
import sn.dev.ct.immosen.exception.ResourceNotFoundException;
import sn.dev.ct.immosen.mapper.BienMapper;
import sn.dev.ct.immosen.mapper.UtilisateurMapper;
import sn.dev.ct.immosen.repository.BienRepository;
import sn.dev.ct.immosen.repository.UtilisateurRepository;
import sn.dev.ct.immosen.service.BienService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BienServiceImpl implements BienService {

    private final BienRepository bienRepository;
    private final BienMapper mapper = Mappers.getMapper(BienMapper.class);
    private final UtilisateurRepository utilisateurRepository;

    @Override
    public BienDTO create(BienDTO bienDTO) {
        Utilisateur proprietaire;
        if(bienDTO.getProprietaireId() != null){
            proprietaire = utilisateurRepository.findById(bienDTO.getProprietaireId()).orElseThrow(
                    () -> new ResourceNotFoundException("Utilisateur", bienDTO.getProprietaireId())
            );
        }else if (bienDTO.getProprietaire() == null){
            Utilisateur newUser = UtilisateurMapper.toEntity(bienDTO.getProprietaire());
            proprietaire = utilisateurRepository.save(newUser);
        }else{
            throw new BadRequestException("Propriétaire du bien non fourni");
        }
        Bien bien = mapper.toEntity(bienDTO);
        bien.setProprietaire(proprietaire);
        Bien savedBien = bienRepository.save(bien);
        return mapper.toDTO(savedBien);
    }

    @Override
    public BienDTO getById(Long id) {
        return bienRepository.findById(id).map(
                mapper::toDTO
        ).orElseThrow(() ->new ResourceNotFoundException("Bien", id));
    }

    @Override
    public List<BienDTO> getAll() {
        return bienRepository.findAll()
                .stream().map(
                        mapper::toDTO
                ).collect(Collectors.toList());
    }

    @Override
    public BienDTO update(BienDTO bienDTO, Long id) {
        Bien bien  = bienRepository.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("Bien", id));

        bien.setName(bienDTO.getName());
        bien.setReference(bienDTO.getReference());
        bien.setType(bienDTO.getType());
        bien.setAdresse(bienDTO.getAdresse());
        bien.setSuperficie(bienDTO.getSuperficie());
        bien.setNombrePieces(bienDTO.getNombrePieces());
        bien.setPrix(bienDTO.getPrix());
        bien.setStatut(bienDTO.getStatut());
        bien.setDescription(bienDTO.getDescription());

        if (bienDTO.getPhotos() != null) {
            bien.setPhotos(bien.getPhotos());
        }
        if(bienDTO.getProprietaireId() != null) {
            Utilisateur proprietaire = utilisateurRepository.findById(bienDTO.getId())
                    .orElseThrow(() ->new ResourceNotFoundException("Utilisateur", bienDTO.getId()));
            bien.setProprietaire(proprietaire);
        }
        Bien updated = bienRepository.save(bien);
        return mapper.toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        Bien bien = bienRepository.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("Bien", id));
        bienRepository.delete(bien);
    }
}
