package cultureapp.domain.core;

import cultureapp.domain.account.Account;

public interface TokenService {
    String getToken(Account account);
    long getExpiresIn();
}
