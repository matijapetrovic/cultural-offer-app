package cultureapp.rest.category;

import cultureapp.domain.category.command.AddCategoryUseCase;
import cultureapp.domain.category.command.DeleteCategoryUseCase;
import cultureapp.domain.category.command.UpdateCategoryUseCase;
import cultureapp.domain.category.exception.CategoryAlreadyExistsException;
import cultureapp.domain.category.exception.CategoryCannotBeDeletedException;
import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.category.query.GetCategoriesQueryHandler;
import cultureapp.domain.category.query.GetCategoryByIdQueryHandler;
import cultureapp.domain.category.query.GetCategoryNamesQueryHandler;
import cultureapp.domain.subcategory.exception.SubcategoryAlreadyExistsException;
import cultureapp.rest.core.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {
    private final AddCategoryUseCase addCategoryUseCase;
    private final GetCategoriesQueryHandler getCategoriesQueryHandler;
    private final GetCategoryByIdQueryHandler getCategoryByIdQueryHandler;
    private final GetCategoryNamesQueryHandler getCategoryNamesQueryHandler;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> addCategory(@RequestBody CategoryRequest request) throws CategoryNotFoundException, CategoryAlreadyExistsException {
        AddCategoryUseCase.AddCategoryCommand command =
                new AddCategoryUseCase.AddCategoryCommand(request.getName());
        addCategoryUseCase.addCategory(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/names")
    public ResponseEntity<List<GetCategoryNamesQueryHandler.GetCategoryNamesDTO>> getNames() {
        return ResponseEntity.ok(getCategoryNamesQueryHandler.getCategoryNames());
    }

    @GetMapping(value = "", params = { "page", "limit" })
    public ResponseEntity<PaginatedResponse<GetCategoriesQueryHandler.GetCategoriesDTO>> getCategories(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit,
            UriComponentsBuilder uriBuilder) throws CategoryNotFoundException {
        GetCategoriesQueryHandler.GetCategoriesQuery query =
                new GetCategoriesQueryHandler.GetCategoriesQuery(page, limit);
        Slice<GetCategoriesQueryHandler.GetCategoriesDTO> result =
                getCategoriesQueryHandler.handleGetCategories(query);
        String resourceUri = "/api/categories";
        uriBuilder.path(resourceUri);
        return ResponseEntity.ok(PaginatedResponse.of(result, uriBuilder));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@PathVariable Long id) throws CategoryNotFoundException {
        GetCategoryByIdQueryHandler.GetCategoryByIdQuery query =
                new GetCategoryByIdQueryHandler.GetCategoryByIdQuery(id);
        return ResponseEntity.ok(getCategoryByIdQueryHandler.handleGetCategory(query));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> updateCategory(@PathVariable Long id,
                                @RequestBody CategoryRequest request) throws CategoryNotFoundException, SubcategoryAlreadyExistsException, CategoryAlreadyExistsException {
        UpdateCategoryUseCase.UpdateCategoryCommand command =
                new UpdateCategoryUseCase.UpdateCategoryCommand(id, request.getName());
        updateCategoryUseCase.updateCategory(command);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) throws CategoryNotFoundException, CategoryCannotBeDeletedException {
        deleteCategoryUseCase.deleteCategoryById(id);
        return ResponseEntity.noContent().build();
    }
}
