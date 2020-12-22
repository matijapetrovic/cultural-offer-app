package cultureapp.domain.cultural_offer.service;

import cultureapp.domain.category.CategoryRepository;
import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.exception.CulturalOfferLocationsFilterException;
import cultureapp.domain.cultural_offer.query.GetCulturalOfferLocationsQueryHandler;
import cultureapp.domain.subcategory.SubcategoryRepository;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GetCulturalOfferLocationsService implements GetCulturalOfferLocationsQueryHandler {
    private final CulturalOfferRepository culturalOfferRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;

    @Override
    public List<GetCulturalOfferLocationsDTO> handleGetCulturalOfferLocations(GetCulturalOfferLocationsQuery query) throws
            SubcategoryNotFoundException,
            CategoryNotFoundException,
            CulturalOfferLocationsFilterException {
        validateQuery(query);

        if (query.getCategoryId() != null)
            categoryRepository.findByIdAndArchivedFalse(query.getCategoryId())
            .orElseThrow(() -> new CategoryNotFoundException(query.getCategoryId()));
        if (query.getSubcategoryId() != null)
            subcategoryRepository.findByIdAndCategoryIdAndArchivedFalse(query.getSubcategoryId(), query.getCategoryId())
                .orElseThrow(() -> new SubcategoryNotFoundException(query.getSubcategoryId(), query.getCategoryId()));

        return culturalOfferRepository.findByLocationAndCategoryIdAndSubcategoryId(
                query.getLatitudeFrom(),
                query.getLatitudeTo(),
                query.getLongitudeFrom(),
                query.getLongitudeTo(),
                query.getCategoryId(),
                query.getSubcategoryId());
    }

    private void validateQuery(GetCulturalOfferLocationsQuery query) throws CulturalOfferLocationsFilterException {
        if (query.getLatitudeFrom() >= query.getLatitudeTo())
            throw new CulturalOfferLocationsFilterException("Latitude from must be less than latitude to");
        if (query.getLongitudeFrom() >= query.getLongitudeTo())
            throw new CulturalOfferLocationsFilterException("Longitude from must be less than longitude to");
        if (query.getCategoryId() == null && query.getSubcategoryId() != null)
            throw new CulturalOfferLocationsFilterException("SubcategoryId must be null if categoryId is null");
    }
}
