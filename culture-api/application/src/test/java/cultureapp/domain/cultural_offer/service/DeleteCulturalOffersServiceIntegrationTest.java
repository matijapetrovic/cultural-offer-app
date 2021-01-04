package cultureapp.domain.cultural_offer.service;

import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static cultureapp.common.CulturalOfferTestData.*;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class DeleteCulturalOffersServiceIntegrationTest {

    @Autowired
    private CulturalOfferRepository culturalOfferRepository;

    @Autowired
    private DeleteCulturalOfferService deleteCulturalOfferService;


    @Test(expected = CulturalOfferNotFoundException.class)
    public void givenCulturalOfferDoesntExistThenDeleteCulturalOfferServiceWillFail() throws CulturalOfferNotFoundException {
        deleteCulturalOfferService.deleteCulturalOffer(NON_EXISTING_CULTURAL_OFFER_ID);
    }

    @Test
    public void givenCulturalOfferIdIsValidThenDeleteCulturalOfferServiceWillSucceed() throws CulturalOfferNotFoundException {

        deleteCulturalOfferService.deleteCulturalOffer(EXISTING_CULTURAL_OFFER_ID);

        Optional<CulturalOffer> previousOffer =
                culturalOfferRepository.findByIdAndArchivedFalse(EXISTING_CULTURAL_OFFER_ID);

        assertTrue(previousOffer.isEmpty());

        CulturalOffer archivedOffer = culturalOfferRepository.findById(EXISTING_CULTURAL_OFFER_ID).orElseThrow();

        archivedOffer.setArchived(false);
        culturalOfferRepository.save(archivedOffer);
    }
}
