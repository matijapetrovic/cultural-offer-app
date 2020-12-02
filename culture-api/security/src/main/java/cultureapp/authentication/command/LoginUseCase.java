package cultureapp.authentication.command;

import cultureapp.LoginResponse;
import cultureapp.authentication.exception.AccountNotActivatedException;
import cultureapp.domain.core.validation.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public interface LoginUseCase {
    LoginResponse login(LoginCommand command) throws AccountNotActivatedException;

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
}
