package cultureapp.domain.category.query;


import cultureapp.domain.category.Category;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface GetCategoryByIdQuery {
    GetCategoryByIdDTO getCategory(Long id);

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
