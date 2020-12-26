package cultureapp.domain.cultural_offer.service;

import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.command.UpdateCulturalOfferUseCase;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.image.exception.ImageNotFoundException;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static cultureapp.common.CategoryTestData.*;
import static cultureapp.common.CulturalOfferTestData.*;
import static cultureapp.common.ImageTestData.*;
import static cultureapp.common.SubcategoryTestData.EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1;
import static cultureapp.common.SubcategoryTestData.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class UpdateCulturalOfferServiceIntegrationTest {

    @Autowired
    private UpdateCulturalOfferService updateCulturalOfferService;

    @Autowired
    private CulturalOfferRepository culturalOfferRepository;

    @Test(expected = CulturalOfferNotFoundException.class)
    public void givenCulturalOfferIdDoesntExistThenUpdateCulturalOfferWillFail() throws CulturalOfferNotFoundException, ImageNotFoundException, SubcategoryNotFoundException {

        UpdateCulturalOfferUseCase.UpdateCulturalOfferCommand command =
                new UpdateCulturalOfferUseCase.UpdateCulturalOfferCommand(
                        NON_EXISTING_CULTURAL_OFFER_ID,
                        NON_EXISTING_CULTURAL_OFFER_NAME,
                        VALID_CULTURAL_OFFER_DESCRIPTION,
                        VALID_CULTURAL_OFFER_LONGITUDE,
                        VALID_CULTURAL_OFFER_LATITUDE,
                        EXISTING_CATEGORY_ID_2,
                        EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_2,
                        List.of(EXISTING_UPDATE_CULTURAL_OFFER_IMAGE_1, EXISTING_UPDATE_CULTURAL_OFFER_IMAGE_2)
                );

        updateCulturalOfferService.updateCulturalOffer(command);
    }

    @Test(expected = SubcategoryNotFoundException.class)
    public void givenSubcategoryDoesntExistThenUpdateCulturalOfferWillFail() throws CulturalOfferNotFoundException, ImageNotFoundException, SubcategoryNotFoundException {

        UpdateCulturalOfferUseCase.UpdateCulturalOfferCommand command =
                new UpdateCulturalOfferUseCase.UpdateCulturalOfferCommand(
                        EXISTING_CULTURAL_OFFER_ID,
                        NON_EXISTING_CULTURAL_OFFER_NAME,
                        VALID_CULTURAL_OFFER_DESCRIPTION,
                        VALID_CULTURAL_OFFER_LONGITUDE,
                        VALID_CULTURAL_OFFER_LATITUDE,
                        EXISTING_CATEGORY_ID,
                        NON_EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1,
                        List.of(EXISTING_UPDATE_CULTURAL_OFFER_IMAGE_1, EXISTING_UPDATE_CULTURAL_OFFER_IMAGE_2)
                );

        updateCulturalOfferService.updateCulturalOffer(command);
    }

    @Test(expected = ImageNotFoundException.class)
    public void givenImageDoesntExistThenUpdateCulturalOfferWillFail() throws CulturalOfferNotFoundException, ImageNotFoundException, SubcategoryNotFoundException {

        UpdateCulturalOfferUseCase.UpdateCulturalOfferCommand command =
                new UpdateCulturalOfferUseCase.UpdateCulturalOfferCommand(
                        EXISTING_CULTURAL_OFFER_ID,
                        NON_EXISTING_CULTURAL_OFFER_NAME,
                        VALID_CULTURAL_OFFER_DESCRIPTION,
                        VALID_CULTURAL_OFFER_LONGITUDE,
                        VALID_CULTURAL_OFFER_LATITUDE,
                        EXISTING_CATEGORY_ID_2,
                        EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_2,
                        List.of(NON_EXISTING_IMAGE_ID_1, NON_EXISTING_IMAGE_ID_2)
                );

        updateCulturalOfferService.updateCulturalOffer(command);
    }


    @Test
    public void givenUpdateCulturalOfferCommandIsValidThenUpdateCulturalOfferWillSucceed() throws CulturalOfferNotFoundException, ImageNotFoundException, SubcategoryNotFoundException {

        UpdateCulturalOfferUseCase.UpdateCulturalOfferCommand command =
                new UpdateCulturalOfferUseCase.UpdateCulturalOfferCommand(
                        EXISTING_CULTURAL_OFFER_ID,
                        NON_EXISTING_CULTURAL_OFFER_NAME,
                        VALID_CULTURAL_OFFER_DESCRIPTION,
                        VALID_CULTURAL_OFFER_LONGITUDE,
                        VALID_CULTURAL_OFFER_LATITUDE,
                        EXISTING_CATEGORY_ID_2,
                        EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_2,
                        List.of(EXISTING_UPDATE_CULTURAL_OFFER_IMAGE_1, EXISTING_UPDATE_CULTURAL_OFFER_IMAGE_2)
                );

        Optional<CulturalOffer> previousOffer =
                culturalOfferRepository.findByIdAndArchivedFalse(EXISTING_CULTURAL_OFFER_ID);
        CulturalOffer previousOfferObject = (CulturalOffer) previousOffer.get();

        updateCulturalOfferService.updateCulturalOffer(command);

        Optional<CulturalOffer> newOffer =
                culturalOfferRepository.findByIdAndArchivedFalse(EXISTING_CULTURAL_OFFER_ID);

        assertTrue(newOffer.isPresent());

        CulturalOffer newOfferObject = (CulturalOffer) newOffer.get();

        assertEquals(NON_EXISTING_CULTURAL_OFFER_NAME, newOfferObject.getName());
        assertEquals(EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_2, newOfferObject.getSubcategory().getId());
        assertEquals(EXISTING_CATEGORY_ID_2, newOfferObject.getSubcategory().getCategory().getId());

        culturalOfferRepository.save(previousOfferObject);

    }
}
