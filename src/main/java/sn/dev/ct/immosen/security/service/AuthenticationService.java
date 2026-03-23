package sn.dev.ct.immosen.security.service;

import sn.dev.ct.immosen.security.dto.JwtResponse;
import sn.dev.ct.immosen.security.dto.SignInRequest;

public interface AuthenticationService {
    JwtResponse signIn(SignInRequest signInRequest);
}
