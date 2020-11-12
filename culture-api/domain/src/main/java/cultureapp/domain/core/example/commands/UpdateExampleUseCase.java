package cultureapp.domain.core.example.commands;

import cultureapp.domain.core.example.exceptions.ExampleNotFoundException;
import cultureapp.domain.validation.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public interface UpdateExampleUseCase {
    void updateExample(UpdateExampleCommand command) throws ExampleNotFoundException;

    @Value
    @EqualsAndHashCode(callSuper = false)
    class UpdateExampleCommand extends SelfValidating<UpdateExampleCommand> {
        @NotNull
        Long id;
        @Email
        String email;
        @NotNull
        Integer number;

        public UpdateExampleCommand(Long id, String email, Integer number) {
            this.id = id;
            this.email = email;
            this.number = number;
            this.validateSelf();
        }

    }
}
