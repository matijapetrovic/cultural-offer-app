package cultureapp.domain.cultural_offer.service;

import cultureapp.domain.account.Account;
import cultureapp.domain.authentication.AuthenticationService;
import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.command.SubscribeToCulturalOfferNewsUseCase;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.cultural_offer.exception.SubscriptionAlreadyExists;
import cultureapp.domain.regular_user.RegularUser;
import cultureapp.domain.regular_user.RegularUserRepository;
import cultureapp.domain.regular_user.exception.RegularUserNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SubscribeToCulturalOfferNewsService implements SubscribeToCulturalOfferNewsUseCase {
    private final AuthenticationService authenticationService;
    private final RegularUserRepository regularUserRepository;
    private final CulturalOfferRepository culturalOfferRepository;

    @Override
    public void subscribe(SubscribeToCulturalOfferNewsCommand command) throws RegularUserNotFound,
            CulturalOfferNotFoundException, SubscriptionAlreadyExists {
        Account authenticated = authenticationService.getAuthenticated();
        RegularUser user = regularUserRepository.findByAccountId(authenticated.getId())
                .orElseThrow(() -> new RegularUserNotFound("Zasto email?"));

        CulturalOffer offer = culturalOfferRepository.findByIdAndArchivedFalse(command.getCulturalOfferId())
                .orElseThrow(() -> new CulturalOfferNotFoundException(command.getCulturalOfferId()));

        if (!user.subscribe(offer))
            throw new SubscriptionAlreadyExists(user.getId(), offer.getId());
        regularUserRepository.save(user);
    }
}
