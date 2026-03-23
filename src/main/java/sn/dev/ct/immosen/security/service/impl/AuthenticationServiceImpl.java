package sn.dev.ct.immosen.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sn.dev.ct.immosen.exception.BadRequestException;
import sn.dev.ct.immosen.repository.UtilisateurRepository;
import sn.dev.ct.immosen.security.dto.JwtResponse;
import sn.dev.ct.immosen.security.dto.SignInRequest;
import sn.dev.ct.immosen.security.service.AuthenticationService;
import sn.dev.ct.immosen.security.service.JwtService;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UtilisateurRepository utilisateurRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtResponse signIn(SignInRequest signInRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
        var user = utilisateurRepository.findByUsername( signInRequest.getUsername())
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));
        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }
        if (!user.isEnabled()) {
            throw new BadRequestException("Account is disabled");
        }
        var token = jwtService.generateToken(user);
        return JwtResponse.builder()
                .id(user.getId())
                .token(token)
                .username(user.getUsername())
                .prenom(user.getPrenom())
                .nom(user.getNom())
                .email(user.getEmail())
                .enabled(user.isEnabled())
                .roles(Collections.singletonList(user.getRole().name()))
                .type("Bearer")
                .build();
    }
}
