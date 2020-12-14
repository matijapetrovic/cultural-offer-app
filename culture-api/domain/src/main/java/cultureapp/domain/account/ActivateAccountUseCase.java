package cultureapp.domain.account;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.constraints.Positive;

public interface ActivateAccountUseCase {
    void activateAccount(@Positive Long accountId) throws AccountNotFoundException;
}
