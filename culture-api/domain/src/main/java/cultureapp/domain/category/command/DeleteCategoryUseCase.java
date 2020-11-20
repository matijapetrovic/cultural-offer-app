package cultureapp.domain.category.command;

import cultureapp.domain.category.exception.CategoryNotFoundException;

import javax.validation.constraints.Positive;

public interface DeleteCategoryUseCase {
    void deleteCategoryById(@Positive Long id) throws CategoryNotFoundException;
}
