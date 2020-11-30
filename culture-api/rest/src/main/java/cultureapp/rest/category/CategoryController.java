package cultureapp.rest.category;

import cultureapp.domain.category.command.AddCategoryUseCase;
import cultureapp.domain.category.command.DeleteCategoryUseCase;
import cultureapp.domain.category.command.UpdateCategoryUseCase;
import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.category.query.GetCategoriesQuery;
import cultureapp.domain.category.query.GetCategoryByIdQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public void addCategory(@RequestBody CategoryRequest request) throws CategoryNotFoundException {
        AddCategoryUseCase.AddCategoryCommand command =
                new AddCategoryUseCase.AddCategoryCommand(request.getName());
        addCategoryUseCase.addCategory(command);
    }

    @GetMapping("")
    public ResponseEntity<List<GetCategoriesQuery.GetCategoriesDTO>> getCategories(
            @RequestParam(required = false) Integer page) throws CategoryNotFoundException {
        Pageable pageable = PageRequest.of(page != null ? page : 0, 2);
        return ResponseEntity.ok(getCategoriesQuery.getCategories(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@PathVariable Long id) throws CategoryNotFoundException {
        return ResponseEntity.ok(getCategoryByIdQuery.getCategory(id));
    }

    @PutMapping("/{id}")
    public void updateCategory(@PathVariable Long id,
                                @RequestBody CategoryRequest request) throws CategoryNotFoundException {
        UpdateCategoryUseCase.UpdateCategoryCommand command =
                new UpdateCategoryUseCase.UpdateCategoryCommand(id, request.getName());
        updateCategoryUseCase.updateCategory(command);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) throws CategoryNotFoundException {
        deleteCategoryUseCase.deleteCategoryById(id);
    }
}
