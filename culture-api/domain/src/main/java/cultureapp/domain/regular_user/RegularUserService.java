package cultureapp.domain.regular_user;

import cultureapp.domain.account.Account;
import cultureapp.domain.regular_user.command.AddRegularUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegularUserService
        implements AddRegularUserUseCase {
    private final RegularUserRepository regularUserRepository;

    @Override
    public void addRegularUser(AddRegularUserCommand command) {
        RegularUser regularUser = RegularUser.of(command.getFirstName(), command.getLastName(),
                Account.of(command.getEmail(), command.getPassword()));
        regularUserRepository.save(regularUser);
    }
}
