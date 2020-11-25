package cultureapp.domain.subcategory.command;

import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.core.validation.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public interface AddSubcategoryUseCase {
    void addSubcategory(AddSubcategoryCommand command) throws CategoryNotFoundException;

    @Value
    @EqualsAndHashCode(callSuper = false)
    class AddSubcategoryCommand extends SelfValidating<AddSubcategoryCommand> {
        @Positive
        Long categoryId;
        @NotBlank
        String name;

        public AddSubcategoryCommand(Long cateogoryId, String name) {
            this.categoryId = cateogoryId;
            this.name = name;
            this.validateSelf();
        }
    }
}
