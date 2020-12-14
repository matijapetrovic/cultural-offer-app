package cultureapp.rest.category;

import cultureapp.domain.category.command.AddCategoryUseCase;
import cultureapp.domain.category.command.DeleteCategoryUseCase;
import cultureapp.domain.category.command.UpdateCategoryUseCase;
import cultureapp.domain.category.exception.CategoryAlreadyExists;
import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.category.query.GetCategoriesQuery;
import cultureapp.domain.category.query.GetCategoryByIdQuery;
import cultureapp.domain.subcategory.exception.SubcategoryAlreadyExists;
import cultureapp.rest.core.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {
    private final AddCategoryUseCase addCategoryUseCase;
    private final GetCategoriesQuery getCategoriesQuery;
    private final GetCategoryByIdQuery getCategoryByIdQuery;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addCategory(@RequestBody CategoryRequest request) throws CategoryNotFoundException, CategoryAlreadyExists {
        AddCategoryUseCase.AddCategoryCommand command =
                new AddCategoryUseCase.AddCategoryCommand(request.getName());
        addCategoryUseCase.addCategory(command);
    }

    @GetMapping(value = "", params = { "page", "limit" })
    public ResponseEntity<PaginatedResponse<GetCategoriesQuery.GetCategoriesDTO>> getCategories(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit,
            UriComponentsBuilder uriBuilder) throws CategoryNotFoundException {

        Slice<GetCategoriesQuery.GetCategoriesDTO> result =
                getCategoriesQuery.getCategories(page, limit);
        String resourceUri = "/api/categories";
        uriBuilder.path(resourceUri);
        return ResponseEntity.ok(PaginatedResponse.of(result, uriBuilder));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@PathVariable Long id) throws CategoryNotFoundException {
        return ResponseEntity.ok(getCategoryByIdQuery.getCategory(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateCategory(@PathVariable Long id,
                                @RequestBody CategoryRequest request) throws CategoryNotFoundException, SubcategoryAlreadyExists, CategoryAlreadyExists {
        UpdateCategoryUseCase.UpdateCategoryCommand command =
                new UpdateCategoryUseCase.UpdateCategoryCommand(id, request.getName());
        updateCategoryUseCase.updateCategory(command);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteCategory(@PathVariable Long id) throws CategoryNotFoundException {
        deleteCategoryUseCase.deleteCategoryById(id);
    }
}
