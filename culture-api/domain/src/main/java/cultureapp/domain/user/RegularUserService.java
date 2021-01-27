package cultureapp.domain.user;

import cultureapp.domain.account.Account;
import cultureapp.domain.account.AccountRepository;
import cultureapp.domain.core.PasswordEncoder;
import cultureapp.domain.authority.Authority;
import cultureapp.domain.core.EmailSender;
import cultureapp.domain.user.command.RegisterRegularUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class RegularUserService implements RegisterRegularUserUseCase {
    private final RegularUserRepository regularUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailSender emailSender;
    private final AccountRepository accountRepository;

    @Override
    public void addRegularUser(RegisterRegularUserCommand command) {
        Account account = accountRepository.save(Account.of(
                command.getEmail(),
                passwordEncoder.encode(command.getPassword()),
                false,
                Set.of(Authority.withId(2L, "ROLE_USER"))
        ));
        RegularUser regularUser = RegularUser.of(
                command.getFirstName(),
                command.getLastName(),
                account
               );
       regularUserRepository.save(regularUser);
       emailSender.sendEmail(account.getEmail(),
               "Account activation",
               String.format("Please click this link: \n http://localhost:4200/auth/activate/%d", account.getId()));
    }

}
