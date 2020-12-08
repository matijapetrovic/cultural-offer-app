package cultureapp.domain.account;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@Service
public class ActivateAccountService implements ActivateAccountUseCase {
    private final AccountService accountService;

    @Override
    public void activateAccount(@Positive Long accountId) throws AccountNotFoundException {
        Account account = accountService.getAccount(accountId);
        account.activate();
        accountService.save(account);
    }
}
