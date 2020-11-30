package cultureapp.domain.subcategory.query;

import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.subcategory.Subcategory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

public interface GetSubcategoriesQuery {
    Slice<GetSubcategoriesDTO> getSubcategories(@Positive Long categoryId,
                                               @PositiveOrZero Integer page,
                                               @Positive Integer limit) throws CategoryNotFoundException;

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
