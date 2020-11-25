package cultureapp.domain.cultural_offer;

import cultureapp.domain.cultural_offer.command.AddCulturalOfferUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
class CulturalOfferService implements
        AddCulturalOfferUseCase {
    private final CulturalOfferRepository repository;

    @Override
    public void addCulturalOffer(AddCulturalOfferCommand command) {
        List<Image> images = null; // saveImages(command.getImages());

        CulturalOffer offer = CulturalOffer.of(
                command.getName(),
                command.getDescription(),
                Location.of(command.getLongitude(), command.getLatitude(), "xd"), // geocode
                images);

        repository.save(offer);
    }
}
