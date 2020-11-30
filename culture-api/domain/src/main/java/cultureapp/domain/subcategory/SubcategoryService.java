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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

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
    public Slice<GetSubcategoriesDTO> getSubcategories(@Positive Long categoryId,
                                                      @PositiveOrZero Integer page,
                                                      @Positive Integer limit) throws CategoryNotFoundException {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        Pageable pageRequest = PageRequest.of(page, limit, Sort.by("name"));

        Slice<Subcategory> subcategories = subcategoryRepository
                .findAllByCategoryIdAndArchivedFalse(category.getId(), pageRequest);

        return subcategories.map(GetSubcategoriesDTO::of);
    }

    @Override
    public GetSubcategoryByIdDTO getSubcategory(@Positive Long id, @Positive Long categoryId)
            throws SubcategoryNotFoundException {
        Subcategory subcategory = subcategoryRepository.findByIdAndCategoryIdAndArchivedFalse(categoryId, id)
                .orElseThrow(() -> new SubcategoryNotFoundException(id, categoryId));
        return GetSubcategoryByIdDTO.of(subcategory);
    }

    @Override
    public void updateSubcategory(UpdateSubcategoryCommand command) throws SubcategoryNotFoundException {
        Subcategory subcategory =
                subcategoryRepository.findByIdAndCategoryIdAndArchivedFalse(command.getCategoryId(), command.getId())
                .orElseThrow(() -> new SubcategoryNotFoundException(command.getId(), command.getCategoryId()));
        if(subcategory.update(command.getName()))
            subcategoryRepository.save(subcategory);
    }

    @Override
    public void deleteSubcategoryById(@Positive Long categoryId, @Positive Long id)
            throws SubcategoryNotFoundException {
        Subcategory subcategory = subcategoryRepository.findByIdAndCategoryIdAndArchivedFalse(id, categoryId)
                .orElseThrow(() -> new SubcategoryNotFoundException(id, categoryId));
        subcategory.archive();
        subcategoryRepository.save(subcategory);
    }
}
