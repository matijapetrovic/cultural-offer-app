package cultureapp.domain.cultural_offer.service;

import cultureapp.domain.core.AuthenticationService;
import cultureapp.domain.cultural_offer.query.GetSubscriptionsForUserQueryHandler;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import cultureapp.domain.user.exception.RegularUserNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static cultureapp.common.AuthenticationTestData.*;
import static cultureapp.common.AuthenticationTestData.EXISTING_ADMINISTRATOR_PASSWORD;
import static cultureapp.common.CategoryTestData.*;
import static cultureapp.common.CulturalOfferTestData.*;
import static cultureapp.common.SubcategoryTestData.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class GetSubscriptionsForUserServiceIntegrationTest {

    @Autowired
    private GetSubscriptionsForUserService getSubscriptionsService;

    @Autowired
    private AuthenticationService authenticationService;

    @Test(expected = RegularUserNotFoundException.class)
    public void givenUserDoesNotExistThenGetSubscriptionsFails() throws
            RegularUserNotFoundException,
            SubcategoryNotFoundException {
        authenticationService.authenticate(EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);

        GetSubscriptionsForUserQueryHandler.GetSubscriptionsForUserQuery query =
                new GetSubscriptionsForUserQueryHandler.GetSubscriptionsForUserQuery(
                        EXISTING_CATEGORY_ID,
                        EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1,
                        0L,
                        100L
                );

        getSubscriptionsService.handleGetSubscriptions(query);
    }

    @Test(expected = SubcategoryNotFoundException.class)
    public void givenSubcategoryDoesntExistThenGetSubscriptionsFails() throws
            RegularUserNotFoundException,
            SubcategoryNotFoundException {
        authenticationService.authenticate(EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);

        GetSubscriptionsForUserQueryHandler.GetSubscriptionsForUserQuery query =
                new GetSubscriptionsForUserQueryHandler.GetSubscriptionsForUserQuery(
                        EXISTING_CATEGORY_ID,
                        NON_EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1,
                        0L,
                        100L
                );

        getSubscriptionsService.handleGetSubscriptions(query);
    }

    @Test
    public void givenSubcategoryExistsAndAuthenticatedUserThenGetSubscriptionsSucceeds() throws RegularUserNotFoundException, SubcategoryNotFoundException {
        authenticationService.authenticate(EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);

        GetSubscriptionsForUserQueryHandler.GetSubscriptionsForUserQuery query =
                new GetSubscriptionsForUserQueryHandler.GetSubscriptionsForUserQuery(
                        EXISTING_CATEGORY_ID,
                        EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1,
                        0L,
                        100L
                );

        Slice<GetSubscriptionsForUserQueryHandler.GetSubscriptionsForUserDTO> resultSlice =
                getSubscriptionsService.handleGetSubscriptions(query);

        List<GetSubscriptionsForUserQueryHandler.GetSubscriptionsForUserDTO> result = resultSlice.toList();

        assertEquals(NUMBER_OF_SUBSCRIPTIONS_FOR_USER_1_AND_SUBCATEGORY_1_1, result.size());
        assertEquals(EXISTING_CULTURAL_OFFER_ID, result.get(0).getId());
    }
}
