package cultureapp.domain.subcategory.query;

import cultureapp.domain.subcategory.Subcategory;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Positive;

public interface GetSubcategoryByIdQuery {
    GetSubcategoryByIdDTO getSubcategory(@Positive Long id, @Positive Long categoryId) throws SubcategoryNotFoundException;

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
