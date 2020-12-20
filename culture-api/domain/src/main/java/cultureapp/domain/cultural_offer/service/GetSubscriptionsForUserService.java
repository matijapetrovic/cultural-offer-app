package cultureapp.domain.cultural_offer.service;

import cultureapp.domain.account.Account;
import cultureapp.domain.core.AuthenticationService;
import cultureapp.domain.cultural_offer.query.GetSubscriptionsForUserQuery;
import cultureapp.domain.user.RegularUser;
import cultureapp.domain.user.RegularUserRepository;
import cultureapp.domain.user.exception.RegularUserNotFoundException;
import cultureapp.domain.subcategory.Subcategory;
import cultureapp.domain.subcategory.SubcategoryId;
import cultureapp.domain.subcategory.SubcategoryRepository;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            @Positive Long subcategoryId) throws RegularUserNotFoundException, SubcategoryNotFoundException {
        Account authenticated = authenticationService.getAuthenticated();
        RegularUser user = regularUserRepository.findByAccountId(authenticated.getId())
                .orElseThrow(() -> new RegularUserNotFoundException("Zasto email?"));

        Subcategory subcategory = subcategoryRepository.findById(SubcategoryId.of(categoryId, subcategoryId))
                .orElseThrow(() -> new SubcategoryNotFoundException(categoryId, subcategoryId));

        return user.getCulturalOffers()
                .stream()
                .filter(culturalOffer -> culturalOffer.getSubcategory().equals(subcategory))
                .map(GetSubscriptionsForUserDTO::of)
                .collect(Collectors.toList());
    }
}
