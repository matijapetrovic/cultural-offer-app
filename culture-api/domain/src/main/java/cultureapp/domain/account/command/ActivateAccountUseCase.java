package cultureapp.domain.account.command;

import cultureapp.domain.account.exception.AccountAlreadyActivatedException;

import cultureapp.domain.account.exception.AccountNotFoundException;
import javax.validation.constraints.Positive;

public interface ActivateAccountUseCase {
    void activateAccount(@Positive Long accountId) throws AccountNotFoundException, AccountAlreadyActivatedException;
}
