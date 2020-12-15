package cultureapp.domain.account.service;

import cultureapp.domain.account.Account;
import cultureapp.domain.account.AccountRepository;
import cultureapp.domain.account.command.ChangePasswordUseCase;
import cultureapp.domain.core.AuthenticationService;
import cultureapp.domain.core.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChangePasswordService implements ChangePasswordUseCase  {
    private final AuthenticationService authenticationService;
    private final PasswordEncoder encoder;
    private final AccountRepository repository;

    @Override
    public boolean changePassword(ChangePasswordCommand command) {
        Account account = authenticationService.getAuthenticated();
        if(!authenticationService.reauthenticate(account.getEmail(), command.getOldPassword()))
            return false;

        if(!account.changePassword(encoder.encode(command.getNewPassword())))
            return false;

        repository.save(account);
        return true;
    }
}
