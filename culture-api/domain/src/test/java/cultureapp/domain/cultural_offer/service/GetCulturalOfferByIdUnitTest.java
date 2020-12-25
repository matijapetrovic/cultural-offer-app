package cultureapp.domain.cultural_offer.service;

import cultureapp.domain.account.Account;
import cultureapp.domain.authority.Authority;
import cultureapp.domain.category.Category;
import cultureapp.domain.core.AuthenticationService;
import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.Location;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.cultural_offer.query.GetCulturalOfferByIdQueryHandler;
import cultureapp.domain.image.Image;
import cultureapp.domain.subcategory.Subcategory;
import cultureapp.domain.user.RegularUser;
import cultureapp.domain.user.RegularUserRepository;
import cultureapp.domain.user.exception.RegularUserNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.validation.ConstraintViolationException;

import java.util.*;

import static cultureapp.common.AuthenticationTestData.*;
import static cultureapp.common.CategoryTestData.*;
import static cultureapp.common.CulturalOfferTestData.*;
import static cultureapp.common.RegularUserTestData.*;
import static cultureapp.common.SubcategoryTestData.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;

public class GetCulturalOfferByIdUnitTest {

    private final CulturalOfferRepository culturalOfferRepository =
            Mockito.mock(CulturalOfferRepository.class);

    private final AuthenticationService authenticationService =
            Mockito.mock(AuthenticationService.class);

    private final RegularUserRepository regularUserRepository =
            Mockito.mock(RegularUserRepository.class);

    private final GetCulturalOfferByIdService getCulturalOfferByIdService =
            new GetCulturalOfferByIdService(
                    culturalOfferRepository,
                    authenticationService,
                    regularUserRepository
            );

    private Account account;
    private RegularUser regularUser;

    @Before
    public void setUp() {
        account = Account.withId(
                EXISTING_REGULAR_USER_ACCOUNT_ID,
                EXISTING_REGULAR_USER_EMAIL,
                EXISTING_REGULAR_USER_PASSWORD,
                true,
                Set.of(Authority.of("ROLE_USER"))
        );
        regularUser = RegularUser.of(
                VALID_REGULAR_USER_FIRST_NAME,
                VALID_REGULAR_USER_LAST_NAME,
                account
        );
    }

    @Test(expected = ConstraintViolationException.class)
    public void givenCulturalOfferIdIsInvalidThenCreateGetCulturalOfferByIdQueryWillFail() {
        new GetCulturalOfferByIdQueryHandler.GetCulturalOfferByIdQuery(
                INVALID_CULTURAL_OFFER_ID
        );
    }

    @Test(expected = CulturalOfferNotFoundException.class)
    public void givenCulturalOfferDoesntExistThenGetCulturalOfferByIdQueryHandlerWillFail() throws CulturalOfferNotFoundException, RegularUserNotFoundException {

        given(culturalOfferRepository
                .findByIdAndArchivedFalse(VALID_CULTURAL_OFFER_ID))
                .willReturn(Optional.empty());

        given(authenticationService
                .getAuthenticated())
                .willReturn(account);

        given(regularUserRepository
                .findByAccountId(EXISTING_REGULAR_USER_ACCOUNT_ID))
                .willReturn(Optional.of(regularUser));

        GetCulturalOfferByIdQueryHandler.GetCulturalOfferByIdQuery query =
                new GetCulturalOfferByIdQueryHandler.GetCulturalOfferByIdQuery(
                        VALID_CULTURAL_OFFER_ID
                );

        GetCulturalOfferByIdQueryHandler.GetCulturalOfferByIdDTO result =
                getCulturalOfferByIdService.handleGetCulturalOffer(query);
    }

    @Test
    public void givenCulturalOfferExistThenGetCulturalOfferByIdQueryHandlerWillSucceed() throws CulturalOfferNotFoundException, RegularUserNotFoundException {

        given(culturalOfferRepository
                .findByIdAndArchivedFalse(VALID_CULTURAL_OFFER_ID))
                .willReturn(Optional.of(CulturalOffer.withId(
                        VALID_CULTURAL_OFFER_ID,
                        VALID_CULTURAL_OFFER_NAME,
                        VALID_CULTURAL_OFFER_DESCRIPTION,
                        Location.of(VALID_LOCATION_LONGITUDE,
                                VALID_LOCATION_LATITUDE,
                                VALID_LOCATION_ADDRESS),
                        List.of(Image.of("path1"), Image.of("path2")),
                        new HashSet<RegularUser>(),
                        Subcategory.withId(
                                VALID_SUBCATEGORY_ID,
                                Category.withId(
                                        VALID_CATEGORY_ID,
                                        VALID_CATEGORY_NAME
                                ),
                                VALID_SUBCATEGORY_NAME)
                )));

        given(authenticationService
                .getAuthenticated())
                .willReturn(account);

        given(regularUserRepository
                .findByAccountId(EXISTING_REGULAR_USER_ACCOUNT_ID))
                .willReturn(Optional.of(regularUser));

        GetCulturalOfferByIdQueryHandler.GetCulturalOfferByIdQuery query =
                new GetCulturalOfferByIdQueryHandler.GetCulturalOfferByIdQuery(
                        VALID_CULTURAL_OFFER_ID
                );

        GetCulturalOfferByIdQueryHandler.GetCulturalOfferByIdDTO result =
                getCulturalOfferByIdService.handleGetCulturalOffer(query);

        assertNotNull(result);
        assertEquals(VALID_CULTURAL_OFFER_ID, result.getId());
        assertEquals(VALID_CULTURAL_OFFER_NAME, result.getName());
        assertEquals(VALID_CULTURAL_OFFER_DESCRIPTION, result.getDescription());
    }



}
