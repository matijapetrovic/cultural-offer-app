package cultureapp.domain.cultural_offer.service;

import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.command.UpdateCulturalOfferUseCase;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.subcategory.Subcategory;
import cultureapp.domain.subcategory.SubcategoryRepository;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateCulturalOfferService implements UpdateCulturalOfferUseCase {
    private final CulturalOfferRepository repository;
    private final SubcategoryRepository subcategoryRepository;

    @Override
    public void updateCulturalOffer(UpdateCulturalOfferCommand command) throws CulturalOfferNotFoundException, SubcategoryNotFoundException {

        CulturalOffer offer = repository.findByIdAndArchivedFalse(command.getId())
                .orElseThrow(() -> new CulturalOfferNotFoundException(command.getId()));

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
}
