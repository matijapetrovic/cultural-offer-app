package cultureapp.domain.category.command;

import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.core.validation.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public interface UpdateCategoryUseCase {
    void updateCategory(UpdateCategoryCommand command) throws CategoryNotFoundException;

    @Value
    @EqualsAndHashCode(callSuper = false)
    class UpdateCategoryCommand extends SelfValidating<UpdateCategoryCommand> {
        @Positive
        Long id;
        @NotBlank
        String name;

        public UpdateCategoryCommand(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
