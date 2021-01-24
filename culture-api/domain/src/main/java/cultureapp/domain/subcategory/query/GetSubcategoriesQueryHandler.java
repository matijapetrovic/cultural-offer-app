package cultureapp.domain.subcategory.query;

import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.core.validation.SelfValidating;
import cultureapp.domain.subcategory.Subcategory;
import lombok.*;
import org.springframework.data.domain.Slice;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public interface GetSubcategoriesQueryHandler {
    Slice<GetSubcategoriesDTO> handleGetSubcategories(GetSubcategoriesQuery query) throws CategoryNotFoundException;

    @Value
    @EqualsAndHashCode(callSuper = false)
    class GetSubcategoriesQuery extends SelfValidating<GetSubcategoriesQuery> {
        @Positive
        Long categoryId;

        @PositiveOrZero
        Integer page;

        @Positive
        Integer limit;

        public GetSubcategoriesQuery(
                Long categoryId,
                Integer page,
                Integer limit
        ) {
            this.categoryId = categoryId;
            this.page = page;
            this.limit = limit;
            this.validateSelf();
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    class GetSubcategoriesDTO {
        Long id;
        String name;
        Long categoryId;

        public static GetSubcategoriesDTO of(Subcategory subcategory) {
            return new GetSubcategoriesDTO(
                    subcategory.getId(),
                    subcategory.getName(),
                    subcategory.getCategory().getId());
        }
    }
}
