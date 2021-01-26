package cultureapp.domain.subcategory.query;

import cultureapp.domain.core.validation.SelfValidating;
import cultureapp.domain.subcategory.Subcategory;
import cultureapp.domain.user.exception.RegularUserNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;
import org.springframework.data.domain.Slice;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public interface GetSubscriptionsSubcategoriesForUserQueryHandler {

    Slice<GetSubscriptionsSubcategoriesForUserDTO> getSubscribedSubcategoriesForUser(GetSubscriptionsSubcategoriesForUserQuery query) throws RegularUserNotFoundException;

    @Value
    @EqualsAndHashCode(callSuper = false)
    class GetSubscriptionsSubcategoriesForUserQuery extends SelfValidating<GetSubscriptionsSubcategoriesForUserQuery> {
        @PositiveOrZero
        @NotNull
        Integer page;

        @Positive
        @NotNull
        Integer limit;

        public GetSubscriptionsSubcategoriesForUserQuery(
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
    class GetSubscriptionsSubcategoriesForUserDTO {
        Long id;
        Long categoryId;
        String name;

        public static GetSubscriptionsSubcategoriesForUserDTO of (
                Subcategory subcategory
        ) {
            return new GetSubscriptionsSubcategoriesForUserDTO(
                    subcategory.getId(),
                    subcategory.getCategory().getId(),
                    subcategory.getName()
            );
        }
    }
}
