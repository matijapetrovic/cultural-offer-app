package cultureapp.domain.regular_user.command;

import cultureapp.domain.account.exception.AccountAlreadyExists;
import cultureapp.domain.core.validation.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotBlank;

public interface AddRegularUserUseCase {
    void addRegularUser(AddRegularUserCommand command) throws AccountAlreadyExists;

    @Value
    @EqualsAndHashCode(callSuper = false)
    class AddRegularUserCommand extends SelfValidating<AddRegularUserUseCase.AddRegularUserCommand> {
        @NotBlank
        String firstName;
        @NotBlank
        String lastName;
        @NotBlank
        String email;
        @NotBlank
        String password;

        public AddRegularUserCommand(String firstName, String lastName, String email, String password) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.password = password;
            this.validateSelf();
        }
    }
}
