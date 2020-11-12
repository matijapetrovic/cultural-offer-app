package cultureapp.domain.core.example.commands;

import cultureapp.domain.validation.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public interface AddExampleUseCase {
    void addExample(AddExampleCommand command);

    @Value
    @EqualsAndHashCode(callSuper = false)
    class AddExampleCommand extends SelfValidating<AddExampleCommand> {
        @Email
        String email;
        @NotNull
        Integer number;

        public AddExampleCommand(String email, Integer number) {
            this.email = email;
            this.number = number;
            this.validateSelf();
        }
    }
}
