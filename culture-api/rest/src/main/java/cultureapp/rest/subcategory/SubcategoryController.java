package cultureapp.rest.subcategory;

import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.subcategory.command.AddSubcategoryUseCase;
import cultureapp.domain.subcategory.query.GetSubcategoriesQuery;
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
}
