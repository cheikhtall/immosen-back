package sn.dev.ct.immosen.security.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sn.dev.ct.immosen.repository.UtilisateurRepository;
import sn.dev.ct.immosen.security.service.UserService;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("🔍 Searching user by username: " + username);

        return utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}

