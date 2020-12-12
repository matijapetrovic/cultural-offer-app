package cultureapp.domain.cultural_offer.service;

import cultureapp.domain.account.Account;
import cultureapp.domain.authentication.AuthenticationService;
import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.command.UnsubscribeFromCulturalOfferNewsUseCase;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.cultural_offer.exception.SubscriptionNotFound;
import cultureapp.domain.regular_user.RegularUser;
import cultureapp.domain.regular_user.RegularUserRepository;
import cultureapp.domain.regular_user.exception.RegularUserNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UnsubscribeFromCulturalOfferNewsService implements UnsubscribeFromCulturalOfferNewsUseCase {
    private final AuthenticationService authenticationService;
    private final RegularUserRepository regularUserRepository;
    private final CulturalOfferRepository culturalOfferRepository;

    @Override
    public void unsubscribe(UnsubscribeFromCulturalOfferNewsCommand command) throws RegularUserNotFound, CulturalOfferNotFoundException, SubscriptionNotFound {
        Account authenticated = authenticationService.getAuthenticated();
        RegularUser user = regularUserRepository.findByAccountId(authenticated.getId())
                .orElseThrow(() -> new RegularUserNotFound("Zasto email?"));

        CulturalOffer offer = culturalOfferRepository.findByIdAndArchivedFalse(command.getCulturalOfferId())
                .orElseThrow(() -> new CulturalOfferNotFoundException(command.getCulturalOfferId()));

        if (!user.unsubscribe(offer))
            throw new SubscriptionNotFound(user.getId(), offer.getId());
        regularUserRepository.save(user);
    }
}
