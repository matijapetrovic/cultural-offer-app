package cultureapp.domain.cultural_offer.service;

import cultureapp.domain.account.Account;
import cultureapp.domain.authentication.AuthenticationService;
import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.query.GetSubscriptionsForUserQuery;
import cultureapp.domain.regular_user.RegularUser;
import cultureapp.domain.regular_user.RegularUserRepository;
import cultureapp.domain.regular_user.exception.RegularUserNotFound;
import cultureapp.domain.subcategory.Subcategory;
import cultureapp.domain.subcategory.SubcategoryId;
import cultureapp.domain.subcategory.SubcategoryRepository;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GetSubscriptionsForUserService implements GetSubscriptionsForUserQuery {
    private final AuthenticationService authenticationService;
    private final RegularUserRepository regularUserRepository;
    private final SubcategoryRepository subcategoryRepository;

    @Override
    public List<GetSubscriptionsForUserDTO> getSubscriptions(
            @Positive Long categoryId,
            @Positive Long subcategoryId) throws RegularUserNotFound, SubcategoryNotFoundException {
        Account authenticated = authenticationService.getAuthenticated();
        RegularUser user = regularUserRepository.findByAccountId(authenticated.getId())
                .orElseThrow(() -> new RegularUserNotFound("Zasto email?"));

        Subcategory subcategory = subcategoryRepository.findById(SubcategoryId.of(categoryId, subcategoryId))
                .orElseThrow(() -> new SubcategoryNotFoundException(categoryId, subcategoryId));

        return user.getCulturalOffers()
                .stream()
                .filter(culturalOffer -> culturalOffer.getSubcategory().equals(subcategory))
                .map(GetSubscriptionsForUserDTO::of)
                .collect(Collectors.toList());
    }
}
