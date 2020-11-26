package cultureapp.rest.subcategory;

import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.subcategory.command.AddSubcategoryUseCase;
import cultureapp.domain.subcategory.command.DeleteSubcategoryUseCase;
import cultureapp.domain.subcategory.command.UpdateSubcategoryUseCase;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import cultureapp.domain.subcategory.query.GetSubcategoriesQuery;
import cultureapp.domain.subcategory.query.GetSubcategoryByIdQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/category/{categoryId}/subcategory", produces = MediaType.APPLICATION_JSON_VALUE)
public class SubcategoryController {
    private final AddSubcategoryUseCase addSubcategoryUseCase;
    private final GetSubcategoriesQuery getSubcategoriesQuery;
    private final GetSubcategoryByIdQuery getSubcategoryByIdQuery;
    private final UpdateSubcategoryUseCase updateSubcategoryUseCase;
    private final DeleteSubcategoryUseCase deleteSubcategoryUseCase;

    @PostMapping("")
    public void addSubcategory(
            @PathVariable Long categoryId,
            @RequestBody SubcategoryRequest request) throws CategoryNotFoundException {
        AddSubcategoryUseCase.AddSubcategoryCommand command =
                new AddSubcategoryUseCase.AddSubcategoryCommand(categoryId, request.getName());
        addSubcategoryUseCase.addSubcategory(command);
    }

    @GetMapping("")
    public ResponseEntity<List<GetSubcategoriesQuery.GetSubcategoriesDTO>> getSubcategories(
            @PathVariable Long categoryId) throws CategoryNotFoundException {
        return ResponseEntity.ok(getSubcategoriesQuery.getSubcategories(categoryId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSubcategoryByIdQuery.GetSubcategoryByIdDTO> getSubcategory(
            @PathVariable Long categoryId, @PathVariable Long id) throws SubcategoryNotFoundException {
        return ResponseEntity.ok(getSubcategoryByIdQuery.getSubcategory(id, categoryId));
    }

    @PutMapping("/{id}")
    public void updateSubcategory(@PathVariable Long categoryId,
                                  @PathVariable Long id,
                                  @RequestBody SubcategoryRequest request) throws SubcategoryNotFoundException {
        UpdateSubcategoryUseCase.UpdateSubcategoryCommand command =
                new UpdateSubcategoryUseCase.UpdateSubcategoryCommand(id, categoryId, request.getName());
        updateSubcategoryUseCase.updateSubcategory(command);
    }

    @DeleteMapping("/{id}")
    public void deleteSubcategory(@PathVariable Long categoryId,
                                  @PathVariable Long id) throws CategoryNotFoundException, SubcategoryNotFoundException {
        deleteSubcategoryUseCase.deleteSubcategoryById(categoryId, id);
    }

}
