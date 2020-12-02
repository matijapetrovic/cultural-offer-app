package cultureapp.domain.news.exception;

public class NewsNotFoundException extends Exception {
    public NewsNotFoundException(Long newsId) {
        super(String.format("News with id %d not found", newsId));
    }

    public NewsNotFoundException(Long newsId, Long culturalOfferId) {
        super(String.format("News with id %d and cultural offer not found", newsId));
    }
}
