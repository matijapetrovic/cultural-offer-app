package cultureapp.domain.core;

import cultureapp.domain.account.Account;

public interface AuthenticationService {
    Account authenticate(String email, String password);
    boolean reauthenticate(String email, String password);
    Account getAuthenticated();
}
