package sn.dev.ct.immosen.security.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sn.dev.ct.immosen.entity.Utilisateur;
import sn.dev.ct.immosen.exception.ResourceNotFoundException;
import sn.dev.ct.immosen.repository.UtilisateurRepository;
import sn.dev.ct.immosen.security.dto.ChangePasswordDto;
import sn.dev.ct.immosen.security.service.AccountApplicationService;

import java.util.Objects;

@Service
public class AccountApplicationServiceImpl implements AccountApplicationService {
    private final UtilisateurRepository utilisateurRepository;
    private PasswordEncoder passwordEncoder;

    public AccountApplicationServiceImpl(UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void changePassword(Long id, ChangePasswordDto changePasswordDto) {
        Objects.requireNonNull(id);
        Utilisateur optionalUtilisateur = utilisateurRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Utilisateur", id)
        );
        if(!passwordEncoder.matches(changePasswordDto.getOldPassword(), optionalUtilisateur.getPassword())) {
            throw new IllegalArgumentException("Le mot de passe saisi n'est pas votre mot de passe actuel");
        }
        if(!changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmPassword())){
            throw new IllegalArgumentException("Le nouveau mot de passe et la confirmation ne correspondent pas");
        }
        optionalUtilisateur.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        utilisateurRepository.save(optionalUtilisateur);
    }
}
