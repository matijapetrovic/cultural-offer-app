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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SubscribeToCulturalOfferNewsService implements SubscribeToCulturalOfferNewsUseCase {
    private final AuthenticationService authenticationService;
    private final RegularUserRepository regularUserRepository;
    private final CulturalOfferRepository culturalOfferRepository;

    @Override
    public void subscribe(SubscribeToCulturalOfferNewsCommand command) throws
            RegularUserNotFoundException,
            CulturalOfferNotFoundException,
            SubscriptionAlreadyExistsException {
        Account authenticated = authenticationService.getAuthenticated();
        RegularUser user = regularUserRepository.findByAccountId(authenticated.getId())
                .orElseThrow(() -> new RegularUserNotFoundException("Zasto email?"));

        CulturalOffer offer = culturalOfferRepository.findByIdAndArchivedFalse(command.getCulturalOfferId())
                .orElseThrow(() -> new CulturalOfferNotFoundException(command.getCulturalOfferId()));

        if (!user.subscribe(offer))
            throw new SubscriptionAlreadyExistsException(user.getId(), offer.getId());
        regularUserRepository.save(user);
    }
}
