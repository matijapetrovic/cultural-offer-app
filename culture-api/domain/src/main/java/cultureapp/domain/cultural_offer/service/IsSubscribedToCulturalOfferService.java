package cultureapp.domain.cultural_offer.service;

import cultureapp.domain.account.Account;
import cultureapp.domain.core.AuthenticationService;
import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.cultural_offer.query.IsSubscribedToCulturalOfferQueryHandler;
import cultureapp.domain.user.RegularUser;
import cultureapp.domain.user.RegularUserRepository;
import cultureapp.domain.user.exception.RegularUserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class IsSubscribedToCulturalOfferService implements IsSubscribedToCulturalOfferQueryHandler {
    private final AuthenticationService authenticationService;
    private final CulturalOfferRepository culturalOfferRepository;
    private final RegularUserRepository regularUserRepository;

    @Override
    public boolean isSubscribed(IsSubscribedToCulturalOfferQuery query) throws CulturalOfferNotFoundException, RegularUserNotFoundException {
        CulturalOffer offer = culturalOfferRepository.findByIdAndArchivedFalse(query.getId())
                .orElseThrow(() -> new CulturalOfferNotFoundException(query.getId()));

        Account account = authenticationService.getAuthenticated();
        RegularUser user = regularUserRepository.findByAccountId(account.getId())
                .orElseThrow(() -> new RegularUserNotFoundException("emaiL??"));
        return user.isSubscribedTo(offer);
    }
}
