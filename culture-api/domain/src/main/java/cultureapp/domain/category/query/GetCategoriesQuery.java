package cultureapp.domain.category.query;

import cultureapp.domain.category.Category;
import cultureapp.domain.category.exception.CategoryNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public interface GetCategoriesQuery {
    List<GetCategoriesDTO> getCategories() throws CategoryNotFoundException;

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    class GetCategoriesDTO {
        Long id;
        String name;

        public static GetCategoriesDTO of(Category category) {
            return new GetCategoriesDTO(
                    category.getId(),
                    category.getName());
        }
    }
}
