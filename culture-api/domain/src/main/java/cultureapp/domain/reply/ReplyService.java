package cultureapp.domain.reply;

import cultureapp.domain.account.Account;
import cultureapp.domain.core.AuthenticationService;
import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.reply.command.AddReplyUseCase;
import cultureapp.domain.reply.exception.ReplyAlreadyExistException;
import cultureapp.domain.review.Review;
import cultureapp.domain.review.ReviewRepository;
import cultureapp.domain.review.exception.ReviewNotFoundException;
import cultureapp.domain.user.Administrator;
import cultureapp.domain.user.AdministratorRepository;
import cultureapp.domain.user.exception.AdminNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReplyService implements AddReplyUseCase {
    private final ReplyRepository replyRepository;
    private final ReviewRepository reviewRepository;
    private final AdministratorRepository adminRepository;
    private final AuthenticationService authenticationService;

//    private final CulturalOfferRepository offerRepository;

    @Override
    public void addReply(AddReplyCommand command) throws ReviewNotFoundException, AdminNotFoundException, ReplyAlreadyExistException {
//        CulturalOffer culturalOffer = offerRepository.findByIdAndArchivedFalse(command.getCulturalOfferId())
//                .orElseThrow(() -> new CulturalOfferNotFoundException(command.getCulturalOfferId()));

        Review review = reviewRepository.findByIdAndCulturalOfferIdAndArchivedFalse(command.getReviewId(), command.getCulturalOfferId())
                .orElseThrow(() -> new ReviewNotFoundException(command.getReviewId(), command.getCulturalOfferId()));

        Account account = authenticationService.getAuthenticated();
        Administrator admin = adminRepository.findById(account.getId())
                .orElseThrow(() -> new AdminNotFoundException(account.getId()));

        Reply reply = Reply.of(review, command.getComment(), admin);

        boolean success = review.addReply(reply);
        if (!success)
            throw new ReplyAlreadyExistException(reply.getReview().getId(), reply.getCulturalOffer());

        replyRepository.save(reply);
        reviewRepository.save(review);
    }
}
