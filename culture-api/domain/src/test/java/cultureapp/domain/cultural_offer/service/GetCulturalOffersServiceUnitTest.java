package cultureapp.domain.cultural_offer.service;

import cultureapp.domain.category.Category;
import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.Location;
import cultureapp.domain.cultural_offer.query.GetCulturalOffersQueryHandler;
import cultureapp.domain.subcategory.Subcategory;
import cultureapp.domain.user.RegularUser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import javax.validation.ConstraintViolationException;

import java.util.HashSet;
import java.util.List;

import static cultureapp.common.CategoryTestData.*;
import static cultureapp.common.CulturalOfferTestData.*;
import static cultureapp.common.SubcategoryTestData.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

public class GetCulturalOffersServiceUnitTest {

    private final CulturalOfferRepository culturalOfferRepository =
            Mockito.mock(CulturalOfferRepository.class);

    private final GetCulturalOffersService getCulturalOffersService =
            new GetCulturalOffersService(culturalOfferRepository);

    private CulturalOffer offer;

    @Before
    public void setUp() {
        offer = CulturalOffer.of(
                VALID_CULTURAL_OFFER_NAME,
                VALID_CULTURAL_OFFER_DESCRIPTION,
                VALID_CULTURAL_OFFER_RATING,
                VALID_CULTURAL_OFFER_REVIEW_COUNT,
                Location.of(
                        VALID_LOCATION_LONGITUDE,
                        VALID_LOCATION_LATITUDE,
                        VALID_LOCATION_ADDRESS
                ),
                List.of(),
                new HashSet<RegularUser>(),
                Subcategory.of(
                        Category.of(
                                VALID_CATEGORY_NAME
                        ),
                        VALID_SUBCATEGORY_NAME
                )
        );
    }


    @Test(expected = ConstraintViolationException.class)
    public void givenPageIsInvalidThenCreateGetCulturalOffersQueryWillFail() {
        new GetCulturalOffersQueryHandler.GetCulturalOffersQuery(
                INVALID_PAGE_NUMBER, VALID_LIMIT_NUMBER
        );
    }

    @Test(expected = ConstraintViolationException.class)
    public void givenLimitIsInvalidThenCreateGetCulturalOffersQueryWillFail() {
        new GetCulturalOffersQueryHandler.GetCulturalOffersQuery(
                VALID_PAGE_NUMBER, INVALID_LIMIT_NUMBER
        );
    }

    @Test
    public void givenQueryIsValidThenGetCulturalOfferQueryHandlerWillSucceed() {
        List<CulturalOffer> culturalOfferList = List.of(offer, offer);
        Slice<CulturalOffer> slice = new SliceImpl<>(culturalOfferList);

        given(culturalOfferRepository
                .findAllByArchivedFalse(notNull()))
                .willReturn(slice);

        GetCulturalOffersQueryHandler.GetCulturalOffersQuery query =
                new GetCulturalOffersQueryHandler.GetCulturalOffersQuery(
                        VALID_PAGE_NUMBER,
                        VALID_LIMIT_NUMBER
                );

        Slice<GetCulturalOffersQueryHandler.GetCulturalOffersDTO> result =
                getCulturalOffersService.handleGetCulturalOffers(query);

        then(culturalOfferRepository)
                .should()
                .findAllByArchivedFalse(notNull());

        assertEquals(slice.getNumberOfElements(), result.getNumberOfElements());
    }
}
