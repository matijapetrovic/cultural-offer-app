package cultureapp.domain.subcategory.query;

import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.subcategory.Subcategory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Positive;
import java.util.List;

public interface GetSubcategoriesQuery {
    List<GetSubcategoriesDTO> getSubcategories(@Positive Long categoryId) throws CategoryNotFoundException;

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    class GetSubcategoriesDTO {
        Long id;
        String name;

        public static GetSubcategoriesDTO of(Subcategory subcategory) {
            return new GetSubcategoriesDTO(
                    subcategory.getId(),
                    subcategory.getName());
        }
    }
}
