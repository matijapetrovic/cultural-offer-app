package cultureapp.domain.cultural_offer.service;


import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static cultureapp.common.CulturalOfferTestData.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CulturalOfferRepositoryIntegrationTest {

    @Autowired
    private CulturalOfferRepository culturalOfferRepository;

    //getSingle

    @Test
    public void whenArchivedThenFindShouldReturnEmpty() {
        Optional<CulturalOffer> result =
                culturalOfferRepository.findByIdAndArchivedFalse(NON_EXISTING_CULTURAL_OFFER_ID);

        assertTrue(result.isEmpty());
    }

    @Test
    public void whenIdIsValidAndNotArchivedThenFindShouldSucceed() {

        Optional<CulturalOffer> result =
                culturalOfferRepository.findByIdAndArchivedFalse(EXISTING_CULTURAL_OFFER_ID);

        assertFalse(result.isEmpty());

        CulturalOffer culturalOffer = (CulturalOffer) result.get();
        assertFalse(culturalOffer.isArchived());
        assertEquals(EXISTING_CULTURAL_OFFER_ID, culturalOffer.getId());
        assertEquals(EXISTING_CULTURAL_OFFER_NAME, culturalOffer.getName());
    }

    //getAll

    @Test
    public void whenPageIsFirstThenFindShouldReturnNonEmpty() {

        Pageable pageRequest = PageRequest.of(FIRST_PAGE_CULTURAL_OFFERS, CULTURAL_OFFER_PAGE_SIZE, Sort.by("name"));

        Slice<CulturalOffer> result =
                culturalOfferRepository.findAllByArchivedFalse(pageRequest);

        assertEquals(CULTURAL_OFFER_PAGE_SIZE, result.getNumberOfElements());
    }

    @Test
    public void whenPageIsThirdThenFindShouldReturnEmpty() {

        Pageable pageRequest = PageRequest.of(LAST_PAGE_CULTURAL_OFFERS + 1, CULTURAL_OFFER_PAGE_SIZE, Sort.by("name"));

        Slice<CulturalOffer> result =
                culturalOfferRepository.findAllByArchivedFalse(pageRequest);

        assertEquals(0, result.getNumberOfElements());

    }

}
