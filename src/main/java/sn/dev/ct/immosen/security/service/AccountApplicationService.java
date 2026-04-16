package sn.dev.ct.immosen.security.service;

import sn.dev.ct.immosen.security.dto.ChangePasswordDto;

public interface AccountApplicationService {
    void changePassword(Long id, ChangePasswordDto changePasswordDto);
}
