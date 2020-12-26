package cultureapp.domain.cultural_offer.service;

import cultureapp.domain.category.Category;
import cultureapp.domain.core.EmailSender;
import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.Location;
import cultureapp.domain.cultural_offer.command.UpdateCulturalOfferUseCase;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.image.Image;
import cultureapp.domain.image.ImageRepository;
import cultureapp.domain.image.exception.ImageNotFoundException;
import cultureapp.domain.subcategory.Subcategory;
import cultureapp.domain.subcategory.SubcategoryRepository;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import cultureapp.domain.user.RegularUser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.validation.ConstraintViolationException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static cultureapp.common.CategoryTestData.*;
import static cultureapp.common.CulturalOfferTestData.*;
import static cultureapp.common.CulturalOfferTestData.VALID_CULTURAL_OFFER_DESCRIPTION;
import static cultureapp.common.ImageTestData.*;
import static cultureapp.common.SubcategoryTestData.*;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

public class UpdateCulturalOfferServiceUnitTest {

    private final EmailSender emailSender =
            Mockito.mock(EmailSender.class);

    private final SubcategoryRepository subcategoryRepository =
            Mockito.mock(SubcategoryRepository.class);

    private final CulturalOfferRepository culturalOfferRepository =
            Mockito.mock(CulturalOfferRepository.class);

    private final ImageRepository imageRepository =
            Mockito.mock(ImageRepository.class);

    private final UpdateCulturalOfferService updateCulturalOfferService =
            new UpdateCulturalOfferService(
                    culturalOfferRepository,
                    subcategoryRepository,
                    imageRepository,
                    emailSender
             );

    private List<Long> imageIds;
    private List<Image> images;
    private Subcategory valid_subcategory;
    private UpdateCulturalOfferUseCase.UpdateCulturalOfferCommand valid_command;
    private CulturalOffer valid_offer;
    private Set<RegularUser> subscribers;

    @Before
    public void setUp() {
        imageIds = List.of(EXISTING_IMAGE_ID_1, EXISTING_IMAGE_ID_2);
        images = List.of(Image.of("path1"), Image.of("path2"));
        subscribers = new HashSet<>();
        valid_command = new UpdateCulturalOfferUseCase.UpdateCulturalOfferCommand(
                VALID_CULTURAL_OFFER_ID,
                VALID_CULTURAL_OFFER_NAME,
                VALID_CULTURAL_OFFER_DESCRIPTION,
                VALID_CULTURAL_OFFER_LATITUDE,
                VALID_CULTURAL_OFFER_LONGITUDE,
                VALID_CATEGORY_ID,
                VALID_SUBCATEGORY_ID,
                imageIds
        );
        valid_subcategory = Subcategory.withId(
                VALID_SUBCATEGORY_ID,
                Category.withId(
                        VALID_CATEGORY_ID,
                        VALID_CATEGORY_NAME
                ),
                VALID_SUBCATEGORY_NAME);

        valid_offer = CulturalOffer.withId(
                VALID_CULTURAL_OFFER_ID,
                VALID_CULTURAL_OFFER_NAME,
                VALID_CULTURAL_OFFER_DESCRIPTION,
                Location.of(VALID_LOCATION_LONGITUDE,
                        VALID_LOCATION_LATITUDE,
                        VALID_LOCATION_ADDRESS),
                images,
                subscribers,
                valid_subcategory
        );
    }

    @Test(expected = ConstraintViolationException.class)
    public void givenCulturalOfferIdIsInvalidThenUpdateCulturalOfferCommandWillFail() {
        new UpdateCulturalOfferUseCase.UpdateCulturalOfferCommand(
                INVALID_CULTURAL_OFFER_ID,
                VALID_CULTURAL_OFFER_NAME,
                VALID_CULTURAL_OFFER_DESCRIPTION,
                VALID_CULTURAL_OFFER_LATITUDE,
                VALID_CULTURAL_OFFER_LONGITUDE,
                VALID_CATEGORY_ID,
                VALID_SUBCATEGORY_ID,
                imageIds
        );
    }


    @Test(expected = ConstraintViolationException.class)
    public void givenCulturalOfferNameIsEmptyThenCreateUpdateCulturalOfferCommandWillFail() {
        new UpdateCulturalOfferUseCase.UpdateCulturalOfferCommand(
                VALID_CULTURAL_OFFER_ID,
                INVALID_CULTURAL_OFFER_NAME,
                VALID_CULTURAL_OFFER_DESCRIPTION,
                VALID_CULTURAL_OFFER_LATITUDE,
                VALID_CULTURAL_OFFER_LONGITUDE,
                VALID_CATEGORY_ID,
                VALID_SUBCATEGORY_ID,
                imageIds
        );
    }

    @Test(expected = ConstraintViolationException.class)
    public void givenCulturalOfferDescriptionIsInvalidThenCreateUpdateCulturalOfferCommandWillFail() {
        new UpdateCulturalOfferUseCase.UpdateCulturalOfferCommand(
                VALID_CULTURAL_OFFER_ID,
                VALID_CULTURAL_OFFER_NAME,
                INVALID_CULTURAL_OFFER_DESCRIPTION,
                VALID_CULTURAL_OFFER_LATITUDE,
                VALID_CULTURAL_OFFER_LONGITUDE,
                VALID_CATEGORY_ID,
                VALID_SUBCATEGORY_ID,
                imageIds
        );
    }

    @Test(expected = ConstraintViolationException.class)
    public void givenLatitudeIsInvalidThenCreateUpdateCulturalOfferCommandWillFail() {
        new UpdateCulturalOfferUseCase.UpdateCulturalOfferCommand(
                VALID_CULTURAL_OFFER_ID,
                VALID_CULTURAL_OFFER_NAME,
                VALID_CULTURAL_OFFER_DESCRIPTION,
                INVALID_CULTURAL_OFFER_LATITUDE,
                VALID_CULTURAL_OFFER_LONGITUDE,
                VALID_CATEGORY_ID,
                VALID_SUBCATEGORY_ID,
                imageIds
        );
    }

    @Test(expected = ConstraintViolationException.class)
    public void givenLongitudeIsInvalidThenCreateUpdateCulturalOfferCommandWillFail() {
        new UpdateCulturalOfferUseCase.UpdateCulturalOfferCommand(
                VALID_CULTURAL_OFFER_ID,
                VALID_CULTURAL_OFFER_NAME,
                VALID_CULTURAL_OFFER_DESCRIPTION,
                VALID_CULTURAL_OFFER_LATITUDE,
                INVALID_CULTURAL_OFFER_LONGITUDE,
                VALID_CATEGORY_ID,
                VALID_SUBCATEGORY_ID,
                imageIds
        );
    }

    @Test(expected = ConstraintViolationException.class)
    public void givenCategoryIdIsInvalidThenCreateUpdateCulturalOfferCommandWillFail() {
        new UpdateCulturalOfferUseCase.UpdateCulturalOfferCommand(
                VALID_CULTURAL_OFFER_ID,
                VALID_CULTURAL_OFFER_NAME,
                VALID_CULTURAL_OFFER_DESCRIPTION,
                VALID_CULTURAL_OFFER_LATITUDE,
                VALID_CULTURAL_OFFER_LONGITUDE,
                INVALID_CATEGORY_ID,
                VALID_SUBCATEGORY_ID,
                imageIds
        );
    }

    @Test(expected = ConstraintViolationException.class)
    public void givenSubcategoryIdIsInvalidThenCreateUpdateCulturalOfferCommandWillFail() {
        new UpdateCulturalOfferUseCase.UpdateCulturalOfferCommand(
                VALID_CULTURAL_OFFER_ID,
                VALID_CULTURAL_OFFER_NAME,
                VALID_CULTURAL_OFFER_DESCRIPTION,
                VALID_CULTURAL_OFFER_LATITUDE,
                VALID_CULTURAL_OFFER_LONGITUDE,
                VALID_CATEGORY_ID,
                INVALID_SUBCATEGORY_ID,
                imageIds
        );
    }

    @Test(expected = CulturalOfferNotFoundException.class)
    public void GivenCulturalOfferDoesntExistThenUpdateCulturalOfferWillFail() throws SubcategoryNotFoundException, ImageNotFoundException, CulturalOfferNotFoundException {
        given(culturalOfferRepository
                .findByOfferIdWithSubscriptions(VALID_CULTURAL_OFFER_ID))
                .willReturn(Optional.empty());

        given(subcategoryRepository
                .findByIdAndCategoryIdAndArchivedFalse(VALID_SUBCATEGORY_ID, VALID_CATEGORY_ID))
                .willReturn(Optional.of(valid_subcategory));

        given(imageRepository
                .findById(EXISTING_IMAGE_ID_1))
                .willReturn(Optional.of(Image.of("path1")));

        given(imageRepository
                .findById(EXISTING_IMAGE_ID_2))
                .willReturn(Optional.of(Image.of("path2")));

        updateCulturalOfferService.updateCulturalOffer(valid_command);
    }

    @Test(expected = SubcategoryNotFoundException.class)
    public void GivenSubcategoryDoesntExistThenUpdateCulturalOfferWillFail() throws SubcategoryNotFoundException, ImageNotFoundException, CulturalOfferNotFoundException {
        given(culturalOfferRepository
                .findByOfferIdWithSubscriptions(VALID_CULTURAL_OFFER_ID))
                .willReturn(Optional.of(valid_offer));

        given(subcategoryRepository
                .findByIdAndCategoryIdAndArchivedFalse(VALID_SUBCATEGORY_ID, VALID_CATEGORY_ID))
                .willReturn(Optional.empty());

        given(imageRepository
                .findById(EXISTING_IMAGE_ID_1))
                .willReturn(Optional.of(Image.of("path1")));

        given(imageRepository
                .findById(EXISTING_IMAGE_ID_2))
                .willReturn(Optional.of(Image.of("path2")));

        updateCulturalOfferService.updateCulturalOffer(valid_command);
    }

    @Test
    public void givenUpdateCulturalOfferCommandIsValidThenUpdateCulturalOfferWillSucceed() throws ImageNotFoundException, SubcategoryNotFoundException, CulturalOfferNotFoundException {
        given(imageRepository
                .findById(EXISTING_IMAGE_ID_1))
                .willReturn(Optional.of(Image.of("path1")));

        given(imageRepository
                .findById(EXISTING_IMAGE_ID_2))
                .willReturn(Optional.of(Image.of("path2")));

        given(culturalOfferRepository
                .findByOfferIdWithSubscriptions(VALID_CULTURAL_OFFER_ID))
                .willReturn(Optional.of(valid_offer));

        given(subcategoryRepository
                .findByIdAndCategoryIdAndArchivedFalse(VALID_SUBCATEGORY_ID, VALID_CATEGORY_ID))
                .willReturn(Optional.of(valid_subcategory));

        updateCulturalOfferService.updateCulturalOffer(valid_command);

        then(culturalOfferRepository)
                .should()
                .save(notNull());
    }

    @Test(expected = ImageNotFoundException.class)
    public void givenImageIsInvalidThenUpdateCulturalOfferWillSucceed() throws ImageNotFoundException, SubcategoryNotFoundException, CulturalOfferNotFoundException {
        given(imageRepository
                .findById(EXISTING_IMAGE_ID_1))
                .willReturn(Optional.empty());

        given(imageRepository
                .findById(EXISTING_IMAGE_ID_2))
                .willReturn(Optional.of(Image.of("path2")));

        given(culturalOfferRepository
                .findByOfferIdWithSubscriptions(VALID_CULTURAL_OFFER_ID))
                .willReturn(Optional.of(valid_offer));

        given(subcategoryRepository
                .findByIdAndCategoryIdAndArchivedFalse(VALID_SUBCATEGORY_ID, VALID_CATEGORY_ID))
                .willReturn(Optional.of(valid_subcategory));

        updateCulturalOfferService.updateCulturalOffer(valid_command);
    }


}
