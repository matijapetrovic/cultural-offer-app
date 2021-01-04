package cultureapp.domain.subcategory.query;

import cultureapp.domain.core.validation.SelfValidating;
import cultureapp.domain.subcategory.Subcategory;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

public interface GetSubcategoryNamesQueryHandler {

    List<GetSubcategoryNamesDTO> getSubcategoryNames(GetSubcategoryNamesQuery query);

    @Value
    @EqualsAndHashCode(callSuper = false)
    class GetSubcategoryNamesQuery extends SelfValidating<GetSubcategoryNamesQuery> {
        @Positive
        @NotNull
        Long categoryId;

        public GetSubcategoryNamesQuery(Long categoryId) {
            this.categoryId = categoryId;
            this.validateSelf();
        }
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    class GetSubcategoryNamesDTO {
        Long id;
        String name;

        public static GetSubcategoryNamesDTO of(Subcategory subcategory) {
            return new GetSubcategoryNamesDTO(
                    subcategory.getId(),
                    subcategory.getName());
        }
    }
}
