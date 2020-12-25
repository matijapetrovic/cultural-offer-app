package cultureapp.domain.cultural_offer.service;

import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.command.DeleteCulturalOfferUseCase;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Positive;

@RequiredArgsConstructor
@Service
public class DeleteCulturalOfferService implements DeleteCulturalOfferUseCase {
    private final CulturalOfferRepository repository;

    @Override
    public void deleteCulturalOffer(@Positive Long id) throws CulturalOfferNotFoundException {
        // TODO: ERROR -> should be AndArchivedFalse and ALL delete methods should be idempotent
        CulturalOffer offer = repository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new CulturalOfferNotFoundException(id));

        offer.archive();

        repository.save(offer);
    }
}
