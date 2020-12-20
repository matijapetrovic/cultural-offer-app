package cultureapp.domain.cultural_offer.service;

import cultureapp.domain.account.Account;
import cultureapp.domain.core.AuthenticationService;
import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.cultural_offer.query.GetCulturalOfferByIdQuery;
import cultureapp.domain.user.RegularUser;
import cultureapp.domain.user.RegularUserRepository;
import cultureapp.domain.user.exception.RegularUserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetCulturalOfferByIdService implements GetCulturalOfferByIdQuery {
    private final CulturalOfferRepository repository;
    private final AuthenticationService authenticationService;
    private final RegularUserRepository regularUserRepository;

    @Override
    public GetCulturalOfferByIdDTO getCulturalOffer(Long id) throws
            CulturalOfferNotFoundException,
            RegularUserNotFoundException {
        CulturalOffer offer = repository.findByIdAndArchivedFalse(id).orElseThrow(() -> new CulturalOfferNotFoundException(id));
        Account account = authenticationService.getAuthenticated();

        Boolean subscribed = null;
        if (account != null && account.hasRole("ROLE_USER")) {
            RegularUser user = regularUserRepository.findByAccountId(account.getId())
                    .orElseThrow(() -> new RegularUserNotFoundException("emaiL??"));
            subscribed = user.isSubscribedTo(offer);
        }

        return GetCulturalOfferByIdDTO.of(offer, subscribed);
    }
}
