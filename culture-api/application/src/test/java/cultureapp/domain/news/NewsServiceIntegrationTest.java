package cultureapp.domain.news;

import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.image.Image;
import cultureapp.domain.image.exception.ImageNotFoundException;
import cultureapp.domain.news.command.AddNewsUseCase;
import cultureapp.domain.news.command.UpdateNewsUseCase;
import cultureapp.domain.news.exception.NewsAlreadyExistException;
import cultureapp.domain.news.exception.NewsNotFoundException;
import cultureapp.domain.news.query.GetNewsByIdQueryHandler;
import cultureapp.domain.news.query.GetNewsForOfferQueryHandler;
import cultureapp.domain.user.Administrator;
import cultureapp.domain.user.exception.AdminNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static cultureapp.common.AdministratorTestData.EXISTING_ADMIN_ID_3;
import static cultureapp.common.AdministratorTestData.EXISTING_ADMIN_ID_4;
import static cultureapp.common.AdministratorTestData.NON_EXISTING_ADMIN_ID_10;
import static cultureapp.common.CulturalOfferTestData.EXISTING_CULTURAL_OFFER_ID;
import static cultureapp.common.CulturalOfferTestData.INVALID_CULTURAL_OFFER_ID;
import static cultureapp.common.CulturalOfferTestData.NON_EXISTING_CULTURAL_OFFER_ID;
import static cultureapp.common.NewsTestData.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
@Transactional
public class NewsServiceIntegrationTest {
    private final int FIRST_PAGE = 0;
    private final int SECOND_PAGE = 1;
    private final int SIZE = 5;


    @Autowired
    private NewsService newsService;

    @Autowired
    private NewsRepository newsRepository;

    /*
    * add news
    * AddNewsCommand    valid
    * expected          added news
    */
    @Test
    public void givenAddNewsCommandIsValidThenAddNewsShouldSucceed() throws AdminNotFoundException, ImageNotFoundException, CulturalOfferNotFoundException, NewsAlreadyExistException {
        long newsCount = newsRepository.count();

        AddNewsUseCase.AddNewsCommand command = new AddNewsUseCase.AddNewsCommand(
                EXISTING_CULTURAL_OFFER_ID,
                VALID_NEWS_TITLE,
//                PRESENT_LOCAL_DATE_TIME,
//                EXISTING_ADMIN_ID_3,
                VALID_NEWS_TEXT,
                List.of(FREE_NEWS_IMAGE_FOR_ADD_ID_7, FREE_NEWS_IMAGE_FOR_ADD_ID_8)
        );
        newsService.addNews(command);
        assertEquals(newsCount + 1, newsRepository.count());

        List<News> allNews = newsRepository.findAll();
        News news = allNews.get(allNews.size() - 1);
        assertEquals(VALID_NEWS_TITLE, news.getTitle());
        assertEquals(VALID_NEWS_TEXT, news.getText());
        assertEquals(EXISTING_CULTURAL_OFFER_ID, news.getCulturalOffer().getId());
        assertEquals(EXISTING_ADMIN_ID_3, news.getAuthor().getId());

        // Rollback
        newsRepository.delete(news);
    }

    /*
    * add news
    * AddNewsCommand    invalid -> non existing cultural offer id
    * expected          exception
    */
    @Test(expected = CulturalOfferNotFoundException.class)
    public void givenNonExistingCulturalOfferIdThenAddNewsShouldFail() throws AdminNotFoundException, ImageNotFoundException, CulturalOfferNotFoundException, NewsAlreadyExistException {
        AddNewsUseCase.AddNewsCommand command = new AddNewsUseCase.AddNewsCommand(
                NON_EXISTING_CULTURAL_OFFER_ID,
                VALID_NEWS_TITLE,
//                PRESENT_LOCAL_DATE_TIME,
//                EXISTING_ADMIN_ID_3,
                VALID_NEWS_TEXT,
                // slike nece biti obrisane iz baze ovom operacijom
                List.of(FREE_NEWS_IMAGE_ID_9, FREE_NEWS_IMAGE_ID_10)
        );
        newsService.addNews(command);
    }

    /*
     * add news
     * AddNewsCommand    invalid -> invalid offer id
     * expected          exception
     */
    @Test(expected = ConstraintViolationException.class)
    public void givenInvalidCulturalOfferIdThenAddNewsShouldFail() throws AdminNotFoundException, ImageNotFoundException, CulturalOfferNotFoundException, NewsAlreadyExistException {
        AddNewsUseCase.AddNewsCommand command = new AddNewsUseCase.AddNewsCommand(
                INVALID_CULTURAL_OFFER_ID,
                VALID_NEWS_TITLE,
//                PRESENT_LOCAL_DATE_TIME,
//                EXISTING_ADMIN_ID_3,
                VALID_NEWS_TEXT,
                // slike nece biti obrisane iz baze ovom operacijom
                List.of(FREE_NEWS_IMAGE_ID_9, FREE_NEWS_IMAGE_ID_10)
        );
        newsService.addNews(command);
    }

    /*
     * add news
     * AddNewsCommand    invalid -> null offer id
     * expected          exception
     */
    @Test(expected = ConstraintViolationException.class)
    public void givenNullCulturalOfferIdThenAddNewsShouldFail() throws AdminNotFoundException, ImageNotFoundException, CulturalOfferNotFoundException, NewsAlreadyExistException {
        AddNewsUseCase.AddNewsCommand command = new AddNewsUseCase.AddNewsCommand(
                null,
                VALID_NEWS_TITLE,
//                PRESENT_LOCAL_DATE_TIME,
//                EXISTING_ADMIN_ID_3,
                VALID_NEWS_TEXT,
                // slike nece biti obrisane iz baze ovom operacijom
                List.of(FREE_NEWS_IMAGE_ID_9, FREE_NEWS_IMAGE_ID_10)
        );
        newsService.addNews(command);
    }


    /*
     * add news
     * AddNewsCommand    invalid -> blank title
     * expected          exception
     */
    @Test(expected = ConstraintViolationException.class)
    public void givenBlankTitleThenAddNewsShouldFail() {
        AddNewsUseCase.AddNewsCommand command = new AddNewsUseCase.AddNewsCommand(
                EXISTING_CULTURAL_OFFER_ID,
                INVALID_NEWS_TITLE,
//                PRESENT_LOCAL_DATE_TIME,
//                EXISTING_ADMIN_ID_3,
                VALID_NEWS_TEXT,
                // slike nece biti obrisane iz baze ovom operacijom
                List.of(FREE_NEWS_IMAGE_ID_9, FREE_NEWS_IMAGE_ID_10)
        );
    }


    /*
     * add news
     * AddNewsCommand    invalid -> null title
     * expected          exception
     */
    @Test(expected = ConstraintViolationException.class)
    public void givenNullTitleThenAddNewsShouldFail() {
        AddNewsUseCase.AddNewsCommand command = new AddNewsUseCase.AddNewsCommand(
                EXISTING_CULTURAL_OFFER_ID,
                null,
//                PRESENT_LOCAL_DATE_TIME,
//                EXISTING_ADMIN_ID_3,
                VALID_NEWS_TEXT,
                // slike nece biti obrisane iz baze ovom operacijom
                List.of(FREE_NEWS_IMAGE_ID_9, FREE_NEWS_IMAGE_ID_10)
        );
    }

    /*
     * add news
     * AddNewsCommand    invalid -> invalid news text
     * expected          exception
     */
    @Test(expected = ConstraintViolationException.class)
    public void givenInvalidNewsTextThenAddNewsShouldFail() {
        AddNewsUseCase.AddNewsCommand command = new AddNewsUseCase.AddNewsCommand(
                EXISTING_CULTURAL_OFFER_ID,
                VALID_NEWS_TITLE,
//                PRESENT_LOCAL_DATE_TIME,
//                EXISTING_ADMIN_ID_3,
                INVALID_NEWS_TEXT,
                // slike nece biti obrisane iz baze ovom operacijom
                List.of(FREE_NEWS_IMAGE_ID_9, FREE_NEWS_IMAGE_ID_10)
        );
    }

    /*
     * add news
     * AddNewsCommand    invalid -> null news text
     * expected          exception
     */
    @Test(expected = ConstraintViolationException.class)
    public void givenNullNewsTextThenAddNewsShouldFail() {
        AddNewsUseCase.AddNewsCommand command = new AddNewsUseCase.AddNewsCommand(
                EXISTING_CULTURAL_OFFER_ID,
                VALID_NEWS_TITLE,
//                PRESENT_LOCAL_DATE_TIME,
//                EXISTING_ADMIN_ID_3,
                null,
                // slike nece biti obrisane iz baze ovom operacijom
                List.of(FREE_NEWS_IMAGE_ID_9, FREE_NEWS_IMAGE_ID_10)
        );
    }

    /*
     * add news
     * AddNewsCommand    invalid -> taken images id
     * expected          exception
     */
    @Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
    public void givenTakenImagesIdThenAddNewsShouldFail() throws AdminNotFoundException, ImageNotFoundException, CulturalOfferNotFoundException, NewsAlreadyExistException {
        AddNewsUseCase.AddNewsCommand command = new AddNewsUseCase.AddNewsCommand(
                EXISTING_CULTURAL_OFFER_ID,
                VALID_NEWS_TITLE,
//                PRESENT_LOCAL_DATE_TIME,
//                EXISTING_ADMIN_ID_3,
                VALID_NEWS_TEXT,

                // exception here
                List.of(TAKEN_NEWS_IMAGE_ID_13, TAKEN_NEWS_IMAGE_ID_14)
        );
        newsService.addNews(command);
        newsRepository.count();
    }

    /*
     * add news
     * AddNewsCommand    invalid -> null images id
     * expected          exception
     */
    @Test(expected = ConstraintViolationException.class)
    public void givenNullImagesIdThenAddNewsShouldFail() throws AdminNotFoundException, ImageNotFoundException, CulturalOfferNotFoundException, NewsAlreadyExistException {
        AddNewsUseCase.AddNewsCommand command = new AddNewsUseCase.AddNewsCommand(
                EXISTING_CULTURAL_OFFER_ID,
                VALID_NEWS_TITLE,
//                PRESENT_LOCAL_DATE_TIME,
//                EXISTING_ADMIN_ID_3,
                VALID_NEWS_TEXT,
                null
        );
        newsService.addNews(command);
    }

    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////

    /*
     * get all news
     * GetNewsForOfferQuery      valid
     * expected                  non empty result
     */
    @Test
    public void givenValidCategoryIdAndFirstPageThenGetAllShouldReturnNonEmpty() throws CulturalOfferNotFoundException {
        GetNewsForOfferQueryHandler.GetNewsForOfferQuery query =
                new GetNewsForOfferQueryHandler.GetNewsForOfferQuery(
                        EXISTING_CULTURAL_OFFER_ID,
                        FIRST_PAGE,
                        SIZE
        );

        Slice<GetNewsForOfferQueryHandler.GetNewsForOfferDTO> result = newsService.handleGetNewsForOffer(query);

        assertEquals(NUM_OF_NON_ARCHIVED_NEWS_PER_OFFER.longValue(), result.getContent().size());
        assertFalse(result.hasNext());
        assertFalse(result.hasPrevious());
    }


    /*
     * get all news
     * GetNewsForOfferQuery      invalid -> second page is empty
     * expected                  empty result
     */
    @Test
    public void givenValidCategoryIdAndSecondPageThenGetAllShouldReturnEmpty() throws CulturalOfferNotFoundException {
        GetNewsForOfferQueryHandler.GetNewsForOfferQuery query =
                new GetNewsForOfferQueryHandler.GetNewsForOfferQuery(
                        EXISTING_CULTURAL_OFFER_ID,
                        SECOND_PAGE,
                        SIZE
                );

        Slice<GetNewsForOfferQueryHandler.GetNewsForOfferDTO> result = newsService.handleGetNewsForOffer(query);

        assertTrue(result.getContent().isEmpty());
        assertFalse(result.hasNext());
        assertTrue(result.hasPrevious());
    }


    /*
     * get all news
     * GetNewsForOfferQuery      invalid -> non existing cultural offer id
     * expected                  exception
     */
    @Test(expected = CulturalOfferNotFoundException.class)
    public void givenInvalidOfferIdAndFirstPageThenGetAllShouldFail() throws CulturalOfferNotFoundException {
        GetNewsForOfferQueryHandler.GetNewsForOfferQuery query =
                new GetNewsForOfferQueryHandler.GetNewsForOfferQuery(
                        NON_EXISTING_CULTURAL_OFFER_ID,
                        FIRST_PAGE,
                        SIZE
                );

        Slice<GetNewsForOfferQueryHandler.GetNewsForOfferDTO> result = newsService.handleGetNewsForOffer(query);
    }

    /*
     * get all news
     * GetNewsForOfferQuery      invalid -> null cultural offer id
     * expected                  exception
     */
    @Test(expected = ConstraintViolationException.class)
    public void givenNullOfferIdAndFirstPageThenGetAllShouldFail() {
        GetNewsForOfferQueryHandler.GetNewsForOfferQuery query =
                new GetNewsForOfferQueryHandler.GetNewsForOfferQuery(
                        null,
                        FIRST_PAGE,
                        SIZE
                );
    }

    /*
     * get all news
     * GetNewsForOfferQuery      invalid -> null page
     * expected                  exception
     */
    @Test(expected = ConstraintViolationException.class)
    public void givenNullPageThenGetAllShouldFail() {
        GetNewsForOfferQueryHandler.GetNewsForOfferQuery query =
                new GetNewsForOfferQueryHandler.GetNewsForOfferQuery(
                        EXISTING_CULTURAL_OFFER_ID,
                        null,
                        SIZE
                );
    }

    /*
     * get all news
     * GetNewsForOfferQuery      invalid -> null size
     * expected                  exception
     */
    @Test(expected = ConstraintViolationException.class)
    public void givenNullSizeThenGetAllShouldFail() {
        GetNewsForOfferQueryHandler.GetNewsForOfferQuery query =
                new GetNewsForOfferQueryHandler.GetNewsForOfferQuery(
                        EXISTING_CULTURAL_OFFER_ID,
                        FIRST_PAGE,
                        null
                );
    }

    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////

    /*
     * get news by id
     * GetNewsForOfferQuery      valid
     * expected                  success
     */
    @Test
    public void givenNewsIdAndOfferIdAreValidThenGetShouldSucceed() throws NewsNotFoundException {
        GetNewsByIdQueryHandler.GetNewsByIdQuery query =
                new GetNewsByIdQueryHandler.GetNewsByIdQuery(
                        EXISTING_NEWS_ID_1_FOR_OFFER_ID_1,
                        EXISTING_CULTURAL_OFFER_ID
                );

        GetNewsByIdQueryHandler.GetNewsByIdDTO result = newsService.handleGetNewsById(query);

        assertEquals(EXISTING_NEWS_ID_1_FOR_OFFER_ID_1, result.getId());
        assertEquals(EXISTING_CULTURAL_OFFER_ID, result.getCulturalOfferId());
        assertEquals(EXISTING_ADMIN_ID_3, result.getAuthorId());
    }

    /*
     * get news by id
     * GetNewsForOfferQuery      invalid -> non existing news id
     * expected                  exception
     */
    @Test(expected = NewsNotFoundException.class)
    public void givenInvalidNewsIdThenGetShouldRaiseException() throws NewsNotFoundException {
        GetNewsByIdQueryHandler.GetNewsByIdQuery query =
                new GetNewsByIdQueryHandler.GetNewsByIdQuery(
                        NON_EXISTING_NEWS_ID_FOR_OFFER_ID_1,
                        EXISTING_CULTURAL_OFFER_ID
                );

        GetNewsByIdQueryHandler.GetNewsByIdDTO result = newsService.handleGetNewsById(query);
    }

    /*
     * get news by id
     * GetNewsForOfferQuery      invalid -> non existing offer id
     * expected                  exception
     */
    @Test(expected = NewsNotFoundException.class)
    public void givenInvalidOfferIdThenGetShouldRaiseException() throws NewsNotFoundException {
        GetNewsByIdQueryHandler.GetNewsByIdQuery query =
                new GetNewsByIdQueryHandler.GetNewsByIdQuery(
                        EXISTING_NEWS_ID_1_FOR_OFFER_ID_1,
                        NON_EXISTING_CULTURAL_OFFER_ID
                );

        GetNewsByIdQueryHandler.GetNewsByIdDTO result = newsService.handleGetNewsById(query);
    }

    /*
     * get news by id
     * GetNewsForOfferQuery      invalid -> null offer id
     * expected                  exception
     */
    @Test(expected = ConstraintViolationException.class)
    public void givenNullOfferIdThenGetShouldRaiseException() {
        GetNewsByIdQueryHandler.GetNewsByIdQuery query =
                new GetNewsByIdQueryHandler.GetNewsByIdQuery(
                        EXISTING_NEWS_ID_1_FOR_OFFER_ID_1,
                        null
                );
    }

    /*
     * get news by id
     * GetNewsForOfferQuery      invalid -> null news id
     * expected                  exception
     */
    @Test(expected = ConstraintViolationException.class)
    public void givenNullNewsIdThenGetShouldRaiseException() {
        GetNewsByIdQueryHandler.GetNewsByIdQuery query =
                new GetNewsByIdQueryHandler.GetNewsByIdQuery(
                        null,
                        EXISTING_CULTURAL_OFFER_ID
                );
    }

    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////

    public News getRollBackNews(Long id, Long offerId) {
        Optional<News> newsOptional = newsRepository.findByIdAndCulturalOfferIdAndArchivedFalse(id, offerId);
        return newsOptional.get();
    }

    /*
     * update news
     * UpdateNewsCommand         valid -> title changed
     * expected                  success
     */
    @Test
    public void givenValidNewsTitleThenUpdateShouldSucceed() throws NewsNotFoundException, ImageNotFoundException, AdminNotFoundException, CulturalOfferNotFoundException, NewsAlreadyExistException {
        News rollBackNews = getRollBackNews(EXISTING_NEWS_ID_1_FOR_OFFER_ID_1, EXISTING_CULTURAL_OFFER_ID);
        String rollBackTitle = rollBackNews.getTitle();

        UpdateNewsUseCase.UpdateNewsCommand command =
                new UpdateNewsUseCase.UpdateNewsCommand(
                        rollBackNews.getId(),                       // news id
                        rollBackNews.getCulturalOffer().getId(),    // offer id
                        NON_EXISTING_NEWS_TITLE,                    // news title
//                        rollBackNews.getPostedDate(),               // localDateTime
//                        rollBackNews.getAuthor().getId(),           // author id
                        rollBackNews.getText(),                     // news text
                        rollBackNews.getImages()                    // images
                                .stream()
                                .map(Image::getId)
                                .collect(Collectors.toList())
                );
        newsService.updateNews(command);

        Optional<News> updatedNewsOptional = newsRepository.findByIdAndCulturalOfferIdAndArchivedFalse(
                EXISTING_NEWS_ID_1_FOR_OFFER_ID_1,
                EXISTING_CULTURAL_OFFER_ID
        );
        assertTrue(updatedNewsOptional.isPresent());


        News updatedNews = updatedNewsOptional.get();

        assertEquals(rollBackNews.getId(), updatedNews.getId());
        assertEquals(rollBackNews.getCulturalOffer().getId(), updatedNews.getCulturalOffer().getId());
        assertEquals(NON_EXISTING_NEWS_TITLE, updatedNews.getTitle());


        // Rollback
        updatedNews.setTitle(rollBackTitle);
        newsRepository.save(updatedNews);
    }

    /*
     * update news
     * UpdateNewsCommand         invalid -> invalid title
     * expected                  exception
     */
    @Test(expected = ConstraintViolationException.class)
    public void givenInvalidNewsTitleThenUpdateShouldRaiseException() {
        UpdateNewsUseCase.UpdateNewsCommand command =
                new UpdateNewsUseCase.UpdateNewsCommand(
                        EXISTING_NEWS_ID_1_FOR_OFFER_ID_1,          // news id
                        EXISTING_CULTURAL_OFFER_ID,                 // offer id

                        // exception here
                        INVALID_NEWS_TITLE,                         // news title

//                        PRESENT_LOCAL_DATE_TIME,                    // localDateTime
//                        EXISTING_ADMIN_ID_3,                        // author id
                        EXISTING_NEWS_TEXT,                         // news text
                        List.of(FREE_NEWS_IMAGE_ID_9, FREE_NEWS_IMAGE_ID_10)                    // images
                );
    }

    /*
     * update news
     * UpdateNewsCommand         invalid -> null title
     * expected                  exception
     */
    @Test(expected = ConstraintViolationException.class)
    public void givenNullTitleThenUpdateShouldRaiseException() {
        UpdateNewsUseCase.UpdateNewsCommand command =
                new UpdateNewsUseCase.UpdateNewsCommand(
                        EXISTING_NEWS_ID_1_FOR_OFFER_ID_1,          // news id
                        EXISTING_CULTURAL_OFFER_ID,                 // offer id

                        // exception here
                        null,                         // news title

//                        PRESENT_LOCAL_DATE_TIME,                    // localDateTime
//                        EXISTING_ADMIN_ID_3,                        // author id
                        EXISTING_NEWS_TEXT,                         // news text
                        List.of(FREE_NEWS_IMAGE_ID_9, FREE_NEWS_IMAGE_ID_10)                    // images
                );
    }

    /*
     * update news
     * UpdateNewsCommand         valid -> text changed
     * expected                  success
     */
    @Test
    public void givenValidTextThenUpdateShouldSucceed() throws NewsNotFoundException, ImageNotFoundException, AdminNotFoundException, CulturalOfferNotFoundException, NewsAlreadyExistException {
        News rollBackNews = getRollBackNews(EXISTING_NEWS_ID_1_FOR_OFFER_ID_1, EXISTING_CULTURAL_OFFER_ID);
        String rollBackText = rollBackNews.getText();

        UpdateNewsUseCase.UpdateNewsCommand command =
                new UpdateNewsUseCase.UpdateNewsCommand(
                        rollBackNews.getId(),                       // news id
                        rollBackNews.getCulturalOffer().getId(),    // offer id
                        rollBackNews.getTitle(),                    // news title
//                        rollBackNews.getPostedDate(),               // localDateTime
//                        rollBackNews.getAuthor().getId(),           // author id
                        NON_EXISTING_NEWS_TEXT,                     // news text
                        rollBackNews.getImages()                    // images
                                .stream()
                                .map(Image::getId)
                                .collect(Collectors.toList())
                );
        newsService.updateNews(command);

        Optional<News> updatedNewsOptional = newsRepository.findByIdAndCulturalOfferIdAndArchivedFalse(
                EXISTING_NEWS_ID_1_FOR_OFFER_ID_1,
                EXISTING_CULTURAL_OFFER_ID
        );
        assertTrue(updatedNewsOptional.isPresent());


        News updatedNews = updatedNewsOptional.get();

        assertEquals(rollBackNews.getId(), updatedNews.getId());
        assertEquals(rollBackNews.getCulturalOffer().getId(), updatedNews.getCulturalOffer().getId());
        assertEquals(NON_EXISTING_NEWS_TEXT, updatedNews.getText());


        // Rollback
        updatedNews.setText(rollBackText);
        newsRepository.save(updatedNews);
    }

    /*
     * update news
     * UpdateNewsCommand         invalid -> invalid text
     * expected                  exception
     */
    @Test(expected = ConstraintViolationException.class)
    public void givenInvalidTextThenUpdateShouldSucceed() {
        UpdateNewsUseCase.UpdateNewsCommand command =
                new UpdateNewsUseCase.UpdateNewsCommand(
                        EXISTING_NEWS_ID_1_FOR_OFFER_ID_1,                       // news id
                        EXISTING_CULTURAL_OFFER_ID,    // offer id
                        EXISTING_NEWS_TITLE,                    // news title
//                        PRESENT_LOCAL_DATE_TIME,               // localDateTime
//                        EXISTING_ADMIN_ID_3,           // author id

                        // exception here
                        INVALID_NEWS_TEXT,                     // news text

                        List.of(FREE_NEWS_IMAGE_ID_9, FREE_NEWS_IMAGE_ID_10)
                );
    }

    /*
     * update news
     * UpdateNewsCommand         invalid -> null text
     * expected                  exception
     */
    @Test(expected = ConstraintViolationException.class)
    public void givenNullTextThenUpdateShouldSucceed() {
        UpdateNewsUseCase.UpdateNewsCommand command =
                new UpdateNewsUseCase.UpdateNewsCommand(
                        EXISTING_NEWS_ID_1_FOR_OFFER_ID_1,                       // news id
                        EXISTING_CULTURAL_OFFER_ID,    // offer id
                        EXISTING_NEWS_TITLE,                    // news title
//                        PRESENT_LOCAL_DATE_TIME,               // localDateTime
//                        EXISTING_ADMIN_ID_3,           // author id

                        // exception here
                        null,                     // news text

                        List.of(FREE_NEWS_IMAGE_ID_9, FREE_NEWS_IMAGE_ID_10)
                );
    }

    /*
     * update news
     * UpdateNewsCommand         valid -> images changed
     * expected                  success
     */
    @Test
    public void givenValidImagesThenUpdateShouldSucceed() throws NewsNotFoundException, ImageNotFoundException, AdminNotFoundException, CulturalOfferNotFoundException, NewsAlreadyExistException {
        News rollBackNews = getRollBackNews(EXISTING_NEWS_ID_1_FOR_OFFER_ID_1, EXISTING_CULTURAL_OFFER_ID);
        List<Image> rollBackImages = rollBackNews.getImages();

        UpdateNewsUseCase.UpdateNewsCommand command =
                new UpdateNewsUseCase.UpdateNewsCommand(
                        rollBackNews.getId(),                       // news id
                        rollBackNews.getCulturalOffer().getId(),    // offer id
                        rollBackNews.getTitle(),                    // news title
//                        rollBackNews.getPostedDate(),               // localDateTime
//                        rollBackNews.getAuthor().getId(),           // author id
                        rollBackNews.getText(),                     // news text
                        List.of(FREE_NEWS_IMAGE_FOR_UPDATE_ID_11, FREE_NEWS_IMAGE_FOR_UPDATE_ID_12)                    // images
                );
        newsService.updateNews(command);

        Optional<News> updatedNewsOptional = newsRepository.findByIdAndCulturalOfferIdAndArchivedFalse(
                EXISTING_NEWS_ID_1_FOR_OFFER_ID_1,
                EXISTING_CULTURAL_OFFER_ID
        );
        assertTrue(updatedNewsOptional.isPresent());


        News updatedNews = updatedNewsOptional.get();

        assertEquals(rollBackNews.getId(), updatedNews.getId());
        assertEquals(rollBackNews.getCulturalOffer().getId(), updatedNews.getCulturalOffer().getId());
        assertEquals(FREE_NEWS_IMAGE_FOR_UPDATE_ID_11, updatedNews.getImages().get(0).getId());
        assertEquals(FREE_NEWS_IMAGE_FOR_UPDATE_ID_12, updatedNews.getImages().get(1).getId());


        // Rollback
        updatedNews.setImages(rollBackImages);
        newsRepository.save(updatedNews);
    }

    /*
     * update news
     * UpdateNewsCommand         invalid -> taken images
     * expected                  exception
     */
    @Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
    public void givenTakenImagesThenUpdateShouldRaiseException() throws NewsNotFoundException, ImageNotFoundException, AdminNotFoundException, CulturalOfferNotFoundException, NewsAlreadyExistException {
        UpdateNewsUseCase.UpdateNewsCommand command =
                new UpdateNewsUseCase.UpdateNewsCommand(
                        EXISTING_NEWS_ID_1_FOR_OFFER_ID_1,                       // news id
                        EXISTING_CULTURAL_OFFER_ID,    // offer id
                        EXISTING_NEWS_TITLE,                    // news title
//                        PRESENT_LOCAL_DATE_TIME,               // localDateTime
//                        EXISTING_ADMIN_ID_3,           // author id
                        EXISTING_NEWS_TEXT,                     // news text

                        // Exception here
                        List.of(TAKEN_NEWS_IMAGE_ID_13, TAKEN_NEWS_IMAGE_ID_14)              // images
                );
        newsService.updateNews(command);
        newsRepository.count();
    }

    /*
     * update news
     * UpdateNewsCommand         invalid -> null images
     * expected                  exception
     */
    @Test(expected = ConstraintViolationException.class)
    public void givenNullImagesThenUpdateShouldRaiseException() {
        UpdateNewsUseCase.UpdateNewsCommand command =
                new UpdateNewsUseCase.UpdateNewsCommand(
                        EXISTING_NEWS_ID_1_FOR_OFFER_ID_1,                       // news id
                        EXISTING_CULTURAL_OFFER_ID,    // offer id
                        EXISTING_NEWS_TITLE,                    // news title
//                        PRESENT_LOCAL_DATE_TIME,               // localDateTime
//                        EXISTING_ADMIN_ID_3,           // author id
                        EXISTING_NEWS_TEXT,                     // news text

                        // Exception here
                        null            // images
                );
    }

    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////

    /*
     * delete news
     * news id                   valid
     * cultural offer id         valid
     * expected                  success
     */
    @Test
    public void givenNewsIdAndCulturalOfferIdAreValidThenDeleteShouldSucceed() {
        Pageable pageable = PageRequest.of(FIRST_PAGE, SIZE);
        int countBefore = newsRepository.findAllByCulturalOfferIdAndArchivedFalse(EXISTING_CULTURAL_OFFER_ID, pageable).getNumberOfElements();


        Optional<News> newsToDeleteOptional = newsRepository.findByIdAndCulturalOfferIdAndArchivedFalse(EXISTING_NEWS_ID_1_FOR_OFFER_ID_1, EXISTING_CULTURAL_OFFER_ID);
        News newsToDelete = newsToDeleteOptional.get();

        newsToDelete.setArchived(true);
        newsRepository.save(newsToDelete);

        int countAfter = newsRepository.findAllByCulturalOfferIdAndArchivedFalse(EXISTING_CULTURAL_OFFER_ID, pageable).getNumberOfElements();


        assertEquals(countBefore, countAfter + 1);


        // Rollback
        newsToDelete.setArchived(false);
        newsRepository.save(newsToDelete);
    }

    /*
     * delete news
     * news id                   invalid -> non existing id
     * cultural offer id         valid
     * expected                  success
     */
    @Test
    public void givenInvalidNewsIdThenDeleteShouldRaiseException() {
        Optional<News> newsToDeleteOptional = newsRepository.findByIdAndCulturalOfferIdAndArchivedFalse(NON_EXISTING_NEWS_ID_FOR_OFFER_ID_1, EXISTING_CULTURAL_OFFER_ID);
        assertTrue(newsToDeleteOptional.isEmpty());
    }

    /*
     * delete news
     * news id                   valid
     * cultural offer id         invalid -> non existing offer id
     * expected                  success
     */
    @Test
    public void givenInvalidOfferIdThenDeleteShouldRaiseException() {
        Optional<News> newsToDeleteOptional = newsRepository.findByIdAndCulturalOfferIdAndArchivedFalse(EXISTING_NEWS_ID_1_FOR_OFFER_ID_1, NON_EXISTING_CULTURAL_OFFER_ID);
        assertTrue(newsToDeleteOptional.isEmpty());
    }

    /*
     * delete news
     * news id                   null
     * cultural offer id         valid
     * expected                  success
     */
    @Test
    public void givenNullNewsIdThenDeleteShouldRaiseException() {
        Optional<News> newsToDeleteOptional = newsRepository.findByIdAndCulturalOfferIdAndArchivedFalse(null, EXISTING_CULTURAL_OFFER_ID);
        assertTrue(newsToDeleteOptional.isEmpty());
    }

    /*
     * delete news
     * news id                   valid
     * cultural offer id         null
     * expected                  success
     */
    @Test
    public void givenNullOfferIdThenDeleteShouldRaiseException() {
        Optional<News> newsToDeleteOptional = newsRepository.findByIdAndCulturalOfferIdAndArchivedFalse(EXISTING_NEWS_ID_1_FOR_OFFER_ID_1, null);
        assertTrue(newsToDeleteOptional.isEmpty());
    }
}
