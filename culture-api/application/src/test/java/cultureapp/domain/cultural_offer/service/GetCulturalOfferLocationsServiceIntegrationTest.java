package cultureapp.domain.cultural_offer.service;

import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.cultural_offer.exception.CulturalOfferLocationsFilterException;
import cultureapp.domain.cultural_offer.query.GetCulturalOfferLocationsQueryHandler;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static cultureapp.common.CategoryTestData.EXISTING_CATEGORY_ID;
import static cultureapp.common.CategoryTestData.NON_EXISTING_CATEGORY_ID;
import static cultureapp.common.CulturalOfferTestData.*;
import static cultureapp.common.SubcategoryTestData.EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1;
import static cultureapp.common.SubcategoryTestData.NON_EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class GetCulturalOfferLocationsServiceIntegrationTest {

    @Autowired
    private GetCulturalOfferLocationsService getCulturalOfferLocationsService;

    @Test(expected = CategoryNotFoundException.class)
    public void givenCategoryDoesntExistGetOfferLocationsWillFail() throws CategoryNotFoundException, CulturalOfferLocationsFilterException, SubcategoryNotFoundException {
        GetCulturalOfferLocationsQueryHandler.GetCulturalOfferLocationsQuery query =
                new GetCulturalOfferLocationsQueryHandler.GetCulturalOfferLocationsQuery(
                        VALID_LATITUDE_RANGE_FROM,
                        VALID_LATITUDE_RANGE_TO,
                        VALID_LONGITUDE_RANGE_FROM,
                        VALID_LONGITUDE_RANGE_TO,
                        NON_EXISTING_CATEGORY_ID,
                        null);

        getCulturalOfferLocationsService.handleGetCulturalOfferLocations(query);
    }

    @Test(expected = SubcategoryNotFoundException.class)
    public void givenSubcategoryDoesntExistGetOfferLocationsWillFail() throws CategoryNotFoundException, CulturalOfferLocationsFilterException, SubcategoryNotFoundException {

        GetCulturalOfferLocationsQueryHandler.GetCulturalOfferLocationsQuery query =
                new GetCulturalOfferLocationsQueryHandler.GetCulturalOfferLocationsQuery(
                        VALID_LATITUDE_RANGE_FROM,
                        VALID_LATITUDE_RANGE_TO,
                        VALID_LONGITUDE_RANGE_FROM,
                        VALID_LONGITUDE_RANGE_TO,
                        EXISTING_CATEGORY_ID,
                        NON_EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1);

        getCulturalOfferLocationsService.handleGetCulturalOfferLocations(query);
    }

    @Test
    public void givenValidQueryAndNoResultsGetOfferLocationsWillReturnEmpty() throws CategoryNotFoundException, CulturalOfferLocationsFilterException, SubcategoryNotFoundException {
        GetCulturalOfferLocationsQueryHandler.GetCulturalOfferLocationsQuery query =
                new GetCulturalOfferLocationsQueryHandler.GetCulturalOfferLocationsQuery(
                        VALID_LATITUDE_RANGE_FROM,
                        VALID_LATITUDE_RANGE_TO - 1 ,
                        VALID_LONGITUDE_RANGE_FROM,
                        VALID_LONGITUDE_RANGE_TO,
                        EXISTING_CATEGORY_ID,
                        2L);

        List<GetCulturalOfferLocationsQueryHandler.GetCulturalOfferLocationsDTO> result =
                getCulturalOfferLocationsService.handleGetCulturalOfferLocations(query);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void givenValidQueryGetOfferLocationsWillSucceed() throws CategoryNotFoundException, CulturalOfferLocationsFilterException, SubcategoryNotFoundException {
        GetCulturalOfferLocationsQueryHandler.GetCulturalOfferLocationsQuery query =
                new GetCulturalOfferLocationsQueryHandler.GetCulturalOfferLocationsQuery(
                        EXISTING_LATITUDE_RANGE_FROM,
                        EXISTING_LATITUDE_RANGE_TO,
                        EXISTING_LONGITUDE_RANGE_FROM,
                        EXISTING_LONGITUDE_RANGE_TO,
                        EXISTING_CATEGORY_ID,
                        EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1);

        List<GetCulturalOfferLocationsQueryHandler.GetCulturalOfferLocationsDTO> result =
                getCulturalOfferLocationsService.handleGetCulturalOfferLocations(query);

        assertNotNull(result);
        assertEquals(NUMBER_OF_CULTURAL_OFFERS_FOR_SUBCATEGORY_1_1, result.size());
    }
}
