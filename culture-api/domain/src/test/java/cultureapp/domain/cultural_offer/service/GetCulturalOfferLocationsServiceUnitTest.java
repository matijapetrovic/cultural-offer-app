package cultureapp.domain.cultural_offer.service;

import cultureapp.domain.category.Category;
import cultureapp.domain.category.CategoryRepository;
import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.exception.CulturalOfferLocationsFilterException;
import cultureapp.domain.cultural_offer.query.GetCulturalOfferLocationsQueryHandler;
import cultureapp.domain.cultural_offer.service.GetCulturalOfferLocationsService;
import cultureapp.domain.subcategory.Subcategory;
import cultureapp.domain.subcategory.SubcategoryRepository;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static cultureapp.common.CulturalOfferTestData.*;
import static cultureapp.domain.DomainUnitTestsUtil.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.junit.Assert.*;

public class GetCulturalOfferLocationsServiceUnitTest {

    private final CulturalOfferRepository culturalOfferRepository =
            Mockito.mock(CulturalOfferRepository.class);

    private final CategoryRepository categoryRepository =
            Mockito.mock(CategoryRepository.class);

    private final SubcategoryRepository subcategoryRepository =
            Mockito.mock(SubcategoryRepository.class);

    private final GetCulturalOfferLocationsService getCulturalOfferLocationsService =
            new GetCulturalOfferLocationsService(
                    culturalOfferRepository,
                    categoryRepository,
                    subcategoryRepository);

    @Test(expected = CulturalOfferLocationsFilterException.class)
    public void givenInvalidLatitudeRangeGetOfferLocationsWillFail() throws CategoryNotFoundException, CulturalOfferLocationsFilterException, SubcategoryNotFoundException {
        GetCulturalOfferLocationsQueryHandler.GetCulturalOfferLocationsQuery query =
                new GetCulturalOfferLocationsQueryHandler.GetCulturalOfferLocationsQuery(
                        INVALID_LATITUDE_RANGE_FROM,
                        INVALID_LATITUDE_RANGE_TO,
                        VALID_LONGITUDE_RANGE_FROM,
                        VALID_LONGITUDE_RANGE_TO,
                        VALID_FILTER_CATEGORY_ID,
                        VALID_FILTER_SUBCATEGORY_ID);

        getCulturalOfferLocationsService.handleGetCulturalOfferLocations(query);
    }

    @Test(expected = CulturalOfferLocationsFilterException.class)
    public void givenInvalidLongitudeRangeGetOfferLocationsWillFail() throws CategoryNotFoundException, CulturalOfferLocationsFilterException, SubcategoryNotFoundException {
        GetCulturalOfferLocationsQueryHandler.GetCulturalOfferLocationsQuery query =
                new GetCulturalOfferLocationsQueryHandler.GetCulturalOfferLocationsQuery(
                        VALID_LATITUDE_RANGE_FROM,
                        VALID_LATITUDE_RANGE_TO,
                        INVALID_LONGITUDE_RANGE_FROM,
                        INVALID_LONGITUDE_RANGE_TO,
                        VALID_FILTER_CATEGORY_ID,
                        VALID_FILTER_SUBCATEGORY_ID);

        getCulturalOfferLocationsService.handleGetCulturalOfferLocations(query);
    }

    @Test(expected = CulturalOfferLocationsFilterException.class)
    public void givenInvalidCategoryGetOfferLocationsWillFail() throws CategoryNotFoundException, CulturalOfferLocationsFilterException, SubcategoryNotFoundException {
        GetCulturalOfferLocationsQueryHandler.GetCulturalOfferLocationsQuery query =
                new GetCulturalOfferLocationsQueryHandler.GetCulturalOfferLocationsQuery(
                        INVALID_LATITUDE_RANGE_FROM,
                        INVALID_LATITUDE_RANGE_TO,
                        VALID_LONGITUDE_RANGE_FROM,
                        VALID_LONGITUDE_RANGE_TO,
                        INVALID_FILTER_CATEGORY_ID,
                        INVALID_FILTER_SUBCATEGORY_ID);

        getCulturalOfferLocationsService.handleGetCulturalOfferLocations(query);
    }

    @Test(expected = CategoryNotFoundException.class)
    public void givenCategoryDoesntExistGetOfferLocationsWillFail() throws CategoryNotFoundException, CulturalOfferLocationsFilterException, SubcategoryNotFoundException {
        given(categoryRepository.findByIdAndArchivedFalse(VALID_FILTER_CATEGORY_ID))
                .willReturn(Optional.empty());

        GetCulturalOfferLocationsQueryHandler.GetCulturalOfferLocationsQuery query =
                new GetCulturalOfferLocationsQueryHandler.GetCulturalOfferLocationsQuery(
                        VALID_LATITUDE_RANGE_FROM,
                        VALID_LATITUDE_RANGE_TO,
                        VALID_LONGITUDE_RANGE_FROM,
                        VALID_LONGITUDE_RANGE_TO,
                        VALID_FILTER_CATEGORY_ID,
                        VALID_FILTER_SUBCATEGORY_ID);

        getCulturalOfferLocationsService.handleGetCulturalOfferLocations(query);
    }

    @Test(expected = SubcategoryNotFoundException.class)
    public void givenSubcategoryDoesntExistGetOfferLocationsWillFail() throws CategoryNotFoundException, CulturalOfferLocationsFilterException, SubcategoryNotFoundException {
        Category category = validCategory();
        given(categoryRepository.findByIdAndArchivedFalse(category.getId()))
                .willReturn(Optional.of(category));

        given(subcategoryRepository.findByIdAndCategoryIdAndArchivedFalse(VALID_FILTER_SUBCATEGORY_ID, category.getId()))
                .willReturn(Optional.empty());

        GetCulturalOfferLocationsQueryHandler.GetCulturalOfferLocationsQuery query =
                new GetCulturalOfferLocationsQueryHandler.GetCulturalOfferLocationsQuery(
                        VALID_LATITUDE_RANGE_FROM,
                        VALID_LATITUDE_RANGE_TO,
                        VALID_LONGITUDE_RANGE_FROM,
                        VALID_LONGITUDE_RANGE_TO,
                        category.getId(),
                        VALID_FILTER_SUBCATEGORY_ID);

        getCulturalOfferLocationsService.handleGetCulturalOfferLocations(query);
    }

    @Test
    public void givenValidQueryAndNoResultsGetOfferLocationsWillReturnEmpty() throws CategoryNotFoundException, CulturalOfferLocationsFilterException, SubcategoryNotFoundException {
        Category category = validCategory();
        given(categoryRepository.findByIdAndArchivedFalse(category.getId()))
                .willReturn(Optional.of(category));

        Subcategory subcategory = validSubcategoryWithCategory(category);

        given(subcategoryRepository.findByIdAndCategoryIdAndArchivedFalse(subcategory.getId(), category.getId()))
                .willReturn(Optional.of(subcategory));

        GetCulturalOfferLocationsQueryHandler.GetCulturalOfferLocationsQuery query =
                new GetCulturalOfferLocationsQueryHandler.GetCulturalOfferLocationsQuery(
                        VALID_LATITUDE_RANGE_FROM,
                        VALID_LATITUDE_RANGE_TO,
                        VALID_LONGITUDE_RANGE_FROM,
                        VALID_LONGITUDE_RANGE_TO,
                        category.getId(),
                        subcategory.getId());

        given(culturalOfferRepository.findByLocationAndCategoryIdAndSubcategoryId(
            query.getLatitudeFrom(),
            query.getLatitudeTo(),
            query.getLongitudeFrom(),
            query.getLongitudeTo(),
            query.getCategoryId(),
            query.getSubcategoryId()))
        .willReturn(List.of());

        List<GetCulturalOfferLocationsQueryHandler.GetCulturalOfferLocationsDTO> result =
                getCulturalOfferLocationsService.handleGetCulturalOfferLocations(query);

        assertNotNull(result);
        assertEquals(0, result.size());

        then(culturalOfferRepository)
                .should()
                .findByLocationAndCategoryIdAndSubcategoryId(
                        query.getLatitudeFrom(),
                        query.getLatitudeTo(),
                        query.getLongitudeFrom(),
                        query.getLongitudeTo(),
                        query.getCategoryId(),
                        query.getSubcategoryId());
    }

    @Test
    public void givenValidQueryGetOfferLocationsWillSucceed() throws CategoryNotFoundException, CulturalOfferLocationsFilterException, SubcategoryNotFoundException {
        Category category = validCategory();
        given(categoryRepository.findByIdAndArchivedFalse(category.getId()))
                .willReturn(Optional.of(category));

        Subcategory subcategory = validSubcategoryWithCategory(category);

        given(subcategoryRepository.findByIdAndCategoryIdAndArchivedFalse(subcategory.getId(), category.getId()))
                .willReturn(Optional.of(subcategory));

        GetCulturalOfferLocationsQueryHandler.GetCulturalOfferLocationsQuery query =
                new GetCulturalOfferLocationsQueryHandler.GetCulturalOfferLocationsQuery(
                        VALID_LATITUDE_RANGE_FROM,
                        VALID_LATITUDE_RANGE_TO,
                        VALID_LONGITUDE_RANGE_FROM,
                        VALID_LONGITUDE_RANGE_TO,
                        category.getId(),
                        subcategory.getId());

        CulturalOffer offer1 = validCulturalOfferWithSubcategory(subcategory);

        given(culturalOfferRepository.findByLocationAndCategoryIdAndSubcategoryId(
                query.getLatitudeFrom(),
                query.getLatitudeTo(),
                query.getLongitudeFrom(),
                query.getLongitudeTo(),
                query.getCategoryId(),
                query.getSubcategoryId()))
                .willReturn(List.of(
                        new GetCulturalOfferLocationsQueryHandler.GetCulturalOfferLocationsDTO(
                                offer1.getId(),
                                offer1.getName(),
                                offer1.getLocation()
                        ),
                        new GetCulturalOfferLocationsQueryHandler.GetCulturalOfferLocationsDTO(
                                offer1.getId(),
                                offer1.getName(),
                                offer1.getLocation()
                        )
                ));

        List<GetCulturalOfferLocationsQueryHandler.GetCulturalOfferLocationsDTO> result =
                getCulturalOfferLocationsService.handleGetCulturalOfferLocations(query);

        assertNotNull(result);
        assertEquals(2, result.size());

        then(culturalOfferRepository)
                .should()
                .findByLocationAndCategoryIdAndSubcategoryId(
                        query.getLatitudeFrom(),
                        query.getLatitudeTo(),
                        query.getLongitudeFrom(),
                        query.getLongitudeTo(),
                        query.getCategoryId(),
                        query.getSubcategoryId());
    }
}
