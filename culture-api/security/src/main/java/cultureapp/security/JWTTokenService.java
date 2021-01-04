package cultureapp.security;

import cultureapp.domain.account.Account;
import cultureapp.domain.core.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JWTTokenService implements TokenService {
    private final TokenUtils tokenUtils;

    @Override
    public String getToken(Account account) {
        return tokenUtils.generateToken(account.getEmail());
    }

    @Override
    public long getExpiresIn() {
        return tokenUtils.getExpiredIn();
    }
}
