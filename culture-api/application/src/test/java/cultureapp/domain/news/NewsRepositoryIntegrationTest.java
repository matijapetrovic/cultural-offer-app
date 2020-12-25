package cultureapp.domain.news;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static cultureapp.common.CulturalOfferTestData.*;
import static cultureapp.common.NewsTestData.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:test.properties")
public class NewsRepositoryIntegrationTest {
    private final int PAGE_SIZE = 3;
    private final Pageable FIRST_PAGE = PageRequest.of(0,  PAGE_SIZE);
    private final Pageable TENTH_PAGE = PageRequest.of(10, PAGE_SIZE);

    @Autowired
    private NewsRepository newsRepository;

    /*
     * testing find by id
     * news id           valid
     * cultural offer id valid
     * news              not archived
     * expected          success
     */
    @Test
    public void whenIdAndCulturalOfferIdAreValidAndNotArchivedThenFindShouldSucceed() {
        Optional<News> newsOptional = newsRepository.findByIdAndCulturalOfferIdAndArchivedFalse(
                EXISTING_NEWS_ID_1_FOR_OFFER_ID_1,
                EXISTING_CULTURAL_OFFER_ID
        );
        assertFalse(newsOptional.isEmpty());

        News news = newsOptional.get();

        assertFalse(news.getArchived());
        assertEquals(EXISTING_NEWS_TITLE_1_FOR_OFFER_ID_1, news.getTitle());
        assertEquals(EXISTING_CULTURAL_OFFER_NAME, news.getCulturalOffer().getName());
    }

    /*
     * testing find by id
     * news id           invalid -> not in cultural offer
     * cultural offer id valid
     * news              not archived
     * expected          empty result
     */
    @Test
    public void whenIdIsInvalidThenFindShouldReturnEmpty() {
        Optional<News> newsOptional = newsRepository.findByIdAndCulturalOfferIdAndArchivedFalse(
                NON_EXISTING_NEWS_ID_FOR_OFFER_ID_1,
                EXISTING_CULTURAL_OFFER_ID
        );
        assertTrue(newsOptional.isEmpty());
    }

    /*
     * testing find by id
     * news id           valid
     * cultural offer id invalid -> news not in this offer
     * news              not archived
     * expected          empty result
     */
    @Test
    public void whenCulturalOfferIdIsInvalidThenFindShouldReturnEmpty() {
        Optional<News> newsOptional = newsRepository.findByIdAndCulturalOfferIdAndArchivedFalse(
                EXISTING_NEWS_ID_1_FOR_OFFER_ID_1,
                EXISTING_CULTURAL_OFFER_ID_2
        );
        assertTrue(newsOptional.isEmpty());
    }


    /*
     * testing find by id
     * news id           valid
     * cultural offer id valid
     * news              archived
     * expected          empty result
     */
    @Test
    public void whenArchivedThenFindShouldReturnEmpty() {
        Optional<News> newsOptional = newsRepository.findByIdAndCulturalOfferIdAndArchivedFalse(
                ARCHIVED_NEWS_ID_3_FOR_OFFER_ID_1,
                EXISTING_CULTURAL_OFFER_ID
        );
        assertTrue(newsOptional.isEmpty());
    }


    /*
     * testing find all
     * cultural offer id  valid
     * page               valid - 1
     * expected           success
     */
    @Test
    public void whenCulturalOfferIdIsValidAndPageIsFirstThenFindShouldReturnNonEmpty() {
        Slice<News> newsSlice = newsRepository.findAllByCulturalOfferIdAndArchivedFalse(
                EXISTING_CULTURAL_OFFER_ID,
                FIRST_PAGE
        );
        assertEquals(NUM_OF_NON_ARCHIVED_NEWS_PER_OFFER.longValue(), newsSlice.getNumberOfElements());
        assertEquals(EXISTING_NEWS_TITLE_1_FOR_OFFER_ID_1, newsSlice.getContent().get(0).getTitle());
    }


    /*
     * testing find all
     * cultural offer id  invalid -> not existing
     * page               valid - 1
     * expected           empty result
     */
    @Test
    public void whenCulturalOfferIdInvalidThenFindShouldReturnEmpty() {
        Slice<News> newsSlice = newsRepository.findAllByCulturalOfferIdAndArchivedFalse(
                NON_EXISTING_CULTURAL_OFFER_ID,
                FIRST_PAGE
        );
        assertFalse(newsSlice.hasContent());
    }


    /*
     * testing find all
     * cultural offer id  valid
     * page               invalid - 10
     * expected           empty result
     */
    @Test
    public void whenCulturalOfferIdIsValidAndPageIsTenthThenFindShouldReturnEmpty() {
        Slice<News> newsSlice = newsRepository.findAllByCulturalOfferIdAndArchivedFalse(
                EXISTING_CULTURAL_OFFER_ID,
                TENTH_PAGE
        );
        assertFalse(newsSlice.hasContent());
    }
}
