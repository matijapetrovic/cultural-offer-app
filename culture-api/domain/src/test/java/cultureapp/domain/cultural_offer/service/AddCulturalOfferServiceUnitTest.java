package cultureapp.domain.cultural_offer.service;

import cultureapp.domain.category.Category;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.command.AddCulturalOfferUseCase;
import cultureapp.domain.image.Image;
import cultureapp.domain.image.ImageRepository;
import cultureapp.domain.image.exception.ImageNotFoundException;
import cultureapp.domain.subcategory.Subcategory;
import cultureapp.domain.subcategory.SubcategoryRepository;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.validation.ConstraintViolationException;

import java.util.List;
import java.util.Optional;

import static cultureapp.common.CategoryTestData.*;
import static cultureapp.common.CulturalOfferTestData.*;
import static cultureapp.common.ImageTestData.*;
import static cultureapp.common.SubcategoryTestData.*;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

public class AddCulturalOfferServiceUnitTest {

    private final SubcategoryRepository subcategoryRepository =
            Mockito.mock(SubcategoryRepository.class);

    private final CulturalOfferRepository culturalOfferRepository =
            Mockito.mock(CulturalOfferRepository.class);

    private final ImageRepository imageRepository =
            Mockito.mock(ImageRepository.class);

    private final AddCulturalOfferService addCulturalOfferService =
            new AddCulturalOfferService(
                    culturalOfferRepository,
                    imageRepository,
                    subcategoryRepository
            );

    private List<Long> imageIds;
    private AddCulturalOfferUseCase.AddCulturalOfferCommand valid_command;
    private Subcategory valid_subcategory;

    @Before
    public void setUp() {
        imageIds = List.of(EXISTING_IMAGE_ID_1, EXISTING_IMAGE_ID_2);
        valid_command = new AddCulturalOfferUseCase.AddCulturalOfferCommand(
                VALID_CULTURAL_OFFER_NAME,
                VALID_CULTURAL_OFFER_LATITUDE,
                VALID_CULTURAL_OFFER_LONGITUDE,
                imageIds,
                VALID_CULTURAL_OFFER_DESCRIPTION,
                VALID_CATEGORY_ID,
                VALID_SUBCATEGORY_ID
        );
        valid_subcategory = Subcategory.withId(
                VALID_SUBCATEGORY_ID,
                Category.withId(
                        VALID_CATEGORY_ID,
                        VALID_CATEGORY_NAME
                        ),
                VALID_SUBCATEGORY_NAME);
    }

    @Test(expected = ConstraintViolationException.class)
    public void givenCulturalOfferNameIsEmptyThenCreateAddCulturalOfferCommandWillFail() {
        new AddCulturalOfferUseCase.AddCulturalOfferCommand(
                INVALID_CULTURAL_OFFER_NAME,
                VALID_CULTURAL_OFFER_LATITUDE,
                VALID_CULTURAL_OFFER_LONGITUDE,
                imageIds,
                VALID_CULTURAL_OFFER_DESCRIPTION,
                VALID_CATEGORY_ID,
                VALID_SUBCATEGORY_ID
        );
    }

    @Test(expected = ConstraintViolationException.class)
    public void givenLatitudeIsInvalidThenCreateAddCulturalOfferCommandWillFail() {
        new AddCulturalOfferUseCase.AddCulturalOfferCommand(
                VALID_CULTURAL_OFFER_NAME,
                INVALID_CULTURAL_OFFER_LATITUDE,
                VALID_CULTURAL_OFFER_LONGITUDE,
                imageIds,
                VALID_CULTURAL_OFFER_DESCRIPTION,
                VALID_CATEGORY_ID,
                VALID_SUBCATEGORY_ID
        );
    }

    @Test(expected = ConstraintViolationException.class)
    public void givenLongitudeIsInvalidThenCreateAddCulturalOfferCommandWillFail() {
        new AddCulturalOfferUseCase.AddCulturalOfferCommand(
                VALID_CULTURAL_OFFER_NAME,
                VALID_CULTURAL_OFFER_LATITUDE,
                INVALID_CULTURAL_OFFER_LONGITUDE,
                imageIds,
                VALID_CULTURAL_OFFER_DESCRIPTION,
                VALID_CATEGORY_ID,
                VALID_SUBCATEGORY_ID
        );
    }

    @Test(expected = ConstraintViolationException.class)
    public void givenDescriptionIsInvalidThenCreateAddCulturalOfferCommandWillFail() {
        new AddCulturalOfferUseCase.AddCulturalOfferCommand(
                VALID_CULTURAL_OFFER_NAME,
                VALID_CULTURAL_OFFER_LATITUDE,
                VALID_CULTURAL_OFFER_LONGITUDE,
                imageIds,
                INVALID_CULTURAL_OFFER_DESCRIPTION,
                VALID_CATEGORY_ID,
                VALID_SUBCATEGORY_ID
        );
    }

    @Test(expected = ConstraintViolationException.class)
    public void givenCategoryIdIsInvalidThenCreateAddCulturalOfferCommandWillFail() {
        new AddCulturalOfferUseCase.AddCulturalOfferCommand(
                VALID_CULTURAL_OFFER_NAME,
                VALID_CULTURAL_OFFER_LATITUDE,
                VALID_CULTURAL_OFFER_LONGITUDE,
                imageIds,
                VALID_CULTURAL_OFFER_DESCRIPTION,
                INVALID_CATEGORY_ID,
                VALID_SUBCATEGORY_ID
        );
    }

    @Test(expected = ConstraintViolationException.class)
    public void givenSubcategoryIdIsInvalidThenCreateAddCulturalOfferCommandWillFail() {
        new AddCulturalOfferUseCase.AddCulturalOfferCommand(
                VALID_CULTURAL_OFFER_NAME,
                VALID_CULTURAL_OFFER_LATITUDE,
                VALID_CULTURAL_OFFER_LONGITUDE,
                imageIds,
                VALID_CULTURAL_OFFER_DESCRIPTION,
                VALID_CATEGORY_ID,
                INVALID_SUBCATEGORY_ID
        );
    }

    @Test(expected = SubcategoryNotFoundException.class)
    public void GivenSubcategoryDoesntExistThenAddCulturalOfferWillFail() throws SubcategoryNotFoundException, ImageNotFoundException {
        given(subcategoryRepository
                .findByIdAndCategoryIdAndArchivedFalse(VALID_SUBCATEGORY_ID, VALID_CATEGORY_ID))
                .willReturn(Optional.empty());

        given(imageRepository
                .findById(EXISTING_IMAGE_ID_1))
                .willReturn(Optional.of(Image.of("path1")));

        given(imageRepository
                .findById(EXISTING_IMAGE_ID_2))
                .willReturn(Optional.of(Image.of("path2")));

        addCulturalOfferService.addCulturalOffer(valid_command);
    }

    @Test
    public void givenAddCulturalOfferCommandIsValidThenAddCulturalOfferWillSucceed() throws ImageNotFoundException, SubcategoryNotFoundException {
        given(imageRepository
                .findById(EXISTING_IMAGE_ID_1))
                .willReturn(Optional.of(Image.of("path1")));

        given(imageRepository
                .findById(EXISTING_IMAGE_ID_2))
                .willReturn(Optional.of(Image.of("path2")));

        given(subcategoryRepository
                .findByIdAndCategoryIdAndArchivedFalse(VALID_SUBCATEGORY_ID, VALID_CATEGORY_ID))
                .willReturn(Optional.of(valid_subcategory));

        addCulturalOfferService.addCulturalOffer(valid_command);

        then(culturalOfferRepository)
                .should()
                .save(notNull());
    }

    @Test(expected = ImageNotFoundException.class)
    public void givenImageIsInvalidThenAddCulturalOfferWillFail() throws ImageNotFoundException, SubcategoryNotFoundException {
        given(imageRepository
                .findById(EXISTING_IMAGE_ID_1))
                .willReturn(Optional.empty());

        given(imageRepository
                .findById(EXISTING_IMAGE_ID_2))
                .willReturn(Optional.of(Image.of("path2")));

        given(subcategoryRepository
                .findByIdAndCategoryIdAndArchivedFalse(VALID_SUBCATEGORY_ID, VALID_CATEGORY_ID))
                .willReturn(Optional.of(valid_subcategory));

        addCulturalOfferService.addCulturalOffer(valid_command);
    }
}
