package cultureapp.domain.cultural_offer.service;


import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.cultural_offer.query.GetCulturalOfferByIdQueryHandler;
import cultureapp.domain.user.exception.RegularUserNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static cultureapp.common.CategoryTestData.*;
import static cultureapp.common.CulturalOfferTestData.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class GetCulturalOfferByIdServiceIntegrationTest {

    @Autowired
    private GetCulturalOfferByIdService getCulturalOfferByIdService;

    @Test(expected = CulturalOfferNotFoundException.class)
    public void givenCulturalOfferDoesntExistThenGetCulturalOfferByIdServiceWillFail() throws CulturalOfferNotFoundException, RegularUserNotFoundException {
        getCulturalOfferByIdService.handleGetCulturalOffer(
                new GetCulturalOfferByIdQueryHandler.GetCulturalOfferByIdQuery(
                        NON_EXISTING_CULTURAL_OFFER_ID
                )
        );
    }

    @Test
    public void givenGetCulturalOfferByIdQueryIsValidThenGetCulturalOfferByIdServiceWillSucceed() throws CulturalOfferNotFoundException, RegularUserNotFoundException {

        GetCulturalOfferByIdQueryHandler.GetCulturalOfferByIdQuery query =
                new GetCulturalOfferByIdQueryHandler.GetCulturalOfferByIdQuery(
                        EXISTING_CULTURAL_OFFER_ID
                );

        GetCulturalOfferByIdQueryHandler.GetCulturalOfferByIdDTO offer =
                getCulturalOfferByIdService.handleGetCulturalOffer(query);

        assertNotNull(offer);
        assertEquals(EXISTING_CULTURAL_OFFER_ID, offer.getId());
        assertEquals(EXISTING_CULTURAL_OFFER_NAME, offer.getName());

    }
}
