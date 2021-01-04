package cultureapp.domain.reply.exception;

public class ReplyAlreadyExistException extends Exception {
    public ReplyAlreadyExistException(Long reviewId, Long culturalOfferId) {
        super(String.format("Review with id %d, in cultural offer with id %d, already has reply.", reviewId, culturalOfferId));
    }

    public ReplyAlreadyExistException(Long reviewId, Long culturalOfferId, Throwable cause) {
        super(String.format("Review with id %d, in cultural offer with id %d, already has reply.", reviewId, culturalOfferId), cause);
    }
}
