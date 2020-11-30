package cultureapp.domain.cultural_offer.add;

import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.Image;
import cultureapp.domain.cultural_offer.Location;
import cultureapp.domain.cultural_offer.command.AddCulturalOfferUseCase;
import cultureapp.domain.subcategory.Subcategory;
import cultureapp.domain.subcategory.SubcategoryRepository;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
class AddCulturalOfferService implements
        AddCulturalOfferUseCase {
    private final CulturalOfferRepository repository;

    private final SubcategoryRepository subcategoryRepository;

    @Override
    public void addCulturalOffer(AddCulturalOfferCommand command) throws SubcategoryNotFoundException {
        List<Image> images = null; // saveImages(command.getImages());

        Subcategory subcategory = subcategoryRepository.findByIdAndCategoryIdAndArchivedFalse(command.getSubcategoryId(),
                command.getCategoryId()).orElseThrow(() -> new SubcategoryNotFoundException(command.getSubcategoryId(), command.getCategoryId()));

        CulturalOffer offer = CulturalOffer.of(
                command.getName(),
                command.getDescription(),
                Location.of(command.getLongitude(), command.getLatitude(), "xd"), // geocode
                images,
                subcategory);

        repository.save(offer);
    }
}
