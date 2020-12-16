package cultureapp.domain.subcategory;

import cultureapp.domain.category.Category;
import cultureapp.domain.category.CategoryRepository;
import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.subcategory.command.AddSubcategoryUseCase;
import cultureapp.domain.subcategory.command.DeleteSubcategoryUseCase;
import cultureapp.domain.subcategory.command.UpdateSubcategoryUseCase;
import cultureapp.domain.subcategory.exception.SubcategoryAlreadyExistsException;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import cultureapp.domain.subcategory.query.GetSubcategoriesQuery;
import cultureapp.domain.subcategory.query.GetSubcategoryByIdQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
        DeleteSubcategoryUseCase {
    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void addSubcategory(AddSubcategoryCommand command)
            throws CategoryNotFoundException, SubcategoryAlreadyExistsException {
        Category category = categoryRepository.findByIdAndArchivedFalse(command.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(command.getCategoryId()));

        Subcategory subcategory = Subcategory.of(category, command.getName());
        saveSubcategory(subcategory);
    }

    @Override
    public Slice<GetSubcategoriesDTO> getSubcategories(
            @Positive Long categoryId,
            @PositiveOrZero Integer page,
            @Positive Integer limit) throws CategoryNotFoundException {
        Category category = categoryRepository.findByIdAndArchivedFalse(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        Pageable pageRequest = PageRequest.of(page, limit, Sort.by("name"));

        Slice<Subcategory> subcategories = subcategoryRepository
                .findAllByCategoryIdAndArchivedFalse(category.getId(), pageRequest);

        return subcategories.map(GetSubcategoriesDTO::of);
    }

    @Override
    public GetSubcategoryByIdDTO getSubcategory(@Positive Long id, @Positive Long categoryId)
            throws SubcategoryNotFoundException {
        Subcategory subcategory = subcategoryRepository.findByIdAndCategoryIdAndArchivedFalse(id, categoryId)
                .orElseThrow(() -> new SubcategoryNotFoundException(id, categoryId));
        return GetSubcategoryByIdDTO.of(subcategory);
    }

    @Override
    public void updateSubcategory(UpdateSubcategoryCommand command)
            throws SubcategoryNotFoundException, SubcategoryAlreadyExistsException {
        Subcategory subcategory =
                subcategoryRepository.findByIdAndCategoryIdAndArchivedFalse(command.getId(), command.getCategoryId())
                .orElseThrow(() -> new SubcategoryNotFoundException(command.getId(), command.getCategoryId()));
        if(subcategory.update(command.getName()))
            saveSubcategory(subcategory);
    }

    @Override
    public void deleteSubcategoryById(@Positive Long id, @Positive Long categoryId)
            throws SubcategoryNotFoundException {
        Subcategory subcategory = subcategoryRepository.findByIdAndCategoryIdAndArchivedFalse(id, categoryId)
                .orElseThrow(() -> new SubcategoryNotFoundException(id, categoryId));
        subcategory.archive();
        subcategoryRepository.save(subcategory);
    }

    private void saveSubcategory(Subcategory subcategory) throws SubcategoryAlreadyExistsException {
        try {
            subcategoryRepository.save(subcategory);
        } catch (DataIntegrityViolationException ex) {
            throw new SubcategoryAlreadyExistsException(subcategory.getName());
        }
    }
}
