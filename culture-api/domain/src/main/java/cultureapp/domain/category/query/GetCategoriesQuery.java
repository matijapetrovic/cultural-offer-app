package cultureapp.domain.category.query;

import cultureapp.domain.category.Category;
import cultureapp.domain.category.exception.CategoryNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public interface GetCategoriesQuery {
    Slice<GetCategoriesDTO> getCategories(@PositiveOrZero Integer page,
                                          @Positive Integer limit) throws CategoryNotFoundException;

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
