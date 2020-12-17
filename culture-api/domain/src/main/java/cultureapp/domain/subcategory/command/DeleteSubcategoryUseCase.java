package cultureapp.domain.subcategory.command;

import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;

import javax.validation.constraints.Positive;

public interface DeleteSubcategoryUseCase {
    void deleteSubcategoryById(@Positive Long id, @Positive Long categoryId) throws SubcategoryNotFoundException, CategoryNotFoundException;
}
