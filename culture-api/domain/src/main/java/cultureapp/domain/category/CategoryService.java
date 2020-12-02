package cultureapp.domain.category;

import cultureapp.domain.category.command.AddCategoryUseCase;
import cultureapp.domain.category.command.DeleteCategoryUseCase;
import cultureapp.domain.category.command.UpdateCategoryUseCase;
import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.category.query.GetCategoriesQuery;
import cultureapp.domain.category.query.GetCategoryByIdQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RequiredArgsConstructor
@Service
public class CategoryService implements
        AddCategoryUseCase,
        DeleteCategoryUseCase,
        UpdateCategoryUseCase,
        GetCategoriesQuery,
        GetCategoryByIdQuery {
    private final CategoryRepository categoryRepository;

    @Override
    public void addCategory(AddCategoryCommand command) {
        Category category = Category.of(command.getName());
        categoryRepository.save(category);
    }

    @Override
    public GetCategoryByIdDTO getCategory(@Positive Long id) throws CategoryNotFoundException {
        Category category = categoryRepository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        return GetCategoryByIdDTO.of(category);
    }

    @Override
    public Slice<GetCategoriesDTO> getCategories(@PositiveOrZero Integer page,
                                                 @Positive Integer limit) {

        Pageable pageRequest = PageRequest.of(page, limit, Sort.by("name"));
        Slice<Category> categories =
                categoryRepository.findAllByArchivedFalse(pageRequest);
        return categories.map(GetCategoriesDTO::of);
    }

    @Override
    public void updateCategory(UpdateCategoryCommand command) throws CategoryNotFoundException {
        Category category = categoryRepository.findByIdAndArchivedFalse(command.getId())
                .orElseThrow(() -> new CategoryNotFoundException(command.getId()));
        if(category.update(command.getName()))
            categoryRepository.save(category);
    }

    @Override
    public void deleteCategoryById(@Positive Long id) throws CategoryNotFoundException {
        Category category = categoryRepository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        category.archive();
        categoryRepository.save(category);
    }
}
