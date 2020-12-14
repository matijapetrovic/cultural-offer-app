package cultureapp.domain.regular_user.query;

import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.cultural_offer.query.GetSubscriptionsForUserQuery;
import cultureapp.domain.image.Image;
import cultureapp.domain.regular_user.RegularUser;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public interface GetUsersSubscribedToOfferQuery {
    List<GetUsersSubscribedToOfferDTO> getSubscribedUsers(Long culturalOfferId) throws CulturalOfferNotFoundException;

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    class GetUsersSubscribedToOfferDTO {
        Long id;
        String email;
        String firstName;
        String lastName;

        public static GetUsersSubscribedToOfferDTO of(RegularUser user) {
            return new GetUsersSubscribedToOfferDTO(
                    user.getId(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName());
        }
    }
}
