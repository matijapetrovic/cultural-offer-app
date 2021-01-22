package cultureapp.domain.cultural_offer.service;

import cultureapp.domain.category.Category;
import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.Location;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.image.Image;
import cultureapp.domain.subcategory.Subcategory;
import cultureapp.domain.user.RegularUser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static cultureapp.common.CategoryTestData.*;
import static cultureapp.common.CulturalOfferTestData.*;
import static cultureapp.common.SubcategoryTestData.*;
import static org.mockito.BDDMockito.given;

public class DeleteCulturalOfferServiceUnitTest {

    private final CulturalOfferRepository repository =
            Mockito.mock(CulturalOfferRepository.class);

    private final DeleteCulturalOfferService deleteCulturalOfferService =
            new DeleteCulturalOfferService(repository);

    private CulturalOffer valid_offer;
    private List<Image> images;
    private Set<RegularUser> subscribers;
    private Subcategory subcategory;

    @Before
    public void setUp() {
        images = List.of(Image.of("path1"), Image.of("path2"));
        subscribers = new HashSet<>();
        subcategory = Subcategory.withId(
                VALID_SUBCATEGORY_ID,
                Category.withId(
                        VALID_CATEGORY_ID,
                        VALID_CATEGORY_NAME
                ),
                VALID_SUBCATEGORY_NAME
        );
        valid_offer = CulturalOffer.withId(
                VALID_CULTURAL_OFFER_ID,
                VALID_CULTURAL_OFFER_NAME,
                VALID_CULTURAL_OFFER_DESCRIPTION,
                VALID_CULTURAL_OFFER_RATING,
                VALID_CULTURAL_OFFER_REVIEW_COUNT,
                Location.of(VALID_LOCATION_LONGITUDE,
                        VALID_LOCATION_LATITUDE,
                        VALID_LOCATION_ADDRESS),
                images,
                subscribers,
                subcategory
        );
    }


    @Test(expected = CulturalOfferNotFoundException.class)
    public void givenCulturalOfferDoesntExistThenDeleteCulturalOfferWillFail() throws CulturalOfferNotFoundException {
        given(repository.findByIdAndArchivedFalse(VALID_CULTURAL_OFFER_ID))
                .willReturn(Optional.empty());

        deleteCulturalOfferService.deleteCulturalOffer(VALID_CULTURAL_OFFER_ID);
    }

    @Test
    public void givenCulturalOfferExistThenDeleteCulturalOfferWillSucceed() throws CulturalOfferNotFoundException {
        given(repository.findByIdAndArchivedFalse(VALID_CULTURAL_OFFER_ID))
                .willReturn(Optional.of(valid_offer));


        deleteCulturalOfferService.deleteCulturalOffer(VALID_CULTURAL_OFFER_ID);
    }

}
