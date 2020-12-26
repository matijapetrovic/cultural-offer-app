package cultureapp.domain.cultural_offer.service;


import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.command.AddCulturalOfferUseCase;
import cultureapp.domain.image.exception.ImageNotFoundException;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static cultureapp.common.CategoryTestData.*;
import static cultureapp.common.CulturalOfferTestData.*;
import static cultureapp.common.ImageTestData.*;
import static cultureapp.common.SubcategoryTestData.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class AddCulturalOfferServiceIntegrationTest {

    @Autowired
    private AddCulturalOfferService addCulturalOfferService;

    @Autowired
    private CulturalOfferRepository culturalOfferRepository;

    @Test(expected = SubcategoryNotFoundException.class)
    public void givenSubcategoryDoesntExistThanAddCulturalOfferWillFail() throws ImageNotFoundException, SubcategoryNotFoundException {
        AddCulturalOfferUseCase.AddCulturalOfferCommand command = new AddCulturalOfferUseCase.AddCulturalOfferCommand(
                VALID_CULTURAL_OFFER_NAME,
                VALID_CULTURAL_OFFER_LONGITUDE,
                VALID_CULTURAL_OFFER_LATITUDE,
                List.of(EXISTING_ADD_CULTURAL_OFFER_IMAGE_1, EXISTING_ADD_CULTURAL_OFFER_IMAGE_2),
                VALID_CULTURAL_OFFER_DESCRIPTION,
                EXISTING_CATEGORY_ID,
                NON_EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1
        );

        addCulturalOfferService.addCulturalOffer(command);
    }

    @Test(expected = ImageNotFoundException.class)
    public void givenImageIdDoesntExistThenAddCulturalOfferWillFail() throws ImageNotFoundException, SubcategoryNotFoundException {
        AddCulturalOfferUseCase.AddCulturalOfferCommand command = new AddCulturalOfferUseCase.AddCulturalOfferCommand(
                VALID_CULTURAL_OFFER_NAME,
                VALID_CULTURAL_OFFER_LONGITUDE,
                VALID_CULTURAL_OFFER_LATITUDE,
                List.of(NON_EXISTING_IMAGE_ID_1, NON_EXISTING_IMAGE_ID_2),
                VALID_CULTURAL_OFFER_DESCRIPTION,
                EXISTING_CATEGORY_ID,
                EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1
        );

        addCulturalOfferService.addCulturalOffer(command);
    }

    @Test
    public void givenAddCulturalOfferCommandIsValidThenAddCulturalOfferServiceWillSucceed() throws ImageNotFoundException, SubcategoryNotFoundException {

        long culturalOfferCount = culturalOfferRepository.count();

        AddCulturalOfferUseCase.AddCulturalOfferCommand command = new AddCulturalOfferUseCase.AddCulturalOfferCommand(
                VALID_CULTURAL_OFFER_NAME,
                VALID_CULTURAL_OFFER_LONGITUDE,
                VALID_CULTURAL_OFFER_LATITUDE,
                List.of(EXISTING_ADD_CULTURAL_OFFER_IMAGE_1, EXISTING_ADD_CULTURAL_OFFER_IMAGE_2),
                VALID_CULTURAL_OFFER_DESCRIPTION,
                EXISTING_CATEGORY_ID,
                EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1
        );

        addCulturalOfferService.addCulturalOffer(command);

        assertEquals(culturalOfferCount + 1, culturalOfferRepository.count());

        CulturalOffer offer = culturalOfferRepository.findAll().get((int) culturalOfferCount);

        assertEquals(EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1, offer.getSubcategory().getId());
        assertEquals(EXISTING_CATEGORY_ID, offer.getSubcategory().getCategory().getId());

        offer.setImages(List.of());

        culturalOfferRepository.delete(offer);
    }


}
