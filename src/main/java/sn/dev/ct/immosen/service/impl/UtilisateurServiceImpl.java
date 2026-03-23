package sn.dev.ct.immosen.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sn.dev.ct.immosen.dto.UtilisateurDTO;
import sn.dev.ct.immosen.entity.Photo;
import sn.dev.ct.immosen.entity.Utilisateur;
import sn.dev.ct.immosen.exception.ResourceNotFoundException;
import sn.dev.ct.immosen.mapper.PhotoMapper;
import sn.dev.ct.immosen.mapper.UtilisateurMapper;
import sn.dev.ct.immosen.repository.UtilisateurRepository;
import sn.dev.ct.immosen.service.UtilisateurService;
import sn.dev.ct.immosen.util.RandomStringGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final PhotoMapper photoMapper;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository,
                                  PhotoMapper photoMapper) {
        this.utilisateurRepository = utilisateurRepository;
        this.photoMapper = photoMapper;
    }

    @Override
    public UtilisateurDTO create(UtilisateurDTO dto) {
        Utilisateur utilisateur = UtilisateurMapper.toEntity(dto);
        //Encode password here
        String password = RandomStringGenerator.generateRandomString(8);
        String hashedPassword = passwordEncoder.encode(password);
        utilisateur.setPassword(hashedPassword);
        if (utilisateur.getUsername() == null) {
            utilisateur.setUsername(utilisateur.getEmail());
        }
        utilisateur.setup();
        List<Photo> photos = new ArrayList<>();
        if (dto.getPhotos() != null) {
            for (var photoDto : dto.getPhotos()) {
                var photo = photoMapper.toEntity(photoDto);
                photo.setUtilisateur(utilisateur); // <-- important
                photos.add(photo);
            }
        }
        utilisateur.setPhotos(photos);
        Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
        System.out.println("Le password :: "+password);
        return UtilisateurMapper.toDto(savedUtilisateur);
    }

    @Override
    public UtilisateurDTO getById(Long id) {
        Utilisateur user =  utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", id));
        user.getPhotos().size();
        return UtilisateurMapper.toDto(user);
    }

    @Override
    public List<UtilisateurDTO> getAll() {
        List<Utilisateur> users = utilisateurRepository.findAll();

        // forcer le chargement des photos pour chaque utilisateur
        users.forEach(u -> u.getPhotos().size());

        return users.stream().map(UtilisateurMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public UtilisateurDTO update(Long id, UtilisateurDTO dto) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", id));
        utilisateur.setNom(dto.getNom());
        utilisateur.setPrenom(dto.getPrenom());
        utilisateur.setEmail(dto.getEmail());
        utilisateur.setTelephone(dto.getTelephone());
        utilisateur.setRole(dto.getRole());
        List<Photo> photos = new ArrayList<>();
        dto.getPhotos().stream().forEach(photo -> {
            photoMapper.toEntity(photo);
            photos.add(photoMapper.toEntity(photo));
        });
        utilisateur.setPhotos(photos);
        return UtilisateurMapper.toDto(utilisateurRepository.save(utilisateur));
    }

    @Override
    public void delete(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Utilisateur", id));
        utilisateurRepository.delete(utilisateur);
    }

    @Override
    public UtilisateurDTO activateOrDesactivateUtilisateur(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Utilisateur", id));
        utilisateur.setEnabled(!utilisateur.isEnabled());
        utilisateurRepository.save(utilisateur);
        return UtilisateurMapper.toDto(utilisateur);
    }
}
