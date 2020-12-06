package cultureapp.domain.authentication.command;

import cultureapp.domain.account.Account;
import cultureapp.domain.authentication.exception.AccountNotActivatedException;
import cultureapp.domain.authority.Authority;
import cultureapp.domain.core.validation.SelfValidating;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

public interface LoginUseCase {
    LoginDTO login(LoginCommand command) throws AccountNotActivatedException;

    @Value
    @EqualsAndHashCode(callSuper = false)
    class LoginCommand extends SelfValidating<LoginCommand> {
        @Email
        String email;
        @NotBlank
        String password;

        public LoginCommand(
                String email,
                String password) {
            this.email = email;
            this.password = password;
            this.validateSelf();
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    class LoginDTO {
        String email;
        List<String> role;

        public static LoginDTO of(Account account) {
            return new LoginDTO(account.getEmail(), mapRoles(account));
        }

        private static List<String> mapRoles(Account account) {
            return account.
                    getAuthorities()
                    .stream()
                    .map(Authority::getName)
                    .collect(Collectors.toList());
        }
    }
}
