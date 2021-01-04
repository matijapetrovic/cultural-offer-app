package cultureapp.domain.cultural_offer.service;

import cultureapp.domain.account.Account;
import cultureapp.domain.core.AuthenticationService;
import cultureapp.domain.cultural_offer.query.GetSubscriptionsForUserQueryHandler;
import cultureapp.domain.user.RegularUser;
import cultureapp.domain.user.RegularUserRepository;
import cultureapp.domain.user.exception.RegularUserNotFoundException;
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
public class GetSubscriptionsForUserService implements GetSubscriptionsForUserQueryHandler {
    private final AuthenticationService authenticationService;
    private final RegularUserRepository regularUserRepository;
    private final SubcategoryRepository subcategoryRepository;

    @Override
    public List<GetSubscriptionsForUserDTO> handleGetSubscriptions(GetSubscriptionsForUserQuery query) throws
            RegularUserNotFoundException,
            SubcategoryNotFoundException {
        Account authenticated = authenticationService.getAuthenticated();
        RegularUser user = regularUserRepository.findByAccountIdWithSubscriptions(authenticated.getId())
                .orElseThrow(() -> new RegularUserNotFoundException("Zasto email?"));

        Subcategory subcategory = subcategoryRepository.findById(SubcategoryId.of(query.getCategoryId(), query.getSubcategoryId()))
                .orElseThrow(() -> new SubcategoryNotFoundException(query.getCategoryId(), query.getSubcategoryId()));

        return user.getCulturalOffers()
                .stream()
                .filter(culturalOffer -> culturalOffer.getSubcategory().equals(subcategory))
                .map(GetSubscriptionsForUserDTO::of)
                .collect(Collectors.toList());
    }
}
