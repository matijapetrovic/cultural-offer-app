package cultureapp.domain.reply;

import cultureapp.domain.reply.command.AddReplyUseCase;
import cultureapp.domain.reply.exception.ReplyAlreadyExistException;
import cultureapp.domain.review.Review;
import cultureapp.domain.review.ReviewRepository;
import cultureapp.domain.review.exception.ReviewNotFoundException;
import cultureapp.domain.user.exception.AdminNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static cultureapp.common.AdministratorTestData.EXISTING_ADMIN_ID_3;
import static cultureapp.common.AdministratorTestData.NON_EXISTING_ADMIN_ID_10;
import static cultureapp.common.CulturalOfferTestData.NON_EXISTING_CULTURAL_OFFER_ID;
import static cultureapp.common.ReplyTestData.CULTURAL_OFFER_ID_1_WITH_REVIEWS;
import static cultureapp.common.ReplyTestData.INVALID_COMMENT;
import static cultureapp.common.ReplyTestData.REVIEW_ID_2_FOR_OFFER_ID_1_WITH_REPLY;
import static cultureapp.common.ReplyTestData.REVIEW_ID_3_FOR_OFFER_ID_1_WITH_NO_REPLY;
import static cultureapp.common.ReplyTestData.VALID_COMMENT;
import static cultureapp.common.ReviewTestData.NON_EXISTING_REVIEW_ID_FOR_CULTURAL_OFFER_ID_1;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
@Transactional
public class ReplyServiceIntegrationTest {
    @Autowired
    private ReplyService replyService;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    /*
    * add reply
    * AddReplyCommand           valid
    * except                    success
    */
    @Test
    public void givenValidCommandThenAddReplyShouldSucceed() throws AdminNotFoundException, ReviewNotFoundException, ReplyAlreadyExistException {
        Optional<Review> reviewOptional = reviewRepository.findByIdAndCulturalOfferIdAndArchivedFalse(
                REVIEW_ID_3_FOR_OFFER_ID_1_WITH_NO_REPLY,
                CULTURAL_OFFER_ID_1_WITH_REVIEWS
        );
        Review review = reviewOptional.get();


        long replyCount = replyRepository.count();

        AddReplyUseCase.AddReplyCommand command = new AddReplyUseCase.AddReplyCommand(
                CULTURAL_OFFER_ID_1_WITH_REVIEWS,
                REVIEW_ID_3_FOR_OFFER_ID_1_WITH_NO_REPLY,
//                EXISTING_ADMIN_ID_3,
                VALID_COMMENT
        );
        replyService.addReply(command);
        assertEquals(replyCount + 1, replyRepository.count());

        List<Reply> allReplies = replyRepository.findAll();
        Reply reply = allReplies.get(allReplies.size() - 1);
        assertEquals(VALID_COMMENT, reply.getComment());
        assertEquals(EXISTING_ADMIN_ID_3, reply.getAdministrator().getId());

        assertEquals(review.getId(), reply.getReview().getId());
        assertEquals(review.getReply().getId(), reply.getId());
        assertEquals(review.getCulturalOffer().getId(), reply.getCulturalOffer());

        // Rollback
        review.setReply(null);
        reviewRepository.save(review);
        replyRepository.delete(reply);
    }

    /*
     * add reply
     * AddReplyCommand           invalid -> non existing cultural offer id
     * except                    exception
     */
    @Test(expected = ReviewNotFoundException.class)
    public void givenNonExistingCulturalOfferIdThenAddReplyShouldFail() throws AdminNotFoundException, ReviewNotFoundException, ReplyAlreadyExistException {
        AddReplyUseCase.AddReplyCommand command = new AddReplyUseCase.AddReplyCommand(
                NON_EXISTING_CULTURAL_OFFER_ID,
                REVIEW_ID_3_FOR_OFFER_ID_1_WITH_NO_REPLY,
//                EXISTING_ADMIN_ID_3,
                VALID_COMMENT
        );
        replyService.addReply(command);
    }

    /*
     * add reply
     * AddReplyCommand           invalid -> review already has reply
     * except                    exception
     */
    @Test(expected = ReplyAlreadyExistException.class)
    public void givenReviewWithWhichHasReplyThenAddReplyShouldFail() throws AdminNotFoundException, ReviewNotFoundException, ReplyAlreadyExistException {
        AddReplyUseCase.AddReplyCommand command = new AddReplyUseCase.AddReplyCommand(
                CULTURAL_OFFER_ID_1_WITH_REVIEWS,
                REVIEW_ID_2_FOR_OFFER_ID_1_WITH_REPLY,
//                EXISTING_ADMIN_ID_3,
                VALID_COMMENT
        );
        replyService.addReply(command);
    }

    /*
     * add reply
     * AddReplyCommand           invalid -> non existing review id
     * except                    exception
     */
    @Test(expected = ReviewNotFoundException.class)
    public void givenInvalidReviewIdThenAddReplyShouldFail() throws AdminNotFoundException, ReviewNotFoundException, ReplyAlreadyExistException {
        AddReplyUseCase.AddReplyCommand command = new AddReplyUseCase.AddReplyCommand(
                CULTURAL_OFFER_ID_1_WITH_REVIEWS,
                NON_EXISTING_REVIEW_ID_FOR_CULTURAL_OFFER_ID_1,
//                EXISTING_ADMIN_ID_3,
                VALID_COMMENT
        );
        replyService.addReply(command);
    }

    /*
     * add reply
     * AddReplyCommand           invalid -> non existing admin id
     * except                    exception
     */
    @Test(expected = AdminNotFoundException.class)
    public void givenInvalidAdminIdThenAddReplyShouldFail() throws AdminNotFoundException, ReviewNotFoundException, ReplyAlreadyExistException {
        AddReplyUseCase.AddReplyCommand command = new AddReplyUseCase.AddReplyCommand(
                CULTURAL_OFFER_ID_1_WITH_REVIEWS,
                REVIEW_ID_2_FOR_OFFER_ID_1_WITH_REPLY,
//                NON_EXISTING_ADMIN_ID_10,
                VALID_COMMENT
        );
        replyService.addReply(command);
    }

    /*
     * add reply
     * AddReplyCommand           invalid -> invalid comment
     * except                    exception
     */
    @Test(expected = ConstraintViolationException.class)
    public void givenInvalidCommentThenAddReplyShouldFail() throws AdminNotFoundException, ReviewNotFoundException, ReplyAlreadyExistException {
        AddReplyUseCase.AddReplyCommand command = new AddReplyUseCase.AddReplyCommand(
                CULTURAL_OFFER_ID_1_WITH_REVIEWS,
                REVIEW_ID_2_FOR_OFFER_ID_1_WITH_REPLY,
//                EXISTING_ADMIN_ID_3,
                INVALID_COMMENT
        );
        replyService.addReply(command);
    }

    /*
     * add reply
     * AddReplyCommand           invalid -> null offer id
     * except                    exception
     */
    @Test(expected = ConstraintViolationException.class)
    public void givenNullOfferIdThenAddReplyShouldFail() throws AdminNotFoundException, ReviewNotFoundException, ReplyAlreadyExistException {
        AddReplyUseCase.AddReplyCommand command = new AddReplyUseCase.AddReplyCommand(
                null,
                REVIEW_ID_2_FOR_OFFER_ID_1_WITH_REPLY,
//                EXISTING_ADMIN_ID_3,
                VALID_COMMENT
        );
        replyService.addReply(command);
    }

    /*
     * add reply
     * AddReplyCommand           invalid -> null review id
     * except                    exception
     */
    @Test(expected = ConstraintViolationException.class)
    public void givenNullReviewIdThenAddReplyShouldFail() throws AdminNotFoundException, ReviewNotFoundException, ReplyAlreadyExistException {
        AddReplyUseCase.AddReplyCommand command = new AddReplyUseCase.AddReplyCommand(
                CULTURAL_OFFER_ID_1_WITH_REVIEWS,
                null,
//                EXISTING_ADMIN_ID_3,
                VALID_COMMENT
        );
        replyService.addReply(command);
    }

    /*
     * add reply
     * AddReplyCommand           invalid -> null admin id
     * except                    exception
     */
    @Test(expected = ConstraintViolationException.class)
    public void givenNullAdminIdThenAddReplyShouldFail() throws AdminNotFoundException, ReviewNotFoundException, ReplyAlreadyExistException {
        AddReplyUseCase.AddReplyCommand command = new AddReplyUseCase.AddReplyCommand(
                CULTURAL_OFFER_ID_1_WITH_REVIEWS,
                REVIEW_ID_2_FOR_OFFER_ID_1_WITH_REPLY,
//                null,
                VALID_COMMENT
        );
        replyService.addReply(command);
    }

    /*
     * add reply
     * AddReplyCommand           invalid -> null comment
     * except                    exception
     */
    @Test(expected = ConstraintViolationException.class)
    public void givenNullCommentThenAddReplyShouldFail() throws AdminNotFoundException, ReviewNotFoundException, ReplyAlreadyExistException {
        AddReplyUseCase.AddReplyCommand command = new AddReplyUseCase.AddReplyCommand(
                CULTURAL_OFFER_ID_1_WITH_REVIEWS,
                REVIEW_ID_2_FOR_OFFER_ID_1_WITH_REPLY,
//                EXISTING_ADMIN_ID_3,
                null
        );
        replyService.addReply(command);
    }
}
