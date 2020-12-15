package cultureapp.domain.account.command;

import cultureapp.domain.account.exception.AccountAlreadyActivatedException;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.constraints.Positive;

public interface ActivateAccountUseCase {
    void activateAccount(@Positive Long accountId) throws AccountNotFoundException, cultureapp.domain.account.exception.AccountNotFoundException, AccountAlreadyActivatedException;
}
