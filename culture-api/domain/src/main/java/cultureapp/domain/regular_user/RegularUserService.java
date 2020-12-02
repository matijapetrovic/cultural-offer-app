package cultureapp.domain.regular_user;

import cultureapp.domain.account.Account;
import cultureapp.domain.account.AccountRepository;
import cultureapp.domain.regular_user.command.AddRegularUserUseCase;
import cultureapp.domain.regular_user.exception.RegularUserAlreadyExists;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegularUserService implements AddRegularUserUseCase {
    private final RegularUserRepository regularUserRepository;
    private final AccountRepository accountRepository;

    @Override
    public void addRegularUser(AddRegularUserCommand command) throws RegularUserAlreadyExists {
        Account account = accountRepository.save(Account.of(command.getEmail(), command.getPassword()));
        RegularUser regularUser = RegularUser.of(
                command.getFirstName(),
                command.getLastName(),
                account
               );
       saveRegularUser(regularUser);
    }

    // TODO prosledi email usera, ovo je radjeno pre nego sto je account implementiran
    private void saveRegularUser(RegularUser user) throws RegularUserAlreadyExists {
        try {
            regularUserRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new RegularUserAlreadyExists("a");
        }
    }
}
