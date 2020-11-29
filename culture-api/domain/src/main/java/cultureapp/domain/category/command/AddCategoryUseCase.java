package cultureapp.domain.category.command;

import cultureapp.domain.core.validation.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotBlank;

public interface AddCategoryUseCase {
    void addCategory(AddCategoryCommand command);

    @Value
    @EqualsAndHashCode(callSuper = false)
    class AddCategoryCommand extends SelfValidating<AddCategoryCommand> {
        @NotBlank
        String name;

        public AddCategoryCommand(String name) {
            this.name = name;
            this.validateSelf();
        }
    }
}