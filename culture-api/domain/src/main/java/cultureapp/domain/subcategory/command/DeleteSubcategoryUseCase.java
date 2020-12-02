package cultureapp.domain.subcategory.command;

import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;

import javax.validation.constraints.Positive;

public interface DeleteSubcategoryUseCase {
    void deleteSubcategoryById(@Positive Long categoryId, @Positive Long id) throws SubcategoryNotFoundException, CategoryNotFoundException;
}
