package cultureapp.domain.category.query;

import cultureapp.domain.category.Category;
import lombok.*;

import java.util.List;

public interface GetCategoryNamesQueryHandler {
    List<GetCategoryNamesDTO> getCategoryNames();

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    class GetCategoryNamesDTO {
        Long id;
        String name;

        public static GetCategoryNamesDTO of(Category category) {
            return new GetCategoryNamesDTO(
                    category.getId(),
                    category.getName());
        }
    }
}
