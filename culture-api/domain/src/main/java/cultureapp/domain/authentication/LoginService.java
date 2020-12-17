package cultureapp.domain.authentication;

import cultureapp.domain.authentication.command.LoginUseCase;
import cultureapp.domain.authentication.exception.AccountNotActivatedException;
import cultureapp.domain.account.Account;
import cultureapp.domain.core.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class LoginService implements LoginUseCase {
    private final AuthenticationService authenticationService;

    @Override
    public LoginDTO login(LoginCommand command) throws AccountNotActivatedException {
        Account account = authenticationService.authenticate(command.getEmail(), command.getPassword());

        if(!account.isActivated())
            throw new AccountNotActivatedException(command.getEmail());

        return LoginDTO.of(account);
    }
}
