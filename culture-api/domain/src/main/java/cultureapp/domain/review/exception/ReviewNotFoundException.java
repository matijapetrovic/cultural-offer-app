package cultureapp.domain.review.exception;

public class ReviewNotFoundException extends Exception {
    public ReviewNotFoundException(Long reviewId, Long culturalOfferId) {
        super(String.format("Review with id %d not found in cultural offer with id %d", reviewId, culturalOfferId));
    }
}
