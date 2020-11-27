package cultureapp.domain.category;

import cultureapp.domain.category.command.AddCategoryUseCase;
import cultureapp.domain.category.command.DeleteCategoryUseCase;
import cultureapp.domain.category.command.UpdateCategoryUseCase;
import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.category.query.GetCategoriesQuery;
import cultureapp.domain.category.query.GetCategoryByIdQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<GetCategoriesDTO> getCategories() {
        return categoryRepository
                .findAllByArchivedFalse()
                .stream()
                .map(GetCategoriesDTO::of)
                .collect(Collectors.toList());
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
