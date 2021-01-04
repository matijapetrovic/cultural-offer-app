package cultureapp.domain.user.command;

import cultureapp.domain.account.exception.AccountAlreadyExistsException;
import cultureapp.domain.core.validation.SelfValidating;
import cultureapp.domain.core.validation.annotation.Password;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public interface RegisterRegularUserUseCase {
    void addRegularUser(RegisterRegularUserCommand command) throws AccountAlreadyExistsException;

    @Value
    @EqualsAndHashCode(callSuper = false)
    class RegisterRegularUserCommand extends SelfValidating<RegisterRegularUserCommand> {
        @NotBlank
        String firstName;
        @NotBlank
        String lastName;
        @Email
        String email;
        @Password
        String password;

        public RegisterRegularUserCommand(String firstName, String lastName, String email, String password) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.password = password;
            this.validateSelf();
        }
    }
}
