package cultureapp.domain.authentication;

import cultureapp.domain.authentication.command.LoginUseCase;
import cultureapp.domain.authentication.exception.AccountNotActivatedException;
import cultureapp.domain.account.Account;
import cultureapp.domain.core.AuthenticationService;
import cultureapp.domain.core.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class LoginService implements LoginUseCase {
    private final AuthenticationService authenticationService;
    private final TokenService tokenService;

    @Override
    public LoginDTO login(LoginCommand command) throws AccountNotActivatedException {
        Account account = authenticationService.authenticate(command.getEmail(), command.getPassword());

        if(!account.isActivated())
            throw new AccountNotActivatedException(command.getEmail());

        String token = tokenService.getToken(account);
        long expiresIn = tokenService.getExpiresIn();

        return LoginDTO.of(token, account.getAuthorities(), expiresIn);
    }
}
