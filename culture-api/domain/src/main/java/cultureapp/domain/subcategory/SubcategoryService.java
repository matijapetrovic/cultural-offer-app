package cultureapp.domain.subcategory;

import cultureapp.domain.category.Category;
import cultureapp.domain.category.CategoryRepository;
import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.subcategory.command.AddSubcategoryUseCase;
import cultureapp.domain.subcategory.command.DeleteSubcategoryUseCase;
import cultureapp.domain.subcategory.command.UpdateSubcategoryUseCase;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import cultureapp.domain.subcategory.query.GetSubcategoriesQuery;
import cultureapp.domain.subcategory.query.GetSubcategoryByIdQuery;
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
        GetSubcategoriesQuery,
        GetSubcategoryByIdQuery,
        UpdateSubcategoryUseCase,
        DeleteSubcategoryUseCase{
    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void addSubcategory(AddSubcategoryCommand command) throws CategoryNotFoundException {
        Category category = categoryRepository.findById(command.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(command.getCategoryId()));

        Subcategory subcategory = Subcategory.of(category, command.getName());
        subcategoryRepository.save(subcategory);
    }

    @Override
    public List<GetSubcategoriesDTO> getSubcategories(@Positive Long categoryId)
            throws CategoryNotFoundException {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        return subcategoryRepository
                .findAllByCategoryIdAndArchivedFalse(category.getId())
                .stream()
                .map(GetSubcategoriesDTO::of)
                .collect(Collectors.toList());
    }

    @Override
    public GetSubcategoryByIdDTO getSubcategory(@Positive Long id, @Positive Long categoryId)
            throws SubcategoryNotFoundException {
        Subcategory subcategory = subcategoryRepository.findByCategoryIdAndIdAndArchivedFalse(categoryId, id)
                .orElseThrow(() -> new SubcategoryNotFoundException(id, categoryId));
        return GetSubcategoryByIdDTO.of(subcategory);
    }

    @Override
    public void updateSubcategory(UpdateSubcategoryCommand command) throws SubcategoryNotFoundException {
        Subcategory subcategory =
                subcategoryRepository.findByCategoryIdAndIdAndArchivedFalse(command.getCategoryId(), command.getId())
                .orElseThrow(() -> new SubcategoryNotFoundException(command.getId(), command.getCategoryId()));
        if(subcategory.update(command.getName()))
            subcategoryRepository.save(subcategory);
    }

    @Override
    public void deleteSubcategoryById(@Positive Long categoryId, @Positive Long id)
            throws SubcategoryNotFoundException {
        Subcategory subcategory = subcategoryRepository.findByCategoryIdAndIdAndArchivedFalse(categoryId, id)
                .orElseThrow(() -> new SubcategoryNotFoundException(id, categoryId));
        subcategory.archive();
        subcategoryRepository.save(subcategory);
    }
}
