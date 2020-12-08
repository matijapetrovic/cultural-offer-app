package cultureapp.domain.regular_user;

import cultureapp.domain.account.Account;
import cultureapp.domain.account.AccountService;
import cultureapp.domain.authentication.PasswordEncoder;
import cultureapp.domain.authority.Authority;
import cultureapp.domain.email.EmailSender;
import cultureapp.domain.regular_user.command.AddRegularUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RegularUserService implements AddRegularUserUseCase {
    private final RegularUserRepository regularUserRepository;
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final EmailSender emailSender;

    @Override
    public void addRegularUser(AddRegularUserCommand command) {
        Account account = accountService.save(Account.of(
                command.getEmail(),
                passwordEncoder.encode(command.getPassword()),
                false,
                List.of(Authority.withId(2L, "ROLE_USER"))
        ));
        RegularUser regularUser = RegularUser.of(
                command.getFirstName(),
                command.getLastName(),
                account
               );
       regularUserRepository.save(regularUser);
       emailSender.sendEmail(account.getEmail(),
               "Account activation",
               String.format("Please click this link: \n http://localhost:8080/api/auth/activate/%d", account.getId()));
    }
}
