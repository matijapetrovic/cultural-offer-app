package cultureapp.domain.cultural_offer.service;

import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.command.UpdateCulturalOfferUseCase;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.image.Image;
import cultureapp.domain.image.ImageRepository;
import cultureapp.domain.image.exception.ImageNotFoundException;
import cultureapp.domain.subcategory.Subcategory;
import cultureapp.domain.subcategory.SubcategoryRepository;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UpdateCulturalOfferService implements UpdateCulturalOfferUseCase {
    private final CulturalOfferRepository repository;
    private final SubcategoryRepository subcategoryRepository;
    private final ImageRepository imageRepository;

    @Override
    public void updateCulturalOffer(UpdateCulturalOfferCommand command) throws CulturalOfferNotFoundException, SubcategoryNotFoundException, ImageNotFoundException {
        CulturalOffer offer = repository.findByIdAndArchivedFalse(command.getId())
                .orElseThrow(() -> new CulturalOfferNotFoundException(command.getId()));

        List<Image> images = loadImages(command.getImages());
        offer.setImages(images);

        offer.setName(command.getName());
        offer.setDescription(command.getDescription());
        offer.getLocation().setLongitude(command.getLongitude());
        offer.getLocation().setLatitude(command.getLatitude());

        if(updateSubcategory(offer, command.getCategoryId(), command.getSubcategoryId())) {
            Subcategory subcategory = subcategoryRepository.findByIdAndCategoryIdAndArchivedFalse(command.getSubcategoryId(), command.getCategoryId())
                    .orElseThrow(() -> new SubcategoryNotFoundException(command.getSubcategoryId(), command.getCategoryId()));
            offer.setSubcategory(subcategory);
        }

        repository.save(offer);
    }

    private boolean updateSubcategory(CulturalOffer offer, Long categoryId, Long subcategoryId) {
        if(offer.getSubcategory().getCategory().getId() != categoryId || offer.getSubcategory().getId() != subcategoryId) {
            return true;
        }
        return false;
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
