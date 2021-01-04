package cultureapp.domain.cultural_offer.service;

import cultureapp.domain.account.Account;
import cultureapp.domain.core.AuthenticationService;
import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.command.SubscribeToCulturalOfferNewsUseCase;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.cultural_offer.exception.SubscriptionAlreadyExistsException;
import cultureapp.domain.user.RegularUser;
import cultureapp.domain.user.RegularUserRepository;
import cultureapp.domain.user.exception.RegularUserNotFoundException;
import org.junit.Test;
import org.mockito.Mockito;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static cultureapp.common.CulturalOfferTestData.*;
import static cultureapp.domain.DomainUnitTestsUtil.*;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

public class SubscribeToCulturalOfferNewsServiceUnitTest {

    private final AuthenticationService authenticationService =
            Mockito.mock(AuthenticationService.class);
    private final RegularUserRepository regularUserRepository =
            Mockito.mock(RegularUserRepository.class);
    private final CulturalOfferRepository culturalOfferRepository =
            Mockito.mock(CulturalOfferRepository.class);

    private final SubscribeToCulturalOfferNewsService subscribeService =
            new SubscribeToCulturalOfferNewsService(
                    authenticationService,
                    regularUserRepository,
                    culturalOfferRepository);

    @Test(expected = ConstraintViolationException.class)
    public void givenCulturalOfferIsInvalidThenCreateSubscribeCommandWillFail() {
        invalidSubscribeCommand();
    }

    @Test(expected = RegularUserNotFoundException.class)
    public void givenUserDoesNotExistThenSubscribeFails() throws CulturalOfferNotFoundException, RegularUserNotFoundException, SubscriptionAlreadyExistsException {
        Account account = validRegularUserAccount();
        given(authenticationService.getAuthenticated()).willReturn(account);

        given(regularUserRepository.findByAccountId(account.getId())).willReturn(Optional.empty());

        SubscribeToCulturalOfferNewsUseCase.SubscribeToCulturalOfferNewsCommand command = validSubscribeCommand();
        subscribeService.subscribe(command);
    }

    @Test(expected = CulturalOfferNotFoundException.class)
    public void givenCulturalOfferDoesNotExistThenSubscribeFails() throws CulturalOfferNotFoundException, RegularUserNotFoundException, SubscriptionAlreadyExistsException {
        Account account = validRegularUserAccount();
        given(authenticationService.getAuthenticated()).willReturn(account);

        RegularUser user = validRegularUserWithAccount(account);
        given(regularUserRepository.findByAccountIdWithSubscriptions(account.getId())).willReturn(Optional.of(user));

        given(culturalOfferRepository.findByIdAndArchivedFalse(VALID_CULTURAL_OFFER_ID)).willReturn(Optional.empty());

        SubscribeToCulturalOfferNewsUseCase.SubscribeToCulturalOfferNewsCommand command = validSubscribeCommand();
        subscribeService.subscribe(command);
    }

    @Test(expected = SubscriptionAlreadyExistsException.class)
    public void givenSubscriptionAlreadyExistsThenSubscribeFails() throws CulturalOfferNotFoundException, RegularUserNotFoundException, SubscriptionAlreadyExistsException {
        Account account = validRegularUserAccount();
        given(authenticationService.getAuthenticated()).willReturn(account);

        RegularUser user = validRegularUserWithAccount(account);
        given(regularUserRepository.findByAccountIdWithSubscriptions(account.getId())).willReturn(Optional.of(user));

        CulturalOffer culturalOffer = validCulturalOfferWithSubcategory(validSubcategoryWithCategory(validCategory()));
        user.subscribe(culturalOffer);
        given(culturalOfferRepository.findByIdAndArchivedFalse(culturalOffer.getId())).willReturn(Optional.of(culturalOffer));

        SubscribeToCulturalOfferNewsUseCase.SubscribeToCulturalOfferNewsCommand command = validSubscribeCommand();
        subscribeService.subscribe(command);
    }

    @Test
    public void givenValidCulturalOfferIdAndAuthenticatedUserThenSubscribeSucceeds() throws CulturalOfferNotFoundException, RegularUserNotFoundException, SubscriptionAlreadyExistsException {
        Account account = validRegularUserAccount();
        given(authenticationService.getAuthenticated()).willReturn(account);

        RegularUser user = validRegularUserWithAccount(account);
        given(regularUserRepository.findByAccountIdWithSubscriptions(account.getId())).willReturn(Optional.of(user));

        CulturalOffer culturalOffer = validCulturalOfferWithSubcategory(validSubcategoryWithCategory(validCategory()));
        given(culturalOfferRepository.findByIdAndArchivedFalse(culturalOffer.getId())).willReturn(Optional.of(culturalOffer));

        SubscribeToCulturalOfferNewsUseCase.SubscribeToCulturalOfferNewsCommand command = validSubscribeCommand();
        subscribeService.subscribe(command);

        then(regularUserRepository)
                .should()
                .save(user);
    }
}
