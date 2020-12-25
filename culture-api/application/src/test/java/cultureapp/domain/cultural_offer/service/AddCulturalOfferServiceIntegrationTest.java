package cultureapp.domain.cultural_offer.service;


import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.command.AddCulturalOfferUseCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static cultureapp.common.CategoryTestData.*;
import static cultureapp.common.CulturalOfferTestData.*;
import static cultureapp.common.SubcategoryTestData.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class AddCulturalOfferServiceIntegrationTest {

    @Autowired
    private AddCulturalOfferService addCulturalOfferService;

    @Autowired
    private CulturalOfferRepository culturalOfferRepository;

    @Test
    public void givenSubcategoryDoesntExistThanAddCulturalOfferWillFail() {
        AddCulturalOfferUseCase.AddCulturalOfferCommand command = new AddCulturalOfferUseCase.AddCulturalOfferCommand(
                VALID_CULTURAL_OFFER_NAME,
                VALID_CULTURAL_OFFER_LONGITUDE,
                VALID_CULTURAL_OFFER_LATITUDE,
                List.of(),
                VALID_CULTURAL_OFFER_DESCRIPTION,
                EXISTING_CATEGORY_ID,
                EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1
        );
    }
}
