package cultureapp;

import cultureapp.authentication.command.LoginUseCase;
import cultureapp.authentication.exception.AccountNotActivatedException;
import cultureapp.domain.account.Account;
import cultureapp.domain.authority.Authority;
import cultureapp.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthenticationService implements LoginUseCase {
    private final TokenUtils tokenUtils;
    private final AuthenticationManager authenticationManager;

    @Override
    public LoginResponse login(LoginCommand command) throws AccountNotActivatedException {
        Account account = getPrincipal(command);
        if(!account.isActivated())
            throw new AccountNotActivatedException();

        String jwt = tokenUtils.generateToken(account.getEmail());
        int expiresIn = tokenUtils.getExpiredIn();

        return new LoginResponse(jwt,
                mapRoles(account),
                account.isPasswordChanged(),
                expiresIn);
    }

    private Account getPrincipal(LoginCommand command) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(command.getEmail(), command.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return  getPrincipal(authentication);
    }

    private Account getPrincipal(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        return principal.getAccount();
    }

    private List<String> mapRoles(Account account) {
        return account.
                getAuthorities()
                .stream()
                .map(Authority::getName)
                .collect(Collectors.toList());
    }
}
