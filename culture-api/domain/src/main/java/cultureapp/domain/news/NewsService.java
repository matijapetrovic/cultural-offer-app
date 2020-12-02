package cultureapp.domain.news;

import cultureapp.domain.administrator.Administrator;
import cultureapp.domain.administrator.AdministratorRepository;
import cultureapp.domain.administrator.exception.AdminNotFoundException;
import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.Image;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.news.command.AddNewsUseCase;
import cultureapp.domain.news.command.DeleteNewsUseCase;
import cultureapp.domain.news.exception.NewsNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Positive;
import java.io.File;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NewsService implements
        AddNewsUseCase,
        DeleteNewsUseCase {
    private final NewsRepository newsRepository;
    private final CulturalOfferRepository culturalOfferRepository;
    private final AdministratorRepository administratorRepository;

    @Override
    public void addNews(AddNewsCommand command) throws CulturalOfferNotFoundException, AdminNotFoundException {
        CulturalOffer offer = culturalOfferRepository.findByIdAndArchivedFalse(command.getCulturalOfferID())
                .orElseThrow(() -> new CulturalOfferNotFoundException(command.getCulturalOfferID()));

        Administrator admin = administratorRepository.findById(command.getAuthorID())
                .orElseThrow(() -> new AdminNotFoundException(command.getAuthorID()));

        News news = News.of(
                offer,
                command.getName(),
                command.getPostedDate(),
                admin,
                command.getText(),
                false,
                null
        );

        newsRepository.save(news);
    }

    @Override
    public void deleteNews(@Positive Long culturalOfferId, @Positive Long id) throws NewsNotFoundException {
        News news = newsRepository.findByIdAndCulturalOfferIdAndArchivedFalse(id, culturalOfferId)
                .orElseThrow(() -> new NewsNotFoundException(id, culturalOfferId));

        news.archive();

        newsRepository.save(news);
    }
}
