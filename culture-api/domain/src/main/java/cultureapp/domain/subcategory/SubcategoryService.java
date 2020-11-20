package cultureapp.domain.subcategory;

import cultureapp.domain.category.Category;
import cultureapp.domain.category.CategoryRepository;
import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.subcategory.command.AddSubcategoryUseCase;
import cultureapp.domain.subcategory.query.GetSubcategoriesQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SubcategoryService implements
        AddSubcategoryUseCase,
        GetSubcategoriesQuery {
    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void addSubcategory(AddSubcategoryCommand command) throws CategoryNotFoundException {
        Optional<Category> category = categoryRepository.findById(command.getCategoryId());
        if (category.isEmpty())
            throw new CategoryNotFoundException(command.getCategoryId());

        Subcategory subcategory = Subcategory.of(category.get(), command.getName());
        subcategoryRepository.save(subcategory);
    }

    @Override
    public List<GetSubcategoriesDTO> getSubcategories(@Positive Long categoryId) throws CategoryNotFoundException {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty())
            throw new CategoryNotFoundException(categoryId);

        return subcategoryRepository
                .findAllByCategoryId(category.get().getId())
                .stream()
                .map(GetSubcategoriesDTO::of)
                .collect(Collectors.toList());
    }
}
