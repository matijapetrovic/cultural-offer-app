package cultureapp.domain.account;

import cultureapp.domain.account.command.ChangePasswordUseCase;
import cultureapp.domain.authentication.AuthenticationService;
import cultureapp.domain.authentication.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;

@RequiredArgsConstructor
@Service
public class ChangePasswordService implements ChangePasswordUseCase  {
    private final AuthenticationService authenticationService;
    private final PasswordEncoder encoder;
    private final AccountService accountService;

    @Override
    public boolean changePassword(ChangePasswordCommand command) throws AccountNotFoundException {
        Account account = authenticationService.getAuthenticated();
        if(!authenticationService.reauthenticate(account.getEmail(), command.getOldPassword()))
            return false;

        if(!account.changePassword(encoder.encode(command.getNewPassword())))
            return false;

        accountService.save(account);
        return true;
    }
}
