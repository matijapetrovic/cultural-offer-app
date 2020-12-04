package cultureapp.domain.regular_user;

import cultureapp.domain.account.Account;
import cultureapp.domain.account.AccountRepository;
import cultureapp.domain.account.exception.AccountAlreadyExists;
import cultureapp.domain.regular_user.command.AddRegularUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegularUserService implements AddRegularUserUseCase {
    private final RegularUserRepository regularUserRepository;
    private final AccountRepository accountRepository;

    @Override
    public void addRegularUser(AddRegularUserCommand command) throws AccountAlreadyExists {
        Account account = saveAccount(command);
        RegularUser regularUser = RegularUser.of(
                command.getFirstName(),
                command.getLastName(),
                account
               );
        regularUserRepository.save(regularUser);
    }

    private Account saveAccount(AddRegularUserCommand command) throws AccountAlreadyExists {
        try {
            return accountRepository.save(Account.of(command.getEmail(), command.getPassword()));
        } catch (DataIntegrityViolationException ex) {
            throw new AccountAlreadyExists(command.getEmail());
        }
    }
}
