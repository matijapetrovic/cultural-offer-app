package cultureapp.domain.cultural_offer.service;

import static cultureapp.common.CategoryTestData.*;
import static cultureapp.common.SubcategoryTestData.*;
import static cultureapp.domain.DomainUnitTestsUtil.*;

import cultureapp.domain.account.Account;
import cultureapp.domain.core.AuthenticationService;
import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.query.GetSubscriptionsForUserQueryHandler;
import cultureapp.domain.cultural_offer.service.GetSubscriptionsForUserService;
import cultureapp.domain.subcategory.Subcategory;
import cultureapp.domain.subcategory.SubcategoryRepository;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import cultureapp.domain.user.RegularUser;
import cultureapp.domain.user.RegularUserRepository;
import cultureapp.domain.user.exception.RegularUserNotFoundException;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.BDDMockito.given;
import static org.junit.Assert.*;

public class GetSubscriptionsForUserServiceUnitTest {

    private final AuthenticationService authenticationService =
            Mockito.mock(AuthenticationService.class);
    private final RegularUserRepository regularUserRepository =
            Mockito.mock(RegularUserRepository.class);
    private final SubcategoryRepository subcategoryRepository =
            Mockito.mock(SubcategoryRepository.class);

    private final GetSubscriptionsForUserService getSubscriptionsService =
            new GetSubscriptionsForUserService(
                    authenticationService,
                    regularUserRepository,
                    subcategoryRepository);

    @Test(expected = RegularUserNotFoundException.class)
    public void givenUserDoesNotExistThenGetSubscriptionsFails() throws
            RegularUserNotFoundException,
            SubcategoryNotFoundException {
        Account account = validRegularUserAccount();
        given(authenticationService.getAuthenticated()).willReturn(account);

        given(regularUserRepository.findByAccountId(account.getId())).willReturn(Optional.empty());

        GetSubscriptionsForUserQueryHandler.GetSubscriptionsForUserQuery query =
                new GetSubscriptionsForUserQueryHandler.GetSubscriptionsForUserQuery(
                        VALID_CATEGORY_ID,
                        VALID_SUBCATEGORY_ID,
                        0L,
                        100L
                );

        getSubscriptionsService.handleGetSubscriptions(query);
    }

    @Test(expected = SubcategoryNotFoundException.class)
    public void givenSubcategoryDoesntExistThenGetSubscriptionsFails() throws
            RegularUserNotFoundException,
            SubcategoryNotFoundException {
        Account account = validRegularUserAccount();
        given(authenticationService.getAuthenticated()).willReturn(account);

        RegularUser user = validRegularUserWithAccount(account);
        given(regularUserRepository.findByAccountIdWithSubscriptions(account.getId())).willReturn(Optional.of(user));

        given(subcategoryRepository.findById(notNull())).willReturn(Optional.empty());

        GetSubscriptionsForUserQueryHandler.GetSubscriptionsForUserQuery query =
                new GetSubscriptionsForUserQueryHandler.GetSubscriptionsForUserQuery(
                        VALID_CATEGORY_ID,
                        VALID_SUBCATEGORY_ID,
                        0L,
                        100L
                );

        getSubscriptionsService.handleGetSubscriptions(query);
    }

    @Test
    public void givenSubcategoryExistsAndAuthenticatedUserThenGetSubscriptionsSucceeds() throws RegularUserNotFoundException, SubcategoryNotFoundException {
        Account account = validRegularUserAccount();
        given(authenticationService.getAuthenticated()).willReturn(account);

        Subcategory subcategory = validSubcategoryWithCategory(validCategory());
        given(subcategoryRepository.findById(notNull())).willReturn(Optional.of(subcategory));

        CulturalOffer culturalOffer = validCulturalOfferWithSubcategory(subcategory);

        RegularUser user = validRegularUserWithAccount(account);
        user.subscribe(culturalOffer);
        given(regularUserRepository.findByAccountIdWithSubscriptions(account.getId())).willReturn(Optional.of(user));

        GetSubscriptionsForUserQueryHandler.GetSubscriptionsForUserQuery query =
                new GetSubscriptionsForUserQueryHandler.GetSubscriptionsForUserQuery(
                        subcategory.getCategory().getId(),
                        subcategory.getId(),
                        0L,
                        100L
                );

        Slice<GetSubscriptionsForUserQueryHandler.GetSubscriptionsForUserDTO> resultSlice =
                getSubscriptionsService.handleGetSubscriptions(query);

        List<GetSubscriptionsForUserQueryHandler.GetSubscriptionsForUserDTO> result = resultSlice.toList();

        assertEquals(user.getCulturalOffers().size(), result.size());
        assertEquals(culturalOffer.getId(), result.get(0).getId());
        assertEquals(culturalOffer.getName(), result.get(0).getName());
        assertEquals(culturalOffer.getDescription(), result.get(0).getDescription());
        assertEquals(culturalOffer.getImages().size(), result.get(0).getImages().size());
    }
}
