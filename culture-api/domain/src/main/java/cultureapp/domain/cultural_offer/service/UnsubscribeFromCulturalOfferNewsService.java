package cultureapp.domain.cultural_offer.service;

import cultureapp.domain.account.Account;
import cultureapp.domain.core.AuthenticationService;
import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.command.UnsubscribeFromCulturalOfferNewsUseCase;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.cultural_offer.exception.SubscriptionNotFoundException;
import cultureapp.domain.user.RegularUser;
import cultureapp.domain.user.RegularUserRepository;
import cultureapp.domain.user.exception.RegularUserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UnsubscribeFromCulturalOfferNewsService implements UnsubscribeFromCulturalOfferNewsUseCase {
    private final AuthenticationService authenticationService;
    private final RegularUserRepository regularUserRepository;
    private final CulturalOfferRepository culturalOfferRepository;

    @Override
    public void unsubscribe(UnsubscribeFromCulturalOfferNewsCommand command) throws RegularUserNotFoundException, CulturalOfferNotFoundException, SubscriptionNotFoundException {
        Account authenticated = authenticationService.getAuthenticated();
        RegularUser user = regularUserRepository.findByAccountId(authenticated.getId())
                .orElseThrow(() -> new RegularUserNotFoundException("Zasto email?"));

        CulturalOffer offer = culturalOfferRepository.findByIdAndArchivedFalse(command.getCulturalOfferId())
                .orElseThrow(() -> new CulturalOfferNotFoundException(command.getCulturalOfferId()));

        if (!user.unsubscribe(offer))
            throw new SubscriptionNotFoundException(user.getId(), offer.getId());
        regularUserRepository.save(user);
    }
}
