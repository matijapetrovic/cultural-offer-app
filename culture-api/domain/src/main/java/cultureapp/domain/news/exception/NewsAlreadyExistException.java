package cultureapp.domain.news.exception;

public class NewsAlreadyExistException extends Exception {
    public NewsAlreadyExistException(String name) {
        super(String.format("News with name %s already exists", name));
    }
}
