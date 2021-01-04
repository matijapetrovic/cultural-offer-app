package cultureapp.domain.category.query;


import cultureapp.domain.category.Category;
import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.core.validation.SelfValidating;
import lombok.*;

import javax.validation.constraints.Positive;

public interface GetCategoryByIdQueryHandler {
    GetCategoryByIdDTO handleGetCategory(GetCategoryByIdQuery query) throws CategoryNotFoundException;

    @Value
    @EqualsAndHashCode(callSuper = false)
    class GetCategoryByIdQuery extends SelfValidating<GetCategoryByIdQuery> {
        @Positive
        Long id;

        public GetCategoryByIdQuery(Long id) {
            this.id = id;
            this.validateSelf();
        }
    }


    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    class GetCategoryByIdDTO {
        Long id;
        String name;

        public static GetCategoryByIdDTO of(Category category) {
            return new GetCategoryByIdDTO(
                    category.getId(),
                    category.getName());
        }
    }
}
