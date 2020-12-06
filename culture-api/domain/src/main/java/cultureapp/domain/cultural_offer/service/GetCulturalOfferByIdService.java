package cultureapp.domain.cultural_offer.service;

import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.cultural_offer.query.GetCulturalOfferByIdQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetCulturalOfferByIdService implements GetCulturalOfferByIdQuery {
    private final CulturalOfferRepository repository;

    @Override
    public GetCulturalOfferByIdDTO getCulturalOffer(Long id) throws CulturalOfferNotFoundException {
        CulturalOffer offer = repository.findByIdAndArchivedFalse(id).orElseThrow(() -> new CulturalOfferNotFoundException(id));

        return GetCulturalOfferByIdDTO.of(offer);
    }
}
