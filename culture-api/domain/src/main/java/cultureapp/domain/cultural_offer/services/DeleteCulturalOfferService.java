package cultureapp.domain.cultural_offer.services;

import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.command.DeleteCulturalOfferUseCase;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Positive;

@RequiredArgsConstructor
@Service
public class DeleteCulturalOfferService implements DeleteCulturalOfferUseCase {
    private final CulturalOfferRepository repository;

    @Override
    public void deleteCulturalOffer(@Positive Long id) throws CulturalOfferNotFoundException {
        CulturalOffer offer = repository.findById(id)
                .orElseThrow(() -> new CulturalOfferNotFoundException(id));

        offer.archive();

        repository.save(offer);
    }
}
