package cultureapp.domain.cultural_offer.service;

import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.image.Image;
import cultureapp.domain.cultural_offer.Location;
import cultureapp.domain.cultural_offer.command.AddCulturalOfferUseCase;
import cultureapp.domain.image.ImageRepository;
import cultureapp.domain.image.exception.ImageNotFoundException;
import cultureapp.domain.subcategory.Subcategory;
import cultureapp.domain.subcategory.SubcategoryRepository;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
class AddCulturalOfferService implements
        AddCulturalOfferUseCase {
    private final CulturalOfferRepository repository;
    private final ImageRepository imageRepository;
    private final SubcategoryRepository subcategoryRepository;

    @Override
    public void addCulturalOffer(AddCulturalOfferCommand command) throws SubcategoryNotFoundException, ImageNotFoundException {
        List<Image> images = loadImages(command.getImages());

        Subcategory subcategory = subcategoryRepository.findByIdAndCategoryIdAndArchivedFalse(command.getSubcategoryId(),
                command.getCategoryId()).orElseThrow(() -> new SubcategoryNotFoundException(command.getSubcategoryId(), command.getCategoryId()));

        CulturalOffer offer = CulturalOffer.of(
                command.getName(),
                command.getDescription(),
                BigDecimal.ZERO,
                0,
                Location.of(command.getLongitude(), command.getLatitude(), "xd"), // geocode
                images,
                subcategory);

        repository.save(offer);
    }

    private List<Image> loadImages(List<Long> imageIds) throws ImageNotFoundException {
        List<Image> images = new ArrayList<>();
        for (Long imageId : imageIds) {
            Image image = imageRepository.findById(imageId)
                    .orElseThrow(() -> new ImageNotFoundException(imageId));
            images.add(image);
        }
        return images;
    }
}
