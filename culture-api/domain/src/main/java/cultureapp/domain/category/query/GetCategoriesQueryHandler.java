package cultureapp.domain.category.query;

import cultureapp.domain.category.Category;
import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.core.validation.SelfValidating;
import lombok.*;
import org.springframework.data.domain.Slice;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public interface GetCategoriesQueryHandler {
    Slice<GetCategoriesDTO> handleGetCategories(GetCategoriesQuery query) throws CategoryNotFoundException;

    @Value
    @EqualsAndHashCode(callSuper = false)
    class GetCategoriesQuery extends SelfValidating<GetCategoriesQuery> {
        @PositiveOrZero
        Integer page;

        @Positive
        Integer limit;

        public GetCategoriesQuery(
                Integer page,
                Integer limit
        ) {
            this.page = page;
            this.limit = limit;
            this.validateSelf();
        }
    }

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
