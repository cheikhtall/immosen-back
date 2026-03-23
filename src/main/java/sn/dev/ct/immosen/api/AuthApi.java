package sn.dev.ct.immosen.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.dev.ct.immosen.security.dto.JwtResponse;
import sn.dev.ct.immosen.security.dto.SignInRequest;
import sn.dev.ct.immosen.security.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthApi {
    private final AuthenticationService authenticationService;

    public AuthApi(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<JwtResponse> login(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }
}
