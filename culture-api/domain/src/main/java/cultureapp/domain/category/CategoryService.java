package cultureapp.domain.category;

import cultureapp.domain.category.command.AddCategoryUseCase;
import cultureapp.domain.category.query.GetCategoriesQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService implements
        AddCategoryUseCase,
        GetCategoriesQuery {
    private final CategoryRepository categoryRepository;

    @Override
    public void addCategory(AddCategoryCommand command) {
        Category category = Category.of(command.getName());
        categoryRepository.save(category);
    }

    @Override
    public List<GetCategoriesDTO> getCategories() {
        return categoryRepository
                .findAll()
                .stream()
                .map(GetCategoriesDTO::of)
                .collect(Collectors.toList());
    }
}
