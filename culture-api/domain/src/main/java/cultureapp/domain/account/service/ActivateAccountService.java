package cultureapp.domain.account.service;

import cultureapp.domain.account.Account;
import cultureapp.domain.account.AccountRepository;
import cultureapp.domain.account.command.ActivateAccountUseCase;
import cultureapp.domain.account.exception.AccountAlreadyActivatedException;
import cultureapp.domain.account.exception.AccountNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Positive;

@AllArgsConstructor
@Service
public class ActivateAccountService implements ActivateAccountUseCase {
    private final AccountRepository accountRepository;

    @Override
    public void activateAccount(@Positive Long accountId)
            throws AccountNotFoundException, AccountAlreadyActivatedException {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
        account.activate();
        accountRepository.save(account);
    }
}
