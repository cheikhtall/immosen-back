package sn.dev.ct.immosen.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.dev.ct.immosen.entity.Utilisateur;
import sn.dev.ct.immosen.security.dto.ChangePasswordDto;
import sn.dev.ct.immosen.security.service.AccountApplicationService;

@RestController
@RequestMapping("/api/account/")
public class AccountController {

    private AccountApplicationService accountApplicationService;

    public AccountController(AccountApplicationService accountApplicationService) {
        this.accountApplicationService = accountApplicationService;
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@AuthenticationPrincipal Utilisateur userDetails,
                                                 @RequestBody ChangePasswordDto changePasswordDTO) {
        accountApplicationService.changePassword(userDetails.getId(), changePasswordDTO);
        return ResponseEntity.ok("Password changed");
    }
}
