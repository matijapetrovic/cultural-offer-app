package cultureapp.domain.cultural_offer.service;

import cultureapp.domain.account.Account;
import cultureapp.domain.core.AuthenticationService;
import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.command.UnsubscribeFromCulturalOfferNewsUseCase;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.cultural_offer.exception.SubscriptionAlreadyExistsException;
import cultureapp.domain.cultural_offer.exception.SubscriptionNotFoundException;
import cultureapp.domain.user.RegularUser;
import cultureapp.domain.user.RegularUserRepository;
import cultureapp.domain.user.exception.RegularUserNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static cultureapp.common.AuthenticationTestData.*;
import static cultureapp.common.CulturalOfferTestData.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class UnsubscribeFromCulturalOfferNewsServiceIntegrationTest {

    @Autowired
    private UnsubscribeFromCulturalOfferNewsService unsubscribeService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private RegularUserRepository userRepository;

    @Autowired
    private CulturalOfferRepository culturalOfferRepository;

    @Test(expected = RegularUserNotFoundException.class)
    public void givenUserDoesNotExistThenUnsubscribeFails() throws
            CulturalOfferNotFoundException,
            RegularUserNotFoundException,
            SubscriptionNotFoundException {
        authenticationService.authenticate(EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);

        UnsubscribeFromCulturalOfferNewsUseCase.UnsubscribeFromCulturalOfferNewsCommand command =
                new UnsubscribeFromCulturalOfferNewsUseCase.UnsubscribeFromCulturalOfferNewsCommand(EXISTING_SUBSCRIPTION_ID_FOR_USER_2);

        unsubscribeService.unsubscribe(command);
    }

    @Test(expected = CulturalOfferNotFoundException.class)
    public void givenCulturalOfferDoesNotExistThenUnsubscribeFails() throws
            CulturalOfferNotFoundException,
            RegularUserNotFoundException,
            SubscriptionNotFoundException {
        authenticationService.authenticate(EXISTING_REGULAR_USER_EMAIL2, EXISTING_REGULAR_USER_PASSWORD2);

        UnsubscribeFromCulturalOfferNewsUseCase.UnsubscribeFromCulturalOfferNewsCommand command =
                new UnsubscribeFromCulturalOfferNewsUseCase.UnsubscribeFromCulturalOfferNewsCommand(NON_EXISTING_CULTURAL_OFFER_ID);

        unsubscribeService.unsubscribe(command);
    }

    @Test(expected = SubscriptionNotFoundException.class)
    public void givenSubscriptionDoesntExistThenUnsubscribeFails() throws
            CulturalOfferNotFoundException,
            SubscriptionNotFoundException,
            RegularUserNotFoundException {
        authenticationService.authenticate(EXISTING_REGULAR_USER_EMAIL2, EXISTING_REGULAR_USER_PASSWORD2);

        UnsubscribeFromCulturalOfferNewsUseCase.UnsubscribeFromCulturalOfferNewsCommand command =
                new UnsubscribeFromCulturalOfferNewsUseCase.UnsubscribeFromCulturalOfferNewsCommand(NON_EXISTING_SUBSCRIPTION_ID_FOR_USER_2);
        unsubscribeService.unsubscribe(command);
    }

    @Test
    public void givenValidCulturalOfferIdAndAuthenticatedUserThenUnsubscribeSucceeds() throws CulturalOfferNotFoundException, RegularUserNotFoundException, SubscriptionAlreadyExistsException, SubscriptionNotFoundException {
        authenticationService.authenticate(EXISTING_REGULAR_USER_EMAIL2, EXISTING_REGULAR_USER_PASSWORD2);

        UnsubscribeFromCulturalOfferNewsUseCase.UnsubscribeFromCulturalOfferNewsCommand command =
                new UnsubscribeFromCulturalOfferNewsUseCase.UnsubscribeFromCulturalOfferNewsCommand(EXISTING_SUBSCRIPTION_ID_FOR_USER_2);
        unsubscribeService.unsubscribe(command);

        Account account = authenticationService.getAuthenticated();
        assertNotNull(account);
        RegularUser user = userRepository.findByAccountIdWithSubscriptions(account.getId()).orElse(null);
        assertNotNull(user);

        Set<CulturalOffer> culturalOffers = user.getCulturalOffers();
        assertEquals(NUMBER_OF_SUBSCRIPTIONS_FOR_USER_2 - 1, culturalOffers.size());

        boolean deleted = culturalOffers
                .stream()
                .noneMatch(culturalOffer -> culturalOffer.getId().equals(EXISTING_SUBSCRIPTION_ID_FOR_USER_2));
        assertTrue(deleted);

        CulturalOffer offer = culturalOfferRepository.findById(EXISTING_SUBSCRIPTION_ID_FOR_USER_2).orElse(null);
        assertNotNull(offer);
        user.subscribe(offer);
        userRepository.save(user);
    }
}
