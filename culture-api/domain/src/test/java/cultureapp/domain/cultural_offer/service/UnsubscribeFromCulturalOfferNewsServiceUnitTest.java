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
import org.mockito.Mockito;

import javax.validation.ConstraintViolationException;

import java.util.Optional;

import static cultureapp.common.CulturalOfferTestData.*;
import static cultureapp.domain.DomainUnitTestsUtil.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

public class UnsubscribeFromCulturalOfferNewsServiceUnitTest {
    private final AuthenticationService authenticationService =
            Mockito.mock(AuthenticationService.class);
    private final RegularUserRepository regularUserRepository =
            Mockito.mock(RegularUserRepository.class);
    private final CulturalOfferRepository culturalOfferRepository =
            Mockito.mock(CulturalOfferRepository.class);

    private final UnsubscribeFromCulturalOfferNewsService unsubscribeService =
            new UnsubscribeFromCulturalOfferNewsService(
                    authenticationService,
                    regularUserRepository,
                    culturalOfferRepository);

    @Test(expected = ConstraintViolationException.class)
    public void givenCulturalOfferIsInvalidThenCreateUnsubscribeCommandWillFail() {
        invalidUnsubscribeCommand();
    }

    @Test(expected = RegularUserNotFoundException.class)
    public void givenUserDoesNotExistThenUnsubscribeFails() throws
            CulturalOfferNotFoundException,
            RegularUserNotFoundException,
            SubscriptionNotFoundException {
        Account account = validRegularUserAccount();
        given(authenticationService.getAuthenticated()).willReturn(account);

        given(regularUserRepository.findByAccountId(account.getId())).willReturn(Optional.empty());

        UnsubscribeFromCulturalOfferNewsUseCase.UnsubscribeFromCulturalOfferNewsCommand command = validUnsubscribeCommand();
        unsubscribeService.unsubscribe(command);
    }

    @Test(expected = CulturalOfferNotFoundException.class)
    public void givenCulturalOfferDoesNotExistThenUnsubscribeFails() throws
            CulturalOfferNotFoundException,
            RegularUserNotFoundException,
            SubscriptionNotFoundException {
        Account account = validRegularUserAccount();
        given(authenticationService.getAuthenticated()).willReturn(account);

        RegularUser user = validRegularUserWithAccount(account);
        given(regularUserRepository.findByAccountIdWithSubscriptions(account.getId())).willReturn(Optional.of(user));

        given(culturalOfferRepository.findByIdAndArchivedFalse(VALID_CULTURAL_OFFER_ID)).willReturn(Optional.empty());

        UnsubscribeFromCulturalOfferNewsUseCase.UnsubscribeFromCulturalOfferNewsCommand command = validUnsubscribeCommand();
        unsubscribeService.unsubscribe(command);
    }

    @Test(expected = SubscriptionNotFoundException.class)
    public void givenSubscriptionDoesntExistThenUnsubscribeFails() throws
            CulturalOfferNotFoundException,
            SubscriptionNotFoundException,
            RegularUserNotFoundException {
        Account account = validRegularUserAccount();
        given(authenticationService.getAuthenticated()).willReturn(account);

        RegularUser user = validRegularUserWithAccount(account);
        given(regularUserRepository.findByAccountIdWithSubscriptions(account.getId())).willReturn(Optional.of(user));

        CulturalOffer culturalOffer = validCulturalOfferWithSubcategory(validSubcategoryWithCategory(validCategory()));
        given(culturalOfferRepository.findByIdAndArchivedFalse(culturalOffer.getId())).willReturn(Optional.of(culturalOffer));

        UnsubscribeFromCulturalOfferNewsUseCase.UnsubscribeFromCulturalOfferNewsCommand command = validUnsubscribeCommand();
        unsubscribeService.unsubscribe(command);
    }

    @Test
    public void givenValidCulturalOfferIdAndAuthenticatedUserThenUnsubscribeSucceeds() throws CulturalOfferNotFoundException, RegularUserNotFoundException, SubscriptionAlreadyExistsException, SubscriptionNotFoundException {
        Account account = validRegularUserAccount();
        given(authenticationService.getAuthenticated()).willReturn(account);

        RegularUser user = validRegularUserWithAccount(account);
        given(regularUserRepository.findByAccountIdWithSubscriptions(account.getId())).willReturn(Optional.of(user));

        CulturalOffer culturalOffer = validCulturalOfferWithSubcategory(validSubcategoryWithCategory(validCategory()));
        user.subscribe(culturalOffer);
        given(culturalOfferRepository.findByIdAndArchivedFalse(culturalOffer.getId())).willReturn(Optional.of(culturalOffer));

        UnsubscribeFromCulturalOfferNewsUseCase.UnsubscribeFromCulturalOfferNewsCommand command = validUnsubscribeCommand();
        unsubscribeService.unsubscribe(command);

        then(regularUserRepository)
                .should()
                .save(user);
    }

}
