package cultureapp.domain.subcategory.command;

import cultureapp.domain.core.validation.SelfValidating;
import cultureapp.domain.subcategory.exception.SubcategoryAlreadyExistsException;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public interface UpdateSubcategoryUseCase {
    void updateSubcategory(UpdateSubcategoryCommand command) throws SubcategoryNotFoundException, SubcategoryAlreadyExistsException;

    @Value
    @EqualsAndHashCode(callSuper = false)
    class UpdateSubcategoryCommand extends SelfValidating<UpdateSubcategoryCommand> {
        @Positive
        Long id;
        @Positive
        Long categoryId;
        @NotBlank
        String name;

        public UpdateSubcategoryCommand(Long id, Long categoryId, String name) {
            this.id = id;
            this.categoryId = categoryId;
            this.name = name;
            this.validateSelf();
        }
    }
}
