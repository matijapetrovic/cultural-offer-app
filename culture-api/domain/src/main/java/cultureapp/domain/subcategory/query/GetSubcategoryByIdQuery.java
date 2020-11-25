package cultureapp.domain.subcategory.query;

import cultureapp.domain.subcategory.Subcategory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Positive;

public interface GetSubcategoryByIdQuery {
    GetSubcategoryByIdDTO getSubcategory(@Positive Long id, @Positive Long categoryId);

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    class GetSubcategoryByIdDTO {
        Long id;
        String name;

        public static GetSubcategoryByIdDTO of(Subcategory subcategory) {
            return new GetSubcategoryByIdDTO(
                    subcategory.getId(),
                    subcategory.getName());
        }
    }
}
