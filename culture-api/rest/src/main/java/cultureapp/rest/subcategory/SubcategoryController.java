package cultureapp.rest.subcategory;

import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.subcategory.command.AddSubcategoryUseCase;
import cultureapp.domain.subcategory.command.DeleteSubcategoryUseCase;
import cultureapp.domain.subcategory.command.UpdateSubcategoryUseCase;
import cultureapp.domain.subcategory.exception.SubcategoryAlreadyExistsException;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import cultureapp.domain.subcategory.query.GetSubcategoriesQueryHandler;
import cultureapp.domain.subcategory.query.GetSubcategoryByIdQueryHandler;
import cultureapp.rest.core.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/categories/{categoryId}/subcategories", produces = MediaType.APPLICATION_JSON_VALUE)
public class SubcategoryController {
    private final AddSubcategoryUseCase addSubcategoryUseCase;
    private final GetSubcategoriesQueryHandler getSubcategoriesQueryHandler;
    private final GetSubcategoryByIdQueryHandler getSubcategoryByIdQueryHandler;
    private final UpdateSubcategoryUseCase updateSubcategoryUseCase;
    private final DeleteSubcategoryUseCase deleteSubcategoryUseCase;

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> addSubcategory(
            @PathVariable Long categoryId,
            @RequestBody SubcategoryRequest request) throws CategoryNotFoundException, SubcategoryAlreadyExistsException {
        AddSubcategoryUseCase.AddSubcategoryCommand command =
                new AddSubcategoryUseCase.AddSubcategoryCommand(categoryId, request.getName());
        addSubcategoryUseCase.addSubcategory(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "", params = { "page", "limit" })
    public ResponseEntity<PaginatedResponse<GetSubcategoriesQueryHandler.GetSubcategoriesDTO>> getSubcategories(
            @PathVariable Long categoryId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit,
            UriComponentsBuilder uriBuilder) throws CategoryNotFoundException {
        GetSubcategoriesQueryHandler.GetSubcategoriesQuery query =
                new GetSubcategoriesQueryHandler.GetSubcategoriesQuery(categoryId, page, limit);

        Slice<GetSubcategoriesQueryHandler.GetSubcategoriesDTO> result =
                getSubcategoriesQueryHandler.handleGetSubcategories(query);
        String resourceUri = String.format("/api/categories/%d/subcategories", categoryId);
        uriBuilder.path(resourceUri);
        return ResponseEntity.ok(PaginatedResponse.of(result, uriBuilder));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSubcategoryByIdQueryHandler.GetSubcategoryByIdDTO> getSubcategory(
            @PathVariable Long categoryId, @PathVariable Long id) throws SubcategoryNotFoundException {
        GetSubcategoryByIdQueryHandler.GetSubcategoryByIdQuery query =
                new GetSubcategoryByIdQueryHandler.GetSubcategoryByIdQuery(id, categoryId);

        return ResponseEntity.ok(getSubcategoryByIdQueryHandler.handleGetSubcategory(query));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> updateSubcategory(@PathVariable Long categoryId,
                                  @PathVariable Long id,
                                  @RequestBody SubcategoryRequest request) throws SubcategoryNotFoundException, SubcategoryAlreadyExistsException {
        UpdateSubcategoryUseCase.UpdateSubcategoryCommand command =
                new UpdateSubcategoryUseCase.UpdateSubcategoryCommand(id, categoryId, request.getName());
        updateSubcategoryUseCase.updateSubcategory(command);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteSubcategory(@PathVariable Long categoryId,
                                  @PathVariable Long id) throws CategoryNotFoundException, SubcategoryNotFoundException {
        deleteSubcategoryUseCase.deleteSubcategoryById(id, categoryId);
        return ResponseEntity.noContent().build();
    }

}
