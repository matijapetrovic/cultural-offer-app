package cultureapp.domain.review;

import static cultureapp.common.ReviewTestData.*;
import static cultureapp.common.CulturalOfferTestData.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class ReviewRepositoryIntegrationTest {
    private final int PAGE_SIZE = 2;
    private final Pageable FIRST_PAGE = PageRequest.of(0, PAGE_SIZE);

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void whenIdAndCulturalOfferIdAreValidAndNotArchivedThenFindShouldSucceed() {
        Optional<Review> resultOptional = reviewRepository.findByIdAndCulturalOfferIdAndArchivedFalse(
                EXISTING_REVIEW_ID_FOR_CULTURAL_OFFER_ID_1,
                EXISTING_CULTURAL_OFFER_ID);

       assertFalse(resultOptional.isEmpty());

       Review result = resultOptional.get();
       assertFalse(result.isArchived());
       assertEquals(EXISTING_REVIEW_ID_FOR_CULTURAL_OFFER_ID_1, result.getId());
       assertEquals(EXISTING_CULTURAL_OFFER_ID, result.getCulturalOffer().getId());
    }

    @Test
    public void whenArchivedThenFindShouldReturnEmpty() {
        Optional<Review> resultOptional = reviewRepository.findByIdAndCulturalOfferIdAndArchivedFalse(
                EXISTING_REVIEW_ID_FOR_CULTURAL_OFFER_ID_1,
                NON_EXISTING_CULTURAL_OFFER_ID);
        assertTrue(resultOptional.isEmpty());
    }

    @Test
    public void whenCulturalOfferIdIsValidAndPageIsFirstThenFindShouldReturnEmpty() {
        Slice<Review> result = reviewRepository.findAllByCulturalOfferIdAndArchivedFalse(
                EXISTING_CULTURAL_OFFER_ID,
                FIRST_PAGE
        );
        assertEquals(PAGE_SIZE, result.getNumberOfElements());
    }

    @Test
    public void whenCulturalOfferIdIsInvalidAndPageIsFirstThenShouldReturnEmpty() {
        Slice<Review> result = reviewRepository.findAllByCulturalOfferIdAndArchivedFalse(
                NON_EXISTING_CULTURAL_OFFER_ID,
                FIRST_PAGE
        );
        assertEquals(0, result.getNumberOfElements());
    }

    @Test
    public void whenCulturalOfferIdIsValidAndPageIsThirdThenShouldReturnEmpty() {
        Slice<Review> result = reviewRepository.findAllByCulturalOfferIdAndArchivedFalse(
                NON_EXISTING_CULTURAL_OFFER_ID,
                PageRequest.of(2, PAGE_SIZE)
        );
        assertEquals(0, result.getNumberOfElements());
    }
}
