package cultureapp.domain.cultural_offer.service;

import cultureapp.domain.account.Account;
import cultureapp.domain.core.AuthenticationService;
import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.command.SubscribeToCulturalOfferNewsUseCase;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.cultural_offer.exception.SubscriptionAlreadyExistsException;
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


import java.util.Optional;
import java.util.Set;

import static cultureapp.common.AuthenticationTestData.*;
import static cultureapp.common.CulturalOfferTestData.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class SubscribeToCulturalOfferNewsServiceIntegrationTest {

    @Autowired
    private SubscribeToCulturalOfferNewsService subscribeService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private RegularUserRepository userRepository;

    @Test(expected = RegularUserNotFoundException.class)
    public void givenUserDoesNotExistThenSubscribeFails() throws CulturalOfferNotFoundException, RegularUserNotFoundException, SubscriptionAlreadyExistsException {
        authenticationService.authenticate(EXISTING_ADMINISTRATOR_EMAIL, EXISTING_ADMINISTRATOR_PASSWORD);

        SubscribeToCulturalOfferNewsUseCase.SubscribeToCulturalOfferNewsCommand command =
                new SubscribeToCulturalOfferNewsUseCase.SubscribeToCulturalOfferNewsCommand(NON_EXISTING_SUBSCRIPTION_ID_FOR_USER_1);
        subscribeService.subscribe(command);
    }

    @Test(expected = CulturalOfferNotFoundException.class)
    public void givenCulturalOfferDoesNotExistThenSubscribeFails() throws CulturalOfferNotFoundException, RegularUserNotFoundException, SubscriptionAlreadyExistsException {
        authenticationService.authenticate(EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);

        SubscribeToCulturalOfferNewsUseCase.SubscribeToCulturalOfferNewsCommand command =
                new SubscribeToCulturalOfferNewsUseCase.SubscribeToCulturalOfferNewsCommand(NON_EXISTING_CULTURAL_OFFER_ID);

        subscribeService.subscribe(command);
    }

    @Test(expected = SubscriptionAlreadyExistsException.class)
    public void givenSubscriptionAlreadyExistsThenSubscribeFails() throws CulturalOfferNotFoundException, RegularUserNotFoundException, SubscriptionAlreadyExistsException {
        authenticationService.authenticate(EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);

        SubscribeToCulturalOfferNewsUseCase.SubscribeToCulturalOfferNewsCommand command =
                new SubscribeToCulturalOfferNewsUseCase.SubscribeToCulturalOfferNewsCommand(EXISTING_SUBSCRIPTION_ID_FOR_USER_1);
        subscribeService.subscribe(command);

        Account account = authenticationService.getAuthenticated();
        assertNotNull(account);
        RegularUser user = userRepository.findByAccountIdWithSubscriptions(account.getId()).orElse(null);
        assertNotNull(user);

        Set<CulturalOffer> culturalOffers = user.getCulturalOffers();
        assertEquals(NUMBER_OF_SUBSCRIPTIONS_FOR_USER_1, culturalOffers.size());

    }

    @Test
    public void givenValidCulturalOfferIdAndAuthenticatedUserThenSubscribeSucceeds() throws CulturalOfferNotFoundException, RegularUserNotFoundException, SubscriptionAlreadyExistsException {
        authenticationService.authenticate(EXISTING_REGULAR_USER_EMAIL, EXISTING_REGULAR_USER_PASSWORD);

        SubscribeToCulturalOfferNewsUseCase.SubscribeToCulturalOfferNewsCommand command =
                new SubscribeToCulturalOfferNewsUseCase.SubscribeToCulturalOfferNewsCommand(NON_EXISTING_SUBSCRIPTION_ID_FOR_USER_1);
        subscribeService.subscribe(command);

        Account account = authenticationService.getAuthenticated();
        assertNotNull(account);
        RegularUser user = userRepository.findByAccountIdWithSubscriptions(account.getId()).orElse(null);
        assertNotNull(user);

        Set<CulturalOffer> culturalOffers = user.getCulturalOffers();
        assertEquals(NUMBER_OF_SUBSCRIPTIONS_FOR_USER_1 + 1, culturalOffers.size());

        Optional<CulturalOffer> offer = culturalOffers
                .stream()
                .filter(culturalOffer -> culturalOffer.getId().equals(NON_EXISTING_SUBSCRIPTION_ID_FOR_USER_1))
                .findAny();
        assertFalse(offer.isEmpty());
        user.unsubscribe(offer.get());
        userRepository.save(user);
    }
}
