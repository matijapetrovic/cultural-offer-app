package cultureapp.domain.cultural_offer.service;


import cultureapp.domain.cultural_offer.query.GetCulturalOffersQueryHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static cultureapp.common.CulturalOfferTestData.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class GetCulturalOffersServiceIntegrationTest {

    @Autowired
    private GetCulturalOffersQueryHandler getCulturalOffersQueryHandler;


    @Test
    public void givenPageIsFirstThenGetCulturalOffersServiceWillReturnNonEmpty() {

        GetCulturalOffersQueryHandler.GetCulturalOffersQuery query =
                new GetCulturalOffersQueryHandler.GetCulturalOffersQuery(
                        FIRST_PAGE_CULTURAL_OFFERS,
                        CULTURAL_OFFER_PAGE_SIZE
                );

        Slice<GetCulturalOffersQueryHandler.GetCulturalOffersDTO> offers =
                getCulturalOffersQueryHandler.handleGetCulturalOffers(query);

        assertEquals(FIRST_PAGE_NUM_CULTURAL_OFFERS, offers.getNumberOfElements());
        assertTrue(offers.hasNext());
        assertFalse(offers.hasPrevious());
    }

    @Test
    public void givenPageIsLastThenGetCulturalOffersServiceWillReturnNonEmpty() {

        GetCulturalOffersQueryHandler.GetCulturalOffersQuery query =
                new GetCulturalOffersQueryHandler.GetCulturalOffersQuery(
                        LAST_PAGE_CULTURAL_OFFERS,
                        CULTURAL_OFFER_PAGE_SIZE
                );

        Slice<GetCulturalOffersQueryHandler.GetCulturalOffersDTO> offers =
                getCulturalOffersQueryHandler.handleGetCulturalOffers(query);

        assertEquals(LAST_PAGE_NUM_CULTURAL_OFFERS, offers.getNumberOfElements());
        assertFalse(offers.hasNext());
        assertTrue(offers.hasPrevious());
    }
}
