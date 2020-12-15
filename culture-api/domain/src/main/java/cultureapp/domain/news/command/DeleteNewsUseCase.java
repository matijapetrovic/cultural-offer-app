package cultureapp.domain.news.command;

import cultureapp.domain.news.exception.NewsNotFoundException;

import javax.validation.constraints.Positive;

public interface DeleteNewsUseCase {
    void deleteNews(@Positive Long culturalOfferId, @Positive Long id) throws NewsNotFoundException;
}