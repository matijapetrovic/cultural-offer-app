package cultureapp.domain.news.exception;

public class NewsNotFoundException extends Exception {
    public NewsNotFoundException(Long newsId) {
        super(String.format("News with id %d not found", newsId));
    }
}
