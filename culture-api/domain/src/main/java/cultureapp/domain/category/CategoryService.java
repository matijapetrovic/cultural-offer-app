package cultureapp.domain.category;

import cultureapp.domain.category.command.AddCategoryUseCase;
import cultureapp.domain.category.command.DeleteCategoryUseCase;
import cultureapp.domain.category.command.UpdateCategoryUseCase;
import cultureapp.domain.category.exception.CategoryAlreadyExistsException;
import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.category.query.GetCategoriesQueryHandler;
import cultureapp.domain.category.query.GetCategoryByIdQueryHandler;
import cultureapp.domain.category.query.GetCategoryNamesQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService implements
        AddCategoryUseCase,
        DeleteCategoryUseCase,
        UpdateCategoryUseCase,
        GetCategoriesQueryHandler,
        GetCategoryByIdQueryHandler,
        GetCategoryNamesQueryHandler {
    private final CategoryRepository categoryRepository;

    @Override
    public void addCategory(AddCategoryCommand command) throws CategoryAlreadyExistsException {
        Category category = Category.of(command.getName());
        saveCategory(category);
    }

    @Override
    public GetCategoryByIdDTO handleGetCategory(GetCategoryByIdQuery query) throws CategoryNotFoundException {
        Category category = categoryRepository.findByIdAndArchivedFalse(query.getId())
                .orElseThrow(() -> new CategoryNotFoundException(query.getId()));
        return GetCategoryByIdDTO.of(category);
    }

    @Override
    public Slice<GetCategoriesDTO> handleGetCategories(GetCategoriesQuery query) {

        Pageable pageRequest = PageRequest.of(query.getPage(), query.getLimit(), Sort.by("name"));
        Slice<Category> categories =
                categoryRepository.findAllByArchivedFalse(pageRequest);
        return categories.map(GetCategoriesDTO::of);
    }

    @Override
    public void updateCategory(UpdateCategoryCommand command) throws CategoryNotFoundException, CategoryAlreadyExistsException {
        Category category = categoryRepository.findByIdAndArchivedFalse(command.getId())
                .orElseThrow(() -> new CategoryNotFoundException(command.getId()));
        if(category.update(command.getName()))
            saveCategory(category);
    }

    @Override
    public void deleteCategoryById(@Positive Long id) throws CategoryNotFoundException {
        Category category = categoryRepository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        if(!categoryRepository.existsWithSubcategory(id)) {
            category.archive();
            categoryRepository.save(category);
        }
    }


    @Override
    public List<GetCategoryNamesDTO> getCategoryNames() {
        return categoryRepository.findAllByArchivedFalse()
                .stream()
                .map(GetCategoryNamesDTO::of)
                .collect(Collectors.toList());
    }

    private void saveCategory(Category category) throws CategoryAlreadyExistsException {
        try {
            categoryRepository.save(category);
        } catch (DataIntegrityViolationException ex) {
            throw new CategoryAlreadyExistsException(category.getName());
        }
    }

}
