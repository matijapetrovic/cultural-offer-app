package cultureapp.domain.subcategory.query;

import cultureapp.domain.core.validation.SelfValidating;
import cultureapp.domain.subcategory.Subcategory;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import lombok.*;

import javax.validation.constraints.Positive;

public interface GetSubcategoryByIdQueryHandler {
    GetSubcategoryByIdDTO handleGetSubcategory(GetSubcategoryByIdQuery query) throws SubcategoryNotFoundException;

    @Value
    @EqualsAndHashCode(callSuper = false)
    class GetSubcategoryByIdQuery extends SelfValidating<GetSubcategoryByIdQuery> {
        @Positive
        Long id;

        @Positive
        Long categoryId;

        public GetSubcategoryByIdQuery(
                Long id,
                Long categoryId
        ) {
            this.id = id;
            this.categoryId = categoryId;
            this.validateSelf();
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    class GetSubcategoryByIdDTO {
        Long id;
        Long categoryId;
        String name;

        public static GetSubcategoryByIdDTO of(Subcategory subcategory) {
            return new GetSubcategoryByIdDTO(
                    subcategory.getId(),
                    subcategory.getCategory().getId(),
                    subcategory.getName());
        }
    }
}
