package cultureapp.rest.category;

import cultureapp.domain.category.command.AddCategoryUseCase;
import cultureapp.domain.category.query.GetCategoriesQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/category", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {
    private final AddCategoryUseCase addCategoryUseCase;
    private final GetCategoriesQuery getCategoriesQuery;

    @PostMapping("")
    public void addCategory(@RequestBody CategoryRequest request) {
        AddCategoryUseCase.AddCategoryCommand command =
                new AddCategoryUseCase.AddCategoryCommand(request.getName());
        addCategoryUseCase.addCategory(command);
    }

    @GetMapping("")
    public ResponseEntity<List<GetCategoriesQuery.GetCategoriesDTO>> getCategories() {
        return ResponseEntity.ok(getCategoriesQuery.getCategories());
    }

}
